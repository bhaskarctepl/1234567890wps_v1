package com.hillspet.wearables.jaxrs.resource.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.Warning;
import com.hillspet.wearables.common.exceptions.ServiceValidationException;
import com.hillspet.wearables.common.response.ErrorResponse;
import com.hillspet.wearables.common.response.ResponseStatus;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.common.validation.ValidationResult;
import com.hillspet.wearables.dto.Asset;
import com.hillspet.wearables.dto.AssetHistory;
import com.hillspet.wearables.dto.AssetType;
import com.hillspet.wearables.dto.CustomUserDetails;
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
import com.hillspet.wearables.format.validation.FormatValidationHelper;
import com.hillspet.wearables.format.validation.FormatValidationService;
import com.hillspet.wearables.format.validation.ValidationAttribute;
import com.hillspet.wearables.jaxrs.resource.AssetManagementResource;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.request.AssetStudyMappingRequest;
import com.hillspet.wearables.request.BulkAssetUploadRequest;
import com.hillspet.wearables.request.BulkWhiteListingRequest;
import com.hillspet.wearables.request.UnassignAssetRequest;
import com.hillspet.wearables.response.AssetHistoryResponse;
import com.hillspet.wearables.response.AssetResponse;
import com.hillspet.wearables.response.AssetResponseList;
import com.hillspet.wearables.response.AssetTypeResponse;
import com.hillspet.wearables.response.BulkAssetUploadResponse;
import com.hillspet.wearables.response.DeviceInfoListResponse;
import com.hillspet.wearables.response.DeviceInfoResponse;
import com.hillspet.wearables.response.DeviceModelResponse;
import com.hillspet.wearables.response.DeviceResponse;
import com.hillspet.wearables.response.DeviceUnAssignResponse;
import com.hillspet.wearables.response.FirmwareVersionListResponse;
import com.hillspet.wearables.response.FirmwareVersionResponse;
import com.hillspet.wearables.response.PetDevicesResponse;
import com.hillspet.wearables.response.StatusResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.asset.AssetManagementService;

@Service
public class AssetManagementResourceImpl implements AssetManagementResource {

	private static final Logger LOGGER = LogManager.getLogger(AssetManagementResourceImpl.class);

	@Autowired
	private AssetManagementService assetManagementService;

	@Autowired
	private Authentication authentication;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Autowired
	private FormatValidationService formatValidationService;

	@Override
	public Response addDeviceInfo(DeviceInfo deviceInfo) {
		List<Warning> warnings = new ArrayList<>();
		// Step 1: prevalidate
		ValidationResult validationResult = validateDeviceInfoRequest(deviceInfo, warnings);
		if (CollectionUtils.isNotEmpty(validationResult.getErrorList())) {
			LOGGER.error("addDeviceInfo PreValidation check has failed cannot proceed further ",
					validationResult.getErrorList());
			throw new ServiceValidationException("addDeviceInfo PreValidation check has failed cannot proceed further",
					validationResult.getStatusOnError(), validationResult.getErrorList());
		}
		warnings.addAll(validationResult.getWarningList());

		// Step 2: process
		deviceInfo.setCreatedBy(authentication.getAuthUserDetails().getUserId());
		assetManagementService.addDeviceInfo(deviceInfo);

		// Step 5: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	private ValidationResult validateDeviceInfoRequest(DeviceInfo deviceInfo, List<Warning> warnings) {
		ValidationResult validationResult = new ValidationResult();
		FormatValidationHelper formatValidationHelper = formatValidationService.getValidationHelper();

		ValidationAttribute emailAttr = new ValidationAttribute("deviceNumber");
		emailAttr.addRequired(deviceInfo.getDeviceNumber(), WearablesErrorCode.DEVICE_NUMBER_REQUIRED);

		formatValidationHelper.add(emailAttr);

		formatValidationHelper.setProceedOnError(Boolean.TRUE);
		formatValidationHelper.execute(validationResult);
		return validationResult;
	}

	@Override
	public Response updateDeviceInfo(DeviceInfo deviceInfo) {
		List<Warning> warnings = new ArrayList<>();
		// Step 1: prevalidate
		ValidationResult validationResult = validateDeviceInfoRequest(deviceInfo, warnings);
		// TODO: check validationResult is null or not. Since
		// apiOperation.preValidate
		// may return optional of null value.
		if (CollectionUtils.isNotEmpty(validationResult.getErrorList())) {
			LOGGER.error("updateDeviceInfo PreValidation check has failed cannot proceed further ",
					validationResult.getErrorList());
			throw new ServiceValidationException(
					"updateDeviceInfo PreValidation check has failed cannot proceed further",
					validationResult.getStatusOnError(), validationResult.getErrorList());
		}
		warnings.addAll(validationResult.getWarningList());

		// Step 2: process
		deviceInfo.setModifiedBy(authentication.getAuthUserDetails().getUserId());
		assetManagementService.updateDeviceInfo(deviceInfo);

		// Step 5: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response deleteDeviceInfo(int deviceId) {
		assetManagementService.deleteDeviceInfo(deviceId, authentication.getAuthUserDetails().getUserId());

		// Step 5: build a successful response
		CommonResponse response = new CommonResponse();
		response.setMessage("Device has been deleted successfully");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getAssetList(AssetsFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		AssetResponse response = assetManagementService.getAssetList(filter);
		SuccessResponse<AssetResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response updateDeviceFirmware(int firmwareVersionId, String deviceIds, String assetModel) {
		// String assetModel = null;
		assetManagementService.updateDeviceFirmware(firmwareVersionId, deviceIds,
				authentication.getAuthUserDetails().getUserId(), assetModel);

		// Step 5: build a successful response
		CommonResponse response = new CommonResponse();
		response.setMessage("Device Frmware has been updated successfully");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getDeviceInfoById(int deviceId) {
		DeviceInfo deviceInfo = assetManagementService.getDeviceInfoById(deviceId);
		DeviceInfoResponse response = new DeviceInfoResponse();
		response.setDeviceInfo(deviceInfo);
		SuccessResponse<DeviceInfoResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getDeviceInfo(AssetUpdateFirmwareFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		DeviceInfoListResponse response = assetManagementService.getDeviceInfo(filter);
		SuccessResponse<DeviceInfoListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getAllDevices(String studyIds) {
		List<DeviceInfo> deviceInfos = assetManagementService.getAllDevices(studyIds);
		DeviceInfoListResponse response = new DeviceInfoListResponse();
		response.setDeviceInfos(deviceInfos);
		SuccessResponse<DeviceInfoListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getDeviceTypesAndModels() {
		DeviceResponse response = assetManagementService.getDeviceTypesAndModels();
		SuccessResponse<DeviceResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response addFirmwareVersion(FirmwareVersion firmwareVersion) {
		List<Warning> warnings = new ArrayList<>();
		// Step 1: prevalidate
		ValidationResult validationResult = validateFirmwareVersionRequest(firmwareVersion, warnings);
		// TODO: check validationResult is null or not. Since
		// apiOperation.preValidate
		// may return optional of null value.
		if (CollectionUtils.isNotEmpty(validationResult.getErrorList())) {
			LOGGER.error("addFirmwareVersion PreValidation check has failed cannot proceed further ",
					validationResult.getErrorList());
			throw new ServiceValidationException(
					"addFirmwareVersion PreValidation check has failed cannot proceed further",
					validationResult.getStatusOnError(), validationResult.getErrorList());
		}
		warnings.addAll(validationResult.getWarningList());
		firmwareVersion.setCreatedBy(authentication.getAuthUserDetails().getUserId());

		// Step 2: process
		firmwareVersion = assetManagementService.addFirmwareVersion(firmwareVersion);
		FirmwareVersionResponse response = new FirmwareVersionResponse();
		response.setFirmwareVersion(firmwareVersion);

		// Step 5: build a successful response
		SuccessResponse<FirmwareVersionResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	private ValidationResult validateFirmwareVersionRequest(FirmwareVersion firmwareVersion, List<Warning> warnings) {
		ValidationResult validationResult = new ValidationResult();
		FormatValidationHelper formatValidationHelper = formatValidationService.getValidationHelper();

		ValidationAttribute emailAttr = new ValidationAttribute("firmwareVersionNumber");
		emailAttr.addRequired(firmwareVersion.getFirmwareVersionNumber(),
				WearablesErrorCode.FIRMWARE_VERSION_NUMBER_REQUIRED);

		formatValidationHelper.add(emailAttr);

		formatValidationHelper.setProceedOnError(Boolean.TRUE);
		formatValidationHelper.execute(validationResult);
		return validationResult;
	}

	@Override
	public Response updateFirmwareVersion(FirmwareVersion firmwareVersion) {
		List<Warning> warnings = new ArrayList<>();
		// Step 1: prevalidate
		ValidationResult validationResult = validateFirmwareVersionRequest(firmwareVersion, warnings);
		// TODO: check validationResult is null or not. Since
		// apiOperation.preValidate
		// may return optional of null value.
		if (CollectionUtils.isNotEmpty(validationResult.getErrorList())) {
			LOGGER.error("updateFirmwareVersion PreValidation check has failed cannot proceed further ",
					validationResult.getErrorList());
			throw new ServiceValidationException(
					"updateFirmwareVersion PreValidation check has failed cannot proceed further",
					validationResult.getStatusOnError(), validationResult.getErrorList());
		}
		warnings.addAll(validationResult.getWarningList());
		firmwareVersion.setModifiedBy(authentication.getAuthUserDetails().getUserId());

		// Step 2: process
		firmwareVersion = assetManagementService.updateFirmwareVersion(firmwareVersion);
		FirmwareVersionResponse response = new FirmwareVersionResponse();
		response.setFirmwareVersion(firmwareVersion);

		// Step 5: build a successful response
		SuccessResponse<FirmwareVersionResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response deleteFirmwareVersion(int firmwareVersionId, int assetFirmwareVersionId) {
		assetManagementService.deleteFirmwareVersion(firmwareVersionId, assetFirmwareVersionId,
				authentication.getAuthUserDetails().getUserId());

		// Step 5: build a successful response
		CommonResponse response = new CommonResponse();
		response.setMessage("Firmware Version has been deleted successfully");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getFirmwareVersionById(int firmwareVersionId) {
		FirmwareVersion firmwareVersion = assetManagementService.getFirmwareVersionById(firmwareVersionId);
		FirmwareVersionResponse response = new FirmwareVersionResponse();
		response.setFirmwareVersion(firmwareVersion);
		SuccessResponse<FirmwareVersionResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getFirmwareVersions(AssetFirmwareVersionsFilter filter) {
		FirmwareVersionListResponse response = assetManagementService.getFirmwareVersions(filter);
		SuccessResponse<FirmwareVersionListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getAllFirmwareVersions(String assetType, String assetModel) {
		FirmwareVersionListResponse response = assetManagementService.getAllFirmwareVersions(assetType, assetModel);
		SuccessResponse<FirmwareVersionListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response bulkAssetUpload(InputStream uploadedInputStream, FormDataContentDisposition fileDetail) {

		if (StringUtils.isNotBlank(fileDetail.getFileName())
				&& FilenameUtils.getExtension(fileDetail.getFileName()).equals("xlsx")) {
			Integer userId = authentication.getAuthUserDetails().getUserId();
			int[] response = assetManagementService.bulkAssetUpload(uploadedInputStream, fileDetail, userId);
			SuccessResponse<Integer> successResponse = new SuccessResponse<Integer>();
			successResponse.setServiceResponse(response.length);

			return responseBuilder.buildResponse(successResponse);
		} else {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setStatus(new ResponseStatus(Boolean.FALSE, Status.BAD_REQUEST.getStatusCode()));
			return responseBuilder.buildResponse(errorResponse);
		}

	}

	@Override
	public Response downloadBulkAssetUploadFile() {
		Workbook workbook = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			workbook = assetManagementService.generateBulkUploadExcel();
			workbook.write(baos);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (workbook != null) {
					workbook.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		ResponseBuilder response = Response.ok((Object) baos.toByteArray()).header("Content-Disposition",
				"attachment; filename=\"Bulk Asset Upload Template.xlsx\"");
		return response.build();
	}

	@Override
	public Response getBulkUploadDevicesList(BaseFilter filter) {
		int userId = authentication.getAuthUserDetails().getUserId();
		filter.setUserId(userId);
		BulkAssetUploadResponse response = assetManagementService.getBulkUploadDevicesList(filter);

		SuccessResponse<BulkAssetUploadResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response saveBulkUploadDevicesInfo(BulkAssetUploadRequest request) {
		Integer userId = authentication.getAuthUserDetails().getUserId();
		request.setUserId(userId);
		DeviceInfo result = assetManagementService.saveBulkUploadDevicesInfo(request);
		if (result.getDeviceId() > 0) {
			SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
			return responseBuilder.buildResponse(successResponse);
		} else {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setStatus(new ResponseStatus(Boolean.FALSE, Status.BAD_REQUEST.getStatusCode()));
			return responseBuilder.buildResponse(errorResponse);
		}

	}

	@Override
	public Response getAllAssetTypes() {
		List<AssetType> assetType = assetManagementService.getAllAssetTypes();
		AssetTypeResponse response = new AssetTypeResponse();
		response.setAssetTypeList(assetType);
		SuccessResponse<AssetTypeResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getDeviceModelById(String assetType) {
		List<DeviceModel> deviceModel = assetManagementService.getDeviceModelById(assetType);
		DeviceModelResponse response = new DeviceModelResponse();
		response.setDeviceModel(deviceModel);
		SuccessResponse<DeviceModelResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getAssetUnAssignReason() {
		List<DeviceUnAssignReason> reasonList = assetManagementService.getDeviceUnAssinReason();
		DeviceUnAssignResponse response = new DeviceUnAssignResponse();
		response.setUnAssignReason(reasonList);
		SuccessResponse<DeviceUnAssignResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getAssetHistory(int deviceId) {
		List<AssetHistory> reasonList = assetManagementService.getAssetHistory(deviceId);
		AssetHistoryResponse response = new AssetHistoryResponse();
		response.setAssetHistory(reasonList);
		SuccessResponse<AssetHistoryResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getUnConfiguredDevices() {
		AssetResponse response = assetManagementService.getUnConfiguredDevices();
		SuccessResponse<AssetResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response unassignAsset(UnassignAssetRequest request) {
		Integer userId = authentication.getAuthUserDetails().getUserId();
		request.setUserId(userId);

		assetManagementService.unassignAsset(request);

		SuccessResponse<DeviceInfo> successResponse = new SuccessResponse<>();
		// successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);

	}

	@Override
	public Response getPetStudiesByAsset(int deviceId) {
		List<PetStudyDevice> petDevices = assetManagementService.getPetStudiesByAsset(deviceId);
		PetDevicesResponse response = new PetDevicesResponse();
		response.setPetDevices(petDevices);
		SuccessResponse<PetDevicesResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response manageAssetStudyMapping(AssetStudyMappingRequest request) {
		Integer userId = authentication.getAuthUserDetails().getUserId();

		assetManagementService.manageAssetStudyMapping(request, userId);
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setMessage("Asset Study mapping has been successful");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getSensorsListBySsId(String ssId) {
		
		List<Asset> Asset = assetManagementService.getSensorsListBySsId(ssId);
		AssetResponseList response = new AssetResponseList();
		response.setAsset(Asset);
		SuccessResponse<AssetResponseList> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getSensorsList(AssetParam filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		AssetResponseList response = assetManagementService.getSensorsList(filter);
		SuccessResponse<AssetResponseList> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getAssetStatus() {
		
		List<com.hillspet.wearables.dto.Status> status = assetManagementService.getAssetStatus();
		StatusResponse response = new StatusResponse();
		response.setStatusList(status);
		SuccessResponse<StatusResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}
	
	@Override
	public Response bulkWhiteListing(BulkWhiteListingRequest request) {
		Integer userId = authentication.getAuthUserDetails().getUserId();
		assetManagementService.bulkWhiteListing(request, userId);
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setMessage("bulkWhiteListing has been successful");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}
}
