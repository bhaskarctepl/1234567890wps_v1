
/**
 * Created Date: 08-Dec-2020
 * Class Name  : SupportMaterialDao.java
 * Description : Description of the package.
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                rajesh          08-Dec-2020        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.dao.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.CustomerSupport;
import com.hillspet.wearables.dto.CustomerSupportDetails;
import com.hillspet.wearables.dto.CustomerSupportHistory;
import com.hillspet.wearables.dto.SupportListDTO;
import com.hillspet.wearables.dto.SupportMaterialDTO;
import com.hillspet.wearables.dto.SupportMaterialDetails;
import com.hillspet.wearables.dto.SupportMaterialDetailsDTO;
import com.hillspet.wearables.dto.SupportMaterialTypeCountDTO;
import com.hillspet.wearables.dto.filter.SupportFilter;
import com.hillspet.wearables.dto.filter.SupportMaterialFilter;
import com.hillspet.wearables.request.AddSupportMaterialRequest;
import com.hillspet.wearables.request.CustomerSupportRequest;
import com.hillspet.wearables.request.CustomerSupportUpdateRequest;
import com.hillspet.wearables.request.UpdateSupportMaterialRequest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Enter detailed explanation of the class here..
 * <p>
 * This class implementation of the <tt>Interface or class Name</tt> interface
 * or class. In addition to implementing the <tt>Interface Name</tt> interface,
 * this class provides methods to do other operations. (Mention other methods
 * purpose)
 *
 * <p>
 * More Description about the class need to be entered here.
 *
 * @author rajesh
 * @version Wearables Portal Relase Version..
 * @since Available Since Wearables Portal Version.
 * @see New line seperated Classes or Interfaces related to this class.
 */
public interface SupportMaterialDao {

	/*Support Material*/
	public void addSuportMaterial(List<AddSupportMaterialRequest>  addSupportMaterialRequest) throws ServiceExecutionException;
	
	public Map<String,Integer> getSupportMaterialListCount(SupportMaterialFilter filter) throws ServiceExecutionException;
	
	/*public List<SupportMaterialDTO> getSupportMaterialList(SupportMaterialFilter filter) throws ServiceExecutionException;*/
	
	public SupportMaterialDetails getMaterialDetailsById(int id) throws ServiceExecutionException;
	
	public Map<String,Integer> getSupportMaterialCounts(SupportMaterialFilter filter) throws ServiceExecutionException;
	
	public List<SupportMaterialTypeCountDTO> getSupportMaterialTypeCountList(SupportMaterialFilter filter) throws ServiceExecutionException;

	public List<SupportMaterialDetailsDTO> getMaterialDetailsListById(SupportMaterialFilter filter)  throws ServiceExecutionException;

	public Map<String, Integer> getMaterialDetailsListByIdCount(SupportMaterialFilter filter) throws ServiceExecutionException;
	
	public void updateSupportMaterial(UpdateSupportMaterialRequest updateSupportMaterialRequest) throws ServiceExecutionException;

	public Map<String, String> validateTitle(List<AddSupportMaterialRequest> addSupportMaterialRequest) throws ServiceExecutionException;

	public void deleteSupportMaterial(int supportMaterialId, int modifiedBy)  throws ServiceExecutionException;



}
