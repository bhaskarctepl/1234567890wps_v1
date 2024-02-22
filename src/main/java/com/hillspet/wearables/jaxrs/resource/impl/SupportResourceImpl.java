
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.common.utils.ThumbnailGenerator;
import com.hillspet.wearables.dto.CustomUserDetails;
import com.hillspet.wearables.dto.SupportMaterialDetails;
import com.hillspet.wearables.dto.filter.SupportFilter;
import com.hillspet.wearables.dto.filter.SupportMaterialFilter;
import com.hillspet.wearables.jaxrs.resource.SupportResource;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.request.AddSupportMaterialRequest;
import com.hillspet.wearables.request.CustomerSupportRequest;
import com.hillspet.wearables.request.CustomerSupportUpdateRequest;
import com.hillspet.wearables.request.FileDeleteRequest;
import com.hillspet.wearables.request.UpdateSupportMaterialRequest;
import com.hillspet.wearables.response.CustomerSupportListResponse;
import com.hillspet.wearables.response.CustomerSupportResponse;
import com.hillspet.wearables.response.CustomerSupportTicketHistoryResponse;
import com.hillspet.wearables.response.CustomerSupportTicketsDetailsResponse;
import com.hillspet.wearables.response.SupportMaterialListResponse;
import com.hillspet.wearables.response.SupportMaterialTypeCountResponse;
import com.hillspet.wearables.response.SupportMaterialsDetailsListResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.support.SupportService;

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
public class SupportResourceImpl implements SupportResource {

	private static final Logger LOGGER = LogManager.getLogger(SupportResourceImpl.class);
	private static final int BUFFER_SIZE = 262144; //4096;
	@Autowired
	private SupportService supportService;

	@Autowired
	private Authentication authentication;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Value("${mediaPath}")
	private String mediaPath;

	@Override
	public Response addCustomerSupport(CustomerSupportRequest customerSupportRequest) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		supportService.addCustomerSupport(customerSupportRequest, userId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response updateCustomerSupport(CustomerSupportUpdateRequest customerSupportUpdateRequest) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		supportService.updateCustomerSupport(customerSupportUpdateRequest, userId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getCustomerSupportTicketById(int ticketId) {
		CustomerSupportResponse response = supportService.getCustomerSupportTicketById(ticketId);
		SuccessResponse<CustomerSupportResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getCustomerSupportTickets(SupportFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		CustomerSupportListResponse response = supportService.getCustomerSupportTickets(filter);
		SuccessResponse<CustomerSupportListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getCustomerSupportTicketsHistory(int ticketId) {
		CustomerSupportTicketHistoryResponse response = supportService.getCustomerSupportTicketsHistory(ticketId);
		SuccessResponse<CustomerSupportTicketHistoryResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getCustomerSupportTicketsDetails(int ticketId) {
		CustomerSupportTicketsDetailsResponse response = supportService.getCustomerSupportTicketsDetails(ticketId);
		SuccessResponse<CustomerSupportTicketsDetailsResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response deleteFile(FileDeleteRequest request) {
		int modifiedBy = authentication.getAuthUserDetails().getUserId();
		supportService.deleteFile(request.getAttachmentId(), modifiedBy, request.getStatusId());
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response downloadCustomerSupportFiles(String mediaUrl, String originalFileName,String gcFileName) throws IOException, URISyntaxException {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String userMediaPath = mediaPath + File.separator + authentication.getAuthUserDetails().getUserId() +File.separator + timestamp.getTime();
		File userFolder = new File(mediaPath + File.separator + authentication.getAuthUserDetails().getUserId());
		userFolder.mkdir();
		File userFolderWithTime = new File(mediaPath + File.separator + authentication.getAuthUserDetails().getUserId()+File.separator + timestamp.getTime());
		userFolderWithTime.mkdir();
		List<String> files = new ArrayList<>(1);
		downloadFile(mediaUrl,userMediaPath, files, gcFileName);
		File file = new File(files.get(0));
		Response.ResponseBuilder responseBuilder = Response.ok((Object) file);
		responseBuilder.header("Content-Disposition", "attachment; filename="+originalFileName);
		return responseBuilder.build();
	}

	public static void downloadFile(String fileURL, String downloadedMediaTo,List<String> files, String gcFileName) throws IOException, URISyntaxException {

		URL url = new URL(fileURL);
		HttpsURLConnection httpConn = (HttpsURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();

		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String fileName = gcFileName;
			String disposition = httpConn.getHeaderField("Content-Disposition");
			String contentType = httpConn.getContentType();
			int contentLength = httpConn.getContentLength();

			/*if (disposition != null) {
				// extracts file name from header field
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10, disposition.length() - 1);
				}
			} else {
				// extracts file name from URL
				fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
			}*/


			LOGGER.info("Content-Type = " + contentType);
			LOGGER.info("Content-Disposition = " + disposition);
			LOGGER.info("Content-Length = " + contentLength);
			LOGGER.info("fileName = " + fileName);

			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();
			String saveFilePath = downloadedMediaTo + File.separator + fileName;

			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			// after downloading creating the file
			File imageFile = new File(saveFilePath);

			LOGGER.info("imageAbsPath:" + imageFile.getAbsolutePath());
			outputStream.close();
			inputStream.close();
			files.add(saveFilePath); // Adding files path for zipping
			LOGGER.info("File downloaded");
		} else {
			LOGGER.info("No file to download. Server replied HTTP code: " + responseCode);
		}
		httpConn.disconnect();
	}

}
