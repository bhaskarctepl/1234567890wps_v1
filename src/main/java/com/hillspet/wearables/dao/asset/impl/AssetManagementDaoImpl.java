package com.hillspet.wearables.dao.asset.impl;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.IdTokenCredentials;
import com.google.auth.oauth2.IdTokenProvider;
import com.hillspet.wearables.common.constants.SQLConstants;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.asset.AssetManagementDao;
import com.hillspet.wearables.dto.Asset;
import com.hillspet.wearables.dto.AssetHistory;
import com.hillspet.wearables.dto.AssetType;
import com.hillspet.wearables.dto.BulkAssetUploadDeviceInfo;
import com.hillspet.wearables.dto.DeviceFirmwareDetails;
import com.hillspet.wearables.dto.DeviceInfo;
import com.hillspet.wearables.dto.DeviceModel;
import com.hillspet.wearables.dto.DeviceUnAssignReason;
import com.hillspet.wearables.dto.FirmwareVersion;
import com.hillspet.wearables.dto.PetStudyDevice;
import com.hillspet.wearables.dto.filter.AssetFirmwareVersionsFilter;
import com.hillspet.wearables.dto.filter.AssetParam;
import com.hillspet.wearables.dto.filter.AssetUpdateFirmwareFilter;
import com.hillspet.wearables.dto.filter.AssetsFilter;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.request.AssetStudyMappingRequest;
import com.hillspet.wearables.request.BulkAssetUploadRequest;
import com.hillspet.wearables.request.BulkWhiteListingRequest;
import com.hillspet.wearables.request.UnassignAssetRequest;
import com.hillspet.wearables.response.DeviceResponse;

@Repository
public class AssetManagementDaoImpl extends BaseDaoImpl implements AssetManagementDao {

	private static final Logger LOGGER = LogManager.getLogger(AssetManagementDaoImpl.class);

	@Autowired
	private ObjectMapper mapper;

	@Value("${gcp.env}")
	private String environment;

	private String loadHPN1FirmwareUpdateCFUrl = System.getenv("LOAD_HPN1_FIRMWARE_UPDATE_CR_URL");

	private static String BASE_HPN1_FIRMWARE_UPDATE_CR_URL = System.getenv("BASE_HPN1_FIRMWARE_UPDATE_CR_URL");

	private static IdTokenCredentials tokenCredential;

	static {
		try {
			GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
			if (!(credentials instanceof IdTokenProvider)) {
				throw new IllegalArgumentException("Credentials are not an instance of IdTokenProvider.");
			}
			tokenCredential = IdTokenCredentials.newBuilder().setIdTokenProvider((IdTokenProvider) credentials)
					.setTargetAudience(BASE_HPN1_FIRMWARE_UPDATE_CR_URL).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public DeviceInfo addDeviceInfo(DeviceInfo deviceInfo) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_device_number", deviceInfo.getDeviceNumber().trim());
		inputParams.put("p_device_type", deviceInfo.getDeviceType().equals("Other") ? deviceInfo.getOtherAssetType()
				: deviceInfo.getDeviceType());
		inputParams.put("p_device_model", deviceInfo.getDeviceModel().equals("Other") ? deviceInfo.getOtherAssetModel()
				: deviceInfo.getDeviceModel());
		inputParams.put("p_device_location_id", deviceInfo.getDeviceLocationId());
		inputParams.put("p_device_location_others", deviceInfo.getDeviceLocationOthers());
		inputParams.put("p_status_id", deviceInfo.getStatusId());
		inputParams.put("p_mfg_serial_number", deviceInfo.getMfgSerialNumber());
		inputParams.put("p_mfg_firmware", deviceInfo.getMfgFirmware());
		inputParams.put("p_mfg_mac_addr", deviceInfo.getMfgMacAddr());
		inputParams.put("p_wifi_mac_addr", deviceInfo.getWifiMacAddr());
		inputParams.put("p_tracking_number", deviceInfo.getTrackingNumber());
		inputParams.put("p_created_by", deviceInfo.getCreatedBy());
		inputParams.put("p_study_id", deviceInfo.getStudyId());
		inputParams.put("p_is_whiteListed", deviceInfo.getIsWhiteListed());
		inputParams.put("p_wifi_ss", deviceInfo.getWifiSS());
		inputParams.put("p_wifi_ssid", deviceInfo.getWifiSSID());
		inputParams.put("p_box_number", deviceInfo.getBoxNumber());
		// inputParams.put("p_other_asset_type", deviceInfo.getOtherAssetType());
		// inputParams.put("p_other_asset_model", deviceInfo.getOtherAssetModel());

		try {
			LOGGER.info("addDeviceInfo : inputParams {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.DEVICE_INFO_INSERT, inputParams);
			LOGGER.info("addDeviceInfo : outParams {}", outParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				Integer deviceId = (int) outParams.get("last_insert_id");
				deviceInfo.setDeviceId(deviceId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"addDeviceInfo service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.DEVICE_ALREADY_EXISTS,
									deviceInfo.getDeviceNumber())));
				} else if (statusFlag == -3) {
					throw new ServiceExecutionException(
							"addDeviceInfo service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.DEVICE_MODEL_ALREADY_EXISTS,
									deviceInfo.getDeviceModel())));
				} else if (statusFlag == -4) {
					throw new ServiceExecutionException(
							"addDeviceInfo service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.DEVICE_MFG_SERIAL_NUMBER_ALREADY_EXISTS,
									deviceInfo.getMfgSerialNumber())));
				} else if (statusFlag == -5) {
					throw new ServiceExecutionException(
							"addDeviceInfo service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.DEVICE_WIFI_MAC_ADDR_ALREADY_EXISTS,
									deviceInfo.getMfgMacAddr())));
				} else if (statusFlag == -6) {
					throw new ServiceExecutionException(
							"addDeviceInfo service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.DEVICE_WIFI_MAC_ADDR_ALREADY_EXISTS,
									deviceInfo.getMfgMacAddr())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing addDeviceInfo ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return deviceInfo;
	}

	@Override
	public DeviceInfo updateDeviceInfo(DeviceInfo deviceInfo) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_device_id", deviceInfo.getDeviceId());
		inputParams.put("p_device_number", deviceInfo.getDeviceNumber());
		inputParams.put("p_device_type", deviceInfo.getDeviceType());
		inputParams.put("p_device_model", deviceInfo.getDeviceModel());
		inputParams.put("p_device_location_id", deviceInfo.getDeviceLocationId());
		inputParams.put("p_device_location_others", deviceInfo.getDeviceLocationOthers());
		inputParams.put("p_status_id", deviceInfo.getStatusId());
		inputParams.put("p_mfg_serial_number", deviceInfo.getMfgSerialNumber());
		inputParams.put("p_mfg_firmware", deviceInfo.getMfgFirmware());
		inputParams.put("p_mfg_mac_addr", deviceInfo.getMfgMacAddr());
		inputParams.put("p_wifi_mac_addr", deviceInfo.getWifiMacAddr());
		inputParams.put("p_tracking_number", deviceInfo.getTrackingNumber());
		inputParams.put("p_modified_by", deviceInfo.getModifiedBy());
		inputParams.put("p_study_id", deviceInfo.getStudyId());
		inputParams.put("p_is_whiteListed", deviceInfo.getIsWhiteListed());
		inputParams.put("p_wifi_ssid", deviceInfo.getWifiSSID());
		inputParams.put("p_wifi_ss", deviceInfo.getWifiSS());
		inputParams.put("p_box_number", deviceInfo.getBoxNumber());
		try {
			LOGGER.info("updateDeviceInfo : inputParams {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.DEVICE_INFO_UPDATE, inputParams);
			LOGGER.info("updateDeviceInfo : outParams {}", outParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"updateDeviceInfo service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.DEVICE_ALREADY_EXISTS,
									deviceInfo.getDeviceNumber())));
				} else if (statusFlag == -3) {
					throw new ServiceExecutionException(
							"updateDeviceInfo service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.DEVICE_MODEL_ALREADY_EXISTS,
									deviceInfo.getDeviceModel())));
				} else if (statusFlag == -4) {
					throw new ServiceExecutionException(
							"updateDeviceInfo service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.DEVICE_MFG_SERIAL_NUMBER_ALREADY_EXISTS,
									deviceInfo.getMfgSerialNumber())));
				} else if (statusFlag == -5) {
					throw new ServiceExecutionException(
							"updateDeviceInfo service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.DEVICE_MFG_MFG_MAC_ADDR_ALREADY_EXISTS,
									deviceInfo.getMfgMacAddr())));
				} else if (statusFlag == -6) {
					throw new ServiceExecutionException(
							"addDeviceInfo service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.DEVICE_WIFI_MAC_ADDR_ALREADY_EXISTS,
									deviceInfo.getMfgMacAddr())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing updateDeviceInfo ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return deviceInfo;
	}

	@Override
	public void deleteDeviceInfo(int deviceId, int modifiedBy) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_device_id", deviceId);
		inputParams.put("p_modified_by", modifiedBy);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.DEVICE_INFO_DELETE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || (int) outParams.get("out_flag") < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"deleteDeviceInfo service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.DEVICE_CANNOT_DELETE, errorMsg)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing deleteDeviceInfo ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	@Transactional
	public void updateDeviceFirmware(int firmwareVersionId, String deviceIds, int modifiedBy, String assetModel)
			throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_firmware_version_id", firmwareVersionId);
		inputParams.put("p_device_ids", deviceIds);
		inputParams.put("p_modified_by", modifiedBy);
		LOGGER.info("updateDeviceFirmware inparmas ", inputParams);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.DEVICE_INFO_UPDATE_DEVICE_FIRMWARE,
					inputParams);
			LOGGER.info("updateDeviceFirmware outParams ", outParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");

			if (StringUtils.isNotEmpty(errorMsg) || statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"updateDeviceFirmware service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.DEVICE_TYPES_SHOULD_BE_SAME)));
				} else if (statusFlag == -3) {
					throw new ServiceExecutionException(
							"updateDeviceFirmware service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.DEVICE_MODELS_SHOULD_BE_SAME)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			} else {
				// write the code to call HPN Cloud Function
				if (StringUtils.isNotEmpty(assetModel)
						&& (assetModel.equalsIgnoreCase("HPN1") || assetModel.equalsIgnoreCase("HPN1 Beta"))) {
					List<DeviceFirmwareDetails> firmwareDetailsList = getFirmwareUpdateDtlsByDeviceIds(deviceIds);
					invokeHPN1FirmwareUpdateJob(loadHPN1FirmwareUpdateCFUrl, firmwareDetailsList, modifiedBy,
							environment);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing deleteDeviceInfo ", e);
			throw new ServiceExecutionException(e.getMessage());
		}

	}

	@Override
	public DeviceInfo getDeviceInfoById(int deviceId) throws ServiceExecutionException {
		final DeviceInfo deviceInfo = new DeviceInfo();
		LOGGER.debug("getDeviceInfoById called");
		try {
			jdbcTemplate.query(SQLConstants.DEVICE_INFO_GET_BY_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					deviceInfo.setDeviceId(rs.getInt("device_id"));
					deviceInfo.setDeviceNumber(rs.getString("device_number"));
					deviceInfo.setDeviceType(rs.getString("device_type"));
					deviceInfo.setDeviceModel(rs.getString("device_model"));
					deviceInfo.setMfgSerialNumber(rs.getString("mfg_serial_number"));
					deviceInfo.setMfgFirmware(rs.getString("mfg_firmware"));
					deviceInfo.setMfgMacAddr(rs.getString("mfg_mac_addr"));
					deviceInfo.setWifiMacAddr(rs.getString("wifi_mac_addr"));
					deviceInfo.setAddDate(rs.getTimestamp("add_date").toLocalDateTime());
					deviceInfo.setIsGyroScopeDataEnabled(rs.getBoolean("is_gyro_scope_data_enabled"));
					deviceInfo.setIsActive(rs.getBoolean("is_active"));
					deviceInfo.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
					if (rs.getTimestamp("modified_date") != null) {
						deviceInfo.setModifiedDate(rs.getTimestamp("modified_date").toLocalDateTime());
					}
					deviceInfo.setDeviceLocationId(rs.getInt("device_location_id"));
					deviceInfo.setLocation(rs.getString("device_location"));
					deviceInfo.setDeviceLocationOthers(rs.getString("device_location_others"));
					deviceInfo.setStatusId(rs.getInt("STATUS_ID"));
					deviceInfo.setStatus(rs.getString("STATUS_NAME"));
					deviceInfo.setTrackingNumber(rs.getString("tracking_number"));
					deviceInfo.setStudyId(rs.getInt("STUDY_ID"));
					deviceInfo.setStudyName(rs.getString("STUDY_NAME"));
					deviceInfo.setWifiSSID(rs.getInt("WIFI_SSID_ID"));
					deviceInfo.setWifiSS(rs.getString("WIFI_SSID"));
					deviceInfo.setIsWhiteListed(rs.getInt("IS_WHITELISTED"));
					deviceInfo.setBoxNumber(rs.getString("BOX_NUMBER"));
				}
			}, deviceId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceInfoById", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return deviceInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getDeviceCount(AssetUpdateFirmwareFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getDeviceCount called");
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.DEVICE_INFO_LIST_COUNT, String.class, filter.getSearchText(),
					filter.getAssetType(), filter.getAssetLocation(), filter.getAssetModel(), filter.getUserId(),
					filter.getRoleTypeId());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<DeviceInfo> getDeviceInfo(AssetUpdateFirmwareFilter filter) throws ServiceExecutionException {
		List<DeviceInfo> deviceInfoList = new ArrayList<>();

		LOGGER.debug("getDeviceInfo called");

		try {
			jdbcTemplate.query(SQLConstants.DEVICE_INFO_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					DeviceInfo deviceInfo = new DeviceInfo();
					deviceInfo.setSlNumber(rs.getInt("slNo"));
					deviceInfo.setDeviceId(rs.getInt("device_id"));
					deviceInfo.setDeviceNumber(rs.getString("device_number"));
					deviceInfo.setDeviceType(rs.getString("device_type"));
					deviceInfo.setDeviceModel(rs.getString("device_model"));
					deviceInfo.setMfgSerialNumber(rs.getString("mfg_serial_number"));
					deviceInfo.setMfgFirmware(rs.getString("mfg_firmware"));
					deviceInfo.setMfgMacAddr(rs.getString("mfg_mac_addr"));
					deviceInfo.setWifiMacAddr(rs.getString("wifi_mac_addr"));
					deviceInfo.setTrackingNumber(rs.getString("tracking_number"));
					deviceInfo.setAddDate(rs.getTimestamp("add_date").toLocalDateTime());
					deviceInfo.setIsGyroScopeDataEnabled(rs.getBoolean("is_gyro_scope_data_enabled"));
					deviceInfo.setIsActive(rs.getBoolean("is_active"));
					deviceInfo.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
					if (rs.getTimestamp("modified_date") != null) {
						deviceInfo.setModifiedDate(rs.getTimestamp("modified_date").toLocalDateTime());
					}
					deviceInfo.setFirmwareVersionNumber(rs.getString("firmware_version_number"));
					deviceInfo.setLocation(rs.getString("device_location"));

					deviceInfoList.add(deviceInfo);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getAssetType(), filter.getAssetLocation(), filter.getAssetModel(), filter.getUserId(),
					filter.getRoleTypeId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceInfo", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return deviceInfoList;
	}

	@Override
	public List<DeviceInfo> getAllDevices(String studyIds) throws ServiceExecutionException {
		List<DeviceInfo> deviceInfos = new ArrayList<>();
		LOGGER.debug("getAllDevices called");
		try {
			jdbcTemplate.query(SQLConstants.DEVICE_INFO_GET_ALL_DEVICES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					DeviceInfo deviceInfo = new DeviceInfo();
					deviceInfo.setDeviceId(rs.getInt("device_id"));
					deviceInfo.setDeviceNumber(rs.getString("device_number"));
					deviceInfo.setDeviceType(rs.getString("device_type"));
					deviceInfo.setDeviceModel(rs.getString("device_model"));
					deviceInfo.setIsActive(rs.getBoolean("is_active"));
					deviceInfo.setStudyId(rs.getInt("study_id"));
					deviceInfo.setStudyName(rs.getString("study_name"));
					deviceInfos.add(deviceInfo);
				}
			}, studyIds);

		} catch (Exception e) {
			LOGGER.error("error while fetching getAllDevices", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return deviceInfos;
	}

	@Override
	public DeviceResponse getDeviceTypesAndModels() throws ServiceExecutionException {
		LOGGER.debug("getDeviceTypesAndModels called");
		DeviceResponse deviceResponse = new DeviceResponse();
		List<String> deviceModels = new ArrayList<>();
		List<String> deviceTypes = new ArrayList<>();
		try {
			jdbcTemplate.query(SQLConstants.DEVICE_INFO_GET_DEVICE_TYPES_AND_MODELS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					if (StringUtils.isNotEmpty(rs.getString("deviceTypes"))) {
						for (String type : rs.getString("deviceTypes").split(",")) {
							deviceTypes.add(type);
						}
					}
					if (StringUtils.isNotEmpty(rs.getString("models"))) {
						for (String model : rs.getString("models").split(",")) {
							deviceModels.add(model);
						}
					}
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceTypesAndModels", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		deviceResponse.setDeviceTypes(deviceTypes);
		deviceResponse.setDeviceModels(deviceModels);
		LOGGER.debug("getDeviceInfo completed successfully");
		return deviceResponse;
	}

	@Override
	public FirmwareVersion addFirmwareVersion(FirmwareVersion firmwareVersion) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_firmware_version", firmwareVersion.getFirmwareVersionNumber());
		inputParams.put("p_asset_type", firmwareVersion.getAssetType());
		inputParams.put("p_model", firmwareVersion.getModel());
		inputParams.put("p_created_by", firmwareVersion.getCreatedBy());
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.FIRMWARE_VERSION_INSERT, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				Integer firmwareVersionId = (int) outParams.get("last_insert_id");
				firmwareVersion.setFirmwareVersionId(firmwareVersionId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"addFirmwareVersion service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.FIRMWARE_VERSION_ALREADY_EXISTS,
									firmwareVersion.getFirmwareVersionNumber())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing addFirmwareVersion ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return firmwareVersion;
	}

	@Override
	public FirmwareVersion updateFirmwareVersion(FirmwareVersion firmwareVersion) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_firmware_version_id", firmwareVersion.getFirmwareVersionId());
		inputParams.put("p_asset_frimware_version_id", firmwareVersion.getAssetFrimwareVersionId());
		inputParams.put("p_firmware_version", firmwareVersion.getFirmwareVersionNumber());
		inputParams.put("p_asset_type", firmwareVersion.getAssetType());
		inputParams.put("p_model", firmwareVersion.getModel());
		inputParams.put("p_modified_by", firmwareVersion.getModifiedBy());
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.FIRMWARE_VERSION_UPDATE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"updateFirmwareVersion service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.FIRMWARE_VERSION_ALREADY_EXISTS,
									firmwareVersion.getFirmwareVersionNumber())));
				} else if (statusFlag == -3) {
					throw new ServiceExecutionException(
							"updateFirmwareVersion service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
									new WearablesError(WearablesErrorCode.FIRMWARE_VERSION_REFERENCED, errorMsg)));
				} else if (statusFlag == -4) {
					throw new ServiceExecutionException(
							"updateFirmwareVersion service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
									new WearablesError(WearablesErrorCode.FIRMWARE_VERSION_REFERENCED, errorMsg)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing updateFirmwareVersion ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return firmwareVersion;
	}

	@Override
	public void deleteFirmwareVersion(int firmwareVersionId, int assetFirmwareVersionId, int modifiedBy) {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_firmware_version_id", firmwareVersionId);
		inputParams.put("p_asset_frimware_version_id", assetFirmwareVersionId);
		inputParams.put("p_modified_by", modifiedBy);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.FIRMWARE_VERSION_DELETE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || (int) outParams.get("out_flag") < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"deleteFirmwareVersion service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.FIRMWARE_CANNOT_DELETE, errorMsg)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}

			}
		} catch (SQLException e) {
			LOGGER.error("error while executing deleteFirmwareVersion ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public FirmwareVersion getFirmwareVersionById(int firmwareVersionId) throws ServiceExecutionException {
		final FirmwareVersion firmwareVersion = new FirmwareVersion();
		LOGGER.debug("getFirmwareVersionById called");
		try {
			jdbcTemplate.query(SQLConstants.FIRMWARE_VERSION_GET_BY_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					firmwareVersion.setFirmwareVersionId(rs.getInt("firmware_version_id"));
					firmwareVersion.setFirmwareVersionNumber(rs.getString("firmware_version_number"));
					firmwareVersion.setIsActive(rs.getBoolean("is_active"));
					firmwareVersion.setCreatedUser(rs.getString("createdUser"));
					firmwareVersion.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
					if (rs.getTimestamp("modified_date") != null) {
						firmwareVersion.setModifiedUser(rs.getString("modifiedUser"));
						firmwareVersion.setModifiedDate(rs.getTimestamp("modified_date").toLocalDateTime());
					}
				}
			}, firmwareVersionId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getFirmwareVersionById", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return firmwareVersion;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getFirmwareVersionCount(AssetFirmwareVersionsFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getFirmwareVersionCount called");
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.FIRMWARE_VERSION_LIST_COUNT, String.class, filter.getSearchText(),
					filter.getAssetType(), filter.getAssetModel());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getFirmwareVersionCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<FirmwareVersion> getFirmwareVersions(AssetFirmwareVersionsFilter filter)
			throws ServiceExecutionException {
		List<FirmwareVersion> firmwareVersions = new ArrayList<>();
		LOGGER.debug("getFirmwareVersions called");
		try {
			jdbcTemplate.query(SQLConstants.FIRMWARE_VERSION_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					FirmwareVersion firmwareVersion = new FirmwareVersion();
					firmwareVersion.setSlNumber(rs.getInt("slNo"));
					firmwareVersion.setFirmwareVersionId(rs.getInt("firmware_version_id"));
					firmwareVersion.setAssetFrimwareVersionId(rs.getInt("ASSET_FIRMWARE_VERSION_ID"));
					firmwareVersion.setFirmwareVersionNumber(rs.getString("firmware_version_number"));
					firmwareVersion.setAssetType(rs.getString("asset_type"));
					firmwareVersion.setModel(rs.getString("model"));
					firmwareVersion.setIsActive(rs.getBoolean("is_active"));
					firmwareVersion.setCreatedUser(rs.getString("createdUser"));
					firmwareVersion.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
					if (rs.getTimestamp("modified_date") != null) {
						firmwareVersion.setModifiedUser(rs.getString("modifiedUser"));
						firmwareVersion.setModifiedDate(rs.getTimestamp("modified_date").toLocalDateTime());
					}
					firmwareVersions.add(firmwareVersion);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getAssetType(), filter.getAssetModel());

		} catch (Exception e) {
			LOGGER.error("error while fetching getFirmwareVersions", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return firmwareVersions;
	}

	@Override
	public List<FirmwareVersion> getAllFirmwareVersions(String assetType, String assetModel)
			throws ServiceExecutionException {
		List<FirmwareVersion> firmwareVersions = new ArrayList<>();
		LOGGER.debug("getAllFirmwareVersions called");
		try {
			jdbcTemplate.query(SQLConstants.FIRMWARE_VERSION_GET_ALL_VERSIONS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					FirmwareVersion firmwareVersion = new FirmwareVersion();
					firmwareVersion.setFirmwareVersionId(rs.getInt("firmware_version_id"));
					firmwareVersion.setFirmwareVersionNumber(rs.getString("firmware_version_number"));
					firmwareVersion.setIsActive(rs.getBoolean("is_active"));
					firmwareVersions.add(firmwareVersion);
				}

			}, assetType, assetModel);

		} catch (Exception e) {
			LOGGER.error("error while fetching getAllFirmwareVersions", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return firmwareVersions;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getAssetCount(AssetsFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getAssetCount called");
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.ASSET_LIST_COUNT, String.class, filter.getSearchText(),
					filter.getStudyId(), filter.getAssetType(), filter.getStatusId(), filter.getUserId(),
					filter.getRoleTypeId(), filter.getAssetModel(), filter.getAssetNumber(), filter.getAssetLocation(),
					filter.getIsWhiteListed(), filter.getBoxNumber());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getAssetCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<Asset> getAssetList(AssetsFilter filter) throws ServiceExecutionException {
		List<Asset> assetsList = new ArrayList<>();

		LOGGER.debug("getAssetList called");
		LOGGER.debug(filter.getSearchText());
		try {
			jdbcTemplate.query(SQLConstants.ASSET_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					Asset asset = new Asset();
					asset.setSlNumber(rs.getInt("slNo"));
					asset.setDeviceId(rs.getInt("device_id"));
					asset.setDeviceNumber(rs.getString("device_number"));
					asset.setDeviceType(rs.getString("device_type"));
					asset.setDeviceModel(rs.getString("device_model"));
					asset.setIsActive(rs.getBoolean("is_active"));
					asset.setStudy(rs.getString("study"));
					asset.setLocation(rs.getString("device_location"));
					asset.setStatusId(rs.getInt("STATUS_ID"));
					asset.setStatus(rs.getString("STATUS_NAME"));
					asset.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
					asset.setStudyId(rs.getInt("STUDY_ID"));
					if (rs.getTimestamp("modified_date") != null) {
						asset.setModifiedDate(rs.getTimestamp("modified_date").toLocalDateTime());
					}
					asset.setMfgfirm(rs.getString("MFG_FIRMWARE") == null ? "" : rs.getString("MFG_FIRMWARE"));
					asset.setIswhiteListed(rs.getString("IS_WHITELISTED"));
					asset.setWifiSSID(rs.getString("WIFI_SSID"));
					asset.setBoxNumber(rs.getString("BOX_NUMBER"));
					assetsList.add(asset);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getStudyId(), filter.getAssetType(), filter.getStatusId(), filter.getUserId(),
					filter.getRoleTypeId(), filter.getAssetModel(), filter.getAssetNumber(), filter.getAssetLocation(),
					filter.getIsWhiteListed(), filter.getBoxNumber());

		} catch (Exception e) {
			LOGGER.error("error while fetching getAssetList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return assetsList;
	}

	@Override
	public int[] saveBulkDevicesToStaging(List<BulkAssetUploadDeviceInfo> deviceInfo, String attachmentName,
			Integer userId) throws ServiceExecutionException {
		LOGGER.debug("saveBulkDevicesToStaging called");
		LOGGER.debug("saveBulkDevicesToStaging batch size " + deviceInfo.size());

		/* Deleting previously saved user Records from staging table. */
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_user_id", userId);

		/* Below code used to delete previous results. */
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.DEVICE_INFO_BULK_STAGING_RESULTS_DELETE,
					inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			LOGGER.debug("saveBulkDevicesToStaging statusFlag " + statusFlag);
			LOGGER.debug("saveBulkDevicesToStaging errorMsg " + errorMsg);

		} catch (SQLException e) {
			LOGGER.error("error while executing addDeviceInfo ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		int[] result = null;
		try {
			result = jdbcTemplate.batchUpdate(SQLConstants.DEVICE_INFO_BULK_INSERT_STAGING,
					new BatchPreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps, int i) throws SQLException {
							BulkAssetUploadDeviceInfo info = deviceInfo.get(i);
							ps.setString(1, info.getDeviceNumber());
							ps.setString(2, info.getDeviceType());
							ps.setString(3, info.getDeviceModel());
							ps.setString(4, info.getLocation() != null ? info.getLocation().trim() : "");
							if (StringUtils.isBlank(info.getMfgSerialNumber())
									&& info.getDeviceModel().toUpperCase().equals("CMAS")) {
								ps.setString(5, info.getDeviceNumber());
							} else {
								ps.setString(5, info.getMfgSerialNumber());
							}
							// ps.setString(5, info.getMfgSerialNumber());
							ps.setString(6, info.getMfgFirmware());
							ps.setString(7, info.getMfgMacAddr());
							ps.setString(8, info.getWifiMacAddr());
							ps.setInt(9, userId);
							ps.setString(10, info.getTrackingNumber());
							ps.setInt(11, info.getStudyId() == null ? 0 : info.getStudyId());
							ps.setString(12, info.getStudyName());
							ps.setString(13, info.getWifiSSID());
							ps.setString(14, info.getBoxNumber());
							ps.setString(15, info.getAttachmentName());
						}

						@Override
						public int getBatchSize() {
							return deviceInfo.size();
						}
					});
		} catch (Exception e) {
			LOGGER.error("error while saveBulkDevicesToStaging", e);
		}
		LOGGER.debug("saveBulkDevicesToStaging Completed");
		LOGGER.debug("saveBulkDevicesToStaging - validateBulkDevicesList start.");

		validateBulkDevicesList(userId);

		LOGGER.debug("saveBulkDevicesToStaging - validateBulkDevicesList end.");
		return result;
	}

	@Override
	public DeviceInfo saveBulkUploadDevicesInfo(BulkAssetUploadRequest request) throws ServiceExecutionException {
		LOGGER.debug("saveDeviceInfoBulkUpload called");
		LOGGER.debug("saveDeviceInfoBulkUpload userId = " + request.getUserId());
		LOGGER.debug("saveDeviceInfoBulkUpload selectedIds = " + request.getStagingId());
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_user_id", request.getUserId());
		inputParams.put("p_selected_staging_ids", request.getStagingId());
		DeviceInfo deviceInfo = new DeviceInfo();
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.DEVICE_INFO_BULK_SAVE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				Integer deviceID = (int) outParams.get("last_insert_id");
				LOGGER.info("Devices created successfully, Device ID is ", deviceID);
				deviceInfo.setDeviceId(deviceID);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"saveBulkUploadDevicesInfo service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.DEVICE_ALREADY_EXISTS, errorMsg)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}

			LOGGER.debug("saveDeviceInfoBulkUpload statusFlag " + statusFlag);
			LOGGER.debug("saveDeviceInfoBulkUpload errorMsg " + errorMsg);

		} catch (SQLException e) {
			LOGGER.error("error while executing addDeviceInfo ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return deviceInfo;
	}

	@Override
	public void validateBulkDevicesList(Integer userId) throws ServiceExecutionException {

		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_user_id", userId);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.DEVICE_INFO_BULK_VALIDATION_STAGING,
					inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			if (StringUtils.isNotEmpty(errorMsg) || (int) outParams.get("out_flag") < NumberUtils.INTEGER_ONE) {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing deleteDeviceInfo ", e);
			throw new ServiceExecutionException(e.getMessage());
		}

	}

	@Override
	public int getBulkUploadDevicesListCount(BaseFilter filter) throws ServiceExecutionException {
		int totalCount = NumberUtils.INTEGER_ZERO;
		LOGGER.debug("getBulkUploadDevicesListCount called");
		try {
			totalCount = selectForObject(SQLConstants.DEVICE_INFO_BULK_STAGING_LIST_COUNT, Integer.class,
					filter.getUserId());
		} catch (Exception e) {
			LOGGER.error("error while fetching getAssetCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;
	}

	@Override
	public List<BulkAssetUploadDeviceInfo> getBulkUploadDevicesList(BaseFilter filter)
			throws ServiceExecutionException {
		List<BulkAssetUploadDeviceInfo> deviceList = new ArrayList<>();

		LOGGER.debug("getAssetList called");
		try {
			jdbcTemplate.query(SQLConstants.DEVICE_INFO_BULK_STAGING_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					BulkAssetUploadDeviceInfo device = new BulkAssetUploadDeviceInfo();
					device.setStagingId(rs.getInt("ID"));
					device.setDeviceNumber(rs.getString("DEVICE_NUMBER"));
					device.setDeviceType(rs.getString("DEVICE_TYPE"));
					device.setMfgSerialNumber(rs.getString("MFG_SERIAL_NUMBER"));
					device.setDeviceModel(rs.getString("DEVICE_MODEL"));
					device.setDeviceLocationName(rs.getString("DEVICE_LOCATION_NAME"));
					device.setDeviceLocationId(rs.getInt("DEVICE_LOCATION_ID"));
					device.setMfgFirmware(rs.getString("MFG_FIRMWARE"));
					device.setMfgMacAddr(rs.getString("MFG_MAC_ADDR"));
					device.setWifiMacAddr(rs.getString("WIFI_MAC_ADDR"));
					device.setTrackingNumber(rs.getString("TRACKING_NUMBER"));
					device.setStudyId(rs.getInt("STUDY_ID"));
					device.setStudyName(rs.getString("STUDY_NAME"));
					device.setWifiSSID(rs.getString("WIFI_SSID"));
					device.setBoxNumber(rs.getString("BOX_NUMBER"));
					device.setExceptionMsg(rs.getString("EXCEPTION_TYPE"));

					deviceList.add(device);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getUserId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getAssetList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return deviceList;
	}

	@Override
	public List<AssetType> getAllAssetTypes() throws ServiceExecutionException {
		List<AssetType> assetTypes = new ArrayList<>();
		LOGGER.debug("getAllAssetTypes called");
		try {
			jdbcTemplate.query(SQLConstants.ASSET_TYPE_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					AssetType assetType = new AssetType();
					assetType.setDeviceType(rs.getString("DEVICE_TYPE"));
					assetTypes.add(assetType);
				}

			});
			AssetType assetType = new AssetType();
			assetType.setDeviceType("Other");
			assetTypes.add(assetType);
		} catch (Exception e) {
			LOGGER.error("error while fetching getAllDevices", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return assetTypes;
	}

	@Override
	public List<DeviceModel> getDeviceModelById(String assetType) throws ServiceExecutionException {
		List<DeviceModel> deviceModels = new ArrayList<>();
		LOGGER.debug("getDeviceModelById called");
		try {
			jdbcTemplate.query(SQLConstants.DEVICE_MODEL_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					DeviceModel deviceModel = new DeviceModel();
					if (StringUtils.isNotBlank(rs.getString("DEVICE_MODEL"))) {
						deviceModel.setDeviceModel(rs.getString("DEVICE_MODEL"));
						deviceModels.add(deviceModel);
					}

				}
			}, assetType);
			DeviceModel deviceModel = new DeviceModel();
			deviceModel.setDeviceModel("Other");
			deviceModels.add(deviceModel);

		} catch (Exception e) {
			LOGGER.error("error while fetching getAllDevices", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return deviceModels;
	}

	@Override
	public List<DeviceUnAssignReason> getDeviceUnAssinReason() throws ServiceExecutionException {
		List<DeviceUnAssignReason> list = new ArrayList<>();
		try {
			jdbcTemplate.query(SQLConstants.DEVICE_GET_UNASSIGN_REASON_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					DeviceUnAssignReason reason = new DeviceUnAssignReason();
					reason.setReasonId(rs.getInt("REASON_ID"));
					reason.setReasonName(rs.getString("REASON_NAME"));
					reason.setIsActive(rs.getBoolean("IS_ACTIVE"));
					reason.setIsDeleted(rs.getBoolean("IS_DELETED"));
					list.add(reason);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceUnAssinReason", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return list;
	}

	@Override
	public List<FirmwareVersion> getAllFirmwareVersionsList() throws ServiceExecutionException {
		List<FirmwareVersion> firmwareVersions = new ArrayList<>();
		LOGGER.debug("getAllFirmwareVersions called");
		try {
			jdbcTemplate.query(SQLConstants.FIRMWARE_VERSION_GET_ALL_VERSIONS_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					FirmwareVersion firmwareVersion = new FirmwareVersion();
					firmwareVersion.setFirmwareVersionId(rs.getInt("firmware_version_id"));
					firmwareVersion.setFirmwareVersionNumber(rs.getString("firmware_version_number"));
					firmwareVersion.setIsActive(rs.getBoolean("is_active"));
					firmwareVersions.add(firmwareVersion);
				}

			});

		} catch (Exception e) {
			LOGGER.error("error while fetching getAllFirmwareVersions", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return firmwareVersions;
	}

	@Override
	public List<AssetHistory> getAssetHistory(int deviceId) throws ServiceExecutionException {
		List<AssetHistory> AssetHistoryList = new ArrayList<>();
		LOGGER.debug("getAllFirmwareVersions called");
		try {
			jdbcTemplate.query(SQLConstants.GET_ASSET_UPDATE_HISTORY, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					AssetHistory assetHistory = new AssetHistory();
					assetHistory.setNotificationDate(rs.getString("LAST_SAMPLE_DATE"));
					assetHistory.setUpdatedDate(rs.getString("LAST_FILE_DATE"));
					assetHistory.setVersion(rs.getString("FIRMWARE"));
					AssetHistoryList.add(assetHistory);
				}

			}, deviceId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getAllFirmwareVersions", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return AssetHistoryList;
	}

	@Override
	public List<Asset> getUnConfiguredDevices() throws ServiceExecutionException {
		List<Asset> list = new ArrayList<>();
		try {
			jdbcTemplate.query(SQLConstants.DEVICE_GET_UNCONGIFURED_DEVICE_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					Asset asset = new Asset();
					asset.setDeviceId(rs.getInt("DEVICE_ID"));
					asset.setDeviceNumber(rs.getString("DEVICE_NUMBER"));
					asset.setDeviceType(rs.getString("DEVICE_TYPE"));
					asset.setDeviceModel(rs.getString("DEVICE_MODEL"));
					asset.setStudy(rs.getString("STUDY_NAME"));
					asset.setPetName(rs.getString("PET_NAME"));
					asset.setPetId(rs.getString("PET_ID"));
					list.add(asset);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getUnConfiguredDevices", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return list;
	}

	@Override
	public void unassignAsset(UnassignAssetRequest request) throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_unassign_request", mapper.writeValueAsString(request.getPetUnAssignAssets()));
			inputParams.put("p_modified_by", request.getUserId());

			LOGGER.info("unassignAsset inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_UNASSIGN_ASSET, inputParams);
			LOGGER.info("unassignAsset inputParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Unassigned success...");
			} else {
				throw new ServiceExecutionException(errorMsg);
			}

		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing addPet ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public List<PetStudyDevice> getPetStudiesByAsset(int deviceId) throws ServiceExecutionException {
		List<PetStudyDevice> petStudyDevices = new ArrayList<>();
		LOGGER.debug("getPetStudiesByAsset called");
		try {
			jdbcTemplate.query(SQLConstants.ASSET_GET_PET_STUDIES_BY_ASSET, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetStudyDevice petDevice = new PetStudyDevice();
					petDevice.setPetId(rs.getInt("PET_ID"));
					petDevice.setStudyId(rs.getInt("STUDY_ID"));
					petDevice.setStudyName(rs.getString("STUDY_NAME"));
					petDevice.setStudyDescription(rs.getString("STUDY_DESCRIPTION"));
					petDevice.setDeviceId(rs.getInt("DEVICE_ID"));
					petDevice.setDeviceNumber(rs.getString("DEVICE_NUMBER"));
					petDevice.setAssignedOn(rs.getTimestamp("ASSIGN_DATE").toLocalDateTime().toLocalDate());
					petDevice.setPetStudyId(rs.getInt("PET_STUDY_ID"));
					petDevice.setPetStudyDeviceId(rs.getInt("PET_STUDY_DEVICE_ID"));
					petDevice.setDeviceNumber(rs.getString("DEVICE_NUMBER"));

					petStudyDevices.add(petDevice);
				}
			}, deviceId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetDevices", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petStudyDevices;
	}

	private List<DeviceFirmwareDetails> getFirmwareUpdateDtlsByDeviceIds(String deviceIds)
			throws ServiceExecutionException {
		List<DeviceFirmwareDetails> firmwareDetailsList = new ArrayList<>();
		LOGGER.debug("getFirmwareUpdateDtlsByDeviceIds called");
		try {
			jdbcTemplate.query(SQLConstants.GET_FIRMWARE_UPDATE_DETAILS_BY_DEVICE_IDS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					DeviceFirmwareDetails firmwareDtls = new DeviceFirmwareDetails();
					firmwareDtls.setDeviceFirmwareId(rs.getInt(1));
					firmwareDtls.setDeviceId(rs.getInt(2));
					firmwareDtls.setDeviceNumber(rs.getString(3));
					firmwareDtls.setDeviceModel(rs.getString(4));
					firmwareDtls.setVersion(rs.getString(5));
					firmwareDetailsList.add(firmwareDtls);
				}
			}, deviceIds);

		} catch (Exception e) {
			LOGGER.error("error while fetching getFirmwareUpdateDtlsByDeviceIds", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return firmwareDetailsList;
	}

	private String getAccessToken(String endpoint) {
		String bearerToken = "";
		try {
			tokenCredential.refreshIfExpired();
			bearerToken = tokenCredential.refreshAccessToken().getTokenValue();
		} catch (IOException e) {
			System.out.println("Exception in getAccessToken method " + e.getMessage());
			e.printStackTrace();
		}
		return bearerToken;
	}

	private void invokeHPN1FirmwareUpdateJob(String endpoint, List<DeviceFirmwareDetails> firmwareDetailsList,
			int modifiedBy, String environment) {
		try {
			if (StringUtils.isEmpty(endpoint)) {
				System.out.println("endpoint is not available in Env Variables");
			}

			String accessToken = getAccessToken(endpoint);
			System.out.println("accessToken is " + accessToken);
			// Create a WebClient instance with the authorization header
			WebClient webClient = WebClient.builder().baseUrl(BASE_HPN1_FIRMWARE_UPDATE_CR_URL)
					.clientConnector(new ReactorClientHttpConnector())
					.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken).build();

			JSONObject reqeustJson = new JSONObject();
			reqeustJson.put("firmwareDetailsList", firmwareDetailsList);
			reqeustJson.put("modifiedBy", modifiedBy);
			reqeustJson.put("environment", environment);

			String json = reqeustJson.toString();
			LOGGER.info("postDataToEndpoint::request:: " + json);

			webClient.method(HttpMethod.POST).uri(endpoint).contentType(MediaType.APPLICATION_JSON)
					.body(BodyInserters.fromValue(json)).retrieve().onStatus(HttpStatus::is4xxClientError, response -> {
						LOGGER.error("onFailure is4xxClientError response is ::" + response.toString());
						// Handle Bad Request
						throw new RuntimeException("HPN1FirmwareUpdate Bad Request");
					}).onStatus(HttpStatus::is5xxServerError, response -> {
						LOGGER.error("onFailure  is5xxServerError response is ::" + response.toString());
						// Handle Server Error
						throw new RuntimeException("HPN1FirmwareUpdate Job Failed to process the data");
					}).bodyToMono(String.class).subscribe(response -> {
						// System.out.println("Request is successfully processed");
						LOGGER.error("onSuccess response is ::" + response.toString());
					});

		} catch (Exception e) {
			System.out.println("Exception in invokeHPN1FirmwareUpdateJob method " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void manageAssetStudyMapping(AssetStudyMappingRequest request, Integer userId)
			throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_asset_study_json", mapper.writeValueAsString(request));
			inputParams.put("p_modified_by", userId);
			LOGGER.info("manageAssetStudyMapping inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.ASSET_MANAGE_STUDY_MAPPING, inputParams);
			LOGGER.info("manageAssetStudyMapping outParams are {}", outParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Asset Study mapping successful");
			} else {
				throw new ServiceExecutionException(errorMsg);
			}

		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing manageAssetStudyMapping ", e);
			throw new ServiceExecutionException(e.getMessage());
		}

	}

	@Override
	public List<Asset> getSensorsListBySsId(String ssId) throws ServiceExecutionException {
		List<Asset> assetList = new ArrayList<>();

		try {
			jdbcTemplate.query(SQLConstants.ASSET_GET_ASSET_BY_SSID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					Asset asset = new Asset();
					asset.setDeviceId(rs.getInt("DEVICE_ID"));
					asset.setDeviceNumber(rs.getString("DEVICE_NUMBER"));
					asset.setBoxNumber(rs.getString("BOX_NUMBER"));
					assetList.add(asset);
				}
			}, ssId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getSensorsListBySsId", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return assetList;
	}

	@Override
	public List<Asset> getSensorsList(AssetParam filter) throws ServiceExecutionException {

		List<Asset> assetList = new ArrayList<>();

		try {
			jdbcTemplate.query(SQLConstants.ASSET_GET_ASSET_BY_STUDY, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					Asset asset = new Asset();
					asset.setDeviceId(rs.getInt("DEVICE_ID"));
					asset.setDeviceNumber(rs.getString("DEVICE_NUMBER"));
					asset.setTotalCount(rs.getInt("TOTAL_COUNT"));
					asset.setBoxNumber(rs.getString("BOX_NUMBER"));
					assetList.add(asset);
				}
			}, filter.getStatus(), filter.getStudyId(), filter.getSsId(), filter.getQuery(), filter.getStartIndex(),
					filter.getLimit(), filter.getUserId(), filter.getRoleTypeId(), filter.getAssetNumber(),
					filter.getBoxNumber());
		} catch (Exception e) {
			LOGGER.error("error while fetching getSensorsList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return assetList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getSensorsListCount(AssetParam filter) throws ServiceExecutionException {
		LOGGER.debug("getSensorsListCount called");
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.ASSET_GET_ASSET_BY_STUDY_COUNT, String.class, filter.getStatus(),
					filter.getStudyId(), filter.getSsId(), filter.getQuery(), filter.getStartIndex(), filter.getLimit(),
					filter.getUserId(), filter.getRoleTypeId(), filter.getAssetNumber(), filter.getBoxNumber());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getSensorsListCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<com.hillspet.wearables.dto.Status> getAssetStatus() throws ServiceExecutionException {

		List<com.hillspet.wearables.dto.Status> statusList = new ArrayList<>();

		try {
			jdbcTemplate.query(SQLConstants.ASSET_GET_ASSET_STATUS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					com.hillspet.wearables.dto.Status status = new com.hillspet.wearables.dto.Status();
					status.setStatusId(rs.getInt("DEVICE_STATUS_ID"));
					status.setStatusName(rs.getString("STATUS_NAME"));
					statusList.add(status);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getSensorsList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return statusList;
	}

	@Override
	public void bulkWhiteListing(BulkWhiteListingRequest request, Integer userId) throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			ObjectMapper objectMapper = new ObjectMapper();
			inputParams.put("p_asset_json", objectMapper.writeValueAsString(request));
			inputParams.put("p_modified_by", userId);

			LOGGER.info("Executing bulkWhiteListing with params: {}", inputParams);

			Map<String, Object> outParams = callStoredProcedure(SQLConstants.ASSET_BULK_WHITELISTING, inputParams);

			LOGGER.info("bulkWhiteListing output: {}", outParams);

			int statusFlag = (int) outParams.get("out_flag");
			String errorMsg = (String) outParams.get("out_error_msg");

			if (statusFlag <= 0 || errorMsg != null) {
				throw new ServiceExecutionException("Error during bulkWhiteListing: " + errorMsg);
			}

		} catch (Exception e) {
			LOGGER.error("Error in bulkWhiteListing", e);
			throw new ServiceExecutionException(e.getMessage(), e);
		}
	}
}
