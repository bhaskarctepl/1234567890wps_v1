package com.hillspet.wearables.service.pet;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.BehaviorHistory;
import com.hillspet.wearables.dto.PetCampaignPointsDTO;
import com.hillspet.wearables.dto.PetDTO;
import com.hillspet.wearables.dto.PetExternalInfoListDTO;
import com.hillspet.wearables.dto.PetListDTO;
import com.hillspet.wearables.dto.PetName;
import com.hillspet.wearables.dto.PetNote;
import com.hillspet.wearables.dto.PetObservationMediaListDTO;
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
import com.hillspet.wearables.request.ManualRecommendationRequest;
import com.hillspet.wearables.request.PetNotificationRequest;
import com.hillspet.wearables.request.PetRequest;
import com.hillspet.wearables.request.UpdatePetIBWRequest;
import com.hillspet.wearables.request.ValidateDuplicatePetRequest;
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

public interface PetService {

	PetsResponse getPetList(PetFilter filter) throws ServiceExecutionException;

	List<PetListDTO> getPets() throws ServiceExecutionException;

	PetDTO addPet(PetRequest petRequest) throws ServiceExecutionException;

	PetDTO updatePet(PetRequest petRequest) throws ServiceExecutionException;

	PetDTO getPetById(int petId) throws ServiceExecutionException;

	PetDTO getPetByIdAndStudyId(int petId, int studyId) throws ServiceExecutionException;

	void deletePet(int petId, int modifiedBy) throws ServiceExecutionException;

	PetsResponse getPetsByPetParent(int petParentId) throws ServiceExecutionException;

	PetObservationsResponse getPetObservations(PetObservationMediaFilter filter) throws ServiceExecutionException;

	PetParentListResponse getPetParents(PetFilter filter) throws ServiceExecutionException;

	List<StreamDevice> getDeviceHistoryByStreamId(int petStudyId, String streamId) throws ServiceExecutionException;

	PetNotesResponse getPetNotes(PetFilter filter) throws ServiceExecutionException;

	List<PetStudyDevice> getPetDevices(int petId) throws ServiceExecutionException;

	void addPetNote(PetNote petNote) throws ServiceExecutionException;

	PetObservationMediaListResponse getPetObservationMediaList(PetObservationMediaFilter filter)
			throws ServiceExecutionException;

	List<PetObservationMediaListDTO> getPetObservationMediaById(int petId) throws ServiceExecutionException;

	PetsResponse getPetViewPane(PetFilter filter) throws ServiceExecutionException;

	void disassociateStudy(int petId, int studyId) throws ServiceExecutionException;

	PetWeightHistoryResponse getPetWeightHistory(int petId, String fromDate, String toDate)
			throws ServiceExecutionException;

	List<PetStudyDTO> getCurrentStudies(int petId) throws ServiceExecutionException;

	List<PetStudyDTO> getArchiveStudies(int petId) throws ServiceExecutionException;

	PetDevicesResponse getPetDevicesByStudy(PetFilter filter) throws ServiceExecutionException;

	PetDTO getPetDetailsById(int petId) throws ServiceExecutionException;

	PetCampaignPointsDTO getPetCampaignPoints(int petId) throws ServiceExecutionException;

	PetCampaignResponse getPetCampaignPointsList(int petId, int activityId, BaseFilter filter)
			throws ServiceExecutionException;

	PetRedemptionHistoryResponse getPetRedemptionHistory(int petId) throws ServiceExecutionException;

	void redeemRewardPoints(int petId, int pointsRedeemed, int userId) throws ServiceExecutionException;

	List<PetExternalInfoListDTO> getExternalPetInfoList(int studyId) throws ServiceExecutionException;

	ExternalPetInfoResponse getExternalPetInfo(ExternalPetInfoFilter filter) throws ServiceExecutionException;

	PetDTO associateNewStudy(AssociateNewStudyRequest request) throws ServiceExecutionException;

	PetDTO addPetWeight(AddPetWeight addPetWeight) throws ServiceExecutionException;

	void updateWeight(int weightId, String weight, String unit,boolean latest) throws ServiceExecutionException;

	PetFeedingEnthusiasmScaleResponse getPetFeedingEnthusiasmScaleDtls(PetEnthusiasmFilter filter)
			throws ServiceExecutionException;

	PetImageScaleResponse getPetImageScaleDtls(PetImageScaleFilter filter) throws ServiceExecutionException;

	ActivityFactorResultResponseList getPetActivityFactorResult(PetActivityFactorFilter filter)
			throws ServiceExecutionException;

	PetAddressResponse getPetAddressHistoryById(int petId) throws ServiceExecutionException;

	public List<PetName> getPointsAccumulatedPets(int userId) throws ServiceExecutionException;

	String validateDuplicatePet(ValidateDuplicatePetRequest petRequest) throws ServiceExecutionException;

	public BehaviorVisualizationResponse getBehaviorVisualization(int petId) throws ServiceExecutionException;

	public List<BehaviorHistory> getBehaviorHistoryByType(int petId, String behaviorType,
			BehaviorHistoryRequest behaviorHistoryRequest) throws ServiceExecutionException;

	public BehaviorForwardMotionGoalSettingResponse getForwardMotionGoalSetting(int petId);

	public PetLegLengthResponse getPetLegLengthHistoryById(int petId, int unitId) throws ServiceExecutionException;

	public void updateIBW(int petId, int ibwId, UpdatePetIBWRequest updatePetIBWRequest)
			throws ServiceExecutionException;

	public Workbook generateBulkUploadExcel() throws ServiceExecutionException;

	public int bulkUploadExtPetIds(InputStream uploadedInputStream, FormDataContentDisposition fileDetail,
			Integer userId) throws ServiceExecutionException;

	void saveBulkUploadExtPetIds(BulkExtPetIdsUploadRequest request) throws ServiceExecutionException;

	public BulkExtPetIdsUploadResponse getBulkUploadExtPetIdsList(BaseFilter filter) throws ServiceExecutionException;

	public void saveManualEnterForRecommendation(ManualRecommendationRequest manualRecommendationRequest);

	public HashMap<String, String> getPetThresholdDetails(int afId,int userId);	
	
	public void updatePetNotification(PetNotificationRequest petNotificationRequest)
			throws ServiceExecutionException;
	
	PetsResponse getActivePetList(PetFilter filter) throws ServiceExecutionException;
}
