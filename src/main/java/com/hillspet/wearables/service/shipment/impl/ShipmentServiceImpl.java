package com.hillspet.wearables.service.shipment.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.shipment.ShipmentDao;
import com.hillspet.wearables.dto.Shipment;
import com.hillspet.wearables.dto.ShipmentListDTO;
import com.hillspet.wearables.dto.filter.ShipmentFilter;
import com.hillspet.wearables.response.ShipmentsResponse;
import com.hillspet.wearables.service.shipment.ShipmentService;

@Service
public class ShipmentServiceImpl implements ShipmentService {

	private static final Logger LOGGER = LogManager.getLogger(ShipmentServiceImpl.class);

	@Autowired
	private ShipmentDao shipmentDao;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public ShipmentsResponse getShipmentList(ShipmentFilter filter) {
		LOGGER.debug("getShipmentList called");
		int searchedElements = 0;
		String counts = shipmentDao.getShipmentsCount(filter);
		int total = 0;
		try {
			JsonNode jsonCountObj = mapper.readTree(counts);
			total = jsonCountObj.get("totalCount").asInt();
			searchedElements = jsonCountObj.get("count").asInt();
		} catch (Exception e) {
			LOGGER.error("error while fetching getShipmentsCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		List<ShipmentListDTO> shipmentList = total > 0 ? shipmentDao.getShipmentList(filter) : new ArrayList<>();

		ShipmentsResponse response = new ShipmentsResponse();
		response.setShipmentList(shipmentList);
		response.setNoOfElements(shipmentList.size());
		response.setTotalRecords(total);
		response.setSearchElments(searchedElements);

		LOGGER.debug("getShipmentList pet count is {}", shipmentList);
		LOGGER.debug("getShipmentList completed successfully");
		return response;
	}

	@Override
	public Shipment addShipment(Shipment shipment) {
		LOGGER.debug("addShipment called");
		shipment = shipmentDao.addShipment(shipment);
		LOGGER.debug("addShipment completed successfully");
		return shipment;
	}

	@Override
	public Shipment updateShipment(Shipment shipment) {
		LOGGER.debug("updateShipment called");
		shipment = shipmentDao.updateShipment(shipment);
		LOGGER.debug("updateShipment completed successfully");
		return shipment;
	}

	@Override
	public Shipment getShipmentById(int shipmentId) {
		LOGGER.debug("getShipmentById called");
		Shipment shipment = shipmentDao.getShipmentById(shipmentId);
		LOGGER.debug("getShipmentById completed successfully");
		return shipment;
	}

	@Override
	public void deleteShipment(int shipmentId, int modifiedBy) {
		LOGGER.debug("deleteShipment called");
		shipmentDao.deleteShipment(shipmentId, modifiedBy);
		LOGGER.debug("deleteShipment completed successfully");

	}

}
