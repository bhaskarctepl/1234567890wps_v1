package com.hillspet.wearables.service.pet.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.utils.PushNotificationUtil;
import com.hillspet.wearables.dao.pet.PetQuestionnaireDao;
import com.hillspet.wearables.dto.PetListDTO;
import com.hillspet.wearables.dto.PetParent;
import com.hillspet.wearables.dto.PetQuestionnaireReccurrence;
import com.hillspet.wearables.dto.PetSchedQuestionnaire;
import com.hillspet.wearables.dto.filter.PetFilter;
import com.hillspet.wearables.dto.filter.PetQuestionnaireFilter;
import com.hillspet.wearables.request.NotificationRequest;
import com.hillspet.wearables.request.PetQuestionnaireRequest;
import com.hillspet.wearables.response.PetQuestionnaireReccurrenceResponse;
import com.hillspet.wearables.response.PetSchedQuestionnaireListResponse;
import com.hillspet.wearables.response.PetsResponse;
import com.hillspet.wearables.response.QuestionnaireViewResponse;
import com.hillspet.wearables.service.pet.PetQuestionnaireService;

@Service
public class PetQuestionnaireServiceImpl implements PetQuestionnaireService {

	private static final Logger LOGGER = LogManager.getLogger(PetQuestionnaireServiceImpl.class);

	@Autowired
	private PetQuestionnaireDao petQuestionnaireDao;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public PetsResponse getPetsToSchedQuestionnaire(PetFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getPetsToSchedQuestionnaire called");
		Map<String, Integer> mapper = petQuestionnaireDao.getPetsToSchedQuestionnaireCount(filter);
		int searchedElements = mapper.get("searchCount");
		int totalCount = mapper.get("totalCount");

		List<PetListDTO> petList = searchedElements > 0 ? petQuestionnaireDao.getPetsToSchedQuestionnaire(filter)
				: new ArrayList<>();

		PetsResponse response = new PetsResponse();
		response.setPetsList(petList);
		response.setNoOfElements(petList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(searchedElements);

		LOGGER.debug("getPetsToSchedQuestionnaire count is {}", petList);
		LOGGER.debug("getPetsToSchedQuestionnaire completed successfully");
		return response;
	}

	@Override
	public void addPetQuestionnaire(PetQuestionnaireRequest petQuestionnaireRequest) throws ServiceExecutionException {
		LOGGER.debug("addPetQuestionnaire called");
		petQuestionnaireDao.addPetQuestionnaire(petQuestionnaireRequest);
		LOGGER.debug("addPetQuestionnaire completed successfully");
	}

	@Override
	public PetSchedQuestionnaireListResponse getPetSchedQuestionnaireList(PetQuestionnaireFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getPetSchedQuestionnaireList called");
		Map<String, Integer> mapper = petQuestionnaireDao.getPetSchedQuestionnaireCount(filter);
		int searchedElements = mapper.get("searchCount");
		int totalCount = mapper.get("totalCount");

		List<PetSchedQuestionnaire> petSchedQuestionnaires = searchedElements > 0
				? petQuestionnaireDao.getPetSchedQuestionnaireList(filter)
				: new ArrayList<>();

		PetSchedQuestionnaireListResponse response = new PetSchedQuestionnaireListResponse();
		response.setPetSchedQuestionnaires(petSchedQuestionnaires);
		response.setNoOfElements(petSchedQuestionnaires.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(searchedElements);

		LOGGER.debug("getPetSchedQuestionnaireList count is {}", petSchedQuestionnaires);
		LOGGER.debug("getPetSchedQuestionnaireList completed successfully");
		return response;
	}

	@Override
	public PetQuestionnaireReccurrenceResponse getPetQuestionnaireListByConfigId(PetQuestionnaireFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getPetQuestionnaireListByConfigId called");
		Map<String, Integer> mapper = petQuestionnaireDao.getPetQuestionnaireListByConfigCount(filter);
		int searchedElements = mapper.get("searchCount");
		int totalCount = mapper.get("totalCount");

		List<PetQuestionnaireReccurrence> petSchedQuestionnaires = searchedElements > 0
				? petQuestionnaireDao.getPetQuestionnaireListByConfigId(filter)
				: new ArrayList<>();

		PetQuestionnaireReccurrenceResponse response = new PetQuestionnaireReccurrenceResponse();
		response.setPetQuestionnaireReccurrences(petSchedQuestionnaires);
		response.setNoOfElements(petSchedQuestionnaires.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(searchedElements);

		LOGGER.debug("getPetQuestionnaireListByConfigId count is {}", petSchedQuestionnaires);
		LOGGER.debug("getPetQuestionnaireListByConfigId completed successfully");
		return response;
	}

	@Override
	public PetSchedQuestionnaire getPetQuestionnaireByConfigId(int petQuestionnaireConfigId)
			throws ServiceExecutionException {
		LOGGER.debug("deletePetQuestionnaire called");
		PetSchedQuestionnaire petSchedQuestionnaire = petQuestionnaireDao
				.getPetQuestionnaireByConfigId(petQuestionnaireConfigId);
		LOGGER.debug("deletePetQuestionnaire completed successfully");
		return petSchedQuestionnaire;
	}

	@Override
	public void deletePetQuestionnaire(int petQuestionnaireConfigId, int modifiedBy) throws ServiceExecutionException {
		LOGGER.debug("deletePetQuestionnaire called");
		petQuestionnaireDao.deletePetQuestionnaire(petQuestionnaireConfigId, modifiedBy);
		LOGGER.debug("deletePetQuestionnaire completed successfully");
	}

	@Override
	public QuestionnaireViewResponse getQuestionnaireResponse(int petQuestionnaireConfigId, int questionnaireResponseId)
			throws ServiceExecutionException {
		LOGGER.debug("getQuestionnaireResponse called");
		return petQuestionnaireDao.getQuestionnaireResponse(petQuestionnaireConfigId, questionnaireResponseId);
	}

	@Override
	public void sendNotification(NotificationRequest notificationRequest) {
		LOGGER.debug("sendNotification called");
		List<PetParent> petParents = petQuestionnaireDao.getPetParentsByPetId(notificationRequest.getPetId());
		petParents.parallelStream().forEach(obj -> {
			try {
				LOGGER.info("sendNotification user email is {}", obj.getEmail());
				PushNotificationUtil.sendPushNotification("Questionnaire Reminder",
						notificationRequest.getNotificationText(), obj.getFcmToken());
			} catch (IOException e) {
				LOGGER.error("error while fetching sendPushNotification", e);
			}
		});
	}

}
