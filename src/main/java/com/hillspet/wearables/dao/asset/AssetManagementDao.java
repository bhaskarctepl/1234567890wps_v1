package com.hillspet.wearables.dao.asset;

import java.util.List;
import java.util.Map;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.Asset;
import com.hillspet.wearables.dto.AssetHistory;
import com.hillspet.wearables.dto.AssetType;
import com.hillspet.wearables.dto.BulkAssetUploadDeviceInfo;
import com.hillspet.wearables.dto.DeviceInfo;
import com.hillspet.wearables.dto.DeviceModel;
import com.hillspet.wearables.dto.DeviceUnAssignReason;
import com.hillspet.wearables.dto.FirmwareVersion;
import com.hillspet.wearables.dto.PetStudyDevice;
import com.hillspet.wearables.dto.filter.AssetFirmwareVersionsFilter;
import com.hillspet.wearables.dto.filter.AssetUpdateFirmwareFilter;
import com.hillspet.wearables.dto.filter.AssetsFilter;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.request.AssetStudyMappingRequest;
import com.hillspet.wearables.request.BulkAssetUploadRequest;
import com.hillspet.wearables.request.UnassignAssetRequest;
import com.hillspet.wearables.response.DeviceResponse;

public interface AssetManagementDao {

	public DeviceInfo addDeviceInfo(DeviceInfo deviceInfo) throws ServiceExecutionException;

	public DeviceInfo updateDeviceInfo(DeviceInfo deviceInfo) throws ServiceExecutionException;

	public void deleteDeviceInfo(int deviceId, int modifiedBy) throws ServiceExecutionException;

	public void updateDeviceFirmware(int firmwareVersionId, String deviceIds, int modifiedBy, String assetModel)
			throws ServiceExecutionException;

	public DeviceInfo getDeviceInfoById(int deviceId) throws ServiceExecutionException;

	public Map<String, Integer> getDeviceCount(AssetUpdateFirmwareFilter filter) throws ServiceExecutionException;

	public List<DeviceInfo> getDeviceInfo(AssetUpdateFirmwareFilter filter) throws ServiceExecutionException;

	List<DeviceInfo> getAllDevices(String studyIds) throws ServiceExecutionException;

	public DeviceResponse getDeviceTypesAndModels() throws ServiceExecutionException;

	public FirmwareVersion addFirmwareVersion(FirmwareVersion firmwareVersion) throws ServiceExecutionException;

	public FirmwareVersion updateFirmwareVersion(FirmwareVersion firmwareVersion) throws ServiceExecutionException;

	public void deleteFirmwareVersion(int firmwareVersionId, int assetFirmwareVersionId, int modifiedBy)
			throws ServiceExecutionException;

	public FirmwareVersion getFirmwareVersionById(int firmwareVersionId) throws ServiceExecutionException;

	public Map<String, Integer> getFirmwareVersionCount(AssetFirmwareVersionsFilter filter)
			throws ServiceExecutionException;

	public List<FirmwareVersion> getFirmwareVersions(AssetFirmwareVersionsFilter filter)
			throws ServiceExecutionException;

	public List<FirmwareVersion> getAllFirmwareVersions(String assetType, String assetModel)
			throws ServiceExecutionException;

	public Map<String, Integer> getAssetCount(AssetsFilter filter) throws ServiceExecutionException;

	public List<Asset> getAssetList(AssetsFilter filter) throws ServiceExecutionException;

	public int[] saveBulkDevicesToStaging(List<BulkAssetUploadDeviceInfo> list, String fileName, Integer userId)
			throws ServiceExecutionException;

	public DeviceInfo saveBulkUploadDevicesInfo(BulkAssetUploadRequest request) throws ServiceExecutionException;

	public void validateBulkDevicesList(Integer userId) throws ServiceExecutionException;

	public int getBulkUploadDevicesListCount(BaseFilter filter) throws ServiceExecutionException;

	public List<BulkAssetUploadDeviceInfo> getBulkUploadDevicesList(BaseFilter filter) throws ServiceExecutionException;

	public List<AssetType> getAllAssetTypes() throws ServiceExecutionException;

	public List<DeviceModel> getDeviceModelById(String assetType) throws ServiceExecutionException;

	public List<DeviceUnAssignReason> getDeviceUnAssinReason() throws ServiceExecutionException;

	public List<FirmwareVersion> getAllFirmwareVersionsList() throws ServiceExecutionException;

	public List<AssetHistory> getAssetHistory(int deviceId) throws ServiceExecutionException;

	public List<Asset> getUnConfiguredDevices() throws ServiceExecutionException;

	public void unassignAsset(UnassignAssetRequest request) throws ServiceExecutionException;

	public List<PetStudyDevice> getPetStudiesByAsset(int deviceId) throws ServiceExecutionException;

	public void manageAssetStudyMapping(AssetStudyMappingRequest request, Integer userId) throws ServiceExecutionException;
}
