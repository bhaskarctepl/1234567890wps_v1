package com.hillspet.wearables.common.utils;

import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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
import com.hillspet.wearables.dto.GeoCodeAddress;
import com.hillspet.wearables.dto.TimeZone;
import com.hillspet.wearables.dto.filter.AddressFilter;
import com.hillspet.wearables.response.TimeZoneResponse;
import com.hillspet.wearables.service.lookup.LookupService;

@Service
public class GoogleTimeZoneUtil {

	private static final Logger LOGGER = LogManager.getLogger(GoogleTimeZoneUtil.class);

	@Value("${googleapikey}")
	private String googleApiKey;

	@Autowired
	private LookupService lookupService;

	@Autowired
	private ObjectMapper mapper;

	@SuppressWarnings("rawtypes")
	public TimeZoneResponse getTimeZoneByZipcode(AddressFilter addressFilter) {
		TimeZoneResponse timeZoneResponse = new TimeZoneResponse();

		LOGGER.info(StringUtils.isBlank(addressFilter.getZipCode()));

		if (addressFilter != null && !StringUtils.isBlank(addressFilter.getZipCode())) {
			String zipCode = addressFilter.getZipCode().trim();

			if (StringUtils.isNotBlank(addressFilter.getState())) {
				zipCode = addressFilter.getState() + "," + addressFilter.getCountry() + "," + zipCode;
			}

			GeoCodeAddress address = getGeocodeAddress(zipCode);

			List validAddressDetailsList = validateAddress(addressFilter, address);

			Boolean isValidAddress = (Boolean) validAddressDetailsList.get(0);
			String errorMessage = (String) validAddressDetailsList.get(1);

			if (isValidAddress) {
				try {
					String timeZoneJsonString = getTimeZoneData(address.getLat(), address.getLng());

					// Converting the timeZoneJsonNode String to JsonNode
					JsonNode timeZoneJsonNode = mapper.readValue(timeZoneJsonString, JsonNode.class);

					// Reading the Status from timeZoneJsonNode
					String timeZoneDataStatus = timeZoneJsonNode.get("status").asText();

					// Validating the timeZoneJsonNode
					if (timeZoneDataStatus == null || !"OK".equalsIgnoreCase(timeZoneDataStatus)) {
						throw new ServiceValidationException(
								"getTimeZoneByZipcode no data found cannot proceed further",
								Status.BAD_REQUEST.getStatusCode(),
								Arrays.asList(new WearablesError(WearablesErrorCode.INVALID_ADDRESS,
										Constants.ADDRESS_STRING)));
					} else if (timeZoneDataStatus != null && "OK".equalsIgnoreCase(timeZoneDataStatus)) {
						String timeZoneName = timeZoneJsonNode.get("timeZoneId").asText();
						TimeZone timeZone = lookupService.getTimeZoneDetails(timeZoneName);
						if (!StringUtils.isEmpty(timeZone.getTimeZoneName())) {
							address.setTimeZone(timeZone);
							timeZoneResponse.setIsValidAddress(NumberUtils.INTEGER_ONE);
							timeZoneResponse.setAddress(address);
						} else {
							throw new ServiceValidationException(
									"getTimeZoneByZipcode no data found cannot proceed further",
									Status.BAD_REQUEST.getStatusCode(),
									Arrays.asList(new WearablesError(WearablesErrorCode.INVALID_ADDRESS,
											Constants.ADDRESS_STRING)));
						}
					} else {
						throw new ServiceValidationException(
								"getTimeZoneByZipcode no data found cannot proceed further",
								Status.BAD_REQUEST.getStatusCode(),
								Arrays.asList(new WearablesError(WearablesErrorCode.INVALID_ADDRESS,
										Constants.ADDRESS_STRING)));
					}
				} catch (Exception e) {
					LOGGER.error("error while executing getTimeZoneByZipcode {}", e);
					throw new ServiceValidationException("getTimeZoneByZipcode no data found cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.INVALID_ADDRESS)));
				}
			} else {
				String errorMsg = "Invalid Address";
				throw new ServiceExecutionException(errorMsg, Status.BAD_REQUEST.getStatusCode(),
						Arrays.asList(new WearablesError(WearablesErrorCode.INVALID_ADDRESS, errorMessage)));
			}
		}
		return timeZoneResponse;
	}

	public TimeZoneResponse getTimeZoneByAddress(String userAddress) {
		TimeZoneResponse timeZoneResponse = new TimeZoneResponse();

		LOGGER.info(StringUtils.isBlank(userAddress));

		if (userAddress != null && !StringUtils.isBlank(userAddress)) {

			GeoCodeAddress address = getGeocodeAddress(userAddress);

			try {
				String timeZoneJsonString = getTimeZoneData(address.getLat(), address.getLng());

				// Converting the timeZoneJsonNode String to JsonNode
				JsonNode timeZoneJsonNode = mapper.readValue(timeZoneJsonString, JsonNode.class);

				// Reading the Status from timeZoneJsonNode
				String timeZoneDataStatus = timeZoneJsonNode.get("status").asText();

				// Validating the timeZoneJsonNode
				if (timeZoneDataStatus == null || !"OK".equalsIgnoreCase(timeZoneDataStatus)) {
					throw new ServiceValidationException("getTimeZoneByZipcode no data found cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
									new WearablesError(WearablesErrorCode.INVALID_ADDRESS, Constants.ADDRESS_STRING)));
				} else if (timeZoneDataStatus != null && "OK".equalsIgnoreCase(timeZoneDataStatus)) {
					String timeZoneName = timeZoneJsonNode.get("timeZoneId").asText();
					TimeZone timeZone = lookupService.getTimeZoneDetails(timeZoneName);
					if (!StringUtils.isEmpty(timeZone.getTimeZoneName())) {
						address.setTimeZone(timeZone);
						timeZoneResponse.setIsValidAddress(NumberUtils.INTEGER_ONE);
						timeZoneResponse.setAddress(address);
					} else {
						throw new ServiceValidationException(
								"getTimeZoneByZipcode no data found cannot proceed further",
								Status.BAD_REQUEST.getStatusCode(),
								Arrays.asList(new WearablesError(WearablesErrorCode.INVALID_ADDRESS,
										Constants.ADDRESS_STRING)));
					}
				} else {
					throw new ServiceValidationException("getTimeZoneByZipcode no data found cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
									new WearablesError(WearablesErrorCode.INVALID_ADDRESS, Constants.ADDRESS_STRING)));
				}
			} catch (Exception e) {
				LOGGER.error("error while executing getTimeZoneByZipcode {}", e);
				throw new ServiceValidationException("getTimeZoneByZipcode no data found cannot proceed further",
						Status.BAD_REQUEST.getStatusCode(),
						Arrays.asList(new WearablesError(WearablesErrorCode.INVALID_ADDRESS)));
			}
		}
		return timeZoneResponse;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List validateAddress(AddressFilter addressFilter, GeoCodeAddress address) {

		List validAddressDetailsList = new ArrayList<>();
		String errorMessage = "";
		Boolean isValidAddress = Boolean.FALSE;
		String city = "";
		String state = "";
		String country = "";

		if (StringUtils.isNotBlank(addressFilter.getCity())) {
			city = addressFilter.getCity().toLowerCase();
		}
		if (StringUtils.isNotBlank(addressFilter.getState())) {
			state = addressFilter.getState().toLowerCase();
		}
		if (StringUtils.isNotBlank(addressFilter.getCountry())) {
			country = addressFilter.getCountry().toLowerCase();
		}

		if (validateCity(address, city)) {
			if (address.getStates().contains(state)
					|| StringUtils.equalsIgnoreCase(state, address.getStateShortCode().toLowerCase())) {
				if (StringUtils.equalsIgnoreCase(country, address.getCountry())
						|| StringUtils.equalsIgnoreCase(country, address.getCountryShortCode())) {
					isValidAddress = Boolean.TRUE;
				} else if (StringUtils.equalsAnyIgnoreCase("United States", address.getCountry())
						&& StringUtils.equalsIgnoreCase(country, "USA")) {
					isValidAddress = Boolean.TRUE;
				} else if (StringUtils.equalsAnyIgnoreCase("United Kingdom", address.getCountry())
						&& StringUtils.equalsIgnoreCase(country, "UK")) {
					isValidAddress = Boolean.TRUE;
				} else {
					errorMessage = "country";
				}
			} else {
				errorMessage = "state, correct states are " + address.getStates().toString();
			}
		} else {
			errorMessage = "city, correct cities are " + address.getCities().toString();
		}
		validAddressDetailsList.add(isValidAddress);
		validAddressDetailsList.add(errorMessage);

		return validAddressDetailsList;
	}

	private Boolean validateCity(GeoCodeAddress address, String city) {
		Boolean flag = Boolean.FALSE;
		if (address.getCities().contains(city)
				|| StringUtils.equalsIgnoreCase(city, address.getCityShortCode().toLowerCase())) {
			flag = Boolean.TRUE;
		} else {
			for (final String cityName : address.getCities()) {
				if (cityName.contains(city.replace("-", " ")) || city.contains(cityName.replace("-", " "))) {
					flag = Boolean.TRUE;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * @param address
	 * 
	 */
	@SuppressWarnings("unchecked")
	public GeoCodeAddress getGeocodeAddress(String address) throws ServiceExecutionException {
		GeoCodeAddress geoCodeAddress = new GeoCodeAddress();
		try {
			String geoDataJsonString = getGeoCodeData(address);
			// Converting the geoDataJsonString String to JsonNode
			JsonNode geoDataJsonNode = mapper.readValue(geoDataJsonString, JsonNode.class);

			// Reading the Status from geoDataJsonNode
			String geoDataStatus = geoDataJsonNode.get("status").asText();

			// Validating the geoDataStatus
			if (geoDataStatus == null || !"OK".equalsIgnoreCase(geoDataStatus)) {
				throw new ServiceValidationException("getGeocodeAddress no data found cannot proceed further",
						Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
								new WearablesError(WearablesErrorCode.INVALID_ADDRESS, Constants.ADDRESS_STRING)));
			} else if (geoDataStatus != null && "OK".equalsIgnoreCase(geoDataStatus)) {

				JsonNode addressComponentsNode = geoDataJsonNode.get("results").get(0).get("address_components");
				JsonNode formattedAddressNode = geoDataJsonNode.get("results").get(0).get("formatted_address");
				JsonNode postCodeLocalitiesNode = geoDataJsonNode.get("results").get(0).get("postcode_localities");
				JsonNode latLngNode = geoDataJsonNode.get("results").get(0).get("geometry").get("location");

				geoCodeAddress.setLat(latLngNode.get("lat").asText());
				geoCodeAddress.setLng(latLngNode.get("lng").asText());

				String formattedAddress = formattedAddressNode.asText();

				String zipCode = null;

				if (addressComponentsNode.isArray()) {
					List<String> cities = new ArrayList<String>();
					List<String> states = new ArrayList<String>();

					Map<String, String> cityMap = new HashMap<>();
					Map<String, String> cityShortMap = new HashMap<>();
					Map<String, String> stateMap = new HashMap<>();
					Map<String, String> stateShortMap = new HashMap<>();

					for (final JsonNode addressComponentNode : addressComponentsNode) {

						ArrayList<String> typesArrayList = mapper.convertValue(addressComponentNode.get("types"),
								ArrayList.class);

						if (typesArrayList.contains("postal_code")) {
							zipCode = addressComponentNode.get("long_name").asText();
							geoCodeAddress.setZipCode(addressComponentNode.get("long_name").asText());
						}

						if (typesArrayList.contains("country")) {
							geoCodeAddress.setCountry(addressComponentNode.get("long_name").asText());
							geoCodeAddress.setCountryShortCode(addressComponentNode.get("short_name").asText());
						}

						if (typesArrayList.contains("administrative_area_level_2")) {
							cityMap.put("administrative_area_level_2", addressComponentNode.get("long_name").asText());
							cityShortMap.put("administrative_area_level_2",
									addressComponentNode.get("short_name").asText());

							stateMap.put("administrative_area_level_2", addressComponentNode.get("long_name").asText());
							stateShortMap.put("administrative_area_level_2",
									addressComponentNode.get("short_name").asText());

							states.add(addressComponentNode.get("long_name").asText().toLowerCase());
							cities.add(addressComponentNode.get("long_name").asText().toLowerCase());
						}

						if (typesArrayList.contains("administrative_area_level_1")) {
							stateMap.put("administrative_area_level_1", addressComponentNode.get("long_name").asText());
							stateShortMap.put("administrative_area_level_1",
									addressComponentNode.get("short_name").asText());
							states.add(addressComponentNode.get("long_name").asText().toLowerCase());
						}

						if (typesArrayList.contains("route")) {
							cityMap.put("route", addressComponentNode.get("long_name").asText());
							cityShortMap.put("route", addressComponentNode.get("short_name").asText());
							cities.add(addressComponentNode.get("long_name").asText().toLowerCase());
						}

						if (typesArrayList.contains("neighborhood")) {
							cityMap.put("neighborhood", addressComponentNode.get("long_name").asText());
							cityShortMap.put("neighborhood", addressComponentNode.get("short_name").asText());
							cities.add(addressComponentNode.get("long_name").asText().toLowerCase());

							stateMap.put("neighborhood", addressComponentNode.get("long_name").asText());
							stateShortMap.put("neighborhood", addressComponentNode.get("short_name").asText());
							states.add(addressComponentNode.get("long_name").asText().toLowerCase());
						}

						if (typesArrayList.contains("sublocality")) {
							cityMap.put("sublocality", addressComponentNode.get("long_name").asText());
							cityShortMap.put("sublocality", addressComponentNode.get("short_name").asText());
							cities.add(addressComponentNode.get("long_name").asText().toLowerCase());

							stateMap.put("sublocality", addressComponentNode.get("long_name").asText());
							stateShortMap.put("sublocality", addressComponentNode.get("short_name").asText());
							states.add(addressComponentNode.get("long_name").asText().toLowerCase());
						}

						if (typesArrayList.contains("postal_town")) {
							cityMap.put("postal_town", addressComponentNode.get("long_name").asText());
							cityShortMap.put("postal_town", addressComponentNode.get("short_name").asText());
							cities.add(addressComponentNode.get("long_name").asText().toLowerCase());

							stateMap.put("postal_town", addressComponentNode.get("long_name").asText());
							stateShortMap.put("postal_town", addressComponentNode.get("short_name").asText());
							states.add(addressComponentNode.get("long_name").asText().toLowerCase());
						}

						if (typesArrayList.contains("locality")) {
							cityMap.put("locality", addressComponentNode.get("long_name").asText());
							cityShortMap.put("locality", addressComponentNode.get("short_name").asText());
							cities.add(addressComponentNode.get("long_name").asText().toLowerCase());

							stateMap.put("locality", addressComponentNode.get("long_name").asText());
							stateShortMap.put("locality", addressComponentNode.get("short_name").asText());
							states.add(addressComponentNode.get("long_name").asText().toLowerCase());
						}
					}
					setCity(geoCodeAddress, cityMap, cityShortMap);
					setState(geoCodeAddress, stateMap, stateShortMap);

					if (zipCode == null) {
						throw new ServiceValidationException("getGeocodeAddress no data found cannot proceed further",
								Status.BAD_REQUEST.getStatusCode(),
								Arrays.asList(new WearablesError(WearablesErrorCode.INVALID_ADDRESS,
										Constants.ADDRESS_STRING)));
					} else {
						int cityIndex = address.indexOf(geoCodeAddress.getCity());
						if (cityIndex != -1) {
							// Extract substring before the word
							String address1 = address.substring(0, cityIndex).trim();
							if (address1.endsWith(",")) {
								// Remove the last character (comma)
								address1 = address1.substring(0, address1.length() - 1);
							}
							geoCodeAddress.setAddress1(address1);
						} else {
							geoCodeAddress.setAddress1(formattedAddress.split(",")[0]);
						}
					}

					if (postCodeLocalitiesNode != null && postCodeLocalitiesNode.isArray()) {
						for (final JsonNode postCodeLocalityNode : postCodeLocalitiesNode) {
							cities.add(postCodeLocalityNode.textValue().toLowerCase());
						}
					}

					if (!cities.isEmpty()) {
						cities = cities.stream().distinct().collect(Collectors.toList());
						geoCodeAddress.setCities(cities);
					} else {
						throw new ServiceValidationException("getGeocodeAddress no data found cannot proceed further",
								Status.BAD_REQUEST.getStatusCode(),
								Arrays.asList(new WearablesError(WearablesErrorCode.INVALID_ADDRESS,
										Constants.ADDRESS_STRING)));
					}

					geoCodeAddress.setStates(states);

					if (StringUtils.isBlank(geoCodeAddress.getAddress1())) {
						throw new ServiceValidationException("getGeocodeAddress no data found cannot proceed further",
								Status.BAD_REQUEST.getStatusCode(),
								Arrays.asList(new WearablesError(WearablesErrorCode.INVALID_ADDRESS,
										Constants.ADDRESS_STRING)));
					}
				}
			} else {
				throw new ServiceValidationException("getGeocodeAddress no data found cannot proceed further",
						Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
								new WearablesError(WearablesErrorCode.INVALID_ADDRESS, Constants.ADDRESS_STRING)));
			}
		} catch (Exception e) {
			LOGGER.error("Error in getGeocodeAddress: {}", e.toString());
			throw new ServiceValidationException("getGeocodeAddress no data found cannot proceed further",
					Status.BAD_REQUEST.getStatusCode(),
					Arrays.asList(new WearablesError(WearablesErrorCode.INVALID_ADDRESS, Constants.ADDRESS_STRING)));
		}
		return geoCodeAddress;
	}

	private void setCity(GeoCodeAddress geoCodeAddress, Map<String, String> cityMap, Map<String, String> cityShortMap) {
		Set<String> cityKeySet = cityMap.keySet();
		if (geoCodeAddress != null && cityMap != null && !cityMap.isEmpty()) {
			if (cityKeySet.contains("locality")) {
				geoCodeAddress.setCity(cityMap.get("locality"));
				geoCodeAddress.setCityShortCode(cityShortMap.get("locality"));
				return;
			} else if (cityKeySet.contains("postal_town")) {
				geoCodeAddress.setCity(cityMap.get("postal_town"));
				geoCodeAddress.setCityShortCode(cityShortMap.get("postal_town"));
				return;
			} else if (cityKeySet.contains("sublocality")) {
				geoCodeAddress.setCity(cityMap.get("sublocality"));
				geoCodeAddress.setCityShortCode(cityShortMap.get("sublocality"));
				return;
			} else if (cityKeySet.contains("neighborhood")) {
				geoCodeAddress.setCity(cityMap.get("neighborhood"));
				geoCodeAddress.setCityShortCode(cityShortMap.get("neighborhood"));
				return;
			} else if (cityKeySet.contains("administrative_area_level_2")) {
				geoCodeAddress.setCity(cityMap.get("administrative_area_level_2"));
				geoCodeAddress.setCityShortCode(cityShortMap.get("administrative_area_level_2"));
				return;
			} else if (cityKeySet.contains("route")) {
				geoCodeAddress.setCity(cityMap.get("route"));
				geoCodeAddress.setCityShortCode(cityShortMap.get("route"));
				return;
			}
		}
	}

	private void setState(GeoCodeAddress geoCodeAddress, Map<String, String> stateMap,
			Map<String, String> stateShortMap) {
		Set<String> statekeySet = stateMap.keySet();
		if (geoCodeAddress != null && stateMap != null && !stateShortMap.isEmpty()) {
			if (statekeySet.contains("administrative_area_level_1")) {
				geoCodeAddress.setState(stateMap.get("administrative_area_level_1"));
				geoCodeAddress.setStateShortCode(stateShortMap.get("administrative_area_level_1"));
				return;
			} else if (statekeySet.contains("administrative_area_level_2")) {
				geoCodeAddress.setState(stateMap.get("administrative_area_level_2"));
				geoCodeAddress.setStateShortCode(stateShortMap.get("administrative_area_level_2"));
				return;
			} else if (statekeySet.contains("locality")) {
				geoCodeAddress.setState(stateMap.get("locality"));
				geoCodeAddress.setStateShortCode(stateShortMap.get("locality"));
				return;
			} else if (statekeySet.contains("postal_town")) {
				geoCodeAddress.setState(stateMap.get("postal_town"));
				geoCodeAddress.setStateShortCode(stateShortMap.get("postal_town"));
				return;
			} else if (statekeySet.contains("sublocality")) {
				geoCodeAddress.setState(stateMap.get("sublocality"));
				geoCodeAddress.setStateShortCode(stateShortMap.get("sublocality"));
				return;
			} else if (statekeySet.contains("neighborhood")) {
				geoCodeAddress.setState(stateMap.get("neighborhood"));
				geoCodeAddress.setStateShortCode(stateShortMap.get("neighborhood"));
				return;
			}
		}
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
			LOGGER.info("Successfully got result: {}", geoDataJsonString);
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
	public String getTimeZoneData(String lat, String lng) throws Exception {
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
			LOGGER.info("Successfully got result (timeZoneJsonString) : {}", timeZoneJsonString);
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

	@SuppressWarnings("rawtypes")
	public TimeZoneResponse getTimeZoneByZipcodeCountry(AddressFilter addressFilter) {
		TimeZoneResponse timeZoneResponse = new TimeZoneResponse();

		LOGGER.info(StringUtils.isBlank(addressFilter.getZipCode()));

		if (addressFilter != null && !StringUtils.isBlank(addressFilter.getZipCode())) {
			String zipCode = addressFilter.getZipCode().trim();

			if (StringUtils.isNotBlank(addressFilter.getState())) {
				zipCode = addressFilter.getState() + "," + addressFilter.getCountry() + "," + zipCode;
			}

			GeoCodeAddress address = getGeocodeAddress(zipCode);

			List validAddressDetailsList = validateAddress(addressFilter, address);

			Boolean isValidAddress = (Boolean) validAddressDetailsList.get(0);
			String errorMessage = (String) validAddressDetailsList.get(1);

			if (isValidAddress) {
				try {
					String timeZoneJsonString = getTimeZoneData(address.getLat(), address.getLng());

					// Converting the timeZoneJsonNode String to JsonNode
					JsonNode timeZoneJsonNode = mapper.readValue(timeZoneJsonString, JsonNode.class);

					// Reading the Status from timeZoneJsonNode
					String timeZoneDataStatus = timeZoneJsonNode.get("status").asText();

					// Validating the timeZoneJsonNode
					if (timeZoneDataStatus == null || !"OK".equalsIgnoreCase(timeZoneDataStatus)) {
						throw new ServiceValidationException(
								"getTimeZoneByZipcode no data found cannot proceed further",
								Status.BAD_REQUEST.getStatusCode(),
								Arrays.asList(new WearablesError(WearablesErrorCode.INVALID_ADDRESS,
										Constants.ADDRESS_STRING)));
					} else if (timeZoneDataStatus != null && "OK".equalsIgnoreCase(timeZoneDataStatus)) {
						String timeZoneName = timeZoneJsonNode.get("timeZoneId").asText();
						TimeZone timeZone = lookupService.getTimeZoneDetails(timeZoneName);
						if (!StringUtils.isEmpty(timeZone.getTimeZoneName())) {
							address.setTimeZone(timeZone);
							timeZoneResponse.setIsValidAddress(NumberUtils.INTEGER_ONE);
							timeZoneResponse.setAddress(address);
						} else {
							throw new ServiceValidationException(
									"getTimeZoneByZipcode no data found cannot proceed further",
									Status.BAD_REQUEST.getStatusCode(),
									Arrays.asList(new WearablesError(WearablesErrorCode.INVALID_ADDRESS,
											Constants.ADDRESS_STRING)));
						}
					} else {
						throw new ServiceValidationException(
								"getTimeZoneByZipcode no data found cannot proceed further",
								Status.BAD_REQUEST.getStatusCode(),
								Arrays.asList(new WearablesError(WearablesErrorCode.INVALID_ADDRESS,
										Constants.ADDRESS_STRING)));
					}
				} catch (Exception e) {
					LOGGER.error("error while executing getTimeZoneByZipcode {}", e);
					throw new ServiceValidationException("getTimeZoneByZipcode no data found cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.INVALID_ADDRESS)));
				}
			} else {
				String errorMsg = "Invalid Address";
				throw new ServiceExecutionException(errorMsg, Status.BAD_REQUEST.getStatusCode(),
						Arrays.asList(new WearablesError(WearablesErrorCode.INVALID_ADDRESS, errorMessage)));
			}
		}
		return timeZoneResponse;
	}

//	public static void main(String[] args) {
//		GoogleTimeZoneUtil util = new GoogleTimeZoneUtil();
//		GeoCodeAddress i = util.getGeocodeAddress("67 Heol Maengwyn, Machynlleth, Cymru, UK");
//		System.out.println("Address 1: " + i.getAddress1());
//		System.out.println("City: " + i.getCity());
//		System.out.println("State: " + i.getState());
//		System.out.println("Country: " + i.getCountry());
//		System.out.println("zip: " + i.getZipCode());
//
//	}

}
