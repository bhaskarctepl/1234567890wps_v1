
package com.hillspet.wearables.concurrent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.utils.GCPClientUtil;
import com.hillspet.wearables.dao.questionnaire.QuestionnaireDao;
import com.hillspet.wearables.dto.CustomUserDetails;
import com.hillspet.wearables.dto.Question;
import com.hillspet.wearables.dto.QuestionAnswer;
import com.hillspet.wearables.dto.QuestionAnswerOption;
import com.hillspet.wearables.dto.QuestionCategory;
import com.hillspet.wearables.dto.Questionnaire;
import com.hillspet.wearables.dto.QuestionnaireSection;
import com.hillspet.wearables.response.QuestionnaireExportDTO;

/**
 * @author radepu Date: Nov 7, 2024
 */
@Component
public class ExportQuestionnaireUtil {

	private static final Logger LOGGER = LogManager.getLogger(ExportQuestionnaireUtil.class);

	@Autowired
	private QuestionnaireDao questionnaireDao;

	@Autowired
	GCPClientUtil gcpClientUtil;

	@Value("${gcp.bucketName}")
	private String bucketName;

	public boolean generateQuestionnaire(String questionnaireIds, int exportId, CustomUserDetails userDetails) {
		LOGGER.info("Export Questionnaire Started.");
		Workbook workbook = new XSSFWorkbook();
		boolean isProcessCompleted = false;
		try {
			if (questionnaireIds != null) {
				String fileName = null;
				String[] ids = questionnaireIds.split(",");
				fileName = gcpClientUtil.generateQuestionnaireExportFileName();

				AtomicInteger index = new AtomicInteger(0);
				for (String questionnaireId : ids) {
					LOGGER.info("questionnaireId " + questionnaireId);
					Questionnaire data = questionnaireDao.getQuestionnaireById(Integer.valueOf(questionnaireId));
					workbook = generateExcel(workbook, data, index.getAndIncrement(), fileName);
				}

				try {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					workbook.write(baos);

					gcpClientUtil.uploadQuestionnaireExport(baos.toByteArray(), fileName);
				} catch (Exception e) {
					LOGGER.error("Error while uploading questionnaire to GCP ", e);
				}

				// save file Path to database.
				LOGGER.info("Saving file response fileName " + fileName);
				if (fileName != null) {
					isProcessCompleted = questionnaireDao.updateExportQuestionnaireFilePath(exportId, fileName,
							userDetails.getUserId());

					try {
						// Sending Create Mail
						String subject = Constants.EXPORT_QUESTIONNAIRE_MAIL_SUBJECT;
						String messageBody = MessageFormat.format(Constants.EXPORT_QUESTIONNAIRE_MAIL_BODY,
								userDetails.getFullName(), Constants.WEBSITE_SITE_TITLE, Constants.WEBSITE_SITE_TITLE,
								Constants.WEBSITE_SITE_TITLE);

						ThreadPoolExecutor threadPoolExecutor = EmailThreadPoolExecutor.getEmailThreadPoolExecutor();
						EmailSenderThread emailSenderThread = new EmailSenderThread(userDetails.getEmail(), subject,
								messageBody);
						threadPoolExecutor.submit(emailSenderThread);
					} catch (Exception e) {
						LOGGER.error("Error occured while sending mail notification.", e);
					}
				}
			}

		} catch (Exception e) {
			LOGGER.error("Error occured while generating questionnaire template.", e);
		} finally {
			try {
				if (workbook != null) {
					workbook.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return isProcessCompleted;
	}

	private Workbook generateExcel(Workbook workbook, Questionnaire data, int index, String fileName) {
		LOGGER.info("generateExcel called.");
		// --- Ensure sheet1 exists and get starting row ---
		Sheet sheet1 = (index == 0) ? generateExportMetaData(workbook) : workbook.getSheetAt(0);
		int sheet1LastRowNum = sheet1.getLastRowNum();
		int rowCount = sheet1LastRowNum + 1;

		// common style used for data rows
		CellStyle style = workbook.createCellStyle();
		style.setWrapText(true);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);

		// --- Populate sheet1 (question metadata & options) ---
		rowCount = writeQuestionRowsToSheet1(workbook, sheet1, data, rowCount, style);

		// --- Prepare and populate sheet2 (answers grouped by pet) ---
		List<QuestionnaireExportDTO> answers = questionnaireDao
				.getQuestionnaireDetailsForExport(data.getQuestionnaireId());

		LOGGER.info("Questionnaire {} questions length is {} and QuestionnaireId is {}", data.getQuestionnaireName(),
				answers == null ? 0 : answers.size(), data.getQuestionnaireId());

		String questionnaireName = encodeSheetName(data.getQuestionnaireName(), data.getQuestionnaireId());
		writeResponsesSheetOptimized(workbook, questionnaireName, data, answers);

		LOGGER.info("Export Questionnaire Completed.");
		return workbook;
	}

	/**
	 * Writes question metadata and options into sheet1 starting at startRow.
	 * Returns the next available row index (i.e. lastRow + 1).
	 */
	private int writeQuestionRowsToSheet1(Workbook workbook, Sheet sheet1, Questionnaire data, int startRow,
			CellStyle style) {
		int rowCount = startRow;

		LOGGER.info("generateExcel Questions size {}", (data.getQuestions() == null ? 0 : data.getQuestions().size()));

		if (data.getQuestions() == null) {
			return rowCount;
		}

		for (int i = 0; i < data.getQuestions().size(); i++) {

			Question question = data.getQuestions().get(i);
			QuestionnaireSection section = question.getSection();

			List<QuestionAnswerOption> options = question.getQuestionAnswerOptions() == null ? Collections.emptyList()
					: question.getQuestionAnswerOptions();

			if (options.size() > 0) {
				int optionIndex = 1;
				for (QuestionAnswerOption option : options) {

					Row row2 = sheet1.createRow(rowCount); // data

					createCell(row2, 0, getValue(data.getQuestionnaireName()), style);
					createCell(row2, 1, getValue(question.getQuestionOrder()), style);
					createCell(row2, 2, getValue(question.getQuestionCode()), style);
					createCell(row2, 3,
							getValue((section != null && section.getSectionOrder() != null)
									? getValue(section.getSectionOrder())
									: ""),
							style);
					createCell(row2, 4,
							getValue((section != null && section.getSectionName() != null)
									? getValue(section.getSectionName())
									: ""),
							style);
					createCell(row2, 5, getValue(getValue(question.getQuestion())), style);
					createCell(row2, 6, getValue(getValue(question.getQuestionType())), style);

					// Question image (col 7)
					createCell(row2, 7, "", style);
					String imageUrl = question.getQuestionImageUrl();
					if (StringUtils.isNotBlank(imageUrl)) {
						byte[] imageBytes = downloadImageFromUrl(imageUrl);
						if (imageBytes != null) {
							try {
								int pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_JPEG);
								CreationHelper helper = workbook.getCreationHelper();
								Drawing<?> drawing = sheet1.createDrawingPatriarch();
								ClientAnchor anchor = helper.createClientAnchor();
								anchor.setCol1(7);
								anchor.setRow1(rowCount);
								anchor.setCol2(8);
								anchor.setRow2(rowCount + 1);
								anchor.setDx1(0);
								anchor.setDy1(0);
								anchor.setDx2(0);
								anchor.setDy2(0);
								Picture picture = drawing.createPicture(anchor, pictureIdx);
								picture.resize(1.0);
								row2.setHeightInPoints(100); // Set row height for image
								sheet1.setColumnWidth(7, 5000);
								anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
							} catch (Exception e) {
								LOGGER.warn("Failed to attach question image for questionCode {} : {}",
										question.getQuestionCode(), e.getMessage());
								// leave the cell blank if image embedding fails
							}
						}
					}

					// isMandatory
					if (question.getIsMandatory() != null) {
						createCell(row2, 8, question.getIsMandatory() ? "TRUE" : "FALSE", style);
					} else {
						createCell(row2, 8, "FALSE", style);
					}

					createCell(row2, 9, getValue(getQuestionCategory(question.getQuestionCategories())), style);

					// noOfDays
					if (question.getNoOfDays() != null) {
						createCell(row2, 10, getValue(question.getNoOfDays()), style);
					} else {
						createCell(row2, 10, "", style);
					}

					// include in extract
					if (question.getIsIncludeInDataExtract() != null) {
						createCell(row2, 11, question.getIsIncludeInDataExtract() ? "TRUE" : "FALSE", style);
					} else {
						createCell(row2, 11, "FALSE", style);
					}

					// scale type - safe null checks for getOther()
					if (question.getOther() != null) {
						Boolean isVertical = question.getOther().getIsVerticalScale();
						Boolean isHorizontal = question.getOther().getIsContinuousScale();
						if (Boolean.TRUE.equals(isVertical)) {
							createCell(row2, 12, Constants.VERTICAL, style);
						} else if (Boolean.TRUE.equals(isHorizontal)) {
							createCell(row2, 12, Constants.HORIZONTAL, style);
						} else {
							createCell(row2, 12, "", style);
						}

						createCell(row2, 13, getValue(question.getOther().getFloor()), style);
						createCell(row2, 14, getValue(question.getOther().getTickStep()), style);
						createCell(row2, 15, getValue(question.getOther().getCeil()), style);
						createCell(row2, 16, getValue(question.getOther().getFloorDescription()), style);
						createCell(row2, 17, getValue(question.getOther().getCeilDescription()), style);
					} else {
						// fill defaults when other is null
						createCell(row2, 12, "", style);
						createCell(row2, 13, "", style);
						createCell(row2, 14, "", style);
						createCell(row2, 15, "", style);
						createCell(row2, 16, "", style);
						createCell(row2, 17, "", style);
					}

					// option answer text (col 18)
					createCell(row2, 18, getValue(option.getQuestionAnswer()), style);

					// option image (col 19)
					createCell(row2, 19, "", style);
					String optionMediaUrl = option.getAnsOptionMediaUrl();
					if (StringUtils.isNotBlank(optionMediaUrl)) {
						byte[] imageBytes = downloadImageFromUrl(optionMediaUrl);
						if (imageBytes != null) {
							try {
								int pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_JPEG);
								CreationHelper helper = workbook.getCreationHelper();
								Drawing<?> drawing = sheet1.createDrawingPatriarch();
								ClientAnchor anchor = helper.createClientAnchor();
								anchor.setCol1(19);
								anchor.setRow1(rowCount);
								anchor.setCol2(20);
								anchor.setRow2(rowCount + 1);
								anchor.setDx1(0);
								anchor.setDy1(0);
								anchor.setDx2(0);
								anchor.setDy2(0);
								Picture picture = drawing.createPicture(anchor, pictureIdx);
								picture.resize(1.0);
								row2.setHeightInPoints(100);
								sheet1.setColumnWidth(19, 5000);
								anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
							} catch (Exception e) {
								LOGGER.warn("Failed to attach option image for questionCode {} option {} : {}",
										question.getQuestionCode(), option.getQuestionAnswer(), e.getMessage());
							}
						}
					}

					createCell(row2, 20, getValue(option.getSkipTo()), style);

					if (question.getShuffleOptionOrder() != null) {
						createCell(row2, 21, question.getShuffleOptionOrder() ? "TRUE" : "FALSE", style);
					} else {
						createCell(row2, 21, "FALSE", style);
					}

					createCell(row2, 22, optionIndex, style);
					createCell(row2, 23, getValue(getAnswerCategory(option.getAnswerCategories())), style);
					createCell(row2, 24, getValue(getIsQualifyingCategory(question.getQuestionCategories())), style);

					if (option.getSubmitQuestionnaire() != null) {
						createCell(row2, 25, option.getSubmitQuestionnaire() ? "TRUE" : "FALSE", style);
					} else {
						createCell(row2, 25, "FALSE", style);
					}

					optionIndex++;
					rowCount++;
				}
			} else {
				// no options -> single row representing question only
				Row row2 = sheet1.createRow(rowCount); // data

				createCell(row2, 0, getValue(data.getQuestionnaireName()), style);
				createCell(row2, 1, getValue(question.getQuestionOrder()), style);
				createCell(row2, 2, getValue(question.getQuestionCode()), style);
				createCell(row2, 3,
						getValue((section != null && section.getSectionOrder() != null)
								? getValue(section.getSectionOrder())
								: ""),
						style);
				createCell(row2, 4,
						getValue((section != null && section.getSectionName() != null)
								? getValue(section.getSectionName())
								: ""),
						style);
				createCell(row2, 5, getValue(getValue(question.getQuestion())), style);
				createCell(row2, 6, getValue(getValue(question.getQuestionType())), style);

				createCell(row2, 7, "", style);
				String imageUrl = question.getQuestionImageUrl();
				if (StringUtils.isNotBlank(imageUrl)) {
					byte[] imageBytes = downloadImageFromUrl(imageUrl);
					if (imageBytes != null) {
						try {
							int pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_JPEG);
							CreationHelper helper = workbook.getCreationHelper();
							Drawing<?> drawing = sheet1.createDrawingPatriarch();
							ClientAnchor anchor = helper.createClientAnchor();
							anchor.setCol1(7);
							anchor.setRow1(rowCount);
							anchor.setCol2(8);
							anchor.setRow2(rowCount + 1);
							anchor.setDx1(0);
							anchor.setDy1(0);
							anchor.setDx2(0);
							anchor.setDy2(0);
							Picture picture = drawing.createPicture(anchor, pictureIdx);
							picture.resize(1.0);
							row2.setHeightInPoints(100); // Set row height for image
							sheet1.setColumnWidth(7, 5000);
							anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
						} catch (Exception e) {
							LOGGER.warn("Failed to attach question image for questionCode {} : {}",
									question.getQuestionCode(), e.getMessage());
						}
					}
				}

				if (question.getIsMandatory() != null) {
					createCell(row2, 8, question.getIsMandatory() ? "TRUE" : "FALSE", style);
				} else {
					createCell(row2, 8, "FALSE", style);
				}

				createCell(row2, 9, getValue(getQuestionCategory(question.getQuestionCategories())), style);

				if (question.getNoOfDays() != null) {
					createCell(row2, 10, getValue(question.getNoOfDays()), style);
				} else {
					createCell(row2, 10, "", style);
				}

				if (question.getIsIncludeInDataExtract() != null) {
					createCell(row2, 11, question.getIsIncludeInDataExtract() ? "TRUE" : "FALSE", style);
				} else {
					createCell(row2, 11, "FALSE", style);
				}

				if (question.getOther() != null) {
					Boolean isVertical = question.getOther().getIsVerticalScale();
					Boolean isHorizontal = question.getOther().getIsContinuousScale();
					if (Boolean.TRUE.equals(isVertical)) {
						createCell(row2, 12, Constants.VERTICAL, style);
					} else if (Boolean.TRUE.equals(isHorizontal)) {
						createCell(row2, 12, Constants.HORIZONTAL, style);
					} else {
						createCell(row2, 12, "", style);
					}

					createCell(row2, 13, getValue(question.getOther().getFloor()), style);
					createCell(row2, 14, getValue(question.getOther().getTickStep()), style);
					createCell(row2, 15, getValue(question.getOther().getCeil()), style);
					createCell(row2, 16, getValue(question.getOther().getFloorDescription()), style);
					createCell(row2, 17, getValue(question.getOther().getCeilDescription()), style);
				} else {
					createCell(row2, 12, "", style);
					createCell(row2, 13, "", style);
					createCell(row2, 14, "", style);
					createCell(row2, 15, "", style);
					createCell(row2, 16, "", style);
					createCell(row2, 17, "", style);
				}

				createCell(row2, 18, "", style);
				createCell(row2, 19, "", style);
				createCell(row2, 20, "", style);

				if (question.getShuffleOptionOrder() != null) {
					createCell(row2, 21, question.getShuffleOptionOrder() ? "TRUE" : "FALSE", style);
				} else {
					createCell(row2, 21, "FALSE", style);
				}

				createCell(row2, 22, 1, style);
				createCell(row2, 23, "", style);
				createCell(row2, 24, getValue(getIsQualifyingCategory(question.getQuestionCategories())), style);

				createCell(row2, 25, "FALSE", style);

				rowCount++;
			}
		}

		return rowCount;
	}

	/**
	 * Builds Sheet2 (responses grouped by petId) and writes into the workbook.
	 * Fixes duplicate/comma-separated answers by aggregating unique answers by
	 * questionCode.
	 */
	private void writeResponsesSheet(Workbook workbook, String sheetName, Questionnaire data,
			List<QuestionnaireExportDTO> allResponses) {

		Sheet sheet = workbook.createSheet(sheetName);
		LOGGER.info("Sheet created: {}", sheetName);

		// Base headers
		List<String> headers = new ArrayList<>(Arrays.asList("QUESTIONNAIRE_NAME", "STUDY_NAME", "PET_PARENT_NAME",
				"PET_PARENT_EMAIL", "PET_ID", "PET_NAME", "SHARED_DATE", "SUBMITTED_DATE"));

		// Add dynamic question codes from data
		if (data != null && data.getQuestions() != null) {
			data.getQuestions().stream().map(Question::getQuestionCode).filter(StringUtils::isNotBlank)
					.forEach(qCode -> {
						if (!headers.contains(qCode))
							headers.add(qCode);
					});
		}

		// Create header row
		Row headerRow = sheet.createRow(0);
		for (int i = 0; i < headers.size(); i++) {
			sheet.setColumnWidth(i, 22 * 256);
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers.get(i));
			cell.setCellStyle(getHeaderStyle(workbook));
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		int rowIndex = 1;

		if (allResponses == null || allResponses.isEmpty())
			return;

		// Sort responses by grouping keys so that same group responses are consecutive
		allResponses.sort(Comparator.comparingInt(QuestionnaireExportDTO::getPetId)
				.thenComparingInt(QuestionnaireExportDTO::getQuestionnaireId)
				.thenComparing(dto -> dto.getSharedDate(), Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(dto -> dto.getSubmittedDate(), Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(dto -> getValue(dto.getStudyName()).toLowerCase())
				.thenComparing(dto -> getValue(dto.getFullName()).toLowerCase()));

		String prevKey = null;
		Map<String, String> answerMap = new LinkedHashMap<>();
		QuestionnaireExportDTO firstInGroup = null;

		for (QuestionnaireExportDTO dto : allResponses) {
			// Create current group key
			String currKey = dto.getPetId() + "|" + dto.getQuestionnaireId() + "|"
					+ (dto.getSharedDate() != null ? dto.getSharedDate().toString() : "") + "|"
					+ (dto.getSubmittedDate() != null ? dto.getSubmittedDate().toString() : "") + "|"
					+ getValue(dto.getStudyName()).trim().toLowerCase() + "|"
					+ getValue(dto.getFullName()).trim().toLowerCase();

			// If new group starts, write the previous row
			if (!currKey.equals(prevKey)) {
				if (firstInGroup != null) {
					// write row
					Row row = sheet.createRow(rowIndex++);
					int col = 0;
					row.createCell(col++).setCellValue(getValue(firstInGroup.getQuestionnaireName()));
					row.createCell(col++).setCellValue(getValue(firstInGroup.getStudyName()));
					row.createCell(col++).setCellValue(getValue(firstInGroup.getFullName()));
					row.createCell(col++).setCellValue(getValue(firstInGroup.getEmail()));
					row.createCell(col++).setCellValue(firstInGroup.getPetId());
					row.createCell(col++).setCellValue(getValue(firstInGroup.getPetName()));
					row.createCell(col++).setCellValue(
							firstInGroup.getSharedDate() != null ? firstInGroup.getSharedDate().format(formatter) : "");
					row.createCell(col++)
							.setCellValue(firstInGroup.getSubmittedDate() != null
									? firstInGroup.getSubmittedDate().format(formatter)
									: "");

					for (int h = 8; h < headers.size(); h++) {
						String header = headers.get(h);
						row.createCell(h).setCellValue(getValue(answerMap.getOrDefault(header, "")));
					}
				}

				// Start new group
				prevKey = currKey;
				firstInGroup = dto;
				answerMap.clear();
			}

			// Merge answers
			if (StringUtils.isNotBlank(dto.getQuestionCode())) {
				String existing = answerMap.getOrDefault(dto.getQuestionCode(), "");
				String incoming = getAnswers(dto.getAnswers()) != null ? getAnswers(dto.getAnswers()) : "";
				if (StringUtils.isBlank(existing)) {
					answerMap.put(dto.getQuestionCode(), incoming);
				} else if (!StringUtils.isBlank(incoming)) {
					LinkedHashSet<String> uniq = new LinkedHashSet<>(Arrays.asList(existing.split(",")));
					for (String s : incoming.split(",")) {
						if (!s.trim().isEmpty())
							uniq.add(s.trim());
					}
					answerMap.put(dto.getQuestionCode(), String.join(", ", uniq));
				}
			}
		}

		// Write last group
		if (firstInGroup != null) {
			Row row = sheet.createRow(rowIndex++);
			int col = 0;
			row.createCell(col++).setCellValue(getValue(firstInGroup.getQuestionnaireName()));
			row.createCell(col++).setCellValue(getValue(firstInGroup.getStudyName()));
			row.createCell(col++).setCellValue(getValue(firstInGroup.getFullName()));
			row.createCell(col++).setCellValue(getValue(firstInGroup.getEmail()));
			row.createCell(col++).setCellValue(firstInGroup.getPetId());
			row.createCell(col++).setCellValue(getValue(firstInGroup.getPetName()));
			row.createCell(col++).setCellValue(
					firstInGroup.getSharedDate() != null ? firstInGroup.getSharedDate().format(formatter) : "");
			row.createCell(col++).setCellValue(
					firstInGroup.getSubmittedDate() != null ? firstInGroup.getSubmittedDate().format(formatter) : "");

			for (int h = 8; h < headers.size(); h++) {
				String header = headers.get(h);
				row.createCell(h).setCellValue(getValue(answerMap.getOrDefault(header, "")));
			}
		}
	}

	private void writeResponsesSheetOptimized(Workbook workbook, String sheetName, Questionnaire data,
			List<QuestionnaireExportDTO> allResponses) {

		Sheet sheet = workbook.createSheet(sheetName);
		LOGGER.info("Sheet created: {}", sheetName);

		// Base headers
		List<String> headers = new ArrayList<>(Arrays.asList("QUESTIONNAIRE_NAME", "STUDY_NAME", "PET_PARENT_NAME",
				"PET_PARENT_EMAIL", "PET_ID", "PET_NAME", "SHARED_DATE", "SUBMITTED_DATE"));

		// Add dynamic question codes
		if (data != null && data.getQuestions() != null) {
			data.getQuestions().stream().map(Question::getQuestionCode).filter(StringUtils::isNotBlank)
					.forEach(qCode -> {
						if (!headers.contains(qCode))
							headers.add(qCode);
					});
		}

		// Create header row
		Row headerRow = sheet.createRow(0);
		for (int i = 0; i < headers.size(); i++) {
			sheet.setColumnWidth(i, 22 * 256);
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers.get(i));
			cell.setCellStyle(getHeaderStyle(workbook));
		}

		if (allResponses == null || allResponses.isEmpty()) {
			LOGGER.warn("No responses to write.");
			return; // nothing to write
		}

		// Sort responses to ensure groups are consecutive
		allResponses.sort(Comparator.comparingInt(QuestionnaireExportDTO::getPetId)
				.thenComparingInt(QuestionnaireExportDTO::getQuestionnaireId)
				.thenComparing(dto -> dto.getSharedDate(), Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(dto -> dto.getSubmittedDate(), Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(dto -> getValue(dto.getStudyName()).toLowerCase())
				.thenComparing(dto -> getValue(dto.getFullName()).toLowerCase()));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		int rowIndex = 1;
		String prevKey = null;
		Map<String, String> answerMap = new LinkedHashMap<>();
		QuestionnaireExportDTO firstInGroup = null;

		for (QuestionnaireExportDTO dto : allResponses) {
			String currKey = dto.getPetId() + "|" + dto.getQuestionnaireId() + "|"
					+ (dto.getSharedDate() != null ? dto.getSharedDate().toString() : "") + "|"
					+ (dto.getSubmittedDate() != null ? dto.getSubmittedDate().toString() : "") + "|"
					+ getValue(dto.getStudyName()).trim().toLowerCase() + "|"
					+ getValue(dto.getFullName()).trim().toLowerCase();

			// Write previous group if group changed
			if (firstInGroup != null && !currKey.equals(prevKey)) {
				writeRow(sheet, headers, rowIndex++, firstInGroup, answerMap, formatter);
				answerMap.clear();
				firstInGroup = dto;
			} else if (firstInGroup == null) {
				firstInGroup = dto; // first row
			}

			prevKey = currKey;

			// Merge answers
			if (StringUtils.isNotBlank(dto.getQuestionCode())) {
				String existing = answerMap.getOrDefault(dto.getQuestionCode(), "");
				String incoming = getAnswers(dto.getAnswers()) != null ? getAnswers(dto.getAnswers()) : "";
				if (StringUtils.isBlank(existing)) {
					answerMap.put(dto.getQuestionCode(), incoming);
				} else if (!StringUtils.isBlank(incoming)) {
					LinkedHashSet<String> uniq = new LinkedHashSet<>(Arrays.asList(existing.split(",")));
					for (String s : incoming.split(",")) {
						if (!s.trim().isEmpty())
							uniq.add(s.trim());
					}
					answerMap.put(dto.getQuestionCode(), String.join(", ", uniq));
				}
			}
		}

		// Write last group
		if (firstInGroup != null) {
			writeRow(sheet, headers, rowIndex, firstInGroup, answerMap, formatter);
		}
	}

	private void writeRow(Sheet sheet, List<String> headers, int rowIndex, QuestionnaireExportDTO dto,
			Map<String, String> answerMap, DateTimeFormatter formatter) {

		Row row = sheet.createRow(rowIndex);
		int col = 0;
		row.createCell(col++).setCellValue(getValue(dto.getQuestionnaireName()));
		row.createCell(col++).setCellValue(getValue(dto.getStudyName()));
		row.createCell(col++).setCellValue(getValue(dto.getFullName()));
		row.createCell(col++).setCellValue(getValue(dto.getEmail()));
		row.createCell(col++).setCellValue(dto.getPetId());
		row.createCell(col++).setCellValue(getValue(dto.getPetName()));
		row.createCell(col++).setCellValue(dto.getSharedDate() != null ? dto.getSharedDate().format(formatter) : "");
		row.createCell(col++)
				.setCellValue(dto.getSubmittedDate() != null ? dto.getSubmittedDate().format(formatter) : "");

		for (int h = 8; h < headers.size(); h++) {
			String header = headers.get(h);
			row.createCell(h).setCellValue(getValue(answerMap.getOrDefault(header, "")));
		}
	}

	private Sheet generateExportMetaData(Workbook workbook) {
		LOGGER.info("generateExportMetaData called.");
		Sheet sheet1 = workbook.getSheet("QUESTIONNAIRE META DATA");
		if (sheet1 == null) {
			sheet1 = workbook.createSheet("QUESTIONNAIRE META DATA");
		}

		String[] sheet1Headers = new String[] { "QUESTIONNAIRE_NAME", "QUESTION_SEQUENCE", "QUESTION_CODE",
				"SECTION_ORDER", "SECTION_TITLE", "QUESTION_TITLE", "QUESTION_TYPE", "QUESTION_IMAGE", "IS_MANDATORY",
				"QUESTION_CATEGORY", "QUESTION_VALIDITY_IN_DAYS", "INCLUDE_IN_EXTRACT",
				"SCALE_TYPE (VERTICAL/HORIZONTAL)", "SCALE_MIN", "STEP_VALUE", "SCALE_MAX", "SCALE_MIN_DESCRIPTION",
				"SCALE_MAX_DESCRIPTION", "OPTIONS", "OPTION_IMAGE", "JUMP/SKIP_TO_QUESTION", "SHUFFLE_OPTION_ORDER",
				"OPTION_ORDER", "OPTION_CATEGORY", "QUALIFYING_OPTION_CATEGORY", "SUBMIT_QUESTIONNAIRE" };

		Row row = sheet1.createRow(0); // headers

		CellStyle headerCellStyle = getHeaderStyle(workbook);

		for (int i = 0; i < sheet1Headers.length; i++) {

			sheet1.setColumnWidth(i, 22 * 256);

			Cell cell = row.createCell(i);
			cell.setCellValue(sheet1Headers[i]);

			cell.setCellStyle(headerCellStyle);
		}

		// append data

		return sheet1;
	}

	private CellStyle getHeaderStyle(Workbook workbook) {
		CellStyle headerCellStyle = workbook.createCellStyle();

		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		headerCellStyle.setFont(headerFont);

		headerCellStyle.setBorderTop(BorderStyle.MEDIUM);
		headerCellStyle.setBorderRight(BorderStyle.MEDIUM);
		headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
		headerCellStyle.setBorderLeft(BorderStyle.MEDIUM);
		headerCellStyle.setWrapText(true);

		return headerCellStyle;
	}

	private String getQuestionCategory(List<QuestionCategory> questionCategories) {
		if (questionCategories != null && questionCategories.size() > 0) {
			return questionCategories.stream().map(QuestionCategory::getQuestionCategory)
					.collect(Collectors.joining(","));
		}
		return "";
	}

	private String getAnswerCategory(List<QuestionCategory> answerCategory) {
		if (answerCategory != null && answerCategory.size() > 0) {
			return answerCategory.stream().map(QuestionCategory::getQuestionCategory).collect(Collectors.joining(","));
		}
		return "";
	}

	private String getIsQualifyingCategory(List<QuestionCategory> answerCategory) {
		if (answerCategory != null && answerCategory.size() > 0) {
			return answerCategory.stream().map(c -> (String) c.getQuestionCategory())
					.filter(value -> value != null && !value.trim().isEmpty()).collect(Collectors.joining(","));
		}
		return "";
	}

	private String getAnswers(List<QuestionAnswer> answers) {
		if (answers != null && answers.size() > 0) {
			return answers.stream().map(QuestionAnswer::getAnswer).collect(Collectors.joining(","));
		}
		return "";
	}

	private String encodeSheetName(String name, int id) {
		String updatedname = name.replace(":", "__COLON__").replace("/", "__SLASH__").replace("\\", "__BACKSLASH__")
				.replace("?", "__QUESTION__").replace("*", "__STAR__").replace("[", "__OPENBRACKET__")
				.replace("]", "__CLOSEBRACKET__");

		String sheetname = updatedname + "..." + id;
		if (sheetname.length() > 31) {
			return sheetname.substring(0, 25) + "..." + id;
		}
		return sheetname;
	}

	private String getValue(Object value) {
		if (value == null) {
			return "";
		} else {
			return String.valueOf(value);
		}
	}

	private static byte[] downloadImageFromUrl(String imageUrl) {
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.connect();

			int responseCode = connection.getResponseCode();
			if (responseCode != 200) {
				System.out.println("Failed to download image. HTTP Response Code: " + responseCode);
				return null;
			}

			try (InputStream inputStream = connection.getInputStream();
					ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

				byte[] temp = new byte[1024]; // Buffer size
				int bytesRead;
				while ((bytesRead = inputStream.read(temp)) != -1) {
					buffer.write(temp, 0, bytesRead);
				}
				return buffer.toByteArray();
			}

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void createCell(Row row, int columnIndex, Object value, CellStyle style) {
		Cell cell = row.createCell(columnIndex);
		cell.setCellStyle(style); // Apply style
		if (value != null) {
			if (value instanceof String) {
				cell.setCellValue((String) value);
			} else if (value instanceof Boolean) {
				cell.setCellValue((Boolean) value ? "TRUE" : "FALSE");
			} else if (value instanceof Integer || value instanceof Double) {
				cell.setCellValue(Double.parseDouble(value.toString()));
			} else {
				cell.setCellValue(value.toString());
			}
		} else {
			cell.setCellValue("");
		}
	}

}
