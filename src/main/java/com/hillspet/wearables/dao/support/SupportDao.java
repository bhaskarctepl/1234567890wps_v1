
/**
 * Created Date: 08-Dec-2020
 * Class Name  : SupportDao.java
 * Description : Description of the package.
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                sgorle          08-Dec-2020        Mentions the comments on change, for the new file it's not required.
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
 * @author sgorle
 * @version Wearables Portal Relase Version..
 * @since Available Since Wearables Portal Version.
 * @see New line seperated Classes or Interfaces related to this class.
 */
public interface SupportDao {

	public void addCustomerSupport(CustomerSupportRequest customerSupportRequest, Integer userId)
			throws ServiceExecutionException;

	public void updateCustomerSupport(CustomerSupportUpdateRequest customerSupportUpdateRequest, Integer userId)
			throws ServiceExecutionException;

	public CustomerSupport getCustomerSupportTicketById(int ticketId) throws ServiceExecutionException;

	public Map<String, Integer> getCustomerSupportTicketsCount(SupportFilter filter) throws ServiceExecutionException;

	public List<SupportListDTO> getCustomerSupportTickets(SupportFilter filter) throws ServiceExecutionException;

	public CustomerSupportDetails getCustomerSupportTicketsDetails (int ticketId) throws ServiceExecutionException;

	public List<CustomerSupportHistory> getCustomerSupportTicketsHistory (int ticketId) throws ServiceExecutionException;

	public List<Map<String, String>> getGetUploadedFileNames(Integer entityId, String entityName, String folderName) throws ServiceExecutionException;

	public void deleteFile(int attachmentId, int modifiedBy, int statusId) throws ServiceExecutionException;

	public Integer addCustomerSupportUploadFile(String fileName, String gcpName, Integer ticketId, String entity,
			Integer userId, Timestamp now, int status, int update) throws ServiceExecutionException;
	 
}
