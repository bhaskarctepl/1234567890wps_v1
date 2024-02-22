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
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.springframework.http.MediaType;

import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.response.Message;
import com.hillspet.wearables.dto.filter.AddressFilter;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.request.PetParentRequest;
import com.hillspet.wearables.request.PetParentValidateEmailRequest;
import com.hillspet.wearables.request.ValidateAddressRequest;
import com.hillspet.wearables.response.PetParentAddressResponse;
import com.hillspet.wearables.response.PetParentListResponse;
import com.hillspet.wearables.response.PetParentResponse;
import com.hillspet.wearables.response.TimeZoneResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/petParents")
@Api(value = "RESTful service that performs pet parent related operations", tags = { "Pet Parent Management" })
@Produces({ MediaType.APPLICATION_JSON_VALUE, Constants.MEDIA_TYPE_APPLICATION_JSON_INITIAL_VERSION1 })
@Consumes({ MediaType.APPLICATION_JSON_VALUE, Constants.MEDIA_TYPE_APPLICATION_JSON_INITIAL_VERSION1 })
public interface PetParentResource {

	@POST
	@ApiOperation(value = "Add Pet Parent", notes = "Add a pet parent")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetParentResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response addPetParent(
			@Valid @ApiParam(name = "addPetparentRequest", required = true) PetParentRequest petParentRequest);

	@PUT
	@ApiOperation(value = "Update Pet Parent", notes = "Update a Pet Parent")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetParentResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response updatePetParent(
			@Valid @ApiParam(name = "updatePetparentRequest", required = true) PetParentRequest petParentRequest);

	@GET
	@Path("/{id}")
	@ApiOperation(value = "Get the Pet Parent details by Id", notes = "Get the Pet Parent details by Id")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetParentResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPetParentById(@PathParam("id") int petParentId);

	@GET
	@ApiOperation(value = "Get Pet Parent", notes = "<h2>Purpose:</h2>\r\n"
			+ "<p>This function allows API users to search for pet parents based on designated serach criteria.</p>\r\n"
			+ "<h2>Prerequisites:</h2>\r\n"
			+ "<ol>\r\n"
			+ "    <li>Token - Get token from the Login Resource method</li>\r\n"
			+ "    <li>Details of the pet parent being searched</li>\r\n"
			+ "    <li>If no search condition is provided, all the pet parents information will be retrieved</li>\r\n"
			+ "</ol>\r\n"
			+ "<h2>Steps:</h2>\r\n"
			+ "<ol>\r\n"
			+ "    <li>Enable the Parameters section by clicking \"Try it out\".</li>\r\n"
			+ "    <li>Complete the Parameters section.</li>\r\n"
			+ "    <li>Click \"Execute\" to get the response.</li>\r\n"
			+ "</ol>")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetParentListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPetParentList(@BeanParam BaseFilter filter);

	@GET
	@Path("/getPetParents")
	@ApiOperation(value = "Get the Pet Parents", notes = "Get the Pet Parents")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetParentListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPetParents();

	@DELETE
	@Path("/{id}")
	@ApiOperation(value = "Delete Pet Parent", notes = "Deletes a Pet Parent in the application")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response deletePetParent(@PathParam("id") int petParentId);

	
	@POST
	@Path("/validatePetParentEmail")
	@ApiOperation(value = "Validate Pet Parent Email", notes = "Validate a pet parent Email")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetParentResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response validatePetParentEmail(
			@Valid @ApiParam(name = "validatPetParentRequest", value = "<b>*Note:</b> Completely remove any object element which does not have a value.\r\n"
					+ "The API will not run edit checks or calculations. A user will need to log into Prelude and press 'Save' on the form or use 'Batch Edit Checks'.",required = true) PetParentValidateEmailRequest petParentRequest);
	
	@GET
	@Path("/{id}/addressHistory")
	@ApiOperation(value = "Get the Pet Parent address history by Id", notes = "Get the Pet Parent address history by Id")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetParentAddressResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPetParentAddressHistoryById(@PathParam("id") int petParentId);
	
	@GET
	@Path("/validateAddress")
	@ApiOperation(value = "Validate the address and find the timezone", notes = "Validate the given address and if address is valid get the timezone of that address", hidden = true)
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = TimeZoneResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response validateAddress(@BeanParam AddressFilter addressFilter);
	
	@POST
	@Path("/validateAddress")
	@ApiOperation(value = "Validate the address and find the timezone", notes = "Validate the given address and if address is valid get the timezone of that address")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = TimeZoneResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response validateUserAddress(@Valid @ApiParam(name = "validateAddressRequest", required = true) ValidateAddressRequest validateAddressRequest);
	
	@GET
	@Path("/validateUnAssignedPetAddress/{petId}/{petParentId}")
	@ApiOperation(value = "Delete Pet Parent", notes = "Deletes a Pet Parent in the application")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response validateUnAssignedPetAddress(@PathParam("petId") int petId, @PathParam("petParentId") int petParentId);

}
