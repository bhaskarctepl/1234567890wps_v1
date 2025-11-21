
/**
 * Created Date: 08-Dec-2020
 * Class Name  : CustomerSupportImpl.java
 * Description : Description of the package.
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                sgorle          08-Dec-2020        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.jaxrs.resource.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.dto.Question;
import com.hillspet.wearables.dto.Questionnaire;
import com.hillspet.wearables.dto.QuestionnaireInstruction;
import com.hillspet.wearables.dto.QuestionnairePublishHistory;
import com.hillspet.wearables.dto.filter.QuestionnaireFilter;
import com.hillspet.wearables.dto.filter.QuestionnaireResponseByStudyFilter;
import com.hillspet.wearables.dto.filter.QuestionnaireResponseFilter;
import com.hillspet.wearables.jaxrs.resource.QuestionnaireResource;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.request.QuestionnaireRequest;
import com.hillspet.wearables.request.QuestionnaireSkipRequest;
import com.hillspet.wearables.request.RepublishQuestionnaireRequest;
import com.hillspet.wearables.response.ExportQuestionnaireResponse;
import com.hillspet.wearables.response.PetQuestionnaireResponseList;
import com.hillspet.wearables.response.QuestionnaireListResponse;
import com.hillspet.wearables.response.QuestionnaireNameResponse;
import com.hillspet.wearables.response.QuestionnairePublishResponse;
import com.hillspet.wearables.response.QuestionnaireResponse;
import com.hillspet.wearables.response.QuestionnaireResponseByStudyListResponse;
import com.hillspet.wearables.response.QuestionnaireResponseListResponse;
import com.hillspet.wearables.response.QuestionnaireViewResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.questionnaire.QuestionnaireService;

/**
 * Enter detailed explanation of the class here..
 * <p>
 * This class implementation of the <tt>Interface or class Name</tt> interface
 * or class. In addition to implementing the <tt>Interface Name</tt> interface,
 * this class provides methods to do other operations. (Mention other methods
 * purpose)
 *
 * <p>
 * More Description about the class need to be entered here.
 *
 * @author sgorle
 * @version Wearables Portal Relase Version..
 * @since Available Since Wearables Portal Version.
 * @see New line seperated Classes or Interfaces related to this class.
 */
@Service
public class QuestionnaireResourceImpl implements QuestionnaireResource {

	private static final Logger LOGGER = LogManager.getLogger(QuestionnaireResourceImpl.class);

	@Autowired
	private QuestionnaireService questionnaireService;

	@Autowired
	private Authentication authentication;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;
	
	private static final int BUFFER_SIZE = 262144;
	
	@Value("${mediaPath}")
	private String mediaPath;

	@Override
	public Response addQuestionnaire(QuestionnaireRequest questionnaireRequest) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		Questionnaire questionnaire = questionnaireService.addQuestionnaire(questionnaireRequest, userId);
		LOGGER.info("Questionnarei inserted successfully {}", questionnaire.getQuestionnaireId());

		// Step 2: build a successful response
		QuestionnaireResponse questionnaireResponse = new QuestionnaireResponse();
		questionnaireResponse.setQuestionnaire(questionnaire);
		SuccessResponse<QuestionnaireResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(questionnaireResponse);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response updateQuestionnaire(QuestionnaireRequest questionnaireRequest) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		Questionnaire questionnaire = questionnaireService.updateQuestionnaire(questionnaireRequest, userId);

		// Step 2: build a successful response
		QuestionnaireResponse questionnaireResponse = new QuestionnaireResponse();
		questionnaireResponse.setQuestionnaire(questionnaire);
		SuccessResponse<QuestionnaireResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(questionnaireResponse);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getQuestionnaireById(int questionnaireId) {
		QuestionnaireResponse response = questionnaireService.getQuestionnaireById(questionnaireId);
		SuccessResponse<QuestionnaireResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getQuestionnaires(QuestionnaireFilter filter) {
		QuestionnaireListResponse response = questionnaireService.getQuestionnaires(filter);
		SuccessResponse<QuestionnaireListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response deleteQuestionnaire(int questionnaireId) {
		int modifiedBy = authentication.getAuthUserDetails().getUserId();

		// Step 1: process
		questionnaireService.deleteQuestionnaire(questionnaireId, modifiedBy);
		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response addNewInstruction(int questionnaireId, QuestionnaireInstruction questionnaireInstruction) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		questionnaireService.addNewInstruction(questionnaireId, userId, questionnaireInstruction);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response updateInstruction(int questionnaireId, QuestionnaireInstruction questionnaireInstruction) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		questionnaireService.updateInstruction(questionnaireId, userId, questionnaireInstruction);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response deleteInstruction(int questionnaireId, int instructionId) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		questionnaireService.deleteInstruction(questionnaireId, userId, instructionId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response addNewQuestion(int questionnaireId, Question question) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		questionnaireService.addNewQuestion(questionnaireId, userId, question);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response updateQuestion(int questionnaireId, Question question) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		questionnaireService.updateQuestion(questionnaireId, userId, question);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response deleteQuestion(int questionnaireId, int questionId) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		questionnaireService.deleteQuestion(questionnaireId, userId, questionId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getActiveQuestionnaires() {
		QuestionnaireListResponse response = questionnaireService.getActiveQuestionnaires();
		SuccessResponse<QuestionnaireListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getQuestionnaireResponseList(QuestionnaireResponseFilter filter) {
		int userId = authentication.getAuthUserDetails().getUserId();
		filter.setUserId(userId);
		QuestionnaireResponseListResponse response = questionnaireService.getQuestionnaireResponseList(filter);
		SuccessResponse<QuestionnaireResponseListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getQuestionnaireResponseByStudyList(QuestionnaireResponseByStudyFilter filter) {
		QuestionnaireResponseByStudyListResponse response = questionnaireService
				.getQuestionnaireResponseByStudyList(filter);
		SuccessResponse<QuestionnaireResponseByStudyListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getViewQuestionnaireResponse(int questionnaireResponseId, int studyId) {
		QuestionnaireViewResponse response = questionnaireService.getViewQuestionnaireResponse(questionnaireResponseId,
				studyId);
		SuccessResponse<QuestionnaireViewResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getQuestionnaireResponseByPet(QuestionnaireResponseFilter filter) {
		PetQuestionnaireResponseList response = questionnaireService.getQuestionnaireResponseByPet(filter);
		SuccessResponse<PetQuestionnaireResponseList> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response implementSkipOnQuestionnaire(QuestionnaireSkipRequest questionnaireSkipRequest) {
		int modifiedBy = authentication.getAuthUserDetails().getUserId();

		// Step 1: process
		questionnaireService.implementSkipOnQuestionnaire(questionnaireSkipRequest, modifiedBy);
		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response republishQuestionnaire(@Valid RepublishQuestionnaireRequest republishQuestionnaireRequest) {

		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		questionnaireService.republishQuestionnaire(republishQuestionnaireRequest, userId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);

	}

	@Override
	public Response getQuestionnairePublishHistory(int questionnaireId) {
		LOGGER.info("getQuestionnairePublishHistory started {}", questionnaireId);

		List<QuestionnairePublishHistory> publishHistory = questionnaireService
				.getQuestionnairePublishHistory(questionnaireId);
		LOGGER.info("getQuestionnairePublishHistory 1 {}", publishHistory);
		QuestionnairePublishResponse response = new QuestionnairePublishResponse();
		response.setPublishHistory(publishHistory);
		SuccessResponse<QuestionnairePublishResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getCopyQuestionnaireName(String questionnaireName) {
		LOGGER.info("getCopyQuestionnaireName started {}", questionnaireName);

		String proposedQuestionnaireName = questionnaireService.getCopyQuestionnaireName(questionnaireName);

		LOGGER.info("getCopyQuestionnaireName - proposedQuestionnaireName {}", proposedQuestionnaireName);
		QuestionnaireNameResponse response = new QuestionnaireNameResponse();
		response.setQuestionnaireName(proposedQuestionnaireName);
		SuccessResponse<QuestionnaireNameResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	
	@Override
	public Response getExportsRequestedList(QuestionnaireFilter filter) {
		Integer userId = authentication.getAuthUserDetails().getUserId();
		filter.setUserId(userId);
		ExportQuestionnaireResponse response = questionnaireService.getExportsRequestedList(filter);
		SuccessResponse<ExportQuestionnaireResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	
	@Override
	public Response exportQuestionaire(String questionnaireIds) {
		questionnaireService.exportQuestionaire(questionnaireIds, authentication.getAuthUserDetails());
		
		CommonResponse response = new CommonResponse();
		response.setMessage("Export Requested Successfully.");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
		/*Workbook workbook = null;
		  ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			workbook = questionnaireService.exportQuestionaire(questionnaireIds, userId);
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
				"attachment; filename=\"Questionnaire Export.xlsx\"");
		return response.build();*/
	}

 
	@Override
	public Response downloadQuestionnaire(int exportId) {
		// TODO Auto-generated method stub
		String fileUrl = questionnaireService.downloadQuestionnaire(exportId);
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String userMediaPath = mediaPath + File.separator + authentication.getAuthUserDetails().getUserId()
				+ File.separator + timestamp.getTime();

		File userFolder = new File(mediaPath + File.separator + authentication.getAuthUserDetails().getUserId());
		userFolder.mkdir();

		File userFolderWithTime = new File(mediaPath + File.separator + authentication.getAuthUserDetails().getUserId()
				+ File.separator + timestamp.getTime());
		userFolderWithTime.mkdir();
		
		try {
			String fileName = downloadFile(fileUrl, userMediaPath);
			String filePath = userMediaPath + File.separator + fileName;
			File file = new File(filePath);
			ResponseBuilder responseBuilder = Response.ok((Object) file);
			responseBuilder.header("Content-Disposition", "attachment; filename=" + fileName);
			return responseBuilder.build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Response.noContent().build();
	}

	 
	@Override
	public Response getQuestionnairesListForExport(QuestionnaireFilter filter) {
		QuestionnaireListResponse response = questionnaireService.getQuestionnairesForExport(filter);
		SuccessResponse<QuestionnaireListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}
	
	private String downloadFile(String fileURL, String downloadMediaTo) throws IOException {
		URL url = new URL(fileURL);
		HttpsURLConnection httpConn = (HttpsURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();
		String fileName = "";

		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String disposition = httpConn.getHeaderField("Content-Disposition");
			String contentType = httpConn.getContentType();
			int contentLength = httpConn.getContentLength();

			if (disposition != null) {
				// extracts file name from header field
				int index = disposition.indexOf("filename");
				if (index > 0) {
					// fileName = disposition.substring(index + 10, disposition.length() - 1);
					fileName = disposition.split("\\=")[1];
					fileName = fileName.replaceAll("utf-8''", "");
				}
			} else {
				// extracts file name from URL
				fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
			}

			LOGGER.info("Content-Type = " + contentType);
			LOGGER.info("Content-Disposition = " + disposition);
			LOGGER.info("Content-Length = " + contentLength);
			LOGGER.info("fileName = " + fileName);

			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();

			if (fileName.contains("?")) {
				fileName = fileName.substring(0, fileName.indexOf("?"));
				LOGGER.info("fileName updated to = " + fileName);
			}

			String saveFilePath = downloadMediaTo + File.separator + fileName;

			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			// after downloading creating the file
			File mediaFile = new File(saveFilePath);

			LOGGER.info("imageAbsPath:" + mediaFile.getAbsolutePath());
			outputStream.close();
			inputStream.close();
			LOGGER.info("File downloaded");
		} else {
			LOGGER.info("No file to download. Server replied HTTP code: " + responseCode);
		}

		httpConn.disconnect();
		return fileName;
	}
	
}
