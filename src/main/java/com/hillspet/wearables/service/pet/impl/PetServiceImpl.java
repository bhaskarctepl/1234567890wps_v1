package com.hillspet.wearables.service.pet.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.lookup.LookupDao;
import com.hillspet.wearables.dao.pet.PetDao;
import com.hillspet.wearables.dto.BehaviorHistory;
import com.hillspet.wearables.dto.BulkUploadExternalPetIdInfo;
import com.hillspet.wearables.dto.ExternalPetInfoListDTO;
import com.hillspet.wearables.dto.PetCampaignPointsDTO;
import com.hillspet.wearables.dto.PetCampaignPointsListDTO;
import com.hillspet.wearables.dto.PetDTO;
import com.hillspet.wearables.dto.PetExternalInfoListDTO;
import com.hillspet.wearables.dto.PetFeedingEnthusiasmScale;
import com.hillspet.wearables.dto.PetImageScale;
import com.hillspet.wearables.dto.PetListDTO;
import com.hillspet.wearables.dto.PetName;
import com.hillspet.wearables.dto.PetNote;
import com.hillspet.wearables.dto.PetObservation;
import com.hillspet.wearables.dto.PetObservationMediaListDTO;
import com.hillspet.wearables.dto.PetParentListDTO;
import com.hillspet.wearables.dto.PetRedemptionHistoryDTO;
import com.hillspet.wearables.dto.PetStudyDTO;
import com.hillspet.wearables.dto.PetStudyDevice;
import com.hillspet.wearables.dto.StreamDevice;
import com.hillspet.wearables.dto.StudyLocation;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.ExternalPetInfoFilter;
import com.hillspet.wearables.dto.filter.PetActivityFactorFilter;
import com.hillspet.wearables.dto.filter.PetEnthusiasmFilter;
import com.hillspet.wearables.dto.filter.PetFilter;
import com.hillspet.wearables.dto.filter.PetImageScaleFilter;
import com.hillspet.wearables.dto.filter.PetObservationMediaFilter;
import com.hillspet.wearables.request.AddPetWeight;
import com.hillspet.wearables.request.AssociateNewStudyRequest;
import com.hillspet.wearables.request.BehaviorHistoryRequest;
import com.hillspet.wearables.request.BulkExtPetIdsUploadRequest;
import com.hillspet.wearables.request.ManualRecommendationRequest;
import com.hillspet.wearables.request.PetRequest;
import com.hillspet.wearables.request.UpdatePetIBWRequest;
import com.hillspet.wearables.request.ValidateDuplicatePetRequest;
import com.hillspet.wearables.response.ActivityFactorResultResponse;
import com.hillspet.wearables.response.ActivityFactorResultResponseList;
import com.hillspet.wearables.response.BehaviorForwardMotionGoalSettingResponse;
import com.hillspet.wearables.response.BehaviorVisualizationResponse;
import com.hillspet.wearables.response.BulkExtPetIdsUploadResponse;
import com.hillspet.wearables.response.ExternalPetInfoResponse;
import com.hillspet.wearables.response.PetAddressResponse;
import com.hillspet.wearables.response.PetCampaignResponse;
import com.hillspet.wearables.response.PetDevicesResponse;
import com.hillspet.wearables.response.PetFeedingEnthusiasmScaleResponse;
import com.hillspet.wearables.response.PetImageScaleResponse;
import com.hillspet.wearables.response.PetLegLengthResponse;
import com.hillspet.wearables.response.PetNotesResponse;
import com.hillspet.wearables.response.PetObservationMediaListResponse;
import com.hillspet.wearables.response.PetObservationsResponse;
import com.hillspet.wearables.response.PetParentListResponse;
import com.hillspet.wearables.response.PetRedemptionHistoryResponse;
import com.hillspet.wearables.response.PetWeightHistoryResponse;
import com.hillspet.wearables.response.PetsResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.pet.PetService;

@Service
public class PetServiceImpl implements PetService {

	private static final Logger LOGGER = LogManager.getLogger(PetServiceImpl.class);

	@Autowired
	private PetDao petDao;

	@Autowired
	private LookupDao lookupDao;

	@Autowired
	private Authentication authentication;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public PetsResponse getPetList(PetFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getPetList called");
		int searchedElements = 0;
		String counts = petDao.getPetsCount(filter);
		int total, totalActivePets, totalActiveStudies = 0;
		try {
			JsonNode jsonCountObj = mapper.readTree(counts);
			total = jsonCountObj.get("totalCount").asInt();
			totalActivePets = jsonCountObj.get("totalActivePets").asInt();
			totalActiveStudies = jsonCountObj.get("totalActiveStudies").asInt();
			searchedElements = jsonCountObj.get("searchedElementsCount").asInt();
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetsCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		List<PetListDTO> petList = total > 0 ? petDao.getPetList(filter) : new ArrayList<>();

		PetsResponse response = new PetsResponse();
		response.setPetsList(petList);
		response.setNoOfElements(petList.size());
		response.setTotalRecords(total);
		response.setTotalActivePets(totalActivePets);
		response.setTotalActiveStudies(totalActiveStudies);
		response.setSearchElments(searchedElements);

		LOGGER.debug("getPetList pet count is {}", petList);
		LOGGER.debug("getPetList completed successfully");
		return response;
	}

	@Override
	public List<PetListDTO> getPets() throws ServiceExecutionException {
		LOGGER.debug("getPets called");
		List<PetListDTO> pets = petDao.getPets();
		LOGGER.debug("getPets pet count is {}", pets.size());
		LOGGER.debug("getPets completed successfully");
		return pets;
	}

	@Override
	public PetDTO addPet(PetRequest petRequest) throws ServiceExecutionException {
		LOGGER.debug("updatePet called");
		PetDTO petDTO = petDao.addPet(petRequest);
		LOGGER.debug("updatePet completed successfully");
		return petDTO;
	}

	@Override
	public PetDTO updatePet(PetRequest petRequest) throws ServiceExecutionException {
		LOGGER.debug("updatePet called");
		PetDTO petDTO = petDao.updatePet(petRequest);
		LOGGER.debug("updatePet completed successfully");
		return petDTO;
	}

	@Override
	public PetDTO getPetById(int petId) throws ServiceExecutionException {
		LOGGER.debug("getPetById called");
		PetDTO petDTO = petDao.getPetById(petId);
		LOGGER.debug("getPetById completed successfully");
		return petDTO;
	}

	@Override
	public PetDTO getPetByIdAndStudyId(int petId, int studyId) throws ServiceExecutionException {
		LOGGER.debug("getPetByIdAndStudyId called");
		PetDTO petDTO = petDao.getPetByIdAndStudyId(petId, studyId);
		LOGGER.debug("getPetByIdAndStudyId completed successfully");
		return petDTO;
	}

	@Override
	public void deletePet(int petId, int modifiedBy) throws ServiceExecutionException {
		LOGGER.debug("deletePet called");
		petDao.deletePet(petId, modifiedBy);
		LOGGER.debug("deletePet completed successfully");
	}

	@Override
	public PetsResponse getPetsByPetParent(int petParentId) throws ServiceExecutionException {
		LOGGER.debug("getPetsByPetParent called");
		List<PetListDTO> petList = petDao.getPetsByPetParent(petParentId);

		PetsResponse response = new PetsResponse();
		response.setPets(petList);

		LOGGER.debug("getPetsByPetParent pet count is {}", petList.size());
		LOGGER.debug("getPetsByPetParent completed successfully");
		return response;
	}

	@Override
	public PetObservationsResponse getPetObservations(PetObservationMediaFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getPetObservations called");

		Map<String, Integer> mapper = petDao.getPetObservationsCount(filter);

		int searchCount = mapper.get("searchedElementsCount");
		int totalCount = mapper.get("totalCount");

		List<PetObservation> petObservations = totalCount > 0 ? petDao.getPetObservations(filter) : new ArrayList<>();

		PetObservationsResponse response = new PetObservationsResponse();
		response.setPetObservations(petObservations);
		response.setNoOfElements(petObservations.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(searchCount);

		response.setPetObservations(petObservations);
		LOGGER.debug("getPetObservations completed successfully");
		return response;
	}

	@Override
	public PetParentListResponse getPetParents(PetFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getPetParents called");
		String counts = petDao.getPetParentsCount(filter);

		HashMap<String, Integer> countMap = new HashMap<>();
		int totalCount = 0;
		try {
			countMap = mapper.readValue(counts, HashMap.class);
			totalCount = countMap.get("totalCount");
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetParentsCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		List<PetParentListDTO> parentDTOs = totalCount > 0 ? petDao.getPetParents(filter) : new ArrayList<>();
		PetParentListResponse response = new PetParentListResponse();
		response.setPetParents(parentDTOs);
		response.setNoOfElements(parentDTOs.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(totalCount);
		LOGGER.debug("getPetParents completed successfully");
		return response;
	}

	@Override
	public List<StreamDevice> getDeviceHistoryByStreamId(int petStudyId, String streamId)
			throws ServiceExecutionException {
		LOGGER.debug("getDeviceHistoryByStreamId called");
		List<StreamDevice> streamDevices = petDao.getDeviceHistoryByStreamId(petStudyId, streamId);
		LOGGER.debug("getDeviceHistoryByStreamId count is {}", streamDevices.size());
		LOGGER.debug("getPets completed successfully");
		return streamDevices;
	}

	@Override
	public PetNotesResponse getPetNotes(PetFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getPetNotes called");
		String counts = petDao.getPetNotesCount(filter);
		HashMap<String, Integer> countMap = new HashMap<>();
		int totalCount = 0;
		try {
			countMap = mapper.readValue(counts, HashMap.class);
			totalCount = countMap.get("totalCount");
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetParentsCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		List<PetNote> petNotes = totalCount > 0 ? petDao.getPetNotes(filter) : new ArrayList<>();
		PetNotesResponse response = new PetNotesResponse();
		response.setPetNotes(petNotes);
		response.setTotalRecords(totalCount);
		response.setSearchElments(totalCount);
		response.setNoOfElements(petNotes.size());
		LOGGER.debug("getPetNotes completed successfully");
		return response;
	}

	@Override
	public List<PetStudyDevice> getPetDevices(int petId) throws ServiceExecutionException {
		LOGGER.debug("getPetDevices called");
		List<PetStudyDevice> petDevices = petDao.getPetDevices(petId);
		LOGGER.debug("getPetDevices completed successfully");
		return petDevices;
	}

	@Override
	public void addPetNote(PetNote petNote) throws ServiceExecutionException {
		LOGGER.debug("addPetNote called");
		petDao.addPetNote(petNote);
		LOGGER.debug("addPetNote completed successfully");
	}

	@Override
	public PetObservationMediaListResponse getPetObservationMediaList(PetObservationMediaFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getPetObservationMediaList called");
		Map<String, Integer> mapper = petDao.getPetsObservatonMediaCount(filter);

		int searchCount = mapper.get("searchedElementsCount");
		int totalCount = mapper.get("totalCount");
		// HashMap<String, Integer> map = new HashMap<>();

		List<PetObservationMediaListDTO> petObservationMediaList = totalCount > 0
				? petDao.getPetObservationMediaList(filter)
				: new ArrayList<>();

		PetObservationMediaListResponse response = new PetObservationMediaListResponse();
		response.setPetObservationMediaList(petObservationMediaList);
		response.setNoOfElements(petObservationMediaList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(searchCount);

//		LOGGER.debug("getPetObservationMediaList pet count is {}", petObservationMediaList);
		LOGGER.debug("getPetObservationMediaList completed successfully");
		return response;
	}

	@Override
	public List<PetObservationMediaListDTO> getPetObservationMediaById(int petId) throws ServiceExecutionException {
		LOGGER.debug("getPetById called");
		List<PetObservationMediaListDTO> petObservationMediaListDTO = petDao.getPetObservationMediaById(petId);
		LOGGER.debug("getPetById completed successfully");
		return petObservationMediaListDTO;
	}

	@Override
	public PetsResponse getPetViewPane(PetFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getPetViewPane called");
		int searchedElements = 0;
		int userId = authentication.getAuthUserDetails().getUserId();
		int roleTypeId = authentication.getAuthUserDetails().getRoleTypeId();
		filter.setUserId(userId);
		filter.setRoleTypeId(roleTypeId);
		String counts = petDao.getPetViewPaneCount(filter);
		int total = 0;
		try {
			JsonNode jsonCountObj = mapper.readTree(counts);
			total = jsonCountObj.get("totalCount").asInt();
			searchedElements = jsonCountObj.get("searchedElementsCount").asInt();

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetsCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		List<PetListDTO> pets = petDao.getPetViewPane(filter);

		PetsResponse response = new PetsResponse();
		response.setPetsList(pets);
		response.setNoOfElements(pets.size());
		response.setTotalRecords(total);
		response.setSearchElments(searchedElements);

		LOGGER.debug("getPetViewPane completed successfully");
		return response;
	}

	@Override
	public void disassociateStudy(int petId, int studyId) throws ServiceExecutionException {
		LOGGER.debug("disassociateStudy called");
		petDao.disassociateStudy(petId, studyId);
		LOGGER.debug("disassociateStudy completed successfully");
	}

	@Override
	public PetWeightHistoryResponse getPetWeightHistory(int petId, String fromDate, String toDate)
			throws ServiceExecutionException {
		LOGGER.debug("getPetWeightHistory called");
		PetWeightHistoryResponse response = petDao.getPetWeightHistory(petId, fromDate, toDate);
		LOGGER.debug("getPetWeightHistory end");
		return response;
	}

	@Override
	public List<PetStudyDTO> getCurrentStudies(int petId) throws ServiceExecutionException {
		LOGGER.debug("getPetByIdAndStudyId called");
		List<PetStudyDTO> petDTO = petDao.getCurrentStudies(petId);
		LOGGER.debug("getPetByIdAndStudyId completed successfully");
		return petDTO;
	}

	@Override
	public List<PetStudyDTO> getArchiveStudies(int petId) throws ServiceExecutionException {
		LOGGER.debug("getPetByIdAndStudyId called");
		List<PetStudyDTO> petDTO = petDao.getArchiveStudies(petId);
		LOGGER.debug("getPetByIdAndStudyId completed successfully");
		return petDTO;
	}

	@Override
	public PetDevicesResponse getPetDevicesByStudy(PetFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getPetDevicesByStudy called");

		String counts = petDao.getPetDevicesByStudyCount(filter);
		int total = 0;
		try {
			total = Integer.parseInt(counts);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetCampaignListCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		List<PetStudyDevice> petDevices = total > 0 ? petDao.getPetDevicesByStudy(filter) : new ArrayList<>();
		PetDevicesResponse response = new PetDevicesResponse();
		response.setNoOfElements(petDevices.size());
		response.setTotalRecords(total);
		response.setSearchElments(total);
		response.setPetDevices(petDevices);

		LOGGER.debug("getPetDevicesByStudy completed successfully");
		return response;
	}

	@Override
	public PetDTO getPetDetailsById(int petId) throws ServiceExecutionException {
		LOGGER.debug("getPetDetailsById called");
		PetDTO petDTO = petDao.getPetDetailsById(petId);
		LOGGER.debug("getPetDetailsById completed successfully");
		return petDTO;
	}

	@Override
	public PetCampaignPointsDTO getPetCampaignPoints(int petId) throws ServiceExecutionException {
		LOGGER.debug("getPetCampaignPoints called");
		PetCampaignPointsDTO petDTO = petDao.getPetCampaignPoints(petId);
		LOGGER.debug("getPetCampaignPoints completed successfully");
		return petDTO;
	}

	@Override
	public PetCampaignResponse getPetCampaignPointsList(int petId, int activityId, BaseFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getPetCampaignPointsList called");
		String counts = petDao.getPetCampaignListCount(petId, activityId);
		int total = 0;
		try {
			total = Integer.parseInt(counts);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetCampaignListCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		List<PetCampaignPointsListDTO> campaignList = petDao.getPetCampaignPointsList(petId, activityId, filter);
		PetCampaignResponse response = new PetCampaignResponse();
		response.setNoOfElements(campaignList.size());
		response.setTotalRecords(total);
		response.setPetCampaignList(campaignList);

		LOGGER.debug("getPetCampaignPointsList end");

		return response;
	}

	@Override
	public PetRedemptionHistoryResponse getPetRedemptionHistory(int petId) throws ServiceExecutionException {
		LOGGER.debug("getPetRedemptionHistory called");

		List<PetRedemptionHistoryDTO> redemptionHistoryDTOs = petDao.getPetRedemptionHistory(petId);
		PetRedemptionHistoryResponse response = new PetRedemptionHistoryResponse();
		response.setNoOfElements(redemptionHistoryDTOs.size());
		response.setTotalRecords(redemptionHistoryDTOs.size());
		response.setRedemptionHistoryList(redemptionHistoryDTOs);

		LOGGER.debug("getPetRedemptionHistory end");

		return response;
	}

	@Override
	public void redeemRewardPoints(int petId, int pointsRedeemed, int userId) throws ServiceExecutionException {
		LOGGER.debug("redeemRewardPoints called");
		petDao.redeemRewardPoints(petId, pointsRedeemed, userId);
		LOGGER.debug("redeemRewardPoints completed successfully");
	}

	@Override
	public List<PetExternalInfoListDTO> getExternalPetInfoList(int studyId) throws ServiceExecutionException {
		LOGGER.debug("getExternalPetInfo start");
		List<PetExternalInfoListDTO> externalPetList = petDao.getExternalPetInfoList(studyId);
		LOGGER.debug("getExternalPetInfo end externalPetList size is " + externalPetList.size());
		return externalPetList;
	}

	@Override
	public ExternalPetInfoResponse getExternalPetInfo(ExternalPetInfoFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getExternalPetInfo called");
		List<ExternalPetInfoListDTO> externalPetInfoListDTOList = petDao.getExternalPetInfo(filter);
		ExternalPetInfoResponse response = new ExternalPetInfoResponse();
		response.setExternalPetInfoListDTOList(externalPetInfoListDTOList);
		LOGGER.debug("getExternalPetInfo completed successfully");
		return response;
	}

	@Override
	public PetDTO associateNewStudy(AssociateNewStudyRequest request) throws ServiceExecutionException {
		LOGGER.debug("associateNewStudy called");
		PetDTO petDTO = petDao.associateNewStudy(request);
		LOGGER.debug("associateNewStudy completed successfully");
		return petDTO;
	}

	@Override
	public PetDTO addPetWeight(AddPetWeight addPetWeight) throws ServiceExecutionException {
		LOGGER.debug("addPetWeight called");
		PetDTO petDTO = petDao.addPetWeight(addPetWeight);
		LOGGER.debug("addPetWeight completed successfully");
		return petDTO;
	}

	@Override
	public void updateWeight(int weightId, String weight, String unit, boolean latest) throws ServiceExecutionException {
		LOGGER.debug("disassociateStudy called");
		petDao.updateWeight(weightId, weight, unit, latest);
		LOGGER.debug("disassociateStudy completed successfully");
	}

	@Override
	public PetFeedingEnthusiasmScaleResponse getPetFeedingEnthusiasmScaleDtls(PetEnthusiasmFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getPetFeedingEnthusiasmScaleDtls called");
		int searchedElements = 0;
		String counts = petDao.getPetFeedingEnthusiasmScaleDtlsCount(filter);
		int total = 0;
		try {
			JsonNode jsonCountObj = mapper.readTree(counts);
			total = jsonCountObj.get("totalCount").asInt();
			searchedElements = jsonCountObj.get("searchedElementsCount").asInt();

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetFeedingEnthusiasmScaleDtls", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		List<PetFeedingEnthusiasmScale> petEnthusiasmScaleList = total > 0
				? petDao.getPetFeedingEnthusiasmScaleDtls(filter)
				: new ArrayList<>();

		PetFeedingEnthusiasmScaleResponse response = new PetFeedingEnthusiasmScaleResponse();
		response.setPetFeedingEnthusiasmScaleDtls(petEnthusiasmScaleList);
		response.setNoOfElements(petEnthusiasmScaleList.size());
		response.setTotalRecords(total);
		response.setSearchElments(searchedElements);

		LOGGER.debug("getPetFeedingEnthusiasmScaleDtls pet count is {}", petEnthusiasmScaleList);
		LOGGER.debug("getPetFeedingEnthusiasmScaleDtls completed successfully");
		return response;
	}

	@Override
	public PetImageScaleResponse getPetImageScaleDtls(PetImageScaleFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getPetImageScaleDtls called");
		int searchedElements = 0;
		String counts = petDao.getPetImageScaleDtlsCount(filter);
		int total = 0;
		try {
			JsonNode jsonCountObj = mapper.readTree(counts);
			total = jsonCountObj.get("totalCount").asInt();
			searchedElements = jsonCountObj.get("searchedElementsCount").asInt();

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetImageScaleDtls", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		List<PetImageScale> petImageScaleList = total > 0 ? petDao.getPetImageScaleDtls(filter) : new ArrayList<>();

		PetImageScaleResponse response = new PetImageScaleResponse();
		response.setPetImageScaleDtls(petImageScaleList);
		response.setNoOfElements(petImageScaleList.size());
		response.setTotalRecords(total);
		response.setSearchElments(searchedElements);

		LOGGER.debug("getPetImageScaleDtls pet count is {}", petImageScaleList);
		LOGGER.debug("getPetImageScaleDtls completed successfully");
		return response;
	}

	@Override
	public ActivityFactorResultResponseList getPetActivityFactorResult(PetActivityFactorFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("ActivityFactorResultResponseList called");
		int searchedElements = 0;
		String counts = petDao.ActivityFactorResultResponseListCount(filter);
		int total = 0;
		try {
			JsonNode jsonCountObj = mapper.readTree(counts);
			total = jsonCountObj.get("totalCount").asInt();
			searchedElements = jsonCountObj.get("searchedElementsCount").asInt();

		} catch (Exception e) {
			LOGGER.error("error while fetching ActivityFactorResultResponseList", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		List<ActivityFactorResultResponse> petAFResultList = total > 0 ? petDao.getPetActivityFactorResult(filter)
				: new ArrayList<>();
		/*
		 * List<ActivityFactorResultResponse> petAFResultList =
		 * getActivityFactorResultListTemp();
		 */

		ActivityFactorResultResponseList response = new ActivityFactorResultResponseList();
		response.setResultList(petAFResultList);
		response.setNoOfElements(petAFResultList.size());
		response.setTotalRecords(total);
		response.setSearchElments(searchedElements);

		LOGGER.debug("ActivityFactorResultResponseList pet count is {}", petAFResultList);
		LOGGER.debug("ActivityFactorResultResponseList completed successfully");
		return response;
	}

	@Override
	public PetAddressResponse getPetAddressHistoryById(int petId) throws ServiceExecutionException {
		LOGGER.debug("getPetAddressHistoryById called");
		PetAddressResponse petAddresses = petDao.getPetAddressHistoryById(petId);
		LOGGER.debug("getPetAddressHistoryById completed successfully");
		return petAddresses;
	}

	@Override
	public List<PetName> getPointsAccumulatedPets(int userId) throws ServiceExecutionException {
		LOGGER.debug("getPointsAccumulatedPets called");

		List<PetName> petsList = petDao.getPointsAccumulatedPets(userId);

		return petsList;
	}

	@Override
	public String validateDuplicatePet(ValidateDuplicatePetRequest petRequest) throws ServiceExecutionException {
		LOGGER.debug("validateDuplicatePet called");
		return petDao.validateDuplicatePet(petRequest);
	}

	@Override
	public BehaviorVisualizationResponse getBehaviorVisualization(int petId) throws ServiceExecutionException {
		LOGGER.debug("getBehaviorVisualization called");
		return petDao.getBehaviorVisualization(petId);
	}

	@Override
	public List<BehaviorHistory> getBehaviorHistoryByType(int petId, String behaviorType,
			BehaviorHistoryRequest behaviorHistoryRequest) throws ServiceExecutionException {
		LOGGER.debug("getBehaviorHistoryByType called");
		return petDao.getBehaviorHistoryByType(petId, behaviorType, behaviorHistoryRequest);
	}

	@Override
	public BehaviorForwardMotionGoalSettingResponse getForwardMotionGoalSetting(int petId)
			throws ServiceExecutionException {
		LOGGER.debug("getForwardMotionGoalSetting called");
		return petDao.getForwardMotionGoalSetting(petId);
	}

	public PetLegLengthResponse getPetLegLengthHistoryById(int petId, int unitId) throws ServiceExecutionException {
		LOGGER.debug("getPetLegLengthHistoryById called");
		PetLegLengthResponse response = petDao.getPetLegLengthHistoryById(petId, unitId);
		LOGGER.debug("getPetLegLengthHistoryById completed successfully");
		return response;
	}

	@Override
	public void updateIBW(int petId, int ibwId, UpdatePetIBWRequest updatePetIBWRequest)
			throws ServiceExecutionException {
		LOGGER.debug("updateIBW called");
		petDao.updateIBW(petId, ibwId, updatePetIBWRequest);
		LOGGER.debug("updateIBW completed successfully");
	}

	public Workbook generateBulkUploadExcel() {
		LOGGER.debug("generateBulkUploadExcel called");
		Workbook workbook = null;
		workbook = new XSSFWorkbook();
		try {

			Sheet sheet = workbook.createSheet("External Pet ID Template");
			String[] validHeaders = new String[] { "Wearables Pet ID", "Wearables Study ID", "External Pet ID",
					"Study Location" };

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BROWN.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setBorderTop(BorderStyle.MEDIUM);
			headerCellStyle.setBorderRight(BorderStyle.MEDIUM);
			headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
			headerCellStyle.setBorderLeft(BorderStyle.MEDIUM);
			headerCellStyle.setWrapText(true);

			// Row for Header
			CellStyle messageCellStyle = workbook.createCellStyle();
			messageCellStyle.setBorderTop(BorderStyle.MEDIUM);
			messageCellStyle.setBorderRight(BorderStyle.MEDIUM);
			messageCellStyle.setBorderBottom(BorderStyle.MEDIUM);
			messageCellStyle.setBorderLeft(BorderStyle.MEDIUM);
			messageCellStyle.setWrapText(true);
			Font messageFont = workbook.createFont();
			messageFont.setColor(IndexedColors.BROWN.getIndex());
			messageCellStyle.setFont(messageFont);

			Row headerRow = sheet.createRow(0);
			for (int col = 0; col < validHeaders.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(validHeaders[col]);
				cell.setCellStyle(headerCellStyle);
			}

			// Row for Message header
			Row messageRow = sheet.createRow(1);
			for (int col = 0; col < validHeaders.length; col++) {
				Cell cell = messageRow.createCell(col);
				String message = "";
				CellStyle style = workbook.createCellStyle();
				sheet.setColumnWidth(col, 22 * 256);
				cell.setCellStyle(messageCellStyle);
				switch (validHeaders[col]) {
				case "Wearables Pet ID":
					message = "Length: Max. 20 characters\n" + "Required: Yes";
					break;
				case "Wearables Study ID":
					message = "Length: Max. 20 characters\n" + "Required: Yes";
					break;
				case "External Pet ID":
					message = "Length: Max. 20 characters\n" + "Required: Yes";
					break;
				case "Study Location":
					message = "Length: Max. 20 characters \n" + "Required: Yes";
					break;
				}
				cell.setCellValue(message);
				style.setWrapText(true);
			}

			// Added external system Id dropdowns
			DataValidation dataValidation = null;
			DataValidationConstraint constraint = null;
			DataValidationHelper validationHelper = null;
			validationHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);

			Row row = sheet.createRow(2);
			row.createCell(3).setCellValue("");

			validationHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);

			List<StudyLocation> locationList = lookupDao.getStudyLocations();
			locationList.removeIf(location -> location.getStudyLocation().toLowerCase().equals("prelude"));
			List<String> studyLocationNames = locationList.stream().map(StudyLocation::getStudyLocation)
					.collect(Collectors.toList());
			String[] studyLocationArray = null;
			if (studyLocationNames.size() > 0) {
				studyLocationArray = studyLocationNames.toArray(new String[0]);
			}

			if (row.getCell(3).toString() == "") {
				Arrays.sort(studyLocationArray);
				CellRangeAddressList cellRange = new CellRangeAddressList(2, 500, 3, 3);
				try {
					constraint = validationHelper.createExplicitListConstraint(studyLocationArray);
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.error("studyLocationArray error.", e);
				}
				dataValidation = validationHelper.createValidation(constraint, cellRange);
				dataValidation.setSuppressDropDownArrow(true);

				sheet.addValidationData(dataValidation);
			}

			LOGGER.debug("generateBulkUploadExcel ended.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	@Override
	public int bulkUploadExtPetIds(InputStream uploadedInputStream, FormDataContentDisposition fileDetail,
			Integer userId) throws ServiceExecutionException {
		LOGGER.debug("bulkAssetUpload called");
		List<BulkUploadExternalPetIdInfo> list = convertBulkExcelToExtPetIdList(uploadedInputStream, userId);

		if ((list == null || list.size() == 0)) {
			return 0;
		}
		LOGGER.debug("addDeviceInfoBulkUploadPreview bulkAssetUpload size is " + list.size());
		petDao.saveBulkExternalPetIdsToStaging(list, fileDetail.getFileName(), userId);

		LOGGER.debug("bulkAssetUpload - saveBulkDevicesToStaging return result size is " + list.size());
		LOGGER.debug("bulkAssetUpload completed successfully");
		return list.size();
	}

	private List<BulkUploadExternalPetIdInfo> convertBulkExcelToExtPetIdList(InputStream uploadedInputStream,
			int userId) throws ServiceExecutionException {
		final String strRGEX = "^[a-zA-Z\\d-]+$";
		List<BulkUploadExternalPetIdInfo> externalPetIdsList = new ArrayList<>();
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(uploadedInputStream);

			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();

			// LIst of headers from excel
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
			}

			while (rows.hasNext()) {

				Row currentRow = rows.next();

				if (currentRow.getRowNum() < 2) {
					continue; // just skip the rows if row number is 0 or 1
				}
				if (checkIfRowIsEmpty(currentRow)) {
					continue;
				}

				int cellIdx = 0;
				BulkUploadExternalPetIdInfo extPetIdInfo = new BulkUploadExternalPetIdInfo();
				List<String> errorsList = new ArrayList<String>();
				// cn- cell number
				for (int cn = 0; cn < currentRow.getLastCellNum(); cn++) {
					// System.out.println(currentRow.getLastCellNum());
					Cell cell = currentRow.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					DataFormatter formatter = new DataFormatter();
					String value = formatter.formatCellValue(cell).trim();
					int valueLength = value.trim().length();

					switch (cellIdx) {
					case 0:
						if (valueLength == 0) {
							errorsList.add("Wearables Pet ID is mandatory and allows maximum 20 characters");
						} else {
							// Max Length validation
							if (value.trim().length() > 20) {
								errorsList
										.add("Invalid Wearables Pet ID (" + value + "), allows maximum 20 characters");
								value = "";
							}
						}
						extPetIdInfo.setPetId(Integer.parseInt(value));
						break;
					case 1:
						if (valueLength == 0) {
							errorsList.add("Wearables Study Id is mandatory and allows maximum 20 characters");
						} else {
							if (value.trim().length() > 20) {
								errorsList.add(
										"Invalid Wearables Study Id (" + value + "), allows maximum 20 characters");
								value = "";
							}
						}
						extPetIdInfo.setStudyId(value);
						break;
					case 2:
						if (valueLength == 0) {
							errorsList.add("External Pet Id is mandatory and allows maximum 20 characters");
						} else {
							if (value.trim().length() > 20) {
								errorsList.add("Invalid External Pet Id (" + value + "), allows maximum 20 characters");
								value = "";
							} else {
								if (!Pattern.matches(strRGEX, value)) {
									errorsList.add("Invalid External Pet Id (" + value
											+ "), allows alphanumeric, hyphen only");
									value = "";
								}
							}
						}
						extPetIdInfo.setExtPetIdOrTattooNo(value);
						break;
					case 3:
						if (valueLength == 0) {
							errorsList.add("Study Location is mandatory and allows maximum 20 characters");
						} else {
							if (value.trim().length() > 20) {
								errorsList.add("Invalid Study Location (" + value + "), allows maximum 20 characters");
								value = "";
							}
						}
						extPetIdInfo.setStudyLocation(value);
						break;
					default:
						break;
					}
					cellIdx++;
				}
				if (errorsList.size() > 0) {
					extPetIdInfo.setExceptionMsg(StringUtils.join(errorsList, ","));
				}
				externalPetIdsList.add(extPetIdInfo);
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
		return externalPetIdsList;
	}

	private boolean validateHeaders(List<String> givenHeaders) {
		if (!givenHeaders.isEmpty()) {
			String[] validHeaders = new String[] { "Wearables Pet ID", "Wearables Study ID", "External Pet ID",
					"Study Location" };
			for (int i = 0; i < givenHeaders.size(); i++) {
				if (!Arrays.asList(validHeaders).contains(givenHeaders.get(i))) {
					return false;
				}
			}
		}
		return true;
	}

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

	@Override
	public void saveBulkUploadExtPetIds(BulkExtPetIdsUploadRequest request) {
		LOGGER.debug("saveBulkUploadExtPetIds called");
		petDao.saveBulkUploadExtPetIds(request);
		LOGGER.debug("saveBulkUploadExtPetIds completed successfully");
	}

	@Override
	public BulkExtPetIdsUploadResponse getBulkUploadExtPetIdsList(BaseFilter filter) {
		LOGGER.debug("getBulkUploadExtPetIdsList called");
		int total = petDao.getBulkUploadExtPetIdsListCount(filter);

		List<BulkUploadExternalPetIdInfo> extPetIdsList = total > 0 ? petDao.getBulkUploadExtPetIdsList(filter)
				: new ArrayList<>();
		BulkExtPetIdsUploadResponse response = new BulkExtPetIdsUploadResponse();
		response.setExternalPetIdsList(extPetIdsList);
		response.setNoOfElements(extPetIdsList.size());
		response.setSearchElments(total);
		response.setTotalRecords(total);

		LOGGER.debug("getBulkUploadExtPetIdsList is {}", extPetIdsList);
		LOGGER.debug("getBulkUploadExtPetIdsList completed successfully");
		return response;
	}

	@Override
	public void saveManualEnterForRecommendation(ManualRecommendationRequest manualRecommendationRequest) {
		LOGGER.debug("Started SaveManualEnterForRecommendation");
		petDao.saveManualEnterForRecommendation(manualRecommendationRequest);
		LOGGER.debug("Completed SaveManualEnterForRecommendation");
	}

	@Override
	public HashMap<String, String> getPetThresholdDetails(int afId, int userId) {
		LOGGER.debug("Started getPetThresholdDetails");
		HashMap<String, String> response = petDao.getPetThresholdDetails(afId, userId);
		LOGGER.debug("Completed getPetThresholdDetails");
		return response;
	}
}
