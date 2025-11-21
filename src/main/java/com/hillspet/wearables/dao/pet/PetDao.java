package com.hillspet.wearables.dao.pet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.Address;
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
import com.hillspet.wearables.request.PetRequest;
import com.hillspet.wearables.request.UpdatePetIBWRequest;
import com.hillspet.wearables.request.ValidateDuplicatePetRequest;
import com.hillspet.wearables.request.*;
import com.hillspet.wearables.response.ActivityFactorResultResponse;
import com.hillspet.wearables.response.BehaviorForwardMotionGoalSettingResponse;
import com.hillspet.wearables.response.BehaviorVisualizationResponse;
import com.hillspet.wearables.response.PetAddressResponse;
import com.hillspet.wearables.response.PetLegLengthResponse;
import com.hillspet.wearables.response.PetWeightHistoryResponse;

public interface PetDao {

	String getPetsCount(PetFilter filter) throws ServiceExecutionException;

	List<PetListDTO> getPetList(PetFilter filter) throws ServiceExecutionException;

	List<PetListDTO> getPets() throws ServiceExecutionException;

	PetDTO addPet(PetRequest petRequest) throws ServiceExecutionException;

	PetDTO updatePet(PetRequest petRequest) throws ServiceExecutionException;

	PetDTO getPetById(int petId) throws ServiceExecutionException;

	PetDTO getPetByIdAndStudyId(int petId, int studyId) throws ServiceExecutionException;

	void deletePet(int petId, int modifiedBy) throws ServiceExecutionException;

	List<PetListDTO> getPetsByPetParent(int petParentId) throws ServiceExecutionException;

	List<PetObservation> getPetObservations(PetObservationMediaFilter filter) throws ServiceExecutionException;

	Map<String, Integer> getPetObservationsCount(PetObservationMediaFilter filter) throws ServiceExecutionException;

	List<PetParentListDTO> getPetParents(PetFilter filter) throws ServiceExecutionException;

	List<StreamDevice> getDeviceHistoryByStreamId(int petStudyId, String streamId) throws ServiceExecutionException;

	List<PetNote> getPetNotes(PetFilter filter) throws ServiceExecutionException;

	List<PetStudyDevice> getPetDevices(int petId) throws ServiceExecutionException;

	void addPetNote(PetNote petNote) throws ServiceExecutionException;

	List<PetObservationMediaListDTO> getPetObservationMediaList(PetObservationMediaFilter filter)
			throws ServiceExecutionException;

	Map<String, Integer> getPetsObservatonMediaCount(PetObservationMediaFilter filter) throws ServiceExecutionException;

	List<PetObservationMediaListDTO> getPetObservationMediaById(int petId);

	String getPetViewPaneCount(PetFilter filter) throws ServiceExecutionException;

	List<PetListDTO> getPetViewPane(PetFilter filter) throws ServiceExecutionException;

	void disassociateStudy(int petId, int studyId) throws ServiceExecutionException;

	PetWeightHistoryResponse getPetWeightHistory(int petId, String fromDate, String toDate)
			throws ServiceExecutionException;

	String getPetWeightHistoryCount(BaseFilter filter, int petId, String fromDate, String toDate)
			throws ServiceExecutionException;

	List<PetStudyDTO> getCurrentStudies(int petId) throws ServiceExecutionException;

	List<PetStudyDTO> getArchiveStudies(int petId) throws ServiceExecutionException;

	List<PetStudyDevice> getPetDevicesByStudy(PetFilter filter) throws ServiceExecutionException;

	PetDTO getPetDetailsById(int petId) throws ServiceExecutionException;

	PetCampaignPointsDTO getPetCampaignPoints(int petId) throws ServiceExecutionException;

	String getPetCampaignListCount(int petId, int activityId) throws ServiceExecutionException;

	List<PetCampaignPointsListDTO> getPetCampaignPointsList(int petId, int activityId, BaseFilter filter)
			throws ServiceExecutionException;

	String getPetRewardPointsHistoryCount(int petId) throws ServiceExecutionException;

	List<PetRedemptionHistoryDTO> getPetRedemptionHistory(int petId) throws ServiceExecutionException;

	void redeemRewardPoints(int petId, int redeemPoints, Integer userId) throws ServiceExecutionException;

	List<PetExternalInfoListDTO> getExternalPetInfoList(int studyId) throws ServiceExecutionException;

	public List<ExternalPetInfoListDTO> getExternalPetInfo(ExternalPetInfoFilter filter)
			throws ServiceExecutionException;

	PetDTO associateNewStudy(AssociateNewStudyRequest request) throws ServiceExecutionException;

	PetDTO addPetWeight(AddPetWeight addPetWeight) throws ServiceExecutionException;

	void updateWeight(int weightId, String weight, String unit, boolean latest) throws ServiceExecutionException;

	String getPetFeedingEnthusiasmScaleDtlsCount(PetEnthusiasmFilter filter) throws ServiceExecutionException;

	List<PetFeedingEnthusiasmScale> getPetFeedingEnthusiasmScaleDtls(PetEnthusiasmFilter filter)
			throws ServiceExecutionException;

	String getPetImageScaleDtlsCount(PetImageScaleFilter filter) throws ServiceExecutionException;

	List<PetImageScale> getPetImageScaleDtls(PetImageScaleFilter filter) throws ServiceExecutionException;

	String getPetParentsCount(PetFilter filter) throws ServiceExecutionException;

	String getPetNotesCount(PetFilter filter) throws ServiceExecutionException;

	String getPetDevicesByStudyCount(PetFilter filter) throws ServiceExecutionException;

	String ActivityFactorResultResponseListCount(PetActivityFactorFilter filter) throws ServiceExecutionException;

	List<ActivityFactorResultResponse> getPetActivityFactorResult(PetActivityFactorFilter filter)
			throws ServiceExecutionException;

	PetAddressResponse getPetAddressHistoryById(int petId) throws ServiceExecutionException;

	List<PetName> getPointsAccumulatedPets(int userId) throws ServiceExecutionException;

	String validateDuplicatePet(ValidateDuplicatePetRequest petRequest) throws ServiceExecutionException;

	List<Address> getParentAddressList(int petId) throws ServiceExecutionException;

	BehaviorVisualizationResponse getBehaviorVisualization(int petId) throws ServiceExecutionException;

	List<BehaviorHistory> getBehaviorHistoryByType(int petId, String behaviorType,
			BehaviorHistoryRequest behaviorHistoryRequest) throws ServiceExecutionException;

	BehaviorForwardMotionGoalSettingResponse getForwardMotionGoalSetting(int petId) throws ServiceExecutionException;

	PetLegLengthResponse getPetLegLengthHistoryById(int petId, int unitId) throws ServiceExecutionException;

	void updateIBW(int petId, int ibwId, UpdatePetIBWRequest updatePetIBWRequest) throws ServiceExecutionException;

	void saveBulkExternalPetIdsToStaging(List<BulkUploadExternalPetIdInfo> list, String fileName, Integer userId)
			throws ServiceExecutionException;

	void saveBulkUploadExtPetIds(BulkExtPetIdsUploadRequest request) throws ServiceExecutionException;

	int getBulkUploadExtPetIdsListCount(BaseFilter filter) throws ServiceExecutionException;

	List<BulkUploadExternalPetIdInfo> getBulkUploadExtPetIdsList(BaseFilter filter) throws ServiceExecutionException;

	void saveManualEnterForRecommendation(ManualRecommendationRequest manualRecommendationRequest);

	public HashMap<String, String> getPetThresholdDetails(int afId, int userId);
	
	void updatePetNotification(PetNotificationRequest petNotificationRequest) throws ServiceExecutionException;
	
	String getActivePetsCount(PetFilter filter) throws ServiceExecutionException;

	List<PetListDTO> getActivePetList(PetFilter filter) throws ServiceExecutionException;
}
