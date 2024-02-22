package com.hillspet.wearables.service.asset.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
//import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.asset.AssetManagementDao;
import com.hillspet.wearables.dao.lookup.LookupDao;
import com.hillspet.wearables.dao.study.StudyDao;
import com.hillspet.wearables.dto.Asset;
import com.hillspet.wearables.dto.AssetHistory;
import com.hillspet.wearables.dto.AssetType;
import com.hillspet.wearables.dto.BulkAssetUploadDeviceInfo;
import com.hillspet.wearables.dto.DeviceInfo;
import com.hillspet.wearables.dto.DeviceLocation;
import com.hillspet.wearables.dto.DeviceModel;
import com.hillspet.wearables.dto.DeviceUnAssignReason;
import com.hillspet.wearables.dto.FirmwareVersion;
import com.hillspet.wearables.dto.PetStudyDevice;
import com.hillspet.wearables.dto.StudyListDTO;
import com.hillspet.wearables.dto.filter.AssetFirmwareVersionsFilter;
import com.hillspet.wearables.dto.filter.AssetUpdateFirmwareFilter;
import com.hillspet.wearables.dto.filter.AssetsFilter;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.request.AssetStudyMappingRequest;
import com.hillspet.wearables.request.BulkAssetUploadRequest;
import com.hillspet.wearables.request.UnassignAssetRequest;
import com.hillspet.wearables.response.AssetResponse;
import com.hillspet.wearables.response.BulkAssetUploadResponse;
import com.hillspet.wearables.response.DeviceInfoListResponse;
import com.hillspet.wearables.response.DeviceResponse;
import com.hillspet.wearables.response.FirmwareVersionListResponse;
import com.hillspet.wearables.service.asset.AssetManagementService;

@Service
public class AssetManagementServiceImpl implements AssetManagementService {

	private static final Logger LOGGER = LogManager.getLogger(AssetManagementServiceImpl.class);

	@Autowired
	private AssetManagementDao assetManagementDao;

	@Autowired
	private StudyDao studyDao;

	@Autowired
	private LookupDao lookupDao;

	@Override
	public DeviceInfo addDeviceInfo(DeviceInfo deviceInfo) throws ServiceExecutionException {
		return assetManagementDao.addDeviceInfo(deviceInfo);
	}

	@Override
	public DeviceInfo updateDeviceInfo(DeviceInfo deviceInfo) throws ServiceExecutionException {
		return assetManagementDao.updateDeviceInfo(deviceInfo);
	}

	@Override
	public void deleteDeviceInfo(int deviceId, int modifiedBy) throws ServiceExecutionException {
		assetManagementDao.deleteDeviceInfo(deviceId, modifiedBy);
	}

	@Override
	public AssetResponse getAssetList(AssetsFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getAssetsList called");
		Map<String, Integer> mapper = assetManagementDao.getAssetCount(filter);
		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");

		List<Asset> assetsList = total > 0 ? assetManagementDao.getAssetList(filter) : new ArrayList<>();
		AssetResponse response = new AssetResponse();
		response.setAssetList(assetsList);
		response.setNoOfElements(assetsList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);

		LOGGER.debug("getAssets getAssetsList is {}", assetsList);
		LOGGER.debug("getAssetsList completed successfully");
		return response;
	}

	@Override
	public void updateDeviceFirmware(int firmwareVersionId, String deviceIds, int modifiedBy, String assetModel)
			throws ServiceExecutionException {
		assetManagementDao.updateDeviceFirmware(firmwareVersionId, deviceIds, modifiedBy, assetModel);
	}

	@Override
	public DeviceInfo getDeviceInfoById(int deviceId) throws ServiceExecutionException {
		return assetManagementDao.getDeviceInfoById(deviceId);
	}

	@Override
	public DeviceInfoListResponse getDeviceInfo(AssetUpdateFirmwareFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getDeviceInfo called");

		Map<String, Integer> mapper = assetManagementDao.getDeviceCount(filter);

		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<DeviceInfo> deviceInfoList = total > NumberUtils.INTEGER_ZERO ? assetManagementDao.getDeviceInfo(filter)
				: new ArrayList<>();

		DeviceInfoListResponse response = new DeviceInfoListResponse();
		response.setDeviceInfoList(deviceInfoList);
		response.setNoOfElements(deviceInfoList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);

		LOGGER.debug("getDeviceInfo deviceInfoList is {}", deviceInfoList);
		LOGGER.debug("getDeviceInfo completed successfully");
		return response;
	}

	@Override
	public List<DeviceInfo> getAllDevices(String studyIds) throws ServiceExecutionException {
		LOGGER.debug("getAllDevices called");
		List<DeviceInfo> deviceInfos = assetManagementDao.getAllDevices(studyIds);
		LOGGER.debug("getAllDevices deviceInfos are {}", deviceInfos);
		LOGGER.debug("getAllDevices completed successfully");
		return deviceInfos;
	}

	@Override
	public DeviceResponse getDeviceTypesAndModels() throws ServiceExecutionException {
		LOGGER.debug("getDeviceTypesAndModels called");
		DeviceResponse deviceResponse = assetManagementDao.getDeviceTypesAndModels();
		LOGGER.debug("getDeviceTypesAndModels deviceResponse is {}", deviceResponse);
		LOGGER.debug("getDeviceTypesAndModels completed successfully");
		return deviceResponse;
	}

	@Override
	public FirmwareVersion addFirmwareVersion(FirmwareVersion firmwareVersion) throws ServiceExecutionException {
		return assetManagementDao.addFirmwareVersion(firmwareVersion);
	}

	@Override
	public FirmwareVersion updateFirmwareVersion(FirmwareVersion firmwareVersion) throws ServiceExecutionException {
		return assetManagementDao.updateFirmwareVersion(firmwareVersion);
	}

	@Override
	public void deleteFirmwareVersion(int firmwareVersionId, int assetFirmwareVersionId, int modifiedBy)
			throws ServiceExecutionException {
		assetManagementDao.deleteFirmwareVersion(firmwareVersionId, assetFirmwareVersionId, modifiedBy);
	}

	@Override
	public FirmwareVersion getFirmwareVersionById(int firmwareVersionId) throws ServiceExecutionException {
		return assetManagementDao.getFirmwareVersionById(firmwareVersionId);
	}

	@Override
	public FirmwareVersionListResponse getFirmwareVersions(AssetFirmwareVersionsFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getFirmwareVersions called");

		Map<String, Integer> mapper = assetManagementDao.getFirmwareVersionCount(filter);
		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<FirmwareVersion> firmwareVersions = total > NumberUtils.INTEGER_ZERO
				? assetManagementDao.getFirmwareVersions(filter)
				: new ArrayList<>();

		FirmwareVersionListResponse response = new FirmwareVersionListResponse();
		response.setFirmwareVersionList(firmwareVersions);
		response.setNoOfElements(firmwareVersions.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);

		LOGGER.debug("getFirmwareVersions firmwareVersions is {}", firmwareVersions);
		LOGGER.debug("getFirmwareVersions completed successfully");
		return response;
	}

	@Override
	public FirmwareVersionListResponse getAllFirmwareVersions(String assetType, String assetModel)
			throws ServiceExecutionException {
		LOGGER.debug("getAllFirmwareVersions called");
		List<FirmwareVersion> firmwareVersions = assetManagementDao.getAllFirmwareVersions(assetType, assetModel);

		FirmwareVersionListResponse response = new FirmwareVersionListResponse();
		response.setFirmwareVersions(firmwareVersions);

		LOGGER.debug("getAllFirmwareVersions firmwareVersions is {}", firmwareVersions);
		LOGGER.debug("getAllFirmwareVersions completed successfully");
		return response;
	}

	@Override
	public int[] bulkAssetUpload(InputStream uploadedInputStream, FormDataContentDisposition fileDetail, Integer userId)
			throws ServiceExecutionException {
		LOGGER.debug("bulkAssetUpload called");
		int[] result = new int[] {};
		List<BulkAssetUploadDeviceInfo> list = convertBulkExcelToDeviceList(uploadedInputStream, userId);
		if ((list == null || list.size() == 0)) {
			return result;
		}
		LOGGER.debug("addDeviceInfoBulkUploadPreview bulkAssetUpload size is " + list.size());
		result = assetManagementDao.saveBulkDevicesToStaging(list, fileDetail.getFileName(), userId);

		LOGGER.debug("bulkAssetUpload - saveBulkDevicesToStaging return result size is " + result);
		LOGGER.debug("bulkAssetUpload completed successfully");
		return result;
	}

	@Override
	public DeviceInfo saveBulkUploadDevicesInfo(BulkAssetUploadRequest request) throws ServiceExecutionException {
		LOGGER.debug("saveDevicesInfoBulkUpload called");

		DeviceInfo device = assetManagementDao.saveBulkUploadDevicesInfo(request);

		LOGGER.debug("saveDevicesInfoBulkUpload completed successfully");
		return device;
	}

	@Override
	public List<BulkAssetUploadDeviceInfo> convertBulkExcelToDeviceList(InputStream uploadedInputStream, int userId)
			throws ServiceExecutionException {
		List<BulkAssetUploadDeviceInfo> addDeviceList = new ArrayList<>();
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(uploadedInputStream);

			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();

			// LIst of headers from excel
			Row headerRow = sheet.getRow(0);
			List<String> headers = new ArrayList<String>();
			Iterator<Cell> cells = headerRow.cellIterator();
			while (cells.hasNext()) {
				Cell cell = (Cell) cells.next();
				RichTextString value = cell.getRichStringCellValue();
				headers.add(value.getString());
			}
			// validate the template headers
			boolean headerValidation = validateHeaders(headers);
			// if validation fails then write back the message to user.
			if (!headerValidation) {
				// invalid file.
				throw new ServiceExecutionException("Invalid File");
				// return addDeviceList;
			}

			while (rows.hasNext()) {

				Row currentRow = rows.next();

				if (currentRow.getRowNum() < 2) {
					continue; // just skip the rows if row number is 0 or 1
				}
				if (checkIfRowIsEmpty(currentRow)) {
					continue;
				}

				int cellIdx = 0;
				BulkAssetUploadDeviceInfo deviceInfo = new BulkAssetUploadDeviceInfo();
				List<String> errorsList = new ArrayList<String>();
				for (int cn = 0; cn < currentRow.getLastCellNum(); cn++) { // cn- cell number

					// System.out.println(currentRow.getLastCellNum());

					Cell cell = currentRow.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					DataFormatter formatter = new DataFormatter();
					String value = formatter.formatCellValue(cell).trim();
					int valueLength = value.trim().length();

					switch (cellIdx) {
					case 0:
						Cell cellNxt = currentRow.getCell(cn + 2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						String assetModelVal = formatter.formatCellValue(cellNxt).trim();

						if (valueLength == 0) {
							errorsList.add("Asset number is mandatory and allows maximum 20 characters");
						} else {
							// Max Length validation
							if (value.trim().length() > 20) {
								errorsList.add("Invalid asset number (" + value + "), allows maximum 20 characters");
								value = "";

							} else if (!validateAssetNumber(value.trim())
									&& (assetModelVal.indexOf("AGL") != -1 || assetModelVal.indexOf("CMAS") != -1)) { 
								errorsList.add("Asset number is invalid");
							}
						}

						deviceInfo.setDeviceNumber(value);
						break;
					case 1:
						if (valueLength == 0) {
							errorsList.add("Asset type is mandatory and allows maximum 100 characters");
						} else {
							if (value.trim().length() > 100) {
								errorsList.add("Invalid asset type (" + value + "), allows maximum 100 characters");
								value = "";
							}
						}
						deviceInfo.setDeviceType(value);
						break;

					case 2:
						if (valueLength == 0) {
							errorsList.add("Asset model is mandatory and allows maximum 100 characters");
						} else {
							if (value.trim().length() > 100) {
								errorsList.add("Invalid asset model (" + value + "), allows maximum 100 characters");
								value = "";
							}
						}
						deviceInfo.setDeviceModel(value);
						break;

					case 3:
						if (valueLength == 0) {
							errorsList.add("Asset location is mandatory and allows maximum 100 characters");
						} else {
							if (value.trim().length() > 100) {
								errorsList.add("Invalid asset location (" + value + "), allows maximum 100 characters");
								value = "";
							}
						}
						deviceInfo.setLocation(value);
						break;

					case 4:
						if (valueLength == 0) {
							errorsList.add("Serial number is mandatory and allows maximum 50 characters");
						} else {
							if (value.trim().length() > 50) {
								errorsList.add("Invalid serial number (" + value + "), allows maximum 50 characters");
								value = "";
							}
						}
						deviceInfo.setMfgSerialNumber(value);
						break;

					case 5:
						deviceInfo.setMfgFirmware(value);
						break;

					case 6:
						deviceInfo.setMfgMacAddr(value);
						break;
					case 7:
						deviceInfo.setWifiMacAddr(value);
						break;
					case 8:
						deviceInfo.setTrackingNumber(value);
						break;
					case 9:
						deviceInfo.setStudyName(value);
						if (StringUtils.isNotBlank(value)) {
							Integer studyId = getStudyIdByName(value, userId);
							if (studyId != null && studyId > 0) {
								deviceInfo.setStudyId(studyId);
							} else {
								deviceInfo.setExceptionMsg("Study Name is Invalid");
							}
						}
						break;
					default:
						break;
					}
					cellIdx++;
				}
				if (errorsList.size() > 0) {
					deviceInfo.setExceptionMsg(StringUtils.join(errorsList, ","));
				}
				addDeviceList.add(deviceInfo);
			}

		} catch (Exception e) {
			LOGGER.debug("convertBulkExcelToDeviceList Exception" + e.getMessage());
			throw new ServiceExecutionException(e.getMessage());
		} finally {
			try {
				workbook.close();
				uploadedInputStream.close();
			} catch (IOException e) {
				LOGGER.debug("convertBulkExcelToDeviceList IOException" + e.getMessage());
			}
		}
		return addDeviceList;
	}

	private boolean validateHeaders(List<String> givenHeaders) {
		if (!givenHeaders.isEmpty()) {
			String[] validHeaders = new String[] { "Asset Number", "Asset Type", "Asset Model", "Asset Location",
					"Manufacturer Serial Number", "Manufacturer Firmware", "Bluetooth MAC Address", "Wifi MAC Address",
					"Tracking Number", "Study Name" };

			for (int i = 0; i < givenHeaders.size(); i++) {
				if (!Arrays.asList(validHeaders).contains(givenHeaders.get(i))) {
					return false;
				}
			}

		}
		return true;
	}

	private boolean checkIfRowIsEmpty(Row row) {
		if (row == null) {
			return true;
		}
		if (row.getLastCellNum() <= 0) {
			return true;
		}
		for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
			Cell cell = row.getCell(cellNum);
			if (cell != null && cell.getCellType() != CellType.BLANK && StringUtils.isNotBlank(cell.toString())
					&& (!cell.toString().equals("Asset Type") && !cell.toString().equals("Asset Model")
							&& !cell.toString().equals("Asset Location"))) {
				return false;
			}
		}
		return true;
	}

	public boolean validateAssetNumber(String assetNumber) {
		boolean isValid = true;
		String regEx = "^[0-9A-F]*$";
		assetNumber = assetNumber.replaceAll("_", "").replaceAll("-", "");
		if (!assetNumber.matches(regEx) || assetNumber.length() != 7) {
			return isValid = false;
		}
		String lastChar = assetNumber.charAt(assetNumber.length() - 1) + "";
		assetNumber = "0C8A87" + assetNumber.substring(0, assetNumber.length() - 1);
		String hex;
		int sum = 0;
		int n = 16;
		int checkedChar;
		for (int i = assetNumber.length() - 1; i >= 0; i--) {
			int currentValue = 0;

			int codePoint = Integer.parseInt(Character.toString(assetNumber.charAt(i)), n);
			if ((i + 1) % 2 == 0) {
				hex = Integer.toHexString(codePoint * 2);

				for (int j = 0; j < hex.length(); j++) {
					boolean isNumber = hex.substring(j, j + 1).matches("^[0-9]*$");
					if (isNumber) {
						currentValue += Integer.parseInt(hex.substring(j, j + 1));
					} else {
						currentValue += Integer.parseInt(hex.substring(j, j + 1), n);
					}
				}
			} else {
				currentValue += codePoint;
			}
			sum += currentValue;
		}

		if (sum % 16 > 0) {
			checkedChar = 16 - (sum % 16);
		} else {
			checkedChar = 0;
		}
		if (!lastChar.equals(Integer.toHexString(checkedChar).toUpperCase())) {
			return isValid = false;
		}

		return isValid;
	}

	@Override
	public BulkAssetUploadResponse getBulkUploadDevicesList(BaseFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getBulkUploadDevicesList called");
		int total = assetManagementDao.getBulkUploadDevicesListCount(filter);

		List<BulkAssetUploadDeviceInfo> assetsList = total > 0 ? assetManagementDao.getBulkUploadDevicesList(filter)
				: new ArrayList<>();
		BulkAssetUploadResponse response = new BulkAssetUploadResponse();
		response.setDeviceInfoList(assetsList);
		response.setNoOfElements(assetsList.size());
		response.setSearchElments(total);
		response.setTotalRecords(total);

		LOGGER.debug("getBulkUploadDevicesList is {}", assetsList);
		LOGGER.debug("getBulkUploadDevicesList completed successfully");
		return response;
	}

	@Override
	public List<AssetType> getAllAssetTypes() throws ServiceExecutionException {
		LOGGER.debug("getAllAssetTypes called");
		List<AssetType> deviceInfos = assetManagementDao.getAllAssetTypes();
		LOGGER.debug("getAllDevices deviceInfos are {}", deviceInfos);
		LOGGER.debug("getAllDevices completed successfully");
		return deviceInfos;
	}

	@Override
	public List<DeviceModel> getDeviceModelById(String assetType) throws ServiceExecutionException {
		LOGGER.debug("getAllDevices called");
		List<DeviceModel> deviceInfos = assetManagementDao.getDeviceModelById(assetType);
		LOGGER.debug("getAllDevices deviceInfos are {}", deviceInfos);
		LOGGER.debug("getAllDevices completed successfully");
		return deviceInfos;
	}

	@Override
	public Workbook generateBulkUploadExcel() {
		LOGGER.debug("generateBulkUploadExcel called");
		Workbook workbook = null;
		workbook = new XSSFWorkbook();

		try {

			Sheet sheet = workbook.createSheet("Asset Information");
			String[] validHeaders = new String[] { "Asset Number", "Asset Type", "Asset Model", "Asset Location",
					"Manufacturer Serial Number", "Manufacturer Firmware", "Bluetooth MAC Address", "Wifi MAC Address",
					"Tracking Number", "Study Name" };

			DeviceResponse deviceResponse = assetManagementDao.getDeviceTypesAndModels();
			List<String> modelList = new ArrayList<>();
			modelList.add("-");
			List<String> deviceModelList = deviceResponse.getDeviceModels();
			Collections.sort(deviceModelList);
			modelList.addAll(deviceModelList);
			modelList.add("-");
			List<String> assetTypeList = deviceResponse.getDeviceTypes();
			List<DeviceLocation> assetLocation = lookupDao.getDeviceLocationsBulkUpload();
			List<FirmwareVersion> firmWareList = assetManagementDao.getAllFirmwareVersionsList();
			List<StudyListDTO> studyList = studyDao.getStudyList(8989, "Yes", "No");

			String[] modelArray = null;
			String[] assetTypeArray = null;
			String[] assetLocations = new String[assetLocation.size()];
			String[] firmwareArray = new String[firmWareList.size()];
			String[] studyArray = new String[studyList.size()];

			int index = 0;
			for (DeviceLocation value : assetLocation) {
				assetLocations[index] = value.getLocation();
				index++;
			}

			if (modelList.size() > 0) {
				modelArray = modelList.toArray(new String[0]);
			}
			if (assetTypeList.size() > 0) {
				assetTypeArray = assetTypeList.toArray(new String[0]);
			}

			List<String> firmwareArrayList = new ArrayList<>();

			for (FirmwareVersion value : firmWareList) {
				firmwareArrayList.add(value.getFirmwareVersionNumber());
			}
			if (firmwareArrayList.size() > 0) {
				firmwareArray = firmwareArrayList.toArray(new String[0]);
			}

			List<String> assetArrayList = new ArrayList<>();

			for (StudyListDTO value : studyList) {
				assetArrayList.add(value.getStudyName());
			}
			if (assetArrayList.size() > 0) {
				studyArray = assetArrayList.toArray(new String[0]);
			}

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			// headerFont.setFontName("Arial Narrow");
			headerFont.setColor(IndexedColors.BROWN.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerCellStyle.setFont(headerFont);

			headerCellStyle.setBorderTop(BorderStyle.MEDIUM);
			headerCellStyle.setBorderRight(BorderStyle.MEDIUM);
			headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
			headerCellStyle.setBorderLeft(BorderStyle.MEDIUM);
			headerCellStyle.setWrapText(true);

			// Row for Header

			CellStyle messageCellStyle = workbook.createCellStyle();
			messageCellStyle.setBorderTop(BorderStyle.MEDIUM);
			messageCellStyle.setBorderRight(BorderStyle.MEDIUM);
			messageCellStyle.setBorderBottom(BorderStyle.MEDIUM);
			messageCellStyle.setBorderLeft(BorderStyle.MEDIUM);
			messageCellStyle.setWrapText(true);
			Font messageFont = workbook.createFont();
			messageFont.setColor(IndexedColors.BROWN.getIndex());
			messageCellStyle.setFont(messageFont);

			Row headerRow = sheet.createRow(0);
			for (int col = 0; col < validHeaders.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(validHeaders[col]);
				cell.setCellStyle(headerCellStyle);
				if (validHeaders[col].equals("Manufacturer Firmware")) {
					CellStyle headerCellStyle2 = workbook.createCellStyle();
					headerCellStyle2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
					headerCellStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					headerCellStyle2.setBorderTop(BorderStyle.MEDIUM);
					headerCellStyle2.setBorderRight(BorderStyle.MEDIUM);
					headerCellStyle2.setBorderBottom(BorderStyle.MEDIUM);
					headerCellStyle2.setBorderLeft(BorderStyle.MEDIUM);
					headerCellStyle2.setWrapText(true);
					Font messageFont2 = workbook.createFont();
					messageFont2.setColor(IndexedColors.BLACK.getIndex());
					headerCellStyle2.setFont(messageFont2);
					cell.setCellStyle(headerCellStyle2);
				}
				if (validHeaders[col].equals("Bluetooth MAC Address") || validHeaders[col].equals("Wifi MAC Address")) {
					CellStyle headerCellStyle3 = workbook.createCellStyle();
					headerCellStyle3.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
					headerCellStyle3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					headerCellStyle3.setBorderTop(BorderStyle.MEDIUM);
					headerCellStyle3.setBorderRight(BorderStyle.MEDIUM);
					headerCellStyle3.setBorderBottom(BorderStyle.MEDIUM);
					headerCellStyle3.setBorderLeft(BorderStyle.MEDIUM);
					headerCellStyle3.setWrapText(true);
					Font messageFont3 = workbook.createFont();
					messageFont3.setColor(IndexedColors.BLACK.getIndex());
					headerCellStyle3.setFont(messageFont3);
					cell.setCellStyle(headerCellStyle3);
				}

				/*
				 * if(validHeaders[col].equals("Manufacturer MAC Address")) { CellStyle
				 * headerCellStyle2 = workbook.createCellStyle();
				 * headerCellStyle2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
				 * headerCellStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				 * headerCellStyle2.setBorderTop(BorderStyle.MEDIUM);
				 * headerCellStyle2.setBorderRight(BorderStyle.MEDIUM);
				 * headerCellStyle2.setBorderBottom(BorderStyle.MEDIUM);
				 * headerCellStyle2.setBorderLeft(BorderStyle.MEDIUM);
				 * headerCellStyle2.setWrapText(true); Font messageFont2 =
				 * workbook.createFont(); messageFont2.setColor(IndexedColors.BLACK.getIndex());
				 * headerCellStyle2.setFont(messageFont2); cell.setCellStyle(headerCellStyle2);
				 * }
				 */
				if (validHeaders[col].equals("Tracking Number")) {
					CellStyle headerCellStyle2 = workbook.createCellStyle();
					headerCellStyle2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
					headerCellStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					headerCellStyle2.setBorderTop(BorderStyle.MEDIUM);
					headerCellStyle2.setBorderRight(BorderStyle.MEDIUM);
					headerCellStyle2.setBorderBottom(BorderStyle.MEDIUM);
					headerCellStyle2.setBorderLeft(BorderStyle.MEDIUM);
					headerCellStyle2.setWrapText(true);
					Font messageFont2 = workbook.createFont();
					messageFont2.setColor(IndexedColors.BLACK.getIndex());
					headerCellStyle2.setFont(messageFont2);
					cell.setCellStyle(headerCellStyle2);
				}

				if (validHeaders[col].equals("Study Name")) {
					CellStyle headerCellStyle2 = workbook.createCellStyle();
					headerCellStyle2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
					headerCellStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					headerCellStyle2.setBorderTop(BorderStyle.MEDIUM);
					headerCellStyle2.setBorderRight(BorderStyle.MEDIUM);
					headerCellStyle2.setBorderBottom(BorderStyle.MEDIUM);
					headerCellStyle2.setBorderLeft(BorderStyle.MEDIUM);
					headerCellStyle2.setWrapText(true);
					Font messageFont2 = workbook.createFont();
					messageFont2.setColor(IndexedColors.BLACK.getIndex());
					headerCellStyle2.setFont(messageFont2);
					cell.setCellStyle(headerCellStyle2);
				}
			}

			// Row for Message header
			Row messageRow = sheet.createRow(1);
			for (int col = 0; col < validHeaders.length; col++) {
				Cell cell = messageRow.createCell(col);
				String message = "";
				CellStyle style = workbook.createCellStyle();
				sheet.setColumnWidth(col, 22 * 256);
				cell.setCellStyle(messageCellStyle);
				switch (validHeaders[col]) {
				case "Asset Number":
					message = "Length: Max. 20 characters\n" + "Required: Yes";
					break;
				case "Asset Type":
					message = "Length: Max. 100 characters\n" + "Required: Yes";
					break;
				case "Asset Model":
					message = "Length: Max. 100 characters\n" + "Required: Yes";
					break;
				case "Asset Location":
					message = "Length: Max. 100 characters \n" + "Required: Yes";
					break;
				case "Manufacturer Serial Number":
					message = "Length: Max. 50 characters \n" + "Required: Yes";
					break;
				case "Manufacturer Firmware":
					message = "Length: Max. 50 characters \n" + "Required: No";
					CellStyle messageCellStyle3 = workbook.createCellStyle();
					messageCellStyle3.setBorderTop(BorderStyle.MEDIUM);
					messageCellStyle3.setBorderRight(BorderStyle.MEDIUM);
					messageCellStyle3.setBorderBottom(BorderStyle.MEDIUM);
					messageCellStyle3.setBorderLeft(BorderStyle.MEDIUM);
					messageCellStyle3.setWrapText(true);
					Font messageFont3 = workbook.createFont();
					messageFont3.setColor(IndexedColors.BLACK.getIndex());
					messageCellStyle3.setFont(messageFont3);
					cell.setCellStyle(messageCellStyle3);
					break;
				case "Bluetooth MAC Address":
					message = "Length: Max. 20 characters \n" + "Required: No";
					CellStyle messageCellStyle4 = workbook.createCellStyle();
					messageCellStyle4.setBorderTop(BorderStyle.MEDIUM);
					messageCellStyle4.setBorderRight(BorderStyle.MEDIUM);
					messageCellStyle4.setBorderBottom(BorderStyle.MEDIUM);
					messageCellStyle4.setBorderLeft(BorderStyle.MEDIUM);
					messageCellStyle4.setWrapText(true);
					Font messageFont4 = workbook.createFont();
					messageFont4.setColor(IndexedColors.BLACK.getIndex());
					messageCellStyle4.setFont(messageFont4);
					cell.setCellStyle(messageCellStyle4);
					break;
				case "Wifi MAC Address":
					message = "Length: Max. 20 characters \n" + "Required: No";
					CellStyle messageCellStyle5 = workbook.createCellStyle();
					messageCellStyle5.setBorderTop(BorderStyle.MEDIUM);
					messageCellStyle5.setBorderRight(BorderStyle.MEDIUM);
					messageCellStyle5.setBorderBottom(BorderStyle.MEDIUM);
					messageCellStyle5.setBorderLeft(BorderStyle.MEDIUM);
					messageCellStyle5.setWrapText(true);
					Font messageFont5 = workbook.createFont();
					messageFont5.setColor(IndexedColors.BLACK.getIndex());
					messageCellStyle5.setFont(messageFont5);
					cell.setCellStyle(messageCellStyle5);
					break;
				case "Tracking Number":
					message = "Length: Max. 50 characters \n" + "Required: No";

					CellStyle messageCellStyle2 = workbook.createCellStyle();
					messageCellStyle2.setBorderTop(BorderStyle.MEDIUM);
					messageCellStyle2.setBorderRight(BorderStyle.MEDIUM);
					messageCellStyle2.setBorderBottom(BorderStyle.MEDIUM);
					messageCellStyle2.setBorderLeft(BorderStyle.MEDIUM);
					messageCellStyle2.setWrapText(true);
					Font messageFont2 = workbook.createFont();
					messageFont2.setColor(IndexedColors.BLACK.getIndex());
					messageCellStyle2.setFont(messageFont2);
					cell.setCellStyle(messageCellStyle2);
					break;
				case "Study Name":
					message = "Length: Max. 100 characters \n" + "Required: No";

					CellStyle messageCellStyle1 = workbook.createCellStyle();
					messageCellStyle1.setBorderTop(BorderStyle.MEDIUM);
					messageCellStyle1.setBorderRight(BorderStyle.MEDIUM);
					messageCellStyle1.setBorderBottom(BorderStyle.MEDIUM);
					messageCellStyle1.setBorderLeft(BorderStyle.MEDIUM);
					messageCellStyle1.setWrapText(true);
					Font messageFont1 = workbook.createFont();
					messageFont1.setColor(IndexedColors.BLACK.getIndex());
					messageCellStyle1.setFont(messageFont1);
					cell.setCellStyle(messageCellStyle1);
					break;
				}
				cell.setCellValue(message);

				style.setWrapText(true);

			}

			// Added model & asset Type dropdowns
			DataValidation dataValidation = null;
			DataValidationConstraint constraint = null;
			DataValidationHelper validationHelper = null;
			validationHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);

			Sheet sheet2 = workbook.createSheet("ListSheet");
			Sheet sheet3 = workbook.createSheet("ListSheet2");
			Sheet sheet4 = workbook.createSheet("ListSheet3");
			// for (int cn = 2; cn < 500; cn++) {

			Row row = sheet.createRow(2);

			row.createCell(1).setCellValue("");
			row.createCell(2).setCellValue("");
			row.createCell(3).setCellValue("");
			row.createCell(4).setCellValue("");
			row.createCell(5).setCellValue("");
			row.createCell(9).setCellValue("");
			validationHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);

			if (row.getCell(1).toString() == "") {
				Arrays.sort(assetTypeArray);
				CellRangeAddressList cellRange = new CellRangeAddressList(2, 500, 1, 1);
				try {
					constraint = validationHelper.createExplicitListConstraint(assetTypeArray);
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.error("assetTypeArray error.", e);
				}
				dataValidation = validationHelper.createValidation(constraint, cellRange);
				dataValidation.setSuppressDropDownArrow(true);

				sheet.addValidationData(dataValidation);
			}
			if (row.getCell(2).toString() == "") {
				// Arrays.sort(modelArray);

				for (int i = 0; i < modelArray.length - 1; i++) {
					sheet2.createRow(i).createCell(0).setCellValue(modelArray[i] + "");
				}

				sheet2.setSelected(false);

				Name namedCell = workbook.createName();
				namedCell.setNameName("SourceList");
				String reference = "ListSheet!$A$2:$A$500";
				namedCell.setRefersToFormula(reference);

				sheet.setActiveCell(new CellAddress("C3"));

				CellRangeAddressList cellRange = new CellRangeAddressList(2, 500, 2, 2);
				try {
					constraint = validationHelper.createFormulaListConstraint("SourceList");
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.debug("modelArray error.", e);
				}

				dataValidation = validationHelper.createValidation(constraint, cellRange);

				dataValidation.setSuppressDropDownArrow(true);

				sheet.addValidationData(dataValidation);
			}

			if (row.getCell(3).toString() == "") {
				CellRangeAddressList cellRange = new CellRangeAddressList(2, 500, 3, 3);
				constraint = validationHelper.createExplicitListConstraint(assetLocations);
				dataValidation = validationHelper.createValidation(constraint, cellRange);
				dataValidation.setSuppressDropDownArrow(true);

				sheet.addValidationData(dataValidation);
			}
			if (row.getCell(5).toString() == "") {

				Arrays.sort(firmwareArray);
				for (int i = 0; i < firmwareArray.length; i++) {
					sheet3.createRow(i).createCell(0).setCellValue(firmwareArray[i] + "");
				}

				sheet3.setSelected(false);

				Name namedCell = workbook.createName();
				namedCell.setNameName("SourceList2");
				String reference = "ListSheet2!$A$2:$A$500";
				namedCell.setRefersToFormula(reference);

				sheet.setActiveCell(new CellAddress("F3"));

				CellRangeAddressList cellRange = new CellRangeAddressList(2, 500, 5, 5);
				try {
					constraint = validationHelper.createFormulaListConstraint("SourceList2");
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.debug("firmwareArray error.", e);
				}

				dataValidation = validationHelper.createValidation(constraint, cellRange);

				dataValidation.setSuppressDropDownArrow(true);

				sheet.addValidationData(dataValidation);
			}

			if (row.getCell(9).toString() == "") {

				Arrays.sort(studyArray);
				for (int i = 0; i < studyArray.length - 1; i++) {
					sheet4.createRow(i).createCell(0).setCellValue(studyArray[i] + "");
				}
				sheet4.setSelected(false);

				Name namedCell = workbook.createName();
				namedCell.setNameName("SourceList3");
				String reference = "ListSheet3!$A$2:$A$500";
				namedCell.setRefersToFormula(reference);

				sheet.setActiveCell(new CellAddress("J3"));

				CellRangeAddressList cellRange = new CellRangeAddressList(2, 500, 9, 9);
				try {
					constraint = validationHelper.createFormulaListConstraint("SourceList3");
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.debug("studyArray error.", e);
				}

				dataValidation = validationHelper.createValidation(constraint, cellRange);
				dataValidation.setSuppressDropDownArrow(true);
				sheet.addValidationData(dataValidation);
			}

			workbook.setSheetHidden(1, true);
			workbook.setSheetHidden(2, true);
			workbook.setSheetHidden(3, true);
			// }
			LOGGER.debug("generateBulkUploadExcel ended.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	public static String[] addElement(String s[], String e, int position) {
		String[] temp = new String[s.length + 1];
		temp[position] = e;
		System.arraycopy(s, 0, temp, 1, s.length);
		return temp;
	}

	@Override
	public List<DeviceUnAssignReason> getDeviceUnAssinReason() throws ServiceExecutionException {
		LOGGER.debug("getDeviceUnAssinReason called");
		List<DeviceUnAssignReason> list = assetManagementDao.getDeviceUnAssinReason();
		LOGGER.debug("getDeviceUnAssinReason completed successfully");
		return list;
	}

	@Override
	public List<AssetHistory> getAssetHistory(int deviceId) throws ServiceExecutionException {
		LOGGER.debug("getAssetHistory called");
		List<AssetHistory> list = assetManagementDao.getAssetHistory(deviceId);
		LOGGER.debug("getAssetHistory completed successfully");
		return list;
	}

	@Override
	public AssetResponse getUnConfiguredDevices() throws ServiceExecutionException {
		LOGGER.debug("getUnConfiguredDevices called");
		List<Asset> list = assetManagementDao.getUnConfiguredDevices();
		AssetResponse assetResponse = new AssetResponse();
		assetResponse.setUnConfiguredAssetList(list);
		LOGGER.debug("getUnConfiguredDevices completed successfully");
		return assetResponse;
	}

	@Override
	public void unassignAsset(UnassignAssetRequest request) throws ServiceExecutionException {
		LOGGER.debug("unassignAsset called");

		assetManagementDao.unassignAsset(request);

		LOGGER.debug("saveDevicesInfoBulkUpload completed successfully");
	}

	@Override
	public List<PetStudyDevice> getPetStudiesByAsset(int deviceId) throws ServiceExecutionException {
		LOGGER.debug("getPetStudiesByAsset called");
		List<PetStudyDevice> petDevices = assetManagementDao.getPetStudiesByAsset(deviceId);
		LOGGER.debug("getPetStudiesByAsset completed successfully");
		return petDevices;
	}

	@Override
	public void manageAssetStudyMapping(AssetStudyMappingRequest request, Integer userId)
			throws ServiceExecutionException {
		LOGGER.debug("manageAssetStudyMapping called");
		assetManagementDao.manageAssetStudyMapping(request, userId);
		LOGGER.debug("manageAssetStudyMapping completed successfully");
	}

	private Integer getStudyIdByName(String studyName, int userId) throws ServiceExecutionException {
		LOGGER.debug("getStudyIdByName called");
		List<StudyListDTO> studyList = studyDao.getStudyList(userId, "Yes", "No");

		StudyListDTO studyDto = studyList.stream()
				.filter(s -> Objects.equals(s.getStudyName().toLowerCase(), studyName.toLowerCase())).findFirst()
				.orElse(null);

		return studyDto == null ? null : studyDto.getStudyId();
	}
}
