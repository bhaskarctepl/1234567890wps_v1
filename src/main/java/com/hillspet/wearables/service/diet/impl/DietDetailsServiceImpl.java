package com.hillspet.wearables.service.diet.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.constants.ActivityFactorConstants;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.diet.DietDetailsDao;
import com.hillspet.wearables.dto.BulkUploadDietInfo;
import com.hillspet.wearables.dto.DietDTO;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.DietFilter;
import com.hillspet.wearables.request.BulkDietUploadRequest;
import com.hillspet.wearables.request.DietRequest;
import com.hillspet.wearables.response.BulkDietUploadResponse;
import com.hillspet.wearables.response.DietListResponse;
import com.hillspet.wearables.response.DietLookUpResponse;
import com.hillspet.wearables.service.diet.DietDetailsService;

@Service
public class DietDetailsServiceImpl implements DietDetailsService {

	private static final Logger LOGGER = LogManager.getLogger(DietDetailsServiceImpl.class);

	final String DATE_FORMAT = "dd-MMM-yy";

	@Autowired
	DietDetailsDao dietDetailsDao;

	@Autowired
	private ObjectMapper mapper;

	/*
	 * To process and validate diet
	 * */
	@Override
	public HashMap<String, String> bulkAssetUpload(InputStream uploadedInputStream,
			FormDataContentDisposition fileDetail, Integer userId) throws ServiceExecutionException {
		LOGGER.debug("bulkAssetUpload called");
		int[] result;
		HashMap<String, String> responseMap = new HashMap<>();
		Integer auditId = dietDetailsDao.createAuditRecord(fileDetail.getFileName(), userId, 9);
		List<BulkUploadDietInfo> list = convertBulkExcelToDietList(uploadedInputStream, userId);
		if ((list == null || list.isEmpty())) {
			return responseMap;
		}
		list.forEach(LOGGER::info);
		LOGGER.debug("addDietInfoBulkUploadPreview bulkAssetUpload size is " + list.size());
		result = dietDetailsDao.saveBulkDietToStaging(list, fileDetail.getFileName(), userId, auditId);
		if (result.length > 0) {
			dietDetailsDao.updateAuditRecord(auditId, userId, "Completed");
			responseMap.put("recordId", auditId.toString());
			responseMap.put("response", Arrays.toString(result));
		}
		LOGGER.debug("bulkAssetUpload - saveBulkDietToStaging return result size is " + result.length);
		LOGGER.debug("bulkAssetUpload completed successfully");
		return responseMap;
	}

	/*
	* to get uploaded list for review
	* */
	@Override
	public BulkDietUploadResponse getBulkUploadDietList(DietFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getBulkUploadDietList start");
		HashMap<String, Integer> countMap = dietDetailsDao.getBulkUploadDietListCount(filter);
		List<BulkUploadDietInfo> dietList = countMap.get("totalRecords") > 0
				? dietDetailsDao.getBulkUploadDietList(filter)
				: new ArrayList<>();
		BulkDietUploadResponse response = new BulkDietUploadResponse();
		response.setDietInfoList(dietList);
		response.setNoOfElements(dietList.size());
		response.setSearchElments(countMap.get("totalRecords"));
		response.setTotalRecords(countMap.get("totalRecords"));
		response.setErrorRecordsCount(countMap.get("failedRecords"));
		response.setSuccessRecordsCount(countMap.get("successRecords"));
		LOGGER.debug("getBulkUploadDietList is {}", dietList);
		LOGGER.debug("getBulkUploadDietList completed successfully");
		return response;
	}

	/*
	* To Save selected diets
	* */
	@Override
	public Integer saveBulkUploadDietInfo(BulkDietUploadRequest request) throws ServiceExecutionException {
		LOGGER.debug("saveBulkUploadDietInfo called");
		Integer device = dietDetailsDao.saveBulkUploadDietInfo(request);
		LOGGER.debug("saveBulkUploadDietInfo completed successfully");
		return device;
	}

	/*
	* To download diet upload template
	* */
	@Override
	public Workbook generateBulkUploadExcel() throws ServiceExecutionException {
		LOGGER.debug("generateBulkUploadExcel called");
		Workbook workbook = new XSSFWorkbook();
		try {
			Sheet sheet = workbook.createSheet("Diet Information");
			String[] validHeaders = new String[] {
					// "COMPANY_NAME","BRAND","SUB_BRAND","CATEGORY","DIET_NO","DESCRIPTION","MATERIAL_NUM","PRODUCT_TYPE","RECIPE_TYPE","DIET_NAME","CAL_DENSITY_KCAL_KG","PDM_DENSITY_VALUE","PDM_DENSITY_UOM","LATEST_MODIFIED_DATE"
					"DIET_NO", "SPEC_TYPE", "PRODUCT_TYPE", "DIET_NAME", "DESCRIPTION", "IS_ACTIVE",
					"BRAND", "SUB_BRAND", "COMPANY_NAME", "CATEGORY", "CAL_DENSITY", "PDM_DENSITY_VALUE",
					"PDM_DENSITY_UOM", "LATEST_MODIFIED_DATE" };
			DietLookUpResponse dietResponse = dietDetailsDao.getDietDetailsForLookUp();
			String[] companyNameArray = dietResponse.getCompanyName().isEmpty() ? new String[0]
					: dietResponse.getCompanyName().toArray(new String[0]);
			String[] brandArray = dietResponse.getBrand().isEmpty() ? new String[0]
					: dietResponse.getBrand().toArray(new String[0]);
			String[] subBrandArray = dietResponse.getSubBrand().isEmpty() ? new String[0]
					: dietResponse.getSubBrand().toArray(new String[0]);
			String[] categoryArray = dietResponse.getCategory().isEmpty() ? new String[0]
					: dietResponse.getCategory().toArray(new String[0]);
			String[] productTypeArray = dietResponse.getProductType().isEmpty() ? new String[0]
					: dietResponse.getProductType().toArray(new String[0]);
			String[] recipeTypeArray = dietResponse.getRecipeType().isEmpty() ? new String[0]
					: dietResponse.getRecipeType().toArray(new String[0]);
			// Row for Header
			Row headerRow = sheet.createRow(0);
			for (int col = 0; col < validHeaders.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(validHeaders[col]);
				sheet.setColumnWidth(col, 22 * 256);
				if (validHeaders[col].equals("DIET_NO") || validHeaders[col].equals("DIET_NAME")
						|| validHeaders[col].equals("CAL_DENSITY") || validHeaders[col].equals("PDM_DENSITY_VALUE")
						|| validHeaders[col].equals("PDM_DENSITY_UOM")) {
					this.addCellStyle(workbook, cell, IndexedColors.GREY_25_PERCENT.index,
							IndexedColors.BROWN.getIndex(), false);
				} else {
					this.addCellStyle(workbook, cell, IndexedColors.GREY_25_PERCENT.index,
							IndexedColors.BLACK.getIndex(), false);
				}
			}

			// Row for Message header
			/*Row messageRow = sheet.createRow(1);
			for (int col = 0; col < validHeaders.length; col++) {
			    Cell cell = messageRow.createCell(col);
			    String message = "";
			    CellStyle style = workbook.createCellStyle();
			    sheet.setColumnWidth(col, 22 * 256);
			    switch (validHeaders[col]) {
			        case "DIET_NO":
			            message = this.prepareColumnMessage(ActivityFactorConstants.DIET_NO_CHAR_LENGTH.toString(), true);
			            this.addCellStyle(workbook,cell,IndexedColors.WHITE1.index,IndexedColors.BROWN.getIndex(),false);
			            break;
			        case "DIET_NAME":
			            message = this.prepareColumnMessage(ActivityFactorConstants.DIET_NAME_CHAR_LENGTH.toString(), true);
			            this.addCellStyle(workbook,cell,IndexedColors.WHITE1.index,IndexedColors.BROWN.getIndex(),false);
			            break;
			        case "CAL_DENSITY_KCAL_KG":
			            message = this.prepareColumnMessage(ActivityFactorConstants.CAL_VAL_LENGTH, true);
			            this.addCellStyle(workbook,cell,IndexedColors.WHITE1.index,IndexedColors.BROWN.getIndex(),false);
			            break;
			        case "PDM_DENSITY_VALUE":
			            message = this.prepareColumnMessage(ActivityFactorConstants.CAL_VAL_LENGTH, true);
			            this.addCellStyle(workbook,cell,IndexedColors.WHITE1.index,IndexedColors.BROWN.getIndex(),false);
			            break;
			        case "PDM_DENSITY_UOM":
			            message = this.prepareColumnMessage(ActivityFactorConstants.CAL_VAL_LENGTH, true);
			            this.addCellStyle(workbook,cell,IndexedColors.WHITE1.index,IndexedColors.BROWN.getIndex(),false);
			            break;
			        case "COMPANY_NAME":
			            message = this.prepareColumnMessage(ActivityFactorConstants.COMPANY_NAME_CHAR_LENGTH.toString(), false);
			            this.addCellStyle(workbook,cell,IndexedColors.WHITE1.index, IndexedColors.BLACK.getIndex(),false);
			            break;
			        case "BRAND":
			            message = this.prepareColumnMessage(ActivityFactorConstants.BRAND_NAME_CHAR_LENGTH.toString(), false);
			            this.addCellStyle(workbook,cell,IndexedColors.WHITE1.index,IndexedColors.BLACK.getIndex(),false);
			            break;
			        case "SUB_BRAND":
			            message = this.prepareColumnMessage(ActivityFactorConstants.SUB_BRAND_NAME_CHAR_LENGTH.toString(), false);
			            this.addCellStyle(workbook,cell,IndexedColors.WHITE1.index,IndexedColors.BLACK.getIndex(),false);
			            break;
			        case "CATEGORY":
			            message = this.prepareColumnMessage(ActivityFactorConstants.CATEGORY_CHAR_LENGTH.toString(), false);
			            this.addCellStyle(workbook,cell,IndexedColors.WHITE1.index,IndexedColors.BLACK.getIndex(),false);
			            break;
			        case "DESCRIPTION":
			            message = this.prepareColumnMessage(ActivityFactorConstants.DIET_DESCRIPTION_CHAR_LENGTH.toString(), false);
			            this.addCellStyle(workbook,cell,IndexedColors.WHITE1.index,IndexedColors.BLACK.getIndex(),false);
			            break;
			        case "MATERIAL_NUM":
			            message = this.prepareColumnMessage(ActivityFactorConstants.MATERIAL_NUMBER_CHAR_LENGTH.toString(), false);
			            this.addCellStyle(workbook,cell,IndexedColors.WHITE1.index,IndexedColors.BLACK.getIndex(),false);
			            break;
			        case "PRODUCT_TYPE":
			            message = this.prepareColumnMessage(ActivityFactorConstants.PRODUCT_TYPE_CHAR_LENGTH.toString(), false);
			            this.addCellStyle(workbook,cell,IndexedColors.WHITE1.index,IndexedColors.BLACK.getIndex(),false);
			            break;
			        case "RECIPE_TYPE":
			            message = this.prepareColumnMessage(ActivityFactorConstants.RECIPE_TYPE_CHAR_LENGTH.toString(), false);
			            this.addCellStyle(workbook,cell,IndexedColors.WHITE1.index,IndexedColors.BLACK.getIndex(),false);
			            break;
			        case "LATEST_MODIFIED_DATE":
			            message = "Date(dd-mmm-yy)";
			            this.addCellStyle(workbook,cell,IndexedColors.WHITE1.index,IndexedColors.BLACK.getIndex(),false);
			            break;
			    }
			    cell.setCellValue(message);
			    style.setWrapText(true);
			}*/
			// "DIET_NO","SPEC_TYPE","MATERIAL_NUM","PRODUCT_TYPE","DIET_NAME","IS_ACTIVE","BRAND","SUB_BRAND","COMPANY_NAME",
			// "CATEGORY","CAL_DENSITY","PDM_DENSITY_VALUE","PDM_DENSITY_UOM","LATEST_MODIFIED_DATE"
			// Added dropdowns
			// "DIET_NO","SPEC_TYPE","PRODUCT_TYPE","DIET_NAME","DESCRIPTION",
			// "IS_ACTIVE","BRAND","SUB_BRAND","COMPANY_NAME","CATEGORY","CAL_DENSITY","PDM_DENSITY_VALUE",
			// "PDM_DENSITY_UOM","LATEST_MODIFIED_DATE"
			DataValidation dataValidation = null;
			DataValidationConstraint constraint = null;
			DataValidationHelper validationHelper = null;
			Sheet sheet2 = workbook.createSheet("ListSheet");
			Sheet sheet3 = workbook.createSheet("ListSheet2");
			Sheet sheet4 = workbook.createSheet("ListSheet3");
			Sheet sheet5 = workbook.createSheet("ListSheet4");
			Row row = sheet.createRow(1);
			row.createCell(1).setCellValue("");// SPEC_TYPE(RECIPE_TYPE)
			row.createCell(2).setCellValue("");// PRODUCT_TYPE
			row.createCell(6).setCellValue("");// BRAND
			row.createCell(7).setCellValue("");// SUB_BRAND
			row.createCell(8).setCellValue("");// COMPANY_NAME
			row.createCell(9).setCellValue("");// CATEGORY
			validationHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
			if (row.getCell(1).toString() == "") {
				/*CellRangeAddressList cellRange = new CellRangeAddressList(1, 500, 1, 1);
				try {
					constraint = validationHelper.createExplicitListConstraint(recipeTypeArray);
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.error("recipeTypeArray error.", e);
				}
				dataValidation = validationHelper.createValidation(constraint, cellRange);
				dataValidation.setSuppressDropDownArrow(true);
				sheet.addValidationData(dataValidation);*/
				//
				for (int i = 0; i < recipeTypeArray.length - 1; i++) {
					sheet2.createRow(i).createCell(0).setCellValue(recipeTypeArray[i] + "");
				}
				sheet2.setSelected(false);
				Name namedCell = workbook.createName();
				namedCell.setNameName("RecipeList");
				String reference = "ListSheet!$A$2:$A$500";
				namedCell.setRefersToFormula(reference);
				sheet.setActiveCell(new CellAddress("B1"));
				CellRangeAddressList cellRange = new CellRangeAddressList(1, 500, 1, 1);
				try {
					constraint = validationHelper.createFormulaListConstraint("RecipeList");
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.debug("RecipeList error.", e);
				}
				dataValidation = validationHelper.createValidation(constraint, cellRange);
				dataValidation.setSuppressDropDownArrow(true);
				sheet.addValidationData(dataValidation);
			}

			if (row.getCell(2).toString() == "") {
				CellRangeAddressList cellRange = new CellRangeAddressList(1, 500, 2, 2);
				try {
					constraint = validationHelper.createExplicitListConstraint(productTypeArray);
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.error("productTypeArray error.", e);
				}
				dataValidation = validationHelper.createValidation(constraint, cellRange);
				dataValidation.setSuppressDropDownArrow(true);
				sheet.addValidationData(dataValidation);
			}
			if (row.getCell(6).toString() == "") {
				/*CellRangeAddressList cellRange = new CellRangeAddressList(1, 500, 6, 6);
				try {
					constraint = validationHelper.createExplicitListConstraint(brandArray);
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.error("brandArray error.", e);
				}
				dataValidation = validationHelper.createValidation(constraint, cellRange);
				dataValidation.setSuppressDropDownArrow(true);
				sheet.addValidationData(dataValidation);*/
				for (int i = 0; i < brandArray.length - 1; i++) {
					sheet4.createRow(i).createCell(0).setCellValue(brandArray[i] + "");
				}
				sheet4.setSelected(false);
				Name namedCell = workbook.createName();
				namedCell.setNameName("BrandList");
				String reference = "ListSheet3!$A$2:$A$500";
				namedCell.setRefersToFormula(reference);
				sheet.setActiveCell(new CellAddress("G2"));
				CellRangeAddressList cellRange = new CellRangeAddressList(1, 500, 6, 6);
				try {
					constraint = validationHelper.createFormulaListConstraint("BrandList");
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.debug("BrandList error.", e);
				}
				dataValidation = validationHelper.createValidation(constraint, cellRange);
				dataValidation.setSuppressDropDownArrow(true);
				sheet.addValidationData(dataValidation);
			}
			if (row.getCell(7).toString() == "") {
				/*CellRangeAddressList cellRange = new CellRangeAddressList(1, 500, 7, 7);
				try {
					constraint = validationHelper.createExplicitListConstraint(subBrandArray);
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.error("subBrandArray error.", e);
				}
				dataValidation = validationHelper.createValidation(constraint, cellRange);
				dataValidation.setSuppressDropDownArrow(true);
				sheet.addValidationData(dataValidation);*/
				//
				for (int i = 0; i < subBrandArray.length - 1; i++) {
					sheet3.createRow(i).createCell(0).setCellValue(subBrandArray[i] + "");
				}
				sheet3.setSelected(false);
				Name namedCell = workbook.createName();
				namedCell.setNameName("SubBrandList");
				String reference = "ListSheet2!$A$2:$A$500";
				namedCell.setRefersToFormula(reference);
				sheet.setActiveCell(new CellAddress("H2"));
				CellRangeAddressList cellRange = new CellRangeAddressList(1, 500, 7, 7);
				try {
					constraint = validationHelper.createFormulaListConstraint("SubBrandList");
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.debug("SubBrandList error.", e);
				}
				dataValidation = validationHelper.createValidation(constraint, cellRange);
				dataValidation.setSuppressDropDownArrow(true);
				sheet.addValidationData(dataValidation);
			}
			if (row.getCell(8).toString() == "") {
				/*CellRangeAddressList cellRange = new CellRangeAddressList(1, 500, 8, 8);
				try {
					constraint = validationHelper.createExplicitListConstraint(companyNameArray);
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.error("companyNameArray error.", e);
				}
				dataValidation = validationHelper.createValidation(constraint, cellRange);
				dataValidation.setSuppressDropDownArrow(true);
				sheet.addValidationData(dataValidation);*/
				for (int i = 0; i < companyNameArray.length - 1; i++) {
					sheet5.createRow(i).createCell(0).setCellValue(companyNameArray[i] + "");
				}
				sheet5.setSelected(false);
				Name namedCell = workbook.createName();
				namedCell.setNameName("CompanyNameList");
				String reference = "ListSheet4!$A$2:$A$500";
				namedCell.setRefersToFormula(reference);
				sheet.setActiveCell(new CellAddress("I2"));
				CellRangeAddressList cellRange = new CellRangeAddressList(1, 500, 8, 8);
				try {
					constraint = validationHelper.createFormulaListConstraint("CompanyNameList");
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.debug("CompanyNameList error.", e);
				}
				dataValidation = validationHelper.createValidation(constraint, cellRange);
				dataValidation.setSuppressDropDownArrow(true);
				sheet.addValidationData(dataValidation);
			}
			if (row.getCell(9).toString() == "") {
				CellRangeAddressList cellRange = new CellRangeAddressList(1, 500, 9, 9);
				try {
					constraint = validationHelper.createExplicitListConstraint(categoryArray);
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.error("categoryArray error.", e);
				}
				dataValidation = validationHelper.createValidation(constraint, cellRange);
				dataValidation.setSuppressDropDownArrow(true);
				sheet.addValidationData(dataValidation);
			}
			workbook.setSheetHidden(1, true);
			workbook.setSheetHidden(2, true);
			workbook.setSheetHidden(3, true);
			workbook.setSheetHidden(4, true);
			LOGGER.debug("generateBulkUploadExcel ended.");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception ", e);
		}
		return workbook;
	}

	/*
	* Validate uploaded data
	* */
	@Override
	public List<BulkUploadDietInfo> convertBulkExcelToDietList(InputStream uploadedInputStream, int userId)
			throws ServiceExecutionException {
		List<BulkUploadDietInfo> addDietList = new ArrayList<>();
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(uploadedInputStream);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();
			// List of headers from excel
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

				if (currentRow.getRowNum() < 1) {
					continue; // just skip the rows if row number is 0 or 1
				}
				if (checkIfRowIsEmpty(currentRow)) {
					continue;
				}
				// DIET_NO,SPEC_TYPE,PRODUCT_TYPE,DIET_NAME,DESCRIPTION,
				// IS_ACTIVE,BRAND,SUB_BRAND,COMPANY_NAME,
				// CATEGORY,CAL_DENSITY,PDM_DENSITY_VALUE,PDM_DENSITY_UOM,LATEST_MODIFIED_DATE
				int cellIdx = 0;
				BulkUploadDietInfo dietInfo = new BulkUploadDietInfo();
				List<String> errorsList = new ArrayList<String>();
				for (int cn = 0; cn < currentRow.getLastCellNum(); cn++) { // cn- cell number
					Cell cell = currentRow.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					DataFormatter formatter = new DataFormatter();
					String value = formatter.formatCellValue(cell).trim();
					int valueLength = value.trim().length();
					switch (cellIdx) {
					case 0: // DIET_NO
						if (valueLength == 0) {
							errorsList.add(this.isMandatoryErrorMessage("Diet number",
									ActivityFactorConstants.DIET_NO_CHAR_LENGTH.toString()));
						} else {
							if (validateCharLength(value, ActivityFactorConstants.DIET_NO_CHAR_LENGTH)) {
								errorsList.add(this.getInvalidCharLengthErrorMessage("diet number", value,
										ActivityFactorConstants.DIET_NO_CHAR_LENGTH.toString()));
							}
							/*if(!isNumber(value)){
							    errorsList.add("Invalid diet number (" + value + ")");
							} else {
							    dietInfo.setDietNo(Integer.parseInt(value));
							}*/
							else {
								dietInfo.setDietNo(value);
								dietInfo.setMaterialNumber(value); // MATERIAL_NUM
							}
						}
						break;
					case 1: // SPEC_TYPE(RECIPE_TYPE)
						if (validateCharLength(value, ActivityFactorConstants.RECIPE_TYPE_CHAR_LENGTH)) {
							errorsList.add(this.getInvalidCharLengthErrorMessage("recipe type", value,
									ActivityFactorConstants.RECIPE_TYPE_CHAR_LENGTH.toString()));
						} else {
							dietInfo.setRecipeType(value);
						}
						break;
					/*case 2: // MATERIAL_NUM
						if (validateCharLength(value, ActivityFactorConstants.MATERIAL_NUMBER_CHAR_LENGTH)) {
							errorsList.add(this.getInvalidCharLengthErrorMessage(" material number ", value,
									ActivityFactorConstants.MATERIAL_NUMBER_CHAR_LENGTH.toString()));
						} else {
							dietInfo.setMaterialNumber(value);
						}
						break;*/
					case 2: // PRODUCT_TYPE
						if (validateCharLength(value, ActivityFactorConstants.PRODUCT_TYPE_CHAR_LENGTH)) {
							errorsList.add(this.getInvalidCharLengthErrorMessage(" product type ", value,
									ActivityFactorConstants.PRODUCT_TYPE_CHAR_LENGTH.toString()));
						} else {
							dietInfo.setProductType(value);
						}
						break;
					case 3: // DIET_NAME
						if (valueLength == 0) {
							errorsList.add(this.isMandatoryErrorMessage("Diet Name",
									ActivityFactorConstants.DIET_NAME_CHAR_LENGTH.toString()));
						} else {
							if (validateCharLength(value, ActivityFactorConstants.DIET_NAME_CHAR_LENGTH)) {
								errorsList.add(this.getInvalidCharLengthErrorMessage(" diet name ", value,
										ActivityFactorConstants.PRODUCT_TYPE_CHAR_LENGTH.toString()));
							} else {
								dietInfo.setDietName(value);
							}
						}
						break;
					case 4: // DESCRIPTION
						if (validateCharLength(value, ActivityFactorConstants.DIET_DESCRIPTION_CHAR_LENGTH)) {
							errorsList.add(this.getInvalidCharLengthErrorMessage("description", value,
									ActivityFactorConstants.DIET_DESCRIPTION_CHAR_LENGTH.toString()));
							value = "";
						}
						dietInfo.setDietDescription(value);
					case 5: // IS_ACTIVE
						break;
					case 6: // BRAND
						if (validateCharLength(value, ActivityFactorConstants.BRAND_NAME_CHAR_LENGTH)) {
							errorsList.add(this.getInvalidCharLengthErrorMessage("brand", value,
									ActivityFactorConstants.BRAND_NAME_CHAR_LENGTH.toString()));
							value = "";
						}
						dietInfo.setBrand(value);
						break;
					case 7: // SUB_BRAND
						if (validateCharLength(value, ActivityFactorConstants.SUB_BRAND_NAME_CHAR_LENGTH)) {
							errorsList.add(this.getInvalidCharLengthErrorMessage("sub brand", value,
									ActivityFactorConstants.SUB_BRAND_NAME_CHAR_LENGTH.toString()));
							value = "";
						}
						dietInfo.setSubBrand(value);
						break;
					case 8: // COMPANY_NAME
						if (validateCharLength(value, ActivityFactorConstants.COMPANY_NAME_CHAR_LENGTH)) {
							errorsList.add(this.getInvalidCharLengthErrorMessage("diet company name", value,
									ActivityFactorConstants.COMPANY_NAME_CHAR_LENGTH.toString()));
							value = "";
						}
						dietInfo.setCompanyName(value);
						break;
					case 9: // CATEGORY
						if (validateCharLength(value, ActivityFactorConstants.CATEGORY_CHAR_LENGTH)) {
							errorsList.add(this.getInvalidCharLengthErrorMessage("category", value,
									ActivityFactorConstants.CATEGORY_CHAR_LENGTH.toString()));
							value = "";
						}
						dietInfo.setCategory(value);
						break;
					case 10: // CAL_DENSITY
						if (valueLength == 0) {
							errorsList.add(this.isMandatoryErrorMessage("Caloric density(Kcal/Kg)",
									ActivityFactorConstants.CAL_VAL_LENGTH));
						} else {
							if (isNumber(value) || isDecimal(value)) {
								dietInfo.setCalDensityKcalKg(roundDecimalToFive(value));
							} else {
								errorsList.add(this.getInvalidCharLengthErrorMessage("Caloric density(Kcal/Kg)", value,
										ActivityFactorConstants.CAL_VAL_LENGTH));
							}
						}
						break;
					/*  case 5: //DESCRIPTION
					      if (validateCharLength(value, ActivityFactorConstants.DIET_DESCRIPTION_CHAR_LENGTH)){
					          errorsList.add(this.getInvalidCharLengthErrorMessage(" description ",value,ActivityFactorConstants.DIET_DESCRIPTION_CHAR_LENGTH.toString()));
					          value = "";
					      }
					      dietInfo.setDietDescription(value);
					      break;*/
					case 11:// PDM_DENSITY_VALUE
						if (valueLength == 0) {
							errorsList.add(this.isMandatoryErrorMessage("PDM density value",
									ActivityFactorConstants.CAL_VAL_LENGTH));
						} else {
							if (isNumber(value) || isDecimal(value)) {
								dietInfo.setPdmDensityValue(value != null ? roundDecimalToFive(value) : null);
							} else {
								errorsList.add(this.getInvalidCharLengthErrorMessage("PDM density value", value,
										ActivityFactorConstants.CAL_VAL_LENGTH));
							}
						}
						break;
					case 12: // PDM_DENSITY_UOM
						if (valueLength == 0) {
							errorsList.add(this.isMandatoryErrorMessage("PDM UOM",
									ActivityFactorConstants.PDM_UOM_LENGTH.toString()));
						} else {
							if (validateCharLength(value, ActivityFactorConstants.PDM_UOM_LENGTH)) {
								errorsList.add(this.getInvalidCharLengthErrorMessage("PDM UOM", value,
										ActivityFactorConstants.PDM_UOM_LENGTH.toString()));
							} else {
								dietInfo.setPdmDensityUOM(value);
							}
						}
						break;
					case 13: // LATEST_MODIFIED_DATE
						if (value != null && !value.equals("")) {
							SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
							Date date = sdf.parse(value);
							dietInfo.setLastModifiedDate(
									LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
						}
						break;
					default:
						break;
					}
					cellIdx++;
				}
				if (!errorsList.isEmpty()) {
					dietInfo.setExceptionMsg(StringUtils.join(errorsList, ","));
				}
				addDietList.add(dietInfo);
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
		return addDietList;
	}

	/*
	* Validation the template has column
	* */
	private boolean validateHeaders(List<String> givenHeaders) {
		if (!givenHeaders.isEmpty()) {
			String[] validHeaders = new String[] {
					/* "COMPANY_NAME","BRAND","SUB_BRAND","CATEGORY","DIET_NO","DESCRIPTION","MATERIAL_NUM","PRODUCT_TYPE","RECIPE_TYPE","DIET_NAME"
					,"CAL_DENSITY_KCAL_KG","PDM_DENSITY_VALUE","PDM_DENSITY_UOM","DIET_MODIFIED_DATE","SUB_MODIFIED_DATE"
					,"PROPERTIES_MODIFIED_DATE","LATEST_MODIFIED_DATE"*/
					"DIET_NO", "SPEC_TYPE", "MATERIAL_NUM", "PRODUCT_TYPE", "DIET_NAME", "DESCRIPTION", "IS_ACTIVE",
					"BRAND", "SUB_BRAND", "COMPANY_NAME", "CATEGORY", "CAL_DENSITY", "PDM_DENSITY_VALUE",
					"PDM_DENSITY_UOM", "LATEST_MODIFIED_DATE" };

			for (String givenHeader : givenHeaders) {
				if (!Arrays.asList(validHeaders).contains(givenHeader)) {
					return false;
				}
			}

		}
		return true;
	}

	/*
	* Checks row is empty or not
	* */
	private boolean checkIfRowIsEmpty(Row row) {
		if (row == null) {
			return true;
		}
		if (row.getLastCellNum() <= 0) {
			return true;
		}
		for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
			Cell cell = row.getCell(cellNum);
			if (cell != null && cell.getCellType() != CellType.BLANK && StringUtils.isNotBlank(cell.toString())) {
				return false;
			}
		}
		return true;
	}

	/*
	* checks column length is with in DB data type length
	* */
	private boolean validateCharLength(String entity, Integer maxLength) {
		return entity == null || entity.trim().length() > maxLength;
	}

	/*
	* Checks if a given value is number
	* */
	private boolean isNumber(String entity) {
		return entity.matches("\\d+");
	}

	/*
	* checks if a value is decimal of 15,5
	* */
	private boolean isDecimal(String entity) {
		return entity.matches("^\\d{1,10}(\\.\\d{0,5})$");
	}

	/*
	* Round the value is of decimal 5 digit
	* */
	private Double roundDecimalToFive(String entity) {
		if (!entity.equals("")) {
			double value = Double.parseDouble(entity);
			return Math.round(value * 100000.0) / 100000.0;
		} else {
			return null;
		}
	}

	/*
	 *Add styling to the Cell
	 */
	public void addCellStyle(Workbook workbook, Cell cell, short foregroundColor, short color, boolean isBold) {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(foregroundColor);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderTop(BorderStyle.MEDIUM);
		cellStyle.setBorderRight(BorderStyle.MEDIUM);
		cellStyle.setBorderBottom(BorderStyle.MEDIUM);
		cellStyle.setBorderLeft(BorderStyle.MEDIUM);
		cellStyle.setWrapText(true);
		Font font = workbook.createFont();
		font.setBold(isBold);
		font.setColor(color);
		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
	}

	/*
	 * To prepare column message
	 * */
	public String prepareColumnMessage(String length, boolean isRequired) {
		return "Length: Max. " + length + " characters\n" + (isRequired ? "Required: Yes" : "Required: No");
	}

	private String getInvalidCharLengthErrorMessage(String entity, String entityValue, String maxLength) {
		return "Invalid " + entity + " (" + entityValue + "), allows maximum " + maxLength + " characters";
	}

	private String isMandatoryErrorMessage(String entity, String maxLength) {
		return entity + " is mandatory and allows maximum " + maxLength + " characters";
	}

	/**
	 * @author akumarkhaspa
	 * @param request
	 * @return
	 */
	@Override
	public DietDTO addDiet(DietRequest request) throws ServiceExecutionException {
		LOGGER.debug("addDiet called");
		DietDTO dietDTO = dietDetailsDao.addDiet(request);
		LOGGER.debug("addDiet completed successfully");
		return dietDTO;
	}

	/**
	 * @author akumarkhaspa
	 * @param dietRequest
	 * @return
	 */
	@Override
	public DietDTO updateDiet(DietRequest dietRequest) throws ServiceExecutionException {
		LOGGER.debug("updateDiet called");
		DietDTO dietDto = dietDetailsDao.updateDiet(dietRequest);
		LOGGER.debug("updateDiet completed successfully");
		return dietDto;

	}

	/**
	 * @author akumarkhaspa
	 * @param dietId
	 * @return
	 */
	@Override
	public DietDTO getDietById(int dietId) throws ServiceExecutionException {
		LOGGER.debug("getDietById called");
		DietDTO dietDto = dietDetailsDao.getDietById(dietId);
		LOGGER.debug("getDietById completed successfully");
		return dietDto;
	}

	/**
	 * @author akumarkhaspa
	 * @param filter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DietListResponse getDietList(BaseFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getDietList called");

		BaseFilter baseFilter = new BaseFilter();
		baseFilter.setUserId(filter.getUserId());

		String totalCounts = dietDetailsDao.getDietListCount(filter);
		HashMap<String, Integer> countMap = new HashMap<>();
		int searchCount, totalCount = 0;
		try {
			countMap = mapper.readValue(totalCounts, HashMap.class);
			searchCount = countMap.get("searchCount");
			totalCount = countMap.get("totalCount");
		} catch (Exception e) {
			LOGGER.error("error while fetching getDietListCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		List<DietDTO> diets = searchCount > 0 ? dietDetailsDao.getDietList(filter) : new ArrayList<>();

		DietListResponse response = new DietListResponse();
		response.setDietList(diets);
		response.setNoOfElements(diets.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(searchCount);

		LOGGER.debug("getDietList diet count is {}", diets);
		LOGGER.debug("getDietList completed successfully");
		return response;
	}

	/**
	 * @author akumarkhaspa
	 * @return 
	 */
	@Override
	public DietLookUpResponse getFilterByData() throws ServiceExecutionException {
		LOGGER.debug("getFilterByData called");
		DietLookUpResponse dietLookUpResponse = dietDetailsDao.getDietDetailsForLookUp();
		LOGGER.debug("getFilterByData DietLookUpResponse is {}", dietLookUpResponse);
		LOGGER.debug("getFilterByData completed successfully");
		return dietLookUpResponse;
	}
}
