package com.hillspet.wearables.service.study.impl;

import java.sql.SQLNonTransientConnectionException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.utils.PreludeGoogleTimeZoneUtil;
import com.hillspet.wearables.configuration.PreludeAuthClient;
import com.hillspet.wearables.dao.study.impl.PreludeDaoImpl;
import com.hillspet.wearables.service.study.PreludeService;

@Service
public class PreludeServiceImpl implements PreludeService {

	private static final Logger LOGGER = LogManager.getLogger(PreludeServiceImpl.class);

	@Autowired
	PreludeDaoImpl daoImpl;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private PreludeGoogleTimeZoneUtil googleTimeZoneUtil;

	@Autowired
	PreludeAuthClient basicAuthClient;

	private final String callbackURL = "?callback=";

	String authToken = "";

	@Override
	public void loadPreludeData() throws ServiceExecutionException {
		List<Map<String, Object>> extStudyListDB;
		try {
			extStudyListDB = daoImpl.getExternalStudyList();
			LOGGER.info("extStudyListDB  :::::   " + extStudyListDB);
			if (!extStudyListDB.isEmpty()) {
				for (Map<String, Object> studyMapDB : extStudyListDB) {
					LOGGER.info(":::: " + studyMapDB.get("EXT_STUDY_ID") + " - " + studyMapDB.get("URL"));
					if (studyMapDB.get("URL") != null && !studyMapDB.get("URL").toString().trim().equals("")) {

						long loadSiteDataStart = System.currentTimeMillis();
						LOGGER.info("loadSiteData()  ***  start *** ");
						Map<String, String> studyURLDbMap = getExternalStudy(studyMapDB);
						loadSiteData(authToken, studyURLDbMap);
						long loadSiteDataEnd = System.currentTimeMillis();
						LOGGER.info("Time taken to execute loadNewPatientsData()  {}", (loadSiteDataEnd - loadSiteDataStart)/1000);
						
						long loadNewPatientsDataStart = System.currentTimeMillis();
						LOGGER.info("loadNewPatientsData()  ***  start *** ");
						loadNewPatientsData(authToken, studyURLDbMap);
						long loadNewPatientsDataEnd = System.currentTimeMillis();
						LOGGER.info("Time taken to execute loadNewPatientsData()  {}", (loadNewPatientsDataEnd - loadNewPatientsDataStart)/1000);

						// LOGGER.info("loadPatientDemographicData() *** start *** ");
						// loadPatientDemographicData(authToken);
						// LOGGER.info("loadPatientDemographicData() *** end *** ");
						
						long loadPatientStudyDataStart = System.currentTimeMillis();
						LOGGER.info("loadPatientStudyData()  ***  start *** ");
						loadPatientStudyData(authToken, studyURLDbMap);
						long loadPatientStudyDataEnd = System.currentTimeMillis();
						LOGGER.info("Time taken to execute loadPatientStudyData {}", (loadPatientStudyDataEnd - loadPatientStudyDataStart)/1000);
						
						LOGGER.info("portalPreludeDataSync()  ***  start *** ");
						portalPreludeDataSync((String) studyMapDB.get("EXT_STUDY_ID"));
						LOGGER.info("portalPreludeDataSync()  ***  OVER *** ");
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception ", e);
		}
	}

	private void portalPreludeDataSync(String extStudyId) {
		daoImpl.callCreateExtPetParentProc(extStudyId);
		daoImpl.callCreateExtPetProc(extStudyId);
		daoImpl.callUpdateExtPetProc(extStudyId);
		long updateTimeZoneStart = System.currentTimeMillis();
		updateTimeZone();
		long updateTimeZoneEnd = System.currentTimeMillis();
		LOGGER.info(" Time taken to execute updateTimeZone {}", (updateTimeZoneEnd - updateTimeZoneStart)/1000);
		daoImpl.callUpdateExtPetAddressProc(extStudyId);
		
	}

	private Map<String, String> getExternalStudy(Map<String, Object> studyMapDB) {
		LOGGER.info("::: Inside the PreludeJob.getStudies() ::: ");
		Map<String, String> studyDbUrlMap = null;

		String studyUrl = studyMapDB.get("URL").toString();
		String extStudyID = studyMapDB.get("EXT_STUDY_ID").toString();

		authToken = basicAuthClient.getPreludeAuthToken(studyUrl);
		LOGGER.info("AuthToken ::::   " + authToken);

		String studyDBUrl = studyUrl + "/api" + callbackURL + authToken;
		try {
			String studyJson = getPreludeData(studyDBUrl);
			// LOGGER.info("studyJson  ::::  " + studyJson);
			String studyName = getDbValueOfStudy(studyJson, extStudyID);
			LOGGER.info("studyDbValue  ::::  " + studyName);
			if (studyName != null) {
				studyDbUrlMap = new HashMap<String, String>();
				studyDbUrlMap.put("studyURL", studyUrl);
				studyDbUrlMap.put("studyDbValue", studyName);
				studyDbUrlMap.put("studyUiValue", extStudyID);
			}
		} catch (Exception e) {
			LOGGER.error("Exception inside loadSiteData() ::::  " , e);
		}
		return studyDbUrlMap;
	}

	/**
	 * Gets the DB Value in the JSON for the Study
	 * 
	 * @param studyJson
	 * @return
	 */
	private String getDbValueOfStudy(String studyJson, String extStudyID) {
		String studyDBValue = null;
		JSONArray jsonStudyArray = new JSONArray();
		try {
			JSONObject jsonObject = new JSONObject(studyJson);
			if (jsonObject.has("nextChoices")) {
				JSONArray jsonArray = jsonObject.getJSONArray("nextChoices");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject json = (JSONObject) jsonArray.get(i);
					if (json.has("db") && json.get("db") != null && json.get("db").toString().equals("study")) {
						jsonStudyArray.put(jsonArray.get(i + 1));
					}
					i++;
				}
				// LOGGER.info("new json array for study   ::::  " + jsonStudyArray);
			}

			for (int i = 0; i < jsonStudyArray.length(); i++) {
				if (extStudyID.equalsIgnoreCase(jsonStudyArray.getJSONObject(i).optString("ui"))) {
					studyDBValue = jsonStudyArray.getJSONObject(i).optString("db");
				}
			}
		} catch (JSONException e) {
			LOGGER.info("Error msg   ::::  " , e);
		}
		LOGGER.info(":::: Study DB Value ::::" + studyDBValue);
		return studyDBValue;
	}

	private void loadSiteData(String authToken, Map<String, String> studyDbUrlMap) {
		if (studyDbUrlMap != null && studyDbUrlMap.containsKey("studyDbValue")) {
			String studyUrl = studyDbUrlMap.get("studyURL");
			String studyDBValue = studyDbUrlMap.get("studyDbValue");
			String extStudyID = studyDbUrlMap.get("studyUiValue");
			LOGGER.info("inside the PreludeJob.loadSiteData()  ::::  ");
			try {
				List<Object> siteList = getSitesList(studyUrl, authToken, studyDBValue);
				daoImpl.insertExtSiteInfo(extStudyID, siteList);
			} catch (Exception e) {
				LOGGER.error("Exception inside loadSiteData() ::::  " , e);
			}
		}
	}

	private List<Object> getSitesList(String studyUrl, String authToken, String extStudyID) {
		LOGGER.info("inside the getSitesList()  ::::   studyUrl : " + studyUrl + " extStudyName : " + extStudyID);

		String studySitesUrl = studyUrl + "/api" + callbackURL + authToken + "&study=" + extStudyID;
		LOGGER.info("Inside getSitesList(()   studySitesUrl  :::::   " + studySitesUrl);

		String siteJson = getPreludeData(studySitesUrl);
		// LOGGER.info("siteJson   ::::  " + siteJson);
		JSONArray array = new JSONArray();

		try {
			JSONObject jsonObject = new JSONObject(siteJson);
			if (jsonObject.has("nextChoices")) {
				JSONArray jsonArray = jsonObject.getJSONArray("nextChoices");

				// LOGGER.info("jsonObject   ::::  " + jsonArray);

				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject json = (JSONObject) jsonArray.get(i);
					if (json.has("db") && json.get("db") != null && json.get("db").toString().equals("site")) {
						// LOGGER.info("json :::: " + i + " - " + jsonArray.get(i));
						// LOGGER.info("json :::: " + (i + 1) + " - " + jsonArray.get(i + 1));
						array.put(jsonArray.get(i + 1));
					}
					i++;
				}
				// LOGGER.info("new json array for site   ::::  " + extStudyID + " - " + array);
			}
			LOGGER.info("List Object  ::::::   " + array.toList());

		} catch (JSONException e) {
			LOGGER.error("Error msg   ::::  ",  e);
		}

		return array.toList();
	}

	private void loadNewPatientsData(String authToken, Map<String, String> studyURLDbMap) {

		LOGGER.info("inside the PreludeJob.loadNewPatientsData()  ::::  " + studyURLDbMap.get("studyUiValue"));

		String studyDBValue = studyURLDbMap.get("studyDbValue");

		try {
			List<Map<String, Object>> extSiteList = daoImpl.getExtSiteInfoList(studyURLDbMap.get("studyUiValue"));
			// LOGGER.info("list  :::::   " + extSiteList);
			if (!extSiteList.isEmpty()) {
				for (Map<String, Object> map : extSiteList) {

					// LOGGER.info("getExtSiteInfo map  :::::   " + map);
					// LOGGER.info(map.get("EXTERNAL_SITE_NAME") + " - " + map.get("URL"));

					if (map.get("URL") != null && !map.get("URL").toString().trim().equals("")) {

						String studyUrl = map.get("URL").toString();
						String extSiteID = map.get("EXTERNAL_SITE_ID").toString();
						try {
							List<Object> newPatientsList = getNewPatientData(studyUrl, authToken, studyDBValue,
									extSiteID);
							daoImpl.insertExtPetInfo(extSiteID, newPatientsList);
						} catch (Exception e) {
							LOGGER.error("Exception inside loadNewPatientsData() ::::  " + e);
						}
					}
				}
			}
		} catch (SQLNonTransientConnectionException e) {
			LOGGER.error("Exception ( No operations allowed after connection closed. )  :::::   " , e);
		} catch (Exception e) {
			LOGGER.error("Exception ::::  " , e);
		}

	}

	private List<Object> getNewPatientData(String studyUrl, String authToken, String extStudyID, String extSiteID) {

		LOGGER.info("inside the PreludeJob.getNewPatientData()  ::::   studyUrl : " + studyUrl + " extStudyName : "
				+ extStudyID + " extSiteName : " + extSiteID);

		String newPatientUrl = studyUrl + "/api" + callbackURL + authToken + "&study=" + extStudyID + "&site="
				+ extSiteID;

		LOGGER.info("Inside getNewPatientData(()   studySitesUrl  :::::   " + newPatientUrl);

		String newPatientsJson = getPreludeData(newPatientUrl);
		// LOGGER.info("siteJson   ::::  " + newPatientsJson);
		JSONArray array = new JSONArray();

		try {
			JSONObject jsonObject = new JSONObject(newPatientsJson);
			if (jsonObject.has("nextChoices")) {
				JSONArray jsonArray = jsonObject.getJSONArray("nextChoices");

				// LOGGER.info("jsonObject   ::::  " + jsonArray);

				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject json = (JSONObject) jsonArray.get(i);
					if (json.has("db") && json.get("db") != null && json.get("db").toString().equals("patient")) {
						// LOGGER.info("json  ::::  " + i + " - " + jsonArray.get(i));
						// LOGGER.info("json  ::::  " + (i + 1) + " - " + jsonArray.get(i + 1));
						array.put(jsonArray.get(i + 1));
					}
					i++;
				}

				// LOGGER.info("new json array for patient   ::::  " + extStudyID + " - " + array);
			}

			// LOGGER.info("List Object  :::   " + array.toList());

		} catch (JSONException e) {
			LOGGER.error("Error msg   ::::  " , e);
		}

		return array.toList();
	}

	public void loadPatientDemographicData(String authToken, Map<String, String> studyURLDbMap) {

		LOGGER.info("inside the PreludeJob.loadPatientDemographicData() :::: Generate Patient Demographic Data.");

		List<Map<String, Object>> extSiteInfoList;
		try {
			extSiteInfoList = daoImpl.getExtSiteInfoList(studyURLDbMap.get("studyUiValue"));
			LOGGER.info("loadPatientDemographicData.getExtSiteInfoList()  :::::   " + extSiteInfoList);

			for (Map<String, Object> extSiteInfoMap : extSiteInfoList) {

				/*LOGGER.info("getExtSiteInfoList map  :::::   " + extSiteInfoMap);
				LOGGER.info("EXTERNAL_SITE_NAME  :: " + extSiteInfoMap.get("EXTERNAL_SITE_NAME") + " - URL :: "
						+ extSiteInfoMap.get("URL") + "  -  " + extSiteInfoMap.get("EXTERNAL_SITE_ID"));*/

				if (extSiteInfoMap.get("URL") != null && !extSiteInfoMap.get("URL").toString().trim().equals("")) {

					String studyName = extSiteInfoMap.get("EXTERNAL_STUDY_ID").toString();
					String studyUrl = extSiteInfoMap.get("URL").toString();
					// String extSiteName = extSiteInfoMap.get("EXTERNAL_SITE_NAME").toString();
					String extSiteId = extSiteInfoMap.get("EXTERNAL_SITE_ID").toString();
					LOGGER.info("\n extSiteId  :::::   " + extSiteId);

					List<Map<String, Object>> extPatientInfoList = daoImpl.getExtPatientInfoList(extSiteId);
					for (Map<String, Object> extPatientInfoMap : extPatientInfoList) {

						// LOGGER.info("Generate PatientDemographicData ExtPatientInfo: " + extPatientInfoMap + " - studyName : " + studyName);

						List<Map<String, Object>> extStudyExtractDefinitionList = daoImpl
								.getExtStudyExtractDefinitionList(studyName);

						for (Map<String, Object> extStudyExtractDefinitionMap : extStudyExtractDefinitionList) {

							// LOGGER.info("extStudyExtractDefinitionMap  :::  " + extStudyExtractDefinitionMap);

							try {
								JSONObject patientDemographicJson = getPatientDemographicData(studyUrl, authToken,
										studyName, extSiteId, extPatientInfoMap.get("EXT_PET_ID").toString(),
										extStudyExtractDefinitionMap);

								// LOGGER.info("patientDemographicJson  ::::   " + patientDemographicJson);

								if (!patientDemographicJson.isEmpty()) {
									daoImpl.insertExtStudyExtractInfo(patientDemographicJson);
								}
							} catch (Exception e) {
								LOGGER.error("Exception inside loadPatientDemographicData() ::::  " , e);
							}

						}

						if (extPatientInfoMap.get("IS_NOTIFICATION_SENT").toString().trim().equals("false")
								|| !(boolean) (extPatientInfoMap.get("IS_NOTIFICATION_SENT"))) {
							// LOGGER.info("Inside notification for extPatientInfoMap :::  " + extPatientInfoMap);

							try {
								List<Map<String, Object>> extStudyExtractInfoList = daoImpl
										.getExtStudyExtractInfoList(extPatientInfoMap.get("EXT_PET_ID").toString());

								if (extStudyExtractInfoList.size() > 0) {

									StringBuilder sbBody = new StringBuilder();
									sbBody.append("Patient Data\n");

									for (Map<String, Object> extStudyExtractInfoMap : extStudyExtractInfoList) {
										try {
											sbBody.append(extStudyExtractInfoMap.get("FIELD_NAME").toString())
													.append(" : ")
													.append(extStudyExtractInfoMap.get("FIELD_VALUE").toString())
													.append(" \n");
										} catch (Exception e) {
											LOGGER.error("Error occured while sending mail for the pet :::: "
													+ extPatientInfoMap.get("EXT_PET_ID") + " error msg :: "
													, e);
											continue;
										}
									}

									/*String body = sbBody.toString();

									LOGGER.info("Sending Email to Patient . ExtPatientInfo: "
											+ extPatientInfoMap.get("EXT_PET_ID").toString() + " - body : " + body);
									LOGGER.info("body :::  " + body);*/

									/*
									 * ThreadPoolExecutor threadPoolExecutor =
									 * EmailThreadPoolExecutor.getEmailThreadPoolExecutor(); EmailSenderThread
									 * emailSenderThread = new EmailSenderThread(mailTo,
									 * Constants.VALIDATE_PET_MAIL_SUBJECT, body);
									 * threadPoolExecutor.submit(emailSenderThread);
									 */

									daoImpl.updateExtPetNotificationStatus(
											extPatientInfoMap.get("EXT_PET_ID").toString());
								}

							} catch (Exception e) {
								LOGGER.error("Exception inside loadPatientDemographicData() ::::  " , e);
							}
						}

					}

				}
			}

		} catch (SQLNonTransientConnectionException e) {
			LOGGER.error(
					"loadPatientDemographicData Exception ( No operations allowed after connection closed. )  ::::: "
							, e);
		} catch (Exception e) {
			LOGGER.error("loadPatientDemographicData  Exception " , e);
		}

	}

	private JSONObject getPatientDemographicData(String studyUrl, String authToken, String studyName, String extSiteID,
			String extPatientID, Map<String, Object> extStudyExtractDefinitionMap) {

		// LOGGER.info(" START of getPatientDemographicData    ");
		authToken = basicAuthClient.getPreludeAuthToken(studyUrl);
		String patientDemographicUrl = studyUrl + "/api" + callbackURL + authToken + "&"
				+ extStudyExtractDefinitionMap.get("DATA_URL").toString().replace("@PATIENT", extPatientID)
						.replace("@SITE", String.valueOf(extSiteID));

		// String patientDemographicUrl = studyUrl + GET_AUTH_STRING + "&" +
		// extStudyExtractDefinitionMap.get("DATA_URL")
		// .toString().replace("@PATIENT", extPatientID).replace("@SITE",
		// String.valueOf(extSiteID));
		// LOGGER.info("Inside getPatientDemographicData(()   patientDemographicUrl  :::::   " + patientDemographicUrl);

		JSONObject extStudyExtractInfoJson = new JSONObject();
		String patientDemographicJson = getPreludeData(patientDemographicUrl);

		// LOGGER.info("Inside getPatientDemographicData(() patientDemographicJson :::::
		// " + patientDemographicJson.toString());

		// daoImpl.savePreludeInfo(extPatientID, patientDemographicJson.toString());

		if ((patientDemographicJson != null && !patientDemographicJson.trim().equals("")
				&& !patientDemographicJson.startsWith("ERROR"))) {
			// LOGGER.info("getPatientDemographicData  patientDemographicJson   ::::  " + patientDemographicJson);

			try {
				JSONObject jsonObject = new JSONObject(patientDemographicJson);
				// LOGGER.info("jsonObject Object  :::   " + jsonObject);
				String editText = "";
				if (jsonObject.has("editText") && jsonObject.get("editText") != null) {
					editText = jsonObject.get("editText").toString().trim();
				}

				String editWhen = "";
				if (jsonObject.has("editWhen") && jsonObject.get("editWhen") != null) {
					editWhen = jsonObject.get("editWhen").toString().trim();
				}

				// LOGGER.info("editText   ::::  " + editText + "  - editWhen  :::  " + editWhen);

				extStudyExtractInfoJson.put("extractDefID", extStudyExtractDefinitionMap.get("EXTRACT_DEF_ID"));
				extStudyExtractInfoJson.put("extPatientID", extPatientID);
				extStudyExtractInfoJson.put("category", extStudyExtractDefinitionMap.get("CATEGORY"));
				extStudyExtractInfoJson.put("fieldName", extStudyExtractDefinitionMap.get("FIELD"));
				extStudyExtractInfoJson.put("fieldValue", editText);
				extStudyExtractInfoJson.put("createdBy", "Service");
				extStudyExtractInfoJson.put("timestamp", editWhen);
				extStudyExtractInfoJson.put("responseFromPrelude", jsonObject);

				// LOGGER.info("new json for extStudyExtractInfoJson :::: " + extStudyExtractInfoJson);

			} catch (JSONException e) {
				LOGGER.error("getPatientDemographicData  Error msg   ::::  " , e);
			}
		}

		return extStudyExtractInfoJson;
	}

	private void loadPatientStudyData(String authToken, Map<String, String> studyURLDbMap) {

		LOGGER.info("inside the PreludeJob.loadPatientStudyData()  ::::   Generate Patient Demographic Data.");

		try {
			List<Map<String, Object>> extSiteInfoList = daoImpl.getExtSiteInfoList(studyURLDbMap.get("studyUiValue"));
			LOGGER.info(":::: loadPatientStudyData.getExtSiteInfoList()  ::::: " + extSiteInfoList);

			for (Map<String, Object> map : extSiteInfoList) {

				/*LOGGER.info("getExtSiteInfoList map  :::::   " + map);
				LOGGER.info("EXTERNAL_SITE_NAME  :: " + map.get("EXTERNAL_SITE_NAME") + " - URL :: " + map.get("URL")
						+ "  -  " + map.get("EXTERNAL_SITE_ID")); */

				if (map.get("URL") != null && !map.get("URL").toString().trim().equals("")) {

					String studyName = map.get("EXTERNAL_STUDY_ID").toString();
					String studyUrl = map.get("URL").toString();
					String extSiteId = map.get("EXTERNAL_SITE_ID").toString();
					// LOGGER.info(":::: extSiteId  :::::   " + extSiteId);

					List<Map<String, Object>> extPatientInfoList = daoImpl.getExtPatientInfoList(extSiteId);
					for (Map<String, Object> extPatientInfoMap : extPatientInfoList) {

						// LOGGER.info("Generate PatientDemographicData ExtPatientInfo: " + extPatientInfoMap);

						int updateFieldCounter = 0;
						List<Map<String, Object>> extStudyExtractDefinitionList = daoImpl
								.getNeedUpdateExtStudyExtractDefinitionList(studyName);

						for (Map<String, Object> extStudyExtractDefinitionMap : extStudyExtractDefinitionList) {

							try {
								JSONObject patientDemographicJson = getPatientDemographicData(studyUrl, authToken,
										studyName, extSiteId, extPatientInfoMap.get("EXT_PET_ID").toString(),
										extStudyExtractDefinitionMap);

								// LOGGER.info(":: patientDemographicJson  :::: " + patientDemographicJson);

								if (!patientDemographicJson.isEmpty() && patientDemographicJson.has("fieldName")
										&& !patientDemographicJson.get("fieldName").toString().trim().equals("")) {

									// LOGGER.info("fieldName :: " + patientDemographicJson.get("fieldName").toString());

									int i = daoImpl.insertOrUpdateExtStudyExtractInfo(patientDemographicJson);
									if (i > 0) {
										updateFieldCounter++;
									}
								}
							} catch (Exception e) {
								LOGGER.error("Exception inside loadPatientStudyData() :::: " , e);
							}
						}

						// LOGGER.info("updateFieldCounter :::   " + updateFieldCounter);
						if (updateFieldCounter > 0) {

							try {
								List<Map<String, Object>> extStudyExtractInfoList = daoImpl
										.getExtStudyExtractInfoList(extPatientInfoMap.get("EXT_PET_ID").toString());

								if (extStudyExtractInfoList.size() > 0) {

									StringBuilder sbBody = new StringBuilder();
									sbBody.append("Patient Data\n");

									for (Map<String, Object> extStudyExtractInfoMap : extStudyExtractInfoList) {
										try {
											sbBody.append(extStudyExtractInfoMap.get("FIELD_NAME").toString())
													.append(" : ")
													.append(extStudyExtractInfoMap.get("FIELD_VALUE").toString())
													.append(" \n");
										} catch (Exception e) {
											LOGGER.error(
													"Exception inside loadPatientStudyData() ::::  " , e);
											continue;
										}
									}

									// String body = sbBody.toString();

									// LOGGER.info("Sending Email to Patient . ExtPatientInfo: " + extPatientInfoMap.get("EXT_PET_ID").toString() + " - body : " + body);
									/*
									 * ThreadPoolExecutor threadPoolExecutor =
									 * EmailThreadPoolExecutor.getEmailThreadPoolExecutor(); EmailSenderThread
									 * emailSenderThread = new EmailSenderThread(mailTo,
									 * Constants.UPDATE_PET_MAIL_SUBJECT, body);
									 * threadPoolExecutor.submit(emailSenderThread);
									 */
									daoImpl.updateExtPetNotificationStatus(
											extPatientInfoMap.get("EXT_PET_ID").toString());
								}

							} catch (Exception e) {
								LOGGER.error("Exception inside loadPatientStudyData() ::::  " , e);
							}
						}
					}
				}
			}
		} catch (SQLNonTransientConnectionException e) {
			LOGGER.error("loadPatientStudyData :: Exception (No operations allowed after connection closed.)  :: " + e);
		} catch (Exception e) {
			LOGGER.error(":: loadPatientStudyData  Exception ::" + e);
		}
	}

	private void updateTimeZone() {
		LOGGER.info("::: Inside the PreludeJob.updateTimeZone() ::: ");		
		try {
			List<Map<String, Object>> list = daoImpl.getPetParentsAddressHaveNoTimeZoneList();
			LOGGER.info("Address without timezone list  :::::   " + list);
			if (!list.isEmpty()) {
				List<Object> timeZones = new ArrayList<Object>();
				for (Map<String, Object> addressMap : list) {
					// LOGGER.info(":::: " + addressMap.get("PET_PARENT_ADDRESS_ID") + " - " + addressMap.get("ZIP_CODE"));
					if (!addressMap.get("PET_PARENT_ADDRESS_ID").toString().trim().equals("")
							&& !addressMap.get("ZIP_CODE").toString().trim().equals("")) {
						String timeZoneName = googleTimeZoneUtil
								.getTimeZoneByZipcode(addressMap.get("ZIP_CODE").toString().trim());
						Map<String, Object> timezone = new HashMap<>();
						timezone.put("petParentAddressId", addressMap.get("PET_PARENT_ADDRESS_ID"));
						timezone.put("timeZone", timeZoneName);
						timeZones.add(timezone);
					}
				}
				String reqeustJson = mapper.writeValueAsString(timeZones.toArray());
				LOGGER.info("Reqeust Json   :::::   " + reqeustJson);
				daoImpl.updateAddressWithTimeZone(reqeustJson);
			}
		} catch (Exception e) {
			LOGGER.error("Exception " , e);
		}
	}

	private static String getPreludeData(String uri) {
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);
		return result;
	}
}
