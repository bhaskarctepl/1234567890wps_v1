package com.hillspet.wearables.common.utils;

import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.exceptions.ServiceValidationException;

@Service
public class PreludeGoogleTimeZoneUtil {

	private static final Logger LOGGER = LogManager.getLogger(PreludeGoogleTimeZoneUtil.class);

	@Value("${googleapikey}")
	private String googleApiKey;

	@Autowired
	private ObjectMapper mapper;

	public String getTimeZoneByZipcode(String zipCode) {

		//LOGGER.info(StringUtils.isBlank(zipCode));
		String timeZoneName = "";

		if (!StringUtils.isBlank(zipCode)) {

			Map<String, String> addressMap = getGeocodeAddress("US,"+zipCode);

			if (addressMap != null) {
				try {
					String timeZoneJsonString = getTimeZoneData(addressMap.get("lat"), addressMap.get("lng"));
					
					// Converting the timeZoneJsonNode String to JsonNode
					JsonNode timeZoneJsonNode = mapper.readValue(timeZoneJsonString, JsonNode.class);
					// Reading the Status from timeZoneJsonNode
					String timeZoneDataStatus = timeZoneJsonNode.get("status").asText();

					if (timeZoneDataStatus != null && "OK".equalsIgnoreCase(timeZoneDataStatus)) {
						timeZoneName = timeZoneJsonNode.get("timeZoneId").asText();
					}						
				} catch (Exception e) {
					LOGGER.error("error while executing getTimeZoneByZipcode {}", e);
					return timeZoneName;
				}
			} else {
				return timeZoneName;
			}
		}
		return timeZoneName;
	}

	/**
	 * @param address
	 * 
	 */
	private Map<String, String> getGeocodeAddress(String address) throws ServiceExecutionException {
		Map<String, String> addressMap = null;
		try {
			String geoDataJsonString = getGeoCodeData(address);
			// Converting the geoDataJsonString String to JsonNode
			JsonNode geoDataJsonNode = mapper.readValue(geoDataJsonString, JsonNode.class);

			// Reading the Status from geoDataJsonNode
			String geoDataStatus = geoDataJsonNode.get("status").asText();

			// Validating the geoDataStatus
			if (geoDataStatus == null || !"OK".equalsIgnoreCase(geoDataStatus)) {
				return addressMap;
			} else if (geoDataStatus != null && "OK".equalsIgnoreCase(geoDataStatus)) {
				addressMap = new HashMap<String, String>();
				JsonNode latLngNode = geoDataJsonNode.get("results").get(0).get("geometry").get("location");

				addressMap.put("lat", latLngNode.get("lat").asText());
				addressMap.put("lng", latLngNode.get("lng").asText());
				LOGGER.info(addressMap);
			} else {
				return addressMap;
			}
		} catch (Exception e) {
			return addressMap;
		}
		return addressMap;
	}

	/**
	 * 
	 * @param address (zipcode)
	 * @return
	 * @throws Exception
	 */
	private String getGeoCodeData(String address) throws Exception {
		String geoDataJsonString = null;

		// Constructing the geoCodeAPI String using the MessageFormat
		String geoCodeAPI = MessageFormat.format(Constants.GCLOUD_GEO_LOCATION_API, URLEncoder.encode(address, "UTF-8"),
				URLEncoder.encode(googleApiKey, "UTF-8"));

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(geoCodeAPI);
		Response response = target.request().get();

		try {
			if (response.getStatus() != 200) {
				throw new ServiceValidationException("getGeocodeAddress no data found cannot proceed further",
						Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
								new WearablesError(WearablesErrorCode.INVALID_ADDRESS, Constants.ADDRESS_STRING)));
			}
			geoDataJsonString = response.readEntity(String.class);
			// LOGGER.info("Successfully got result: {}", geoDataJsonString);
		} catch (Exception e) {
			LOGGER.error("error while executing getGeocodeAddress {}", e);
			throw new ServiceValidationException("getTimeZoneByZipcode no data found cannot proceed further",
					Status.BAD_REQUEST.getStatusCode(),
					Arrays.asList(new WearablesError(WearablesErrorCode.INVALID_ADDRESS, Constants.ADDRESS_STRING)));
		} finally {
			response.close();
			client.close();
		}
		return geoDataJsonString;
	}

	/**
	 * 
	 * @param lat
	 * @param lng
	 * @return
	 * @throws Exception
	 */
	private String getTimeZoneData(String lat, String lng) throws Exception {
		String timeZoneJsonString = null;

		String timeZoneApi = MessageFormat.format(Constants.GCLOUD_TIME_ZONE_API, URLEncoder.encode(lat, "UTF-8"),
				URLEncoder.encode(lng, "UTF-8"),
				URLEncoder.encode(String.valueOf(System.currentTimeMillis() / 1000), "UTF-8"),
				URLEncoder.encode(googleApiKey, "UTF-8"));

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(timeZoneApi);
		Response response = target.request().get();

		try {
			if (response.getStatus() != 200) {
				throw new ServiceValidationException("getTimeZoneData no data found cannot proceed further",
						Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
								new WearablesError(WearablesErrorCode.INVALID_ADDRESS, Constants.ADDRESS_STRING)));
			}
			timeZoneJsonString = response.readEntity(String.class);
			//LOGGER.info("Successfully got result (timeZoneJsonString) : {}", timeZoneJsonString);
		} catch (Exception e) {
			LOGGER.error("error while executing getTimeZoneData {}", e);
			throw new ServiceValidationException("getTimeZoneByZipcode no data found cannot proceed further",
					Status.BAD_REQUEST.getStatusCode(),
					Arrays.asList(new WearablesError(WearablesErrorCode.INVALID_ADDRESS, Constants.ADDRESS_STRING)));
		} finally {
			response.close();
			client.close();
		}
		return timeZoneJsonString;
	}	
}
