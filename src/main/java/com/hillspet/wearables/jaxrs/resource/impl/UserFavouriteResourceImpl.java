
/**
 * Created Date: 27-Dec-2020
 * Class Name  : UserFavouriteResourceImpl.java
 * Description : Description of the package.
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                sgorle          27-Dec-2020     Created.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.jaxrs.resource.impl;

import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.jaxrs.resource.UserFavouriteResource;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.response.IsUserFavouriteResponse;
import com.hillspet.wearables.response.UserFavouriteListResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.favourite.UserFavouriteService;

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
public class UserFavouriteResourceImpl implements UserFavouriteResource {

	private static final Logger LOGGER = LogManager.getLogger(UserFavouriteResourceImpl.class);

	@Autowired
	private UserFavouriteService userFavouriteService;

	@Autowired
	private Authentication authentication;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Override
	public Response favourite(int menuId, int entityId) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		userFavouriteService.favourite(menuId, entityId, userId, null);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response unfavourite(int menuId, int entityId) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		userFavouriteService.unfavourite(menuId, entityId, userId, null);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getFavouriteRecords(BaseFilter filter) {
		Integer userId = authentication.getAuthUserDetails().getUserId();
		UserFavouriteListResponse response = userFavouriteService.getFavouriteRecords(filter, userId, 0);
		SuccessResponse<UserFavouriteListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response isFavourite(int menuId, int entityId) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		IsUserFavouriteResponse userFavouriteResponse = userFavouriteService.isFavourite(menuId, entityId, userId,
				0);

		// Step 2: build a successful response
		SuccessResponse<IsUserFavouriteResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(userFavouriteResponse);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response checkUserAssociatedStudy(int menuId, int entityId) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		boolean isStudyAssociated = userFavouriteService.checkUserAssociatedStudy(menuId, entityId, userId);

		// Step 2: build a successful response
		SuccessResponse<Boolean> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(isStudyAssociated);
		return responseBuilder.buildResponse(successResponse);
	}

}
