
/**
 * Created Date: 08-Dec-2020
 * Class Name  : SupportService.java
 * Description : Description of the package.
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                sgorle          08-Dec-2020        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.service.support;

import java.util.List;

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
public interface SupportService {

	public void addCustomerSupport(CustomerSupportRequest customerSupportRequest, Integer userId)
			throws ServiceExecutionException;

	public void updateCustomerSupport(CustomerSupportUpdateRequest customerSupportUpdateRequest, Integer userId)
			throws ServiceExecutionException;

	public CustomerSupportResponse getCustomerSupportTicketById(int ticketId) throws ServiceExecutionException;

	public CustomerSupportListResponse getCustomerSupportTickets(SupportFilter filter) throws ServiceExecutionException;

	public CustomerSupportTicketHistoryResponse getCustomerSupportTicketsHistory(int ticketId) throws ServiceExecutionException;

	public CustomerSupportTicketsDetailsResponse getCustomerSupportTicketsDetails(int ticketId) throws ServiceExecutionException;

	public void deleteFile(int attachmentId, int modifiedBy, int statusId) throws ServiceExecutionException;
	
}
