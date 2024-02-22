package com.hillspet.wearables.service.diet;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.BulkUploadDietInfo;
import com.hillspet.wearables.dto.DietDTO;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.DietFilter;
import com.hillspet.wearables.request.BulkDietUploadRequest;
import com.hillspet.wearables.request.DietRequest;
import com.hillspet.wearables.response.BulkDietUploadResponse;
import com.hillspet.wearables.response.DietListResponse;
import com.hillspet.wearables.response.DietLookUpResponse;

public interface DietDetailsService {

	public HashMap<String, String> bulkAssetUpload(InputStream uploadedInputStream,
			FormDataContentDisposition fileDetail, Integer userId) throws ServiceExecutionException;

	public List<BulkUploadDietInfo> convertBulkExcelToDietList(InputStream uploadedInputStream, int userId)
			throws ServiceExecutionException;

	public BulkDietUploadResponse getBulkUploadDietList(DietFilter filter) throws ServiceExecutionException;

	public Integer saveBulkUploadDietInfo(BulkDietUploadRequest request) throws ServiceExecutionException;

	public Workbook generateBulkUploadExcel() throws ServiceExecutionException;

	public DietDTO addDiet(DietRequest request) throws ServiceExecutionException;

	public DietDTO updateDiet(DietRequest dietRequest) throws ServiceExecutionException;

	public DietListResponse getDietList(BaseFilter filter) throws ServiceExecutionException;

	public DietDTO getDietById(int dietId) throws ServiceExecutionException;

	public DietLookUpResponse getFilterByData() throws ServiceExecutionException;

}
