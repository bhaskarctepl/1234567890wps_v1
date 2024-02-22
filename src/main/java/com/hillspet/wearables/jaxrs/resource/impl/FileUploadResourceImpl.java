package com.hillspet.wearables.jaxrs.resource.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.common.utils.GCPClientUtil;
import com.hillspet.wearables.jaxrs.resource.FileUploadResource;


@Service
public class FileUploadResourceImpl implements FileUploadResource {

	@Autowired
	GCPClientUtil gcpClientUtil;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;
	
	@Value("${gcp.bucketName}")
	private String bucketName;

	private static final Logger LOGGER = LogManager.getLogger(FileUploadResourceImpl.class);

	@Override
	public Response uploadFile(InputStream uploadedInputStream, FormDataContentDisposition fileDetail,
			String uploadFolderName, FormDataBodyPart bodyPart) {
		LOGGER.debug("FileUploadResourceImpl  :::  start");
		
		String fileName = gcpClientUtil.uploadFile(uploadedInputStream, bodyPart, uploadFolderName);
		LOGGER.debug("FileUploadResourceImpl  :::  fileName " +  fileName);
		ArrayList<String> fileArray = new ArrayList<>();
		fileArray.add(fileName);
		
		if (StringUtils.isNotBlank(fileName)) {
			String fileUrl = gcpClientUtil.getDownloaFiledUrl(fileName, Constants.GCP_PETPHOTO_PATH);
			fileArray.add(fileUrl);
		}
		
		SuccessResponse<ArrayList<String>> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(fileArray);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response bulkUpload(InputStream uploadedInputStream, FormDataContentDisposition fileDetail,
			String uploadFolderName, List<FormDataBodyPart> bodyPartList) {
		LOGGER.debug("FileUploadResourceImpl  :::  bulkUpload start");
		List<Map<String, String>> fileNameMap = gcpClientUtil.bulkUpload(bodyPartList, uploadFolderName);
		LOGGER.debug("FileUploadResourceImpl  :::  fileNameMap = " + fileNameMap);
		SuccessResponse<List<Map<String, String>>> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(fileNameMap);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getFileUrlByName(String fileName) {
		LOGGER.info("getFileUrlByName start ");
		String fileUrl = "";
		if (StringUtils.isNotBlank(fileName)) {
			fileUrl = gcpClientUtil.getDownloaFiledUrl(fileName, Constants.GCP_PETPHOTO_PATH);
		}
		LOGGER.info("getFileUrlByName end ");
		SuccessResponse<String> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(fileUrl);
		return responseBuilder.buildResponse(successResponse);
	}
}
