package com.hillspet.wearables.jaxrs.resource.impl;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.response.ErrorResponse;
import com.hillspet.wearables.common.response.ResponseStatus;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.dto.DietDTO;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.DietFilter;
import com.hillspet.wearables.jaxrs.resource.DietDetailsResource;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.request.BulkDietUploadRequest;
import com.hillspet.wearables.request.DietRequest;
import com.hillspet.wearables.response.BulkDietUploadResponse;
import com.hillspet.wearables.response.DietListResponse;
import com.hillspet.wearables.response.DietLookUpResponse;
import com.hillspet.wearables.response.DietResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.diet.DietDetailsService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class DietDetailsResourceImpl implements DietDetailsResource {

    private static final Logger LOGGER = LogManager.getLogger(DietDetailsResourceImpl.class);

    @Autowired
    private DietDetailsService dietDetailsService;

    @Autowired
    private Authentication authentication;

    @Autowired
    private JaxrsJsonResponseBuilder responseBuilder;
    /*
    * To Save build upload data
    * */
    @Override
    public Response bulkDietUpload(InputStream uploadedInputStream, FormDataContentDisposition fileDetail) {
        if (StringUtils.isNotBlank(fileDetail.getFileName())  && FilenameUtils.getExtension(fileDetail.getFileName()).equals("xlsx")) {
            Integer userId = authentication.getAuthUserDetails().getUserId();
            HashMap<String,String> response = dietDetailsService.bulkAssetUpload(uploadedInputStream, fileDetail, userId);
            SuccessResponse<HashMap<String,String>> successResponse = new SuccessResponse<>();
            successResponse.setServiceResponse(response);
            return responseBuilder.buildResponse(successResponse);
        } else {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setStatus(new ResponseStatus(Boolean.FALSE, Response.Status.BAD_REQUEST.getStatusCode()));
            return responseBuilder.buildResponse(errorResponse);
        }
    }
    /*
    * Get Uploaded diet data by user
    * */
    @Override
    public Response getBulkUploadDietList(DietFilter filter) {
        int userId = authentication.getAuthUserDetails().getUserId();
        filter.setUserId(userId);
        BulkDietUploadResponse response = dietDetailsService.getBulkUploadDietList(filter);
        SuccessResponse<BulkDietUploadResponse> successResponse = new SuccessResponse<>();
        successResponse.setServiceResponse(response);
        return responseBuilder.buildResponse(successResponse);
    }
    /*
    * To Save selected records
    * */
    @Override
    public Response saveBulkUploadDietInfo(BulkDietUploadRequest request) {
        Integer userId = authentication.getAuthUserDetails().getUserId();
        request.setUserId(userId);
        Integer result = dietDetailsService.saveBulkUploadDietInfo(request);
        if (result > 0) {
            SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
            return responseBuilder.buildResponse(successResponse);
        } else {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setStatus(new ResponseStatus(Boolean.FALSE, Response.Status.BAD_REQUEST.getStatusCode()));
            return responseBuilder.buildResponse(errorResponse);
        }
    }
    /*
    * Service to download template
    * */
    @Override
    public Response downloadBulkDietUploadFile() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Workbook workbook = null;
        try {
            workbook = dietDetailsService.generateBulkUploadExcel();
            workbook.write(byteArrayOutputStream);
        } catch (IOException e) {
            LOGGER.error(e);
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
        Response.ResponseBuilder response = Response.ok(byteArrayOutputStream.toByteArray()).header("Content-Disposition", "attachment; filename=\"Diet Information Template.xlsx\"");
        return response.build();
    }
    
    /**
     * @author akumarkhaspa
     * @param request
     * @return
     */
	@Override
	public Response addDietInfo(DietRequest request) {
		// Step 1: get login user details
		Integer userId = authentication.getAuthUserDetails().getUserId();
		request.setUserId(userId);
		
		//Step 2: calling addDiet method to add diet
		DietDTO dietDto = dietDetailsService.addDiet(request);
		DietResponse response = new DietResponse();
		response.setDietDto(dietDto);
		
		// Step 2: build a successful response
		SuccessResponse<DietResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}
	
	/**
	 * @author akumarkhaspa
	 * @param dietRequest
	 * @return
	 */
	@Override
	public Response updateDiet(DietRequest dietRequest) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		dietRequest.setUserId(userId);
		DietDTO dietDto = dietDetailsService.updateDiet(dietRequest);
		DietResponse response = new DietResponse();
		response.setDietDto(dietDto);
		// Step 5: build a successful response
		SuccessResponse<DietResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	/**
	 * @author akumarkhaspa
	 * @param dietId
	 * @return
	 */
	@Override
	public Response getDietById(int dietId) {
		DietDTO dietDto = dietDetailsService.getDietById(dietId);
		DietResponse response = new DietResponse();
		response.setDietDto(dietDto);
		SuccessResponse<DietResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	/**
	 * @author akumarkhaspa
	 * @param filter
	 * @return
	 */
	@Override
	public Response getDietList(BaseFilter filter) {
		filter.setUserId(authentication.getAuthUserDetails().getUserId());
		DietListResponse response = dietDetailsService.getDietList(filter);
		SuccessResponse<DietListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	/**
	 * @author akumarkhaspa
	 * @return 
	 */
	@Override
	public Response getFilterByData() {
		DietLookUpResponse response = dietDetailsService.getFilterByData();
		SuccessResponse<DietLookUpResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}
}
