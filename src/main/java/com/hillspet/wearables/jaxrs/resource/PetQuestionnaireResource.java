package com.hillspet.wearables.jaxrs.resource;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.springframework.http.MediaType;

import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.response.Message;
import com.hillspet.wearables.dto.filter.PetFilter;
import com.hillspet.wearables.dto.filter.PetQuestionnaireFilter;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.request.NotificationRequest;
import com.hillspet.wearables.request.PetQuestionnaireRequest;
import com.hillspet.wearables.response.PetQuestionnaireReccurrenceResponse;
import com.hillspet.wearables.response.PetSchedQuestionnaireListResponse;
import com.hillspet.wearables.response.PetSchedQuestionnaireResponse;
import com.hillspet.wearables.response.PetsResponse;
import com.hillspet.wearables.response.QuestionnaireViewResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This class providing pet questionnaire details.
 * 
 * @author vvodyaram
 * @since w1.0
 * @version w1.0
 * @version Dec 28, 2023
 */
@Path("/pets/questionnaire")
@Api(value = "RESTful service that performs pet questionnaire related operations", tags = {
		"Pet Questionnaire Management" })
@Produces({ MediaType.APPLICATION_JSON_VALUE, Constants.MEDIA_TYPE_APPLICATION_JSON_INITIAL_VERSION1 })
@Consumes({ MediaType.APPLICATION_JSON_VALUE, Constants.MEDIA_TYPE_APPLICATION_JSON_INITIAL_VERSION1 })
public interface PetQuestionnaireResource {

	@GET
	@Path("/getPetsToSchedQuestionnaire")
	@ApiOperation(value = "Get Pet List to Schedule questionnaire by filters", notes = "Get the Pet List to Schedule questionnaire list based on filters")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetsResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPetsToSchedQuestionnaire(@BeanParam PetFilter filter);

	@POST
	@ApiOperation(value = "Add Pet Questionnaire", notes = "Creates a Pet Questionnaire in the application")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response addPetQuestionnaire(
			@Valid @ApiParam(name = "addPetQuestionnaireRequest", required = true) PetQuestionnaireRequest petQuestionnaireRequest);

	@GET
	@ApiOperation(value = "Get Pet Questionnaire by filters", notes = "Get the Pet Questionnaire list based on filters")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetSchedQuestionnaireListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPetSchedQuestionnaireList(@BeanParam PetQuestionnaireFilter filter);

	@GET
	@Path("/getPetQuestionnaireListByConfigId")
	@ApiOperation(value = "Get Pet Questionnaire by filters", notes = "Get the Pet Questionnaire list based on filters")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetQuestionnaireReccurrenceResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPetQuestionnaireListByConfigId(@BeanParam PetQuestionnaireFilter filter);

	@GET
	@Path("/{petQuestionnaireConfigId}")
	@ApiOperation(value = "Get Pet Questionnaire Scheduled By Config Id", notes = "Get a Pet Questionnaire Scheduled Details By Config Id")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetSchedQuestionnaireResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPetQuestionnaireByConfigId(@PathParam("petQuestionnaireConfigId") int petQuestionnaireConfigId);

	@DELETE
	@Path("/{id}")
	@ApiOperation(value = "Delete Pet Questionnaire", notes = "Deletes a Pet Questionnaire in the application")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response deletePetQuestionnaire(@PathParam("id") int petQuestionnaireConfigId);

	@GET
	@Path("/response/{petQuestionnaireConfigId}/{questionnaireResponseId}")
	@ApiOperation(value = "Get pet questionnaire response by response Id", notes = "Get all questionnaire response list")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = QuestionnaireViewResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getQuestionnaireResponse(@PathParam("petQuestionnaireConfigId") int petQuestionnaireConfigId,
			@PathParam("questionnaireResponseId") int questionnaireResponseId);

	@POST
	@Path("/sendNotification")
	@ApiOperation(value = "To send push notification to pet parents", notes = "To send push notification to pet parents who are not responded to questionnaire")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response sendNotification(
			@Valid @ApiParam(name = "sendNotificationRequest", required = true) NotificationRequest notificationRequest);

}
