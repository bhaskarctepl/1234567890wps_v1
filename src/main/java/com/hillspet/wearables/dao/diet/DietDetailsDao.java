package com.hillspet.wearables.dao.diet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.BulkUploadDietInfo;
import com.hillspet.wearables.dto.DietDTO;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.DietFilter;
import com.hillspet.wearables.request.BulkDietUploadRequest;
import com.hillspet.wearables.request.DietRequest;
import com.hillspet.wearables.response.DietLookUpResponse;

public interface DietDetailsDao {

	public int[] saveBulkDietToStaging(List<BulkUploadDietInfo> list, String fileName, Integer userId, Integer auditId)
			throws ServiceExecutionException;

	public void validateBulkDietList(Integer userId,Integer auditId) throws ServiceExecutionException;

	public Integer createAuditRecord(String fileName, Integer userId, Integer dataSourceId)
			throws ServiceExecutionException;

	public void updateAuditRecord(Integer auditId, Integer userId, String status) throws ServiceExecutionException;

	public HashMap<String, Integer> getBulkUploadDietListCount(DietFilter filter) throws ServiceExecutionException;

	public List<BulkUploadDietInfo> getBulkUploadDietList(DietFilter filter) throws ServiceExecutionException;

	public Integer saveBulkUploadDietInfo(BulkDietUploadRequest request) throws ServiceExecutionException;

	public DietLookUpResponse getDietDetailsForLookUp() throws ServiceExecutionException;

	public DietDTO addDiet(DietRequest request) throws ServiceExecutionException;

	public DietDTO updateDiet(DietRequest dietRequest) throws ServiceExecutionException;

	public DietDTO getDietById(int dietId) throws ServiceExecutionException;

	public String getDietListCount(BaseFilter filter) throws ServiceExecutionException;

	public ArrayList<DietDTO> getDietList(BaseFilter filter) throws ServiceExecutionException;

}
