package com.hillspet.wearables.jaxrs.resource;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.springframework.http.MediaType;

import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.response.Message;
import com.hillspet.wearables.dto.FeedingScheduleConfig;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.FeedingScheduleFilter;
import com.hillspet.wearables.dto.filter.FeedingScheduleResponseFilter;
import com.hillspet.wearables.dto.filter.ImageScaleFilter;
import com.hillspet.wearables.dto.filter.IntakeFilter;
import com.hillspet.wearables.dto.filter.PhaseWisePetListFilter;
import com.hillspet.wearables.dto.filter.QuestionnaireConfigFilter;
import com.hillspet.wearables.dto.filter.QuestionnaireResponseFilter;
import com.hillspet.wearables.dto.filter.StudyDiaryFilter;
import com.hillspet.wearables.dto.filter.StudyDietFilter;
import com.hillspet.wearables.dto.filter.StudyFilter;
import com.hillspet.wearables.jaxrs.resource.impl.PushNotificationListResponse;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.request.AddCrossOverStudyRequest;
import com.hillspet.wearables.request.AddPhaseWisePetRequest;
import com.hillspet.wearables.request.AddStudyRequest;
import com.hillspet.wearables.request.FlaggedRecommendationRequest;
import com.hillspet.wearables.request.FlaggedRecommendationStatusRequest;
import com.hillspet.wearables.request.ImageScoringConfigRequest;
import com.hillspet.wearables.request.PushNotificationConfigRequest;
import com.hillspet.wearables.request.QuestionnaireConfigRequest;
import com.hillspet.wearables.request.StudyActivityFactorRequest;
import com.hillspet.wearables.request.StudyDietRequest;
import com.hillspet.wearables.request.StudyMobileAppConfigRequest;
import com.hillspet.wearables.request.StudyNotesRequest;
import com.hillspet.wearables.request.StudyNotificationConfigRequest;
import com.hillspet.wearables.request.StudyNotificationRequest;
import com.hillspet.wearables.request.StudyPlanRequest;
import com.hillspet.wearables.request.StudyPreludeConfigRequest;
import com.hillspet.wearables.request.StudyQuestionnaireRequest;
import com.hillspet.wearables.response.ImageScoresConfigResponse;
import com.hillspet.wearables.response.ImageScoringScaleResponse;
import com.hillspet.wearables.response.PetBreedResponse;
import com.hillspet.wearables.response.PetsResponse;
import com.hillspet.wearables.response.PreludeListResponse;
import com.hillspet.wearables.response.PushNotificationConfigResponse;
import com.hillspet.wearables.response.QuestionnaireConfigResponse;
import com.hillspet.wearables.response.QuestionnaireListResponse;
import com.hillspet.wearables.response.QuestionnaireResponse;
import com.hillspet.wearables.response.StudyActivityFactorResponse;
import com.hillspet.wearables.response.StudyDietListResponse;
import com.hillspet.wearables.response.StudyImageScalesListResponse;
import com.hillspet.wearables.response.StudyListResponse;
import com.hillspet.wearables.response.StudyMobileAppConfigResponse;
import com.hillspet.wearables.response.StudyNotesListResponse;
import com.hillspet.wearables.response.StudyNotificationConfigResponse;
import com.hillspet.wearables.response.StudyNotificationResponse;
import com.hillspet.wearables.response.StudyPhaseQuestionnaireScheduleList;
import com.hillspet.wearables.response.StudyPlanListResponse;
import com.hillspet.wearables.response.StudyPreludeConfigResponse;
import com.hillspet.wearables.response.StudyResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/study")
@Api(value = "RESTful service that performs study related operations", tags = { "Study Management" })
@Produces({ MediaType.APPLICATION_JSON_VALUE, Constants.MEDIA_TYPE_APPLICATION_JSON_INITIAL_VERSION1 })
@Consumes({ MediaType.APPLICATION_JSON_VALUE, Constants.MEDIA_TYPE_APPLICATION_JSON_INITIAL_VERSION1 })
public interface StudyResource {

	/* ---------------- STUDY SERVICES ------------------------- */
	@POST
	@ApiOperation(value = "Add Study", notes = "Add a Study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response AddStudy(@Valid @ApiParam(name = "addStudyRequest", required = true) AddStudyRequest studyRequest);

	@PUT
	@ApiOperation(value = "Update Study", notes = "Update a Study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response updateStudy(
			@Valid @ApiParam(name = "updateStudyRequest", required = true) AddStudyRequest studyRequest);

	@GET
	@Path("/getStudyBasicDetails/{studyId}")
	@ApiOperation(value = "Get pets associated to a study", notes = "Get pets associated to a study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = StudyResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getStudyBasicDetails(@PathParam("studyId") int studyId);

	@POST
	@Path("/diet/{studyId}")
	@ApiOperation(value = "Add Diet to Study ", notes = "Add a Diet to a Study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = StudyDietListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response addStudyDiet(
			@Valid @ApiParam(name = "studyDietRequest", required = true) StudyDietRequest studyDietRequest,
			@PathParam("studyId") int studyId);

	@GET
	@Path("/diet/{studyId}")
	@ApiOperation(value = "Get diets which are mapped to a study", notes = "Get the diets list based on Filters")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = StudyDietListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getStudyDiets(@BeanParam StudyDietFilter filter, @PathParam("studyId") int studyId);

	@GET
	@Path("{studyId}/diet/{studyDietId}")
	@ApiOperation(value = "Validate Study Diet Configuration", notes = "Validate Study Diet Configuration")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response validateStudyDiet(@PathParam("studyId") int studyId, @PathParam("studyDietId") int studyDietId);

	@POST
	@Path("/plan/{studyId}")
	@ApiOperation(value = "Add Plan to Study ", notes = "Add a Plan to a Study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = StudyPlanListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response addStudyPlan(
			@Valid @ApiParam(name = "studyPlanRequest", required = true) StudyPlanRequest studyPlanRequest,
			@PathParam("studyId") int studyId);

	@GET
	@Path("/plan/{studyId}")
	@ApiOperation(value = "Get plans which are mapped to a study", notes = "Get the plans list based on Filters")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = StudyPlanListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getStudyPlans(@PathParam("studyId") int studyId);

	@POST
	@Path("/mobileappconfig/{studyId}")
	@ApiOperation(value = "Add mobile app config to study ", notes = "Add a mobile app config to a study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response addStudyMobileAppConfig(
			@Valid @ApiParam(name = "studyMobileAppConfigRequest", required = true) StudyMobileAppConfigRequest studyMobileAppConfigRequest,
			@PathParam("studyId") int studyId);

	@GET
	@Path("/mobileappconfig/{studyId}")
	@ApiOperation(value = "Get mobile app config which are mapped to a study", notes = "Get the app config list based on Filters")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = StudyMobileAppConfigResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getStudyMobielAppConfigs(@PathParam("studyId") int studyId);

	@POST
	@Path("/{studyId}/{phaseId}/notes")
	@ApiOperation(value = "Add mobile app config to study ", notes = "Add a notes to a study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = StudyNotesListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response addNotes(
			@Valid @ApiParam(name = "studyNotesRequest", required = true) StudyNotesRequest studyNotesRequest,
			@PathParam("studyId") int studyId, @PathParam("phaseId") int phaseId);

	@GET
	@Path("/{studyId}/{phaseId}/notes")
	@ApiOperation(value = "Get study notes", notes = "Get study notes")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = StudyNotesListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getStudyNotes(@PathParam("studyId") int studyId, @PathParam("phaseId") int phaseId);

	@POST
	@Path("/{studyId}/preludeConfig")
	@ApiOperation(value = "Add mobile app config to study ", notes = "Add a prelude config to a study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response addStudyPreludeConfig(
			@Valid @ApiParam(name = "studyPreludeConfigRequest", required = true) StudyPreludeConfigRequest studyPreludeConfigRequest,
			@PathParam("studyId") int studyId);

	@GET
	@Path("/{studyId}/preludeConfig")
	@ApiOperation(value = "Get study mobile app config", notes = "Get study prelude config")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = StudyPreludeConfigResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getStudyPreludeConfig(@PathParam("studyId") int studyId);

	@POST
	@Path("/{studyId}/activityFactorConfig")
	@ApiOperation(value = "Add mobile app config to study ", notes = "Add activity factor config to a study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response addStudyActivityFactorConfig(
			@Valid @ApiParam(name = "studyActivityFactorRequest", required = true) StudyActivityFactorRequest studyActivityFactorRequest,
			@PathParam("studyId") int studyId);

	@GET
	@Path("/{studyId}/activityFactorConfig")
	@ApiOperation(value = "Get study mobile app config", notes = "Get study activity factor config")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = StudyActivityFactorResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getStudyActivityFactorConfig(@PathParam("studyId") int studyId);

	// ===================================================================================================================================================
	@PUT
	@Path("/associatePlan/{studyId}/{planId}/{subscriptionDate}")
	@ApiOperation(value = "Associate Plan to Study", notes = "Associate Plan to Study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response associatePlan(@PathParam("studyId") int studyId, @PathParam("planId") int planId,
			@PathParam("subscriptionDate") String subscriptionDate);

	@PUT
	@Path("/disassociatePlan/{studyId}/{planId}")
	@ApiOperation(value = "Disassociate Plan with Study", notes = "Disassociate  Plan with Study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response disassociatePlan(@PathParam("studyId") int studyId, @PathParam("planId") int planId);

	@GET
	@Path("/associatedPets/{studyId}")
	@ApiOperation(value = "Get pets associated to a study", notes = "Get pets associated to a study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetsResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response associatedPets(@PathParam("studyId") int studyId);

	@GET
	@ApiOperation(value = "Get Studies", notes = "Get the Study list based on Filters")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = StudyListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getStudies(@BeanParam StudyFilter filter);

	@GET
	@Path("/getStudyList")
	@ApiOperation(value = "Get all Studies", notes = "Get all the Study list")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = StudyListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getStudyList(@QueryParam("includeFuture") String includeFuture,
			@QueryParam("includeVirtual") String includeVirtual);

	@GET
	@Path("/{id}")
	@ApiOperation(value = "Get the Study by Id", notes = "Get the Study details by Id")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = StudyResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getStudyById(@PathParam("id") int studyId);

	@GET
	@Path("getStudies/{petParentId}/{petId}")
	@ApiOperation(value = "Get the Studies by Pet Parent Id and Pet Id", notes = "Get the Studies by Pet Parent Id and Pet Id")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = StudyListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getStudiesByPetParentAndPet(@PathParam("petParentId") int petParentId,
			@PathParam("petId") int petId);

	@DELETE
	@Path("/{id}")
	@ApiOperation(value = "Delete Study", notes = "Deletes a Study in the application")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response deleteStudy(@PathParam("id") int studyId);

	@PUT
	@Path("/associateQuestionnaire/{studyId}/{questionnaireId}/{startDate}/{endDate}")
	@ApiOperation(value = "Associate Questionnaire to Study", notes = "Associate Questionnaire to Study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response associateQuestionnaire(@PathParam("studyId") int studyId,
			@PathParam("questionnaireId") int questionnaireId, @PathParam("startDate") String startDate,
			@PathParam("endDate") String endDate);

	@PUT
	@Path("/updateStudyQuestionnaire/{studyId}/{questionnaireId}/{startDate}/{endDate}")
	@ApiOperation(value = "Associate Plan to Study", notes = "Associate Questionnaire to Study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response updateStudyQuestionnaire(@PathParam("studyId") int studyId,
			@PathParam("questionnaireId") int questionnaireId, @PathParam("startDate") String startDate,
			@PathParam("endDate") String endDate);

	@PUT
	@Path("/disassociateQuestionnaire/{studyId}/{questionnaireId}")
	@ApiOperation(value = "Disassociate Questionnaire with Study", notes = "Disassociate  Questionnaire with Study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response disassociateQuestionnaire(@PathParam("studyId") int studyId,
			@PathParam("questionnaireId") int questionnaireId);

	@GET
	@Path("/associatedQuestionnaires/{studyId}")
	@ApiOperation(value = "Get questionnaires associated to a study", notes = "Get questionnaires associated to a study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = QuestionnaireListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response associatedQuestionnaires(@PathParam("studyId") int studyId);

	/* ---------------- STUDY NOTIFICATIONS SERVICES ------------------------- */
	@GET
	@Path("/getStudyNotifications")
	@ApiOperation(value = "Get Study Notification", notes = "Get the Study Notification list")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = StudyNotificationResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getStudyNotifications(@BeanParam BaseFilter filter);

	@PUT
	@Path("/updateStudyNotificationStatus")
	@ApiOperation(value = "Update Study Notification Status", notes = "Enabel/Disable Study Notification in the application")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response updateStudyNotificationStatus(
			@Valid @ApiParam(name = "updateStudyNotificationStatusReqeust", required = true) StudyNotificationRequest studyNotificationRequest);

	@GET
	@Path("/getStudyListByUser")
	@ApiOperation(value = "Get all Studies by user id", notes = "Get all the Study list By User")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = StudyListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getStudyListByUser();

	@GET
	@Path("/getAllStudyList")
	@ApiOperation(value = "Get all Studies by user id", notes = "Get all the Study list By User")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = StudyListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getAllStudyList();

	@GET
	@Path("/getPreludeDataList/{studyId}")
	@ApiOperation(value = "Get all Prelude data", notes = "Get all the Prelude data list")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PreludeListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPreludeDataList(@PathParam("studyId") int studyId);

	@GET
	@Path("/getPreludeDataByStudy/{studyName}")
	@ApiOperation(value = "Get Prelude Data By Study", notes = "Get all the Prelude Data By Study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PreludeListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPreludeDataByStudy(@PathParam("studyName") String studyName);

	@PUT
	@Path("/associatePushNotifications/{studyId}/{notificationId}/{startDate}/{endDate}")
	@ApiOperation(value = "Associate Notification to Study", notes = "Associate Notification to Study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response associatePushNotifications(@PathParam("studyId") int studyId,
			@PathParam("notificationId") int notificationId, @PathParam("startDate") String startDate,
			@PathParam("endDate") String endDate);

	@PUT
	@Path("/updateStudyPushNotifications/{studyId}/{notificationId}/{startDate}/{endDate}")
	@ApiOperation(value = "Associate Notification to Study", notes = "Associate Notification to Study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response updateStudyPushNotifications(@PathParam("studyId") int studyId,
			@PathParam("notificationId") int notificationId, @PathParam("startDate") String startDate,
			@PathParam("endDate") String endDate);

	@PUT
	@Path("/disassociatePushNotifications/{studyId}/{notificationId}")
	@ApiOperation(value = "Disassociate Notification with Study", notes = "Disassociate  Notification with Study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response disassociatePushNotifications(@PathParam("studyId") int studyId,
			@PathParam("notificationId") int notificationId);

	@GET
	@Path("/associatedPushNotifications/{studyId}")
	@ApiOperation(value = "Get Notifications associated to a study", notes = "Get Notifications associated to a study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PushNotificationListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response associatedPushNotifications(@PathParam("studyId") int studyId);

	@GET
	@Path("/associatedImageScales/{studyId}")
	@ApiOperation(value = "Get Image Scales associated to a study", notes = "Get Image Scales associated to a study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = StudyImageScalesListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response associatedImageScales(@PathParam("studyId") int studyId);

	@GET
	@Path("/getAFPreludeDataList/{studyId}")
	@ApiOperation(value = "Get all AF Prelude data", notes = "Get all the AF Prelude data list")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PreludeListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getAFPreludeDataList(@PathParam("studyId") int studyId);

	@GET
	@Path("/getAFPreludeDataByStudy/{studyName}")
	@ApiOperation(value = "Get Prelude AF Data By Study", notes = "Get all the Prelude Data By Study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PreludeListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getAFPreludeDataByStudy(@PathParam("studyName") String studyName);

	// ------- Services for study questionnaire data extract configuration --------
	@GET
	@Path("/getQuestionnaireDtlsForDataExtractConfig/{studyQuestionnireId}")
	@ApiOperation(value = "Get Questionnaire details to Config Data Extract Config", notes = "Get Questionnaire details to Config Data Extract Config By StudyQuestionnaireId")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = QuestionnaireResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getQuestionnaireDtlsForDataExtractConfig(
			@PathParam("studyQuestionnireId") Integer studyQuestionnireId);

	@PUT
	@Path("/updateDataExtractConfigStudyQuestionnaire")
	@ApiOperation(value = "Update Data Extract Config Study Questionnaire", notes = "Update Data Extract Config Study Questionnaire")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response updateDataExtractConfigStudyQuestionnaire(
			@Valid @ApiParam(name = "addStudyRequest", required = true) StudyQuestionnaireRequest studyQuestionnaireRequest);

	@GET
	@Path("/loadPreludeData")
	@ApiOperation(value = "Load Prelude Data", notes = "Load Prelude Data")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response loadPreludeData();

	// ------- Services for study feeding schedule configuration --------
	@GET
	@Path("/getStudyPhaseDuration/{studyId}/{phaseId}")
	@ApiOperation(value = "Get Study Phase Days", notes = "Get Study Phase Days")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = FeedingScheduleConfig.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getStudyPhaseDuration(@PathParam("studyId") Integer studyId, @PathParam("phaseId") Integer phaseId);

	@GET
	@Path("/getStudyPhaseDiets/{studyId}/{phaseId}")
	@ApiOperation(value = "Get Study Phase Diets", notes = "Get Study Phase Diets")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = FeedingScheduleConfig.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getStudyPhaseDiets(@PathParam("studyId") Integer studyId, @PathParam("phaseId") Integer phaseId);

	@GET
	@Path("/getFeedingScheduleList/{studyId}/{phaseId}")
	@ApiOperation(value = "Get Study Phase Diets", notes = "Get Study Phase Diets")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = FeedingScheduleConfig.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getFeedingScheduleList(@PathParam("studyId") Integer studyId, @PathParam("phaseId") Integer phaseId,
			@BeanParam FeedingScheduleFilter filter);

	@DELETE
	@Path("/deleteFeedingSchedule/{feedingScheduleConfigId}")
	@ApiOperation(value = "Delete Feeding Schedule", notes = "Deletes the feeding schedule based on id")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response deleteFeedingSchedule(@PathParam("feedingScheduleConfigId") int feedingScheduleConfigId);

	@POST
	@Path("/saveStudyPhaseFeedingSchedule")
	@ApiOperation(value = "Save Feeding Schedule", notes = "Save Feeding Schedule")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response saveStudyPhaseFeedingSchedule(
			@Valid @ApiParam(name = "feedingSchedule", required = true) FeedingScheduleConfig feedingSchedule);

	// ----------------- Services for Adding pet,Search page and Delete Pet to Study
	@GET
	@Path("/{studyId}/{phaseId}/getPetList")
	@ApiOperation(value = "Get PetList Based on Study and Phase", notes = "Get PetList Based on Study and Phase")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = StudyListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPhaseWisePetList(@PathParam("studyId") int studyId, @PathParam("phaseId") int phaseId,
			@BeanParam PhaseWisePetListFilter filter);

	@POST
	@Path("/{studyId}/{phaseId}/addPetsToPhase")
	@ApiOperation(value = "Add Pet Phase Wise", notes = "Add a Pet Phase Wise")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response addPhaseWisePet(@PathParam("studyId") int studyId, @PathParam("phaseId") int phaseId,
			@Valid @ApiParam(name = "addPhaseWisePetRequest", required = true) AddPhaseWisePetRequest addPhaseWisePetRequest);

	@PUT
	@Path("/{studyId}/{phaseId}/removePets")
	@ApiOperation(value = "Remove PetFromPhase ", notes = "Remove Phase From Phase")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response removePetsFromPhase(@PathParam("studyId") int studyId, @PathParam("phaseId") int phaseId,
			@Valid @ApiParam(name = "removePetsFromPhaseRequest", required = true) AddPhaseWisePetRequest addPhaseWisePetRequest);

	@PUT
	@Path("/{studyId}/{phaseId}/changePetsTreatmentGroup")
	@ApiOperation(value = "Change Pets Treatment Group", notes = "Change Pets Treatment Group")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response changePetsTreatmentGroup(@PathParam("studyId") int studyId, @PathParam("phaseId") int phaseId,
			@Valid @ApiParam(name = "changePetsTreatmentGroupRequest", required = true) AddPhaseWisePetRequest addPhaseWisePetRequest);

	@PUT
	@Path("/{studyId}/{phaseId}/movePetsToNextPhase")
	@ApiOperation(value = "Move Pets To Next Phase", notes = "Move Pets To Next Phase")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response movePetsToNextPhase(@PathParam("studyId") int studyId, @PathParam("phaseId") int phaseId,
			@Valid @ApiParam(name = "movePetsToNextPhaseRequest", required = true) AddPhaseWisePetRequest addPhaseWisePetRequest);

	@GET
	@Path("/{studyId}/getStudyDiary")
	@ApiOperation(value = "Get Study Dairy List", notes = "This method is used to fetch study diary list.")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getStudyDiary(@PathParam("studyId") int studyId, @BeanParam StudyDiaryFilter filter);

	@POST
	@Path("/{studyId}/{phaseId}/extendPhase")
	@ApiOperation(value = "ExtendPhase", notes = "Extend Phase")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response extendPhase(@PathParam("studyId") int studyId, @PathParam("phaseId") int phaseId,
			@Valid @ApiParam(name = "ExtendPhaseRequest", required = true) AddPhaseWisePetRequest addPhaseWisePetRequest);

	@GET
	@Path("/getFeedingScheduleResponse/{studyId}/{phaseId}")
	@ApiOperation(value = "Get Feeding Schedule Response", notes = "Get Feeding Schedule Response")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = FeedingScheduleConfig.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getFeedingScheduleResponse(@PathParam("studyId") int studyId, @PathParam("phaseId") int phaseId,
			@BeanParam FeedingScheduleResponseFilter filter);

	@GET
	@Path("/getFeedingScheduleByPet/{feedingScheduleId}")
	@ApiOperation(value = "Get Feeding Schedule By Pet", notes = "Get Feeding Schedule By Pet")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = FeedingScheduleConfig.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getFeedingScheduleByPet(@PathParam("feedingScheduleId") int feedingScheduleId);

	@POST
	@Path("/{studyId}/{phaseId}/pushNotificationConfig")
	@ApiOperation(value = "Configure study push notification to study ", notes = "Configure study push notification to a study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PushNotificationConfigResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response pushNotificationConfig(@PathParam("studyId") int studyId, @PathParam("phaseId") int phaseId,
			@Valid @ApiParam(name = "pushNotificationConfigRequest", required = true) PushNotificationConfigRequest pushNotificationConfigRequest);

	@GET
	@Path("/{studyId}/{phaseId}/getPushNotificationConfig")
	@ApiOperation(value = "Get study push notification configuration", notes = "Get study study push notification configuration")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PushNotificationConfigResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPushNotificationConfig(@PathParam("studyId") int studyId, @PathParam("phaseId") int phaseId);

	@POST
	@Path("/{studyId}/{phaseId}/imageScoringConfig")
	@ApiOperation(value = "Configure image scores to study ", notes = "Configure image scores to a study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = ImageScoresConfigResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response imageScoringConfig(@PathParam("studyId") int studyId, @PathParam("phaseId") int phaseId,
			@Valid @ApiParam(name = "imageScoringConfigRequest", required = true) ImageScoringConfigRequest imageScoringConfigRequest);

	@GET
	@Path("/{studyId}/{phaseId}/getImageScoringConfig")
	@ApiOperation(value = "Get study images coring configuration", notes = "Get study images coring configuration")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = ImageScoresConfigResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getImageScoringConfig(@PathParam("studyId") int studyId, @PathParam("phaseId") int phaseId);

	@POST
	@Path("/{studyId}/{phaseId}/questionnaireConfig")
	@ApiOperation(value = "Study Phase Questionnaire Config", notes = "Add Questionnaire Config To Study Phase")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = QuestionnaireConfigResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response questionnaireConfig(@PathParam("studyId") int studyId, @PathParam("phaseId") int phaseId,
			@Valid @ApiParam(name = "questionnaireConfigRequest", required = true) QuestionnaireConfigRequest questionnaireConfigRequest);

	@DELETE
	@Path("/{studyId}/{phaseId}/questionnaireConfig/{questionnaireConfigId}")
	@ApiOperation(value = "Study Phase Questionnaire Config", notes = "Add Questionnaire Config To Study Phase")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response deleteQuestionnaireConfig(@PathParam("studyId") int studyId, @PathParam("phaseId") int phaseId,
			@PathParam("questionnaireConfigId") int questionnaireConfigId);

	@GET
	@Path("/{studyId}/{phaseId}/getQuestionnaireConfig")
	@ApiOperation(value = "Get Study Phase Questionnaire Configuration", notes = "Get Study Phase Questionnaire Configuration")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = QuestionnaireConfigResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getQuestionnaireConfig(@PathParam("studyId") int studyId, @PathParam("phaseId") int phaseId);

	@GET
	@Path("/{studyId}/{phaseId}/getQuestionnaireConfigList")
	@ApiOperation(value = "Get Study Phase Questionnaire Configuration", notes = "Get Study Phase Questionnaire Configuration")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = QuestionnaireConfigResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getQuestionnaireConfigList(@PathParam("studyId") int studyId, @PathParam("phaseId") int phaseId,
			@BeanParam QuestionnaireConfigFilter filter);

	@GET
	@Path("/{studyId}/{phaseId}/{questionnaireId}/getQuestionnaireConfigListByQuestionnaire")
	@ApiOperation(value = "Get Study Phase Questionnaire Configuration", notes = "Get Study Phase Questionnaire Configuration")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = QuestionnaireConfigResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getQuestionnaireConfigListByQuestionnaire(@PathParam("studyId") int studyId,
			@PathParam("phaseId") int phaseId, @PathParam("questionnaireId") int questionnaireId,
			@BeanParam QuestionnaireConfigFilter filter);

	@GET
	@Path("/getImageScoringResponse/{studyId}/{phaseId}")
	@ApiOperation(value = "Get Feeding Schedule Response", notes = "Get Feeding Schedule Response")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = ImageScoringScaleResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getImageScoringResponse(@PathParam("studyId") int studyId, @PathParam("phaseId") int phaseId,
			@BeanParam ImageScaleFilter filter);

	@GET
	@Path("/{studyId}/{phaseId}/questionnaireResponse")
	@ApiOperation(value = "Get Questionnaire Response", notes = "Get Questionnaire Response")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = StudyPhaseQuestionnaireScheduleList.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getQuestionnaireResponse(@PathParam("studyId") int studyId, @PathParam("phaseId") int phaseId,
			@BeanParam QuestionnaireResponseFilter filter);

	@GET
	@Path("/getPetFoodIntakeHistory/{studyId}/{phaseId}/{petId}")
	@ApiOperation(value = "Get Pet Food Intake History", notes = "Get Pet Food Intake History")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = FeedingScheduleConfig.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPetFoodIntakeHistory(@PathParam("studyId") int studyId, @PathParam("phaseId") int phaseId,
			@PathParam("petId") int petId, @BeanParam FeedingScheduleResponseFilter filter);

	@GET
	@Path("/getPets/{studyId}")
	@ApiOperation(value = "Get All the Pets", notes = "Get the Pets list")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetsResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPets(@PathParam("studyId") int studyId);

	@POST
	@Path("/flaggedRecommendation/getThresholdByCountForStudy")
	@ApiOperation(value = "Load Threshold by Count data", notes = "Load Prelude Data")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getThresholdByCount(
			@Valid @ApiParam(name = "getFlaggedRecommendationRequest", required = true) FlaggedRecommendationRequest flaggedRecommendationRequest);

	@POST
	@Path("/flaggedRecommendation/getFilters")
	@ApiOperation(value = "Load the filters data", notes = "Load filter Data")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getFRFilterData(
			@Valid @ApiParam(name = "getFlaggedRecommendationRequest", required = true) FlaggedRecommendationRequest flaggedRecommendationRequest);

	@GET
	@Path("/flaggedRecommendation/getRecommendationListForStudy")
	@ApiOperation(value = "Load Flagged Recommendation details for Study", notes = "Load  Flagged Recommendation details")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getRecommendationListForStudy(@BeanParam FlaggedRecommendationRequest flaggedRecommendationRequest);

	@POST
	@Path("/flaggedRecommendation/updateStatus")
	@ApiOperation(value = "Update the Status", notes = "Update the Status")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response updateStatus(
			@Valid @ApiParam(name = "getFlaggedRecommendationStatusRequest", required = true) FlaggedRecommendationStatusRequest flaggedRecommendationStatusRequest);

	@GET
	@Path("/foodIntake")
	@ApiOperation(value = "Load Food Intake for Study & Pet", notes = "Load Food Intake details")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getFoodIntake(@BeanParam() IntakeFilter intakeFilter);

	/* ---------------- STUDY SERVICES ------------------------- */
	@POST
	@Path("/createCrossOverStudy")
	@ApiOperation(value = "Add Cross Over Study", notes = "Add Cross Over Study for an exsting Study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response createCrossOverStudy(
			@Valid @ApiParam(name = "addCrossOverStudyRequest", required = true) AddCrossOverStudyRequest addCrossOverStudyRequest);

	@GET
	@Path("/{studyId}/{phaseId}/pet-breeds")
	@ApiOperation(value = "Get Pet Breed List by Study and Phase", notes = "Gets the Pet Breed List by Study and Phase")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetBreedResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPetBreeds(@PathParam("studyId") int studyId, @PathParam("phaseId") int phaseId);

	@POST
	@Path("/notificationconfig/{studyId}")
	@ApiOperation(value = "Add Notification config to study ", notes = "Add a Notification config to a study")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response addStudyNotificationConfig(
			@Valid @ApiParam(name = "studyNotificationConfigRequest", required = false) StudyNotificationConfigRequest studyNotificationConfigRequest,
			@PathParam("studyId") int studyId);

	@GET
	@Path("/notificationconfig/{studyId}")
	@ApiOperation(value = "Get Notification config's which are mapped to a study", notes = "Get the Notification config list based on Filters")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = StudyNotificationConfigResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getStudyNotificationConfigs(@PathParam("studyId") int studyId);
}
