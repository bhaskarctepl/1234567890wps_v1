package com.hillspet.wearables.service.asset;

import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
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
import com.hillspet.wearables.response.AssetResponse;
import com.hillspet.wearables.response.BulkAssetUploadResponse;
import com.hillspet.wearables.response.DeviceInfoListResponse;
import com.hillspet.wearables.response.DeviceResponse;
import com.hillspet.wearables.response.FirmwareVersionListResponse;

public interface AssetManagementService {

	public DeviceInfo addDeviceInfo(DeviceInfo deviceInfo) throws ServiceExecutionException;

	public DeviceInfo updateDeviceInfo(DeviceInfo deviceInfo) throws ServiceExecutionException;

	public void deleteDeviceInfo(int deviceId, int modifiedBy) throws ServiceExecutionException;

	public AssetResponse getAssetList(AssetsFilter filter) throws ServiceExecutionException;

	public void updateDeviceFirmware(int firmwareVersionId, String deviceIds, int modifiedBy, String assetModel)
			throws ServiceExecutionException;

	public DeviceInfo getDeviceInfoById(int deviceId) throws ServiceExecutionException;

	public DeviceInfoListResponse getDeviceInfo(AssetUpdateFirmwareFilter filter) throws ServiceExecutionException;

	public List<DeviceInfo> getAllDevices(String studyIds) throws ServiceExecutionException;

	public DeviceResponse getDeviceTypesAndModels() throws ServiceExecutionException;

	public FirmwareVersion addFirmwareVersion(FirmwareVersion firmwareVersion) throws ServiceExecutionException;

	public FirmwareVersion updateFirmwareVersion(FirmwareVersion firmwareVersion) throws ServiceExecutionException;

	public void deleteFirmwareVersion(int firmwareVersionId, int assetFirmwareVersionId, int modifiedBy)
			throws ServiceExecutionException;

	public FirmwareVersion getFirmwareVersionById(int firmwareVersionId) throws ServiceExecutionException;

	public FirmwareVersionListResponse getFirmwareVersions(AssetFirmwareVersionsFilter filter)
			throws ServiceExecutionException;

	public FirmwareVersionListResponse getAllFirmwareVersions(String assetType, String assetModel)
			throws ServiceExecutionException;

	public int[] bulkAssetUpload(InputStream uploadedInputStream, FormDataContentDisposition fileDetail, Integer userId)
			throws ServiceExecutionException;

	public DeviceInfo saveBulkUploadDevicesInfo(BulkAssetUploadRequest request) throws ServiceExecutionException;

	public List<BulkAssetUploadDeviceInfo> convertBulkExcelToDeviceList(InputStream uploadedInputStream, int userId)
			throws ServiceExecutionException;

	public BulkAssetUploadResponse getBulkUploadDevicesList(BaseFilter filter) throws ServiceExecutionException;

	public List<AssetType> getAllAssetTypes() throws ServiceExecutionException;

	public List<DeviceModel> getDeviceModelById(String assetType) throws ServiceExecutionException;

	public Workbook generateBulkUploadExcel() throws ServiceExecutionException;

	public List<DeviceUnAssignReason> getDeviceUnAssinReason() throws ServiceExecutionException;

	public List<AssetHistory> getAssetHistory(int deviceId) throws ServiceExecutionException;

	public AssetResponse getUnConfiguredDevices() throws ServiceExecutionException;

	public void unassignAsset(UnassignAssetRequest request) throws ServiceExecutionException;

	public List<PetStudyDevice> getPetStudiesByAsset(int deviceId) throws ServiceExecutionException;

	public void manageAssetStudyMapping(AssetStudyMappingRequest request, Integer userId) throws ServiceExecutionException;

}
