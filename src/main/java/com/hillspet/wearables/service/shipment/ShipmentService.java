package com.hillspet.wearables.service.shipment;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.Shipment;
import com.hillspet.wearables.dto.filter.ShipmentFilter;
import com.hillspet.wearables.response.ShipmentsResponse;

public interface ShipmentService {

	ShipmentsResponse getShipmentList(ShipmentFilter filter) throws ServiceExecutionException;

	Shipment addShipment(Shipment shipment) throws ServiceExecutionException;

	Shipment updateShipment(Shipment shipment) throws ServiceExecutionException;

	Shipment getShipmentById(int shipmentId) throws ServiceExecutionException;

	void deleteShipment(int shipmentId, int modifiedBy) throws ServiceExecutionException;

}
