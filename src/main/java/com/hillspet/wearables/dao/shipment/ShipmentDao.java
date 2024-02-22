package com.hillspet.wearables.dao.shipment;

import java.util.List;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.Shipment;
import com.hillspet.wearables.dto.ShipmentListDTO;
import com.hillspet.wearables.dto.filter.ShipmentFilter;

public interface ShipmentDao {

	String getShipmentsCount(ShipmentFilter filter) throws ServiceExecutionException;

	List<ShipmentListDTO> getShipmentList(ShipmentFilter filter) throws ServiceExecutionException;

	Shipment addShipment(Shipment shipment) throws ServiceExecutionException;

	Shipment updateShipment(Shipment shipment) throws ServiceExecutionException;

	Shipment getShipmentById(int shipmentId) throws ServiceExecutionException;

	void deleteShipment(int shipmentId, int modifiedBy) throws ServiceExecutionException;

}
