package com.hillspet.wearables.dao.shipment.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.hillspet.wearables.common.constants.SQLConstants;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.shipment.ShipmentDao;
import com.hillspet.wearables.dto.Shipment;
import com.hillspet.wearables.dto.ShipmentListDTO;
import com.hillspet.wearables.dto.filter.ShipmentFilter;

@Repository
public class ShipmentDaoImpl extends BaseDaoImpl implements ShipmentDao {

	private static final Logger LOGGER = LogManager.getLogger(ShipmentDaoImpl.class);

	@Override
	public String getShipmentsCount(ShipmentFilter filter) throws ServiceExecutionException {
		String totalCount = "";
		LOGGER.debug("getShipmentsCount called");
		try {
			totalCount = selectForObject(SQLConstants.SHIPMENT_GET_LIST_COUNT, String.class, filter.getSearchText(),
					filter.getShipmentCompanyId());
		} catch (Exception e) {
			LOGGER.error("error while fetching getShipmentsCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;
	}

	@Override
	public List<ShipmentListDTO> getShipmentList(ShipmentFilter filter) throws ServiceExecutionException {
		List<ShipmentListDTO> shipmentList = new ArrayList<>();
		LOGGER.debug("getShipmentList called");
		try {
			jdbcTemplate.query(SQLConstants.SHIPMENT_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					ShipmentListDTO shipmentListDTO = new ShipmentListDTO();
					shipmentListDTO.setSlNumber(rs.getInt("slNo"));
					shipmentListDTO.setDeviceShipmentId(rs.getInt("DEVICE_SHIPMENT_ID"));
					shipmentListDTO.setShipmentDate(rs.getDate("SHIPMENT_DATE").toLocalDate());
					shipmentListDTO.setTrackingNumber(rs.getString("TRACKING_NUMBER"));
					shipmentListDTO.setStatus(rs.getString("SHIPMENT_STATUS"));
					shipmentListDTO.setDeviceNumber(rs.getString("DEVICE_NUMBER"));
					shipmentListDTO.setPetName(rs.getString("PET_NAME"));
					shipmentListDTO.setShipmentCompanyName(rs.getString("SHIPMENT_COMPANY_NAME"));
					shipmentListDTO.setTrackingUrl(
							MessageFormat.format(rs.getString("TRACKING_URL"), rs.getString("TRACKING_NUMBER")));
					shipmentList.add(shipmentListDTO);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(),
					filter.getSearchText().trim(), filter.getShipmentCompanyId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getShipmentList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return shipmentList;
	}

	@Override
	public Shipment addShipment(Shipment shipment) throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_device_id", shipment.getDeviceId());
			inputParams.put("p_pet_id", shipment.getPetId());
			inputParams.put("p_shipment_company_id", shipment.getShipmentCompanyId());
			inputParams.put("p_shipment_date", shipment.getShipmentDate());
			inputParams.put("p_tracking_number", shipment.getTrackingNumber());
			inputParams.put("p_shipment_status", shipment.getStatus());
			inputParams.put("p_created_by", shipment.getUserId());

			Map<String, Object> outParams = callStoredProcedure(SQLConstants.SHIPMENT_INSERT, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				Integer shipmentId = (int) outParams.get("last_insert_id");
				shipment.setDeviceShipmentId(shipmentId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("addShipment validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
									new WearablesError(WearablesErrorCode.DEVICE_SHIPMENT_ALREADY_EXISTS, errorMsg)));
				} else if (statusFlag == -3) {
					throw new ServiceExecutionException("addShipment validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(
									WearablesErrorCode.DEVICE_SHIPMENT_TRACKING_NUMBER_ALREADY_EXISTS,
									shipment.getTrackingNumber())));
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing addShipment", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return shipment;
	}

	@Override
	public Shipment updateShipment(Shipment shipment) throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_device_shipment_id", shipment.getDeviceShipmentId());
			inputParams.put("p_shipment_company_id", shipment.getShipmentCompanyId());
			inputParams.put("p_shipment_date", shipment.getShipmentDate());
			inputParams.put("p_tracking_number", shipment.getTrackingNumber());
			inputParams.put("p_modified_by", shipment.getUserId());

			Map<String, Object> outParams = callStoredProcedure(SQLConstants.SHIPMENT_UPDATE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("addShipment validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
									new WearablesError(WearablesErrorCode.DEVICE_SHIPMENT_ALREADY_EXISTS, errorMsg)));
				} else if (statusFlag == -3) {
					throw new ServiceExecutionException("addShipment validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(
									WearablesErrorCode.DEVICE_SHIPMENT_TRACKING_NUMBER_ALREADY_EXISTS,
									shipment.getTrackingNumber())));
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing updateShipment ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return shipment;
	}

	@Override
	public Shipment getShipmentById(int shipmentId) throws ServiceExecutionException {
		final Shipment shipment = new Shipment();
		LOGGER.debug("getRoleById called");
		try {
			jdbcTemplate.query(SQLConstants.SHIPMENT_GET_BY_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					shipment.setDeviceShipmentId(rs.getInt("DEVICE_SHIPMENT_ID"));
					shipment.setDeviceId(rs.getInt("DEVICE_ID"));
					shipment.setPetId(rs.getInt("PET_ID"));
					shipment.setShipmentCompanyId(rs.getInt("SHIPMENT_COMPANY_ID"));
					shipment.setShipmentDate(rs.getDate("SHIPMENT_DATE").toLocalDate());
					shipment.setTrackingNumber(rs.getString("TRACKING_NUMBER"));
					shipment.setStatus(rs.getString("SHIPMENT_STATUS"));
					shipment.setDeviceNumber(rs.getString("DEVICE_NUMBER"));
					shipment.setPetName(rs.getString("PET_NAME"));
					shipment.setShipmentCompanyName(rs.getString("SHIPMENT_COMPANY_NAME"));
					shipment.setTrackingUrl(
							MessageFormat.format(rs.getString("TRACKING_URL"), rs.getString("TRACKING_NUMBER")));
				}
			}, shipmentId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getRoleById", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return shipment;
	}

	@Override
	public void deleteShipment(int shipmentId, int modifiedBy) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_device_shipment_id", shipmentId);
		inputParams.put("p_modified_by", modifiedBy);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.SHIPMENT_DELETE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			if (StringUtils.isNotEmpty(errorMsg) || (int) outParams.get("out_flag") < NumberUtils.INTEGER_ONE) {
				throw new ServiceExecutionException(errorMsg, Status.BAD_REQUEST.getStatusCode(),
						Arrays.asList(new WearablesError(WearablesErrorCode.PET_ON_STUDY, errorMsg)));
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing deleteShipment", e);
			throw new ServiceExecutionException(e.getMessage());
		}

	}

}
