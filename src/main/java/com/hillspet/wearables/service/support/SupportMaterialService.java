
/**
 * Created Date: 08-Dec-2020
 * Class Name  : SupportMaterialService.java
 * Description : Description of the package.
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                RAJESH          08-Dec-2020        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.service.support;

import java.util.List;
import java.util.Map;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.SupportMaterialDetails;
import com.hillspet.wearables.dto.filter.SupportFilter;
import com.hillspet.wearables.dto.filter.SupportMaterialFilter;
import com.hillspet.wearables.request.AddSupportMaterialRequest;
import com.hillspet.wearables.request.CustomerSupportRequest;
import com.hillspet.wearables.request.CustomerSupportUpdateRequest;
import com.hillspet.wearables.request.UpdateSupportMaterialRequest;
import com.hillspet.wearables.response.CustomerSupportListResponse;
import com.hillspet.wearables.response.CustomerSupportResponse;
import com.hillspet.wearables.response.CustomerSupportTicketHistoryResponse;
import com.hillspet.wearables.response.CustomerSupportTicketsDetailsResponse;
import com.hillspet.wearables.response.SupportMaterialTypeCountResponse;
import com.hillspet.wearables.response.SupportMaterialsDetailsListResponse;

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
 * @author sgorle
 * @version Wearables Portal Relase Version..
 * @since Available Since Wearables Portal Version.
 * @see New line seperated Classes or Interfaces related to this class.
 */
public interface SupportMaterialService {

	/*Support Material*/
	/*public SupportMaterialListResponse getSupportMaterialList(SupportMaterialFilter filter) throws ServiceExecutionException;*/

	public void addSuportMaterial(List<AddSupportMaterialRequest> addSupportMaterialRequest) throws ServiceExecutionException;
	
	public SupportMaterialsDetailsListResponse getMaterialDetailsListById(SupportMaterialFilter filter) throws ServiceExecutionException;
	
	public SupportMaterialTypeCountResponse getSupportMaterialTypeCountList(SupportMaterialFilter filter) throws ServiceExecutionException;
	
	public SupportMaterialDetails getSupportMaterialDetails(int materialId)  throws ServiceExecutionException;

	public void updateSupportMaterial(UpdateSupportMaterialRequest updateSupportMaterialRequest) throws ServiceExecutionException;

	public Map<String, String> validateTitle(List<AddSupportMaterialRequest> addSupportMaterialRequest)  throws ServiceExecutionException;

	public void deleteSupportMaterial(int supportMaterialId, int modifiedBy)  throws ServiceExecutionException;
}
