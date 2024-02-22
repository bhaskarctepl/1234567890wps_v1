
/**
 * Created Date: 08-Dec-2020
 * Class Name  : SupportImpl.java
 * Description : Description of the package.
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                sgorle          08-Dec-2020        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.service.support.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.support.SupportDao;
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
import com.hillspet.wearables.response.CustomerSupportListResponse;
import com.hillspet.wearables.response.CustomerSupportResponse;
import com.hillspet.wearables.response.CustomerSupportTicketHistoryResponse;
import com.hillspet.wearables.response.CustomerSupportTicketsDetailsResponse;
import com.hillspet.wearables.response.SupportMaterialListResponse;
import com.hillspet.wearables.response.SupportMaterialTypeCountResponse;
import com.hillspet.wearables.response.SupportMaterialsDetailsListResponse;
import com.hillspet.wearables.service.support.SupportService;

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
@Service
public class SupportServiceImpl implements SupportService {

	private static final Logger LOGGER = LogManager.getLogger(SupportServiceImpl.class);

	@Autowired
	private SupportDao supportDao;

	@Override
	public CustomerSupportListResponse getCustomerSupportTickets(SupportFilter filter) throws ServiceExecutionException {
		Map<String, Integer> mapper = supportDao.getCustomerSupportTicketsCount(filter);
		int total =	mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<SupportListDTO> supportList = total > 0 ? supportDao.getCustomerSupportTickets(filter) : new ArrayList<>();
		CustomerSupportListResponse response = new CustomerSupportListResponse();
		response.setSupportList(supportList);
		response.setNoOfElements(supportList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		return response;
	}

	@Override
	public void addCustomerSupport(CustomerSupportRequest customerSupportRequest, Integer userId)
			throws ServiceExecutionException {
		LOGGER.debug("addCustomerSupport called");
		supportDao.addCustomerSupport(customerSupportRequest, userId);
		LOGGER.debug("addCustomerSupport completed successfully");
	}

	@Override
	public void updateCustomerSupport(CustomerSupportUpdateRequest customerSupportUpdateRequest, Integer userId)
			throws ServiceExecutionException {
		LOGGER.debug("updateCustomerSupport called");
		supportDao.updateCustomerSupport(customerSupportUpdateRequest, userId);
		LOGGER.debug("updateCustomerSupport completed successfully");
	}

	@Override
	public CustomerSupportResponse getCustomerSupportTicketById(int ticketId) throws ServiceExecutionException {
		LOGGER.debug("getCustomerSupportTicketById called");
		CustomerSupport customerSupport = supportDao.getCustomerSupportTicketById(ticketId);
		CustomerSupportResponse response = new CustomerSupportResponse();
		response.setCustomerSupport(customerSupport);
		LOGGER.debug("getCustomerSupportTicketById completed successfully");
		return response;
	}

	@Override
	public CustomerSupportTicketHistoryResponse getCustomerSupportTicketsHistory(int ticketId) throws ServiceExecutionException {
		LOGGER.debug("getCustomerSupportTicketsHistory called");
		List<CustomerSupportHistory> customerSupport = supportDao.getCustomerSupportTicketsHistory(ticketId);
		CustomerSupportTicketHistoryResponse response = new CustomerSupportTicketHistoryResponse();
		response.setCustomerSupport(customerSupport);
		LOGGER.debug("getCustomerSupportTicketsHistory completed successfully");
		return response;
	}

	@Override
	public CustomerSupportTicketsDetailsResponse getCustomerSupportTicketsDetails(int ticketId) throws ServiceExecutionException {
		LOGGER.debug("getCustomerSupportTicketsDetails called");
		CustomerSupportDetails customerSupport = supportDao.getCustomerSupportTicketsDetails(ticketId);
		CustomerSupportTicketsDetailsResponse response = new CustomerSupportTicketsDetailsResponse();
		response.setCustomerSupport(customerSupport);
		LOGGER.debug("getCustomerSupportTicketsDetails completed successfully");
		return response;
	}

	@Override
	public void deleteFile(int attachmentId, int modifiedBy, int statusId) throws ServiceExecutionException {
		LOGGER.debug("deleteFile called");
		supportDao.deleteFile(attachmentId, modifiedBy, statusId);
		LOGGER.debug("deleteFile completed successfully");
	}

	/*
	 * @Override public SupportMaterialListResponse
	 * getSupportMaterialList(SupportMaterialFilter filter) throws
	 * ServiceExecutionException { Map<String, Integer> mapper =
	 * supportDao.getSupportMaterialListCount(filter); int total =
	 * mapper.get("count"); int totalCount = mapper.get("totalCount");
	 * List<SupportMaterialDTO> supportList = total > 0 ?
	 * supportDao.getSupportMaterialList(filter) : new ArrayList<>();
	 * SupportMaterialListResponse response = new SupportMaterialListResponse();
	 * response.setSupportMaterialList(supportList);
	 * response.setNoOfElements(supportList.size());
	 * response.setTotalRecords(totalCount); response.setSearchElments(total);
	 * return response; }
	 */

	 
}
