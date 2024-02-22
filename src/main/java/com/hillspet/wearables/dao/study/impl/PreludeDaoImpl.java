/**
 * Created Date: Mar 17, 2021 12:50:00 PM
 * Class Name  : PreludeJob.java
 * � Copyright 2008 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>	<Author>	<Date>	<Comments on Change>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package com.hillspet.wearables.dao.study.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
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
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.BaseDaoImpl;

/**
 * 
 * @author sgorle
 * @date: 14-09-2022
 *
 */
@Repository
public class PreludeDaoImpl extends BaseDaoImpl {

	private static final Logger LOGGER = LogManager.getLogger(PreludeDaoImpl.class);

	@Autowired
	private ObjectMapper mapper;

	private static final String PRELUDE_GET_NEW_PET_PARENT_LIST = "PRELUDE_GET_NEW_PET_PARENT_LIST_NEW";
	private static final String PRELUDE_GET_NEW_PET_LIST = "PRELUDE_GET_NEW_PET_LIST_NEW";
	private static final String PRELUDE_GET_STUDY_INFO = "call PRELUDE_GET_STUDY_INFO()";
	private static final String PRELUDE_UPDATE_PET_STUDY = "PRELUDE_UPDATE_PET_STUDY_NEW";
	private static final String PRELUDE_UPDATE_PET_ADDRESS = "PRELUDE_UPDATE_PET_ADDRESS_NEW";

	private static final String PRELUDE_PET_PARENT_GET_ADDRESS_HAVE_NO_TIMEZONE = "SELECT PET_PARENT_ADDRESS_ID, ZIP_CODE FROM B_PET_PARENT_ADDRESS WHERE TIMEZONE_ID IS NULL AND IS_PRELUDE_ADDRESS = 1 AND ADDRESS_TYPE = 1 AND IS_DELETED = 0";
	private static final String PRELUDE_PET_PARENT_UPDATE_ADDRESS_WITH_TIMEZONE = "PRELUDE_PET_PARENT_UPDATE_ADDRESS_WITH_TIMEZONE";

	private static final String STUDY_LIST_QUERY = "SELECT BESI.* FROM B_EXTERNAL_STUDY_INFO BESI INNER JOIN B_STUDY BS ON BS.STUDY_ID = BESI.STUDY_ID WHERE BS.IS_DELETED = 0 AND BS.IS_COMPLETED = 0 AND BS.IS_ACTIVE = 1 AND (BS.START_DATE IS NULL OR BS.START_DATE <= current_date()) AND (BS.END_DATE IS NULL OR BS.END_DATE >= current_date()) AND BESI.EXT_STUDY_STATUS = 1 AND BESI.IS_DELETED = 0 AND (BESI.URL IS NOT NULL AND TRIM(BESI.URL) != '' AND TRIM(BESI.URL) LIKE '%http%') AND EXT_STUDY_ID IN ('WAM2')";

	private static final String SITE_LIST_QUERY = "SELECT B_EXTERNAL_SITE_INFO.*, B_EXTERNAL_STUDY_INFO.URL FROM B_EXTERNAL_SITE_INFO "
			+ " LEFT JOIN B_EXTERNAL_STUDY_INFO ON B_EXTERNAL_STUDY_INFO.EXT_STUDY_ID = B_EXTERNAL_SITE_INFO.EXTERNAL_STUDY_ID "
			+ " WHERE B_EXTERNAL_SITE_INFO.STATUS=1 and B_EXTERNAL_STUDY_INFO.IS_DELETED = 0 AND B_EXTERNAL_STUDY_INFO.EXT_STUDY_ID IN (?)";

	private static final String PATIENT_LIST_QUERY = "SELECT * FROM B_EXTERNAL_PET_INFO WHERE EXT_SITE_ID = ?";

	private static final String EXT_STUDY_EXTRACT_DEFINITION_QUERY = "SELECT * FROM B_EXTERNAL_STUDY_EXTRACT_DEF "
			+ " WHERE CATEGORY = 'Demographics' AND EXT_STUDY_ID = ? ORDER BY EXTRACT_DEF_ID ASC  ";

	private static final String NEED_UPDATE_EXT_STUDY_EXTRACT_DEFINITION_QUERY = "SELECT * FROM B_EXTERNAL_STUDY_EXTRACT_DEF WHERE EXT_STUDY_ID =  ? "
			+ "AND IS_PORTAL_CONFIGURED = 1 ";

	private static final String EXT_STUDY_EXTRACT_INFO_BY_EXT_PATIENTID_QUERY = "SELECT * FROM B_EXTERNAL_STUDY_EXTRACT_INFO WHERE EXT_PET_ID = ? "
			+ "AND CATEGORY = 'Demographics' ORDER BY EXTRACT_DEF_ID ASC";

	private static final String CHK_QUERY_B_EXTERNAL_SITE_INFO = "SELECT distinct EXTERNAL_SITE_ID FROM B_EXTERNAL_SITE_INFO WHERE EXTERNAL_SITE_ID = ? ";

	private static final String INSERT_QUERY_B_EXTERNAL_SITE_INFO = "INSERT INTO B_EXTERNAL_SITE_INFO "
			+ "(EXTERNAL_SITE_ID, EXTERNAL_SITE_NAME, EXTERNAL_STUDY_ID, STATUS, IS_DELETED, "
			+ " CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE) VALUES (?, ?, ?, 1, 0, 1, now(), 1, now())";

	private static final String CHK_QUERY_B_EXTERNAL_PET_INFO = "SELECT distinct EXT_PET_ID FROM B_EXTERNAL_PET_INFO WHERE EXT_PET_ID = ? ";

	private static final String INSERT_QUERY_B_EXTERNAL_PET_INFO = "INSERT INTO B_EXTERNAL_PET_INFO "
			+ "(EXT_PET_ID, EXT_PET_VALUE, PET_ID, EXT_SITE_ID, EXT_TREATMENT_GROUP, IS_NOTIFICATION_SENT, IS_DELETED, "
			+ " CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE) VALUES (?, ?, NULL, ?, NULL, 0, 0, 1, now(), 1, now())";

	private static final String CHK_QUERY_B_EXTERNAL_PLAN_EXTRACT_INFO = "select EXTRACT_INFO_ID from B_EXTERNAL_STUDY_EXTRACT_INFO WHERE EXT_PET_ID = ? AND EXTRACT_DEF_ID = ?";

	private static final String INSERT_QUERY_B_EXTERNAL_PLAN_EXTRACT_INFO = "INSERT INTO B_EXTERNAL_STUDY_EXTRACT_INFO "
			+ "(EXTRACT_DEF_ID, EXT_PET_ID, CATEGORY, FIELD_NAME, FIELD_VALUE, JSON_VALUE, IS_DELETED, "
			+ " CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE) VALUES (?, ?, ?, ?, ?, ?, 0, 1, now(), 1, now())";

	private static final String UPDATE_NOTIFICATION_B_EXTERNAL_PLAN_EXTRACT_INFO = "UPDATE B_EXTERNAL_PET_INFO SET IS_NOTIFICATION_SENT = 1, MODIFIED_DATE = now() WHERE EXT_PET_ID = ?";

	private static final String CHK_UNIQUE_REC_QUERY_B_EXTERNAL_PLAN_EXTRACT_INFO = "select EXTRACT_INFO_ID from B_EXTERNAL_STUDY_EXTRACT_INFO WHERE EXT_PET_ID = ? AND EXTRACT_DEF_ID = ?"
			+ " AND FIELD_NAME = ? AND FIELD_VALUE = ? AND CATEGORY = ? ";

	private static final String UPDATE_QUERY_B_EXTERNAL_PLAN_EXTRACT_INFO = "UPDATE B_EXTERNAL_STUDY_EXTRACT_INFO "
			+ "SET FIELD_NAME = ?, FIELD_VALUE = ?, CATEGORY = ? , JSON_VALUE = ? , MODIFIED_DATE = NOW() "
			+ "WHERE EXT_PET_ID = ? AND EXTRACT_DEF_ID = ?";

	/**
	 * 
	 * @return
	 * @throws SQLNonTransientConnectionException
	 * 
	 */
	public List<Map<String, Object>> getExtStudyList() throws SQLNonTransientConnectionException {

		LOGGER.info("Inside the PreludeDaoImpl.getExtStudyList() ::   " + STUDY_LIST_QUERY);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = select(STUDY_LIST_QUERY);
		return list;
	}

	/**
	 * 
	 * 
	 * @return
	 * @throws java.sql.SQLNonTransientConnectionException
	 * 
	 */
	public List<Map<String, Object>> getExtSiteInfoList(String studyId) throws SQLNonTransientConnectionException {

		LOGGER.info("Inside the PreludeDaoImpl.getExtSiteInfo()   :::::::::::::::::   ");
		List<Map<String, Object>> siteList = select(SITE_LIST_QUERY, studyId);
		// LOGGER.info("siteList ::::: " + siteList);
		return siteList;
	}

	/**
	 * 
	 * 
	 * @param siteId
	 * @return
	 * @throws SQLNonTransientConnectionException
	 * 
	 */
	public List<Map<String, Object>> getExtPatientInfoList(String siteId) throws SQLNonTransientConnectionException {

		LOGGER.info("Inside the PreludeDaoImpl.getExtPatientInfoList()   ::   siteInfoId  : " + siteId);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = select(PATIENT_LIST_QUERY, siteId);
		// LOGGER.info("list ::::: " + list);
		return list;
	}

	/**
	 * 
	 * 
	 * @param studyId
	 * @return
	 * @throws SQLNonTransientConnectionException
	 * 
	 */
	public List<Map<String, Object>> getExtStudyExtractDefinitionList(String studyId)
			throws SQLNonTransientConnectionException {

		LOGGER.info("Inside PreludeDao etExtStudyExtractDefinitionList()  ::::   siteInfoId : " + studyId);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = select(EXT_STUDY_EXTRACT_DEFINITION_QUERY, studyId);

		// LOGGER.info("list ::::: " + list);
		return list;
	}

	public List<Map<String, Object>> getNeedUpdateExtStudyExtractDefinitionList(String studyId)
			throws java.sql.SQLNonTransientConnectionException {

		LOGGER.info("Inside the PreludeDaoImpl.GetNeedUpdateExtStudyExtractDefinitionList() :::::   siteInfoId  : "
				+ studyId);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = select(NEED_UPDATE_EXT_STUDY_EXTRACT_DEFINITION_QUERY, studyId);
		// LOGGER.info("list ::::: " + list);

		return list;
	}

	public List<Map<String, Object>> getExtStudyExtractInfoList(String extPatientID)
			throws java.sql.SQLNonTransientConnectionException {

		LOGGER.info("Inside the PreludeDaoImpl.getExtStudyExtractInfoList()   :::::::::::::::::   extPatientID  : "
				+ extPatientID);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = select(EXT_STUDY_EXTRACT_INFO_BY_EXT_PATIENTID_QUERY, extPatientID);
		// LOGGER.info("list ::::: " + list);

		return list;
	}

	@SuppressWarnings("unchecked")
	public void insertExtSiteInfo(String study, List<Object> siteList) {

		LOGGER.info("Inside the PreludeDaoImpl.insertExtSiteInfo()   ::::   siteList  : " + siteList.toString());
		try {
			for (Object siteMap : siteList) {
				// LOGGER.info(" siteMap ::: " + siteMap);

				Map<String, Object> map = mapper.convertValue(siteMap, Map.class);
				// LOGGER.info("map :: " + map);

				List<Map<String, Object>> list = select(CHK_QUERY_B_EXTERNAL_SITE_INFO, map.get("db"));

				if (list.size() == 0) {
					try {
						insert(INSERT_QUERY_B_EXTERNAL_SITE_INFO,
								new Object[] { map.get("db").toString(), map.get("ui").toString(), study });
						// LOGGER.info("Site record inserted successfully :::: study : " + study + " map
						// : " + map);
					} catch (Exception e) {
						LOGGER.error("Error oocured inside insertExtSiteInfo ", e);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error oocured inside insertExtSiteInfo ", e);
		}
	}

	@SuppressWarnings("unchecked")
	public void insertExtPetInfo(String extSiteID, List<Object> petList) {

		LOGGER.info("Inside the PreludeDaoImpl.insertExtPetInfo()   :::::::::::::::::  " + " extSiteID   " + extSiteID
				+ "  petList  : " + petList.toString());

		try {
			for (Object petInfoMap : petList) {

				// LOGGER.info(" petInfoMap ::: " + petInfoMap);
				Map<String, Object> map = mapper.convertValue(petInfoMap, Map.class);

				List<Map<String, Object>> list = select(CHK_QUERY_B_EXTERNAL_PET_INFO, map.get("db"));
				if (list.size() == 0) {
					// LOGGER.info("extSiteID :::: " + extSiteID + " map ::: " + map);
					try {
						insert(INSERT_QUERY_B_EXTERNAL_PET_INFO,
								new Object[] { map.get("db").toString(), map.get("ui").toString(), extSiteID });
						// LOGGER.info("Pet record inserted successfully :::: extSiteID : " + extSiteID
						// + " map : " + map);
					} catch (Exception e) {
						LOGGER.error("Error oocured inside insertExtPetInfo() while inserting site record :::   ", e);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error oocured inside insertExtPetInfo ", e);
		}
	}

	public void insertExtStudyExtractInfo(JSONObject patientDemographicJson) {

		LOGGER.info("Inside the PreludeDaoImpl.insertExtStudyExtractInfo()   :::::::::::::::::  ");
		LOGGER.info("patientDemographicJson  :::  " + patientDemographicJson);
		try {

			List<Map<String, Object>> list = select(CHK_QUERY_B_EXTERNAL_PLAN_EXTRACT_INFO, new Object[] {
					patientDemographicJson.get("extPatientID"), patientDemographicJson.get("extractDefID") });

			// LOGGER.info("list size ::: " + list.size());
			if (list.size() == 0) {
				try {
					long id = insertNew(INSERT_QUERY_B_EXTERNAL_PLAN_EXTRACT_INFO,
							new Object[] { patientDemographicJson.get("extractDefID"),
									patientDemographicJson.get("extPatientID"), patientDemographicJson.get("category"),
									patientDemographicJson.get("fieldName"), patientDemographicJson.get("fieldValue"),
									patientDemographicJson.toString() });
					LOGGER.info("Pet record inserted successfully ::::    id : " + id);
				} catch (Exception e) {
					LOGGER.error("Error oocured inside insertExtStudyExtractInfo() while inserting site record :::   ",
							e);
				}
			}

		} catch (Exception e) {
			LOGGER.error("Error oocured inside insertExtPetInfo ", e);
		}
	}

	public void updateExtPetNotificationStatus(String extPetId) {

		LOGGER.info("Inside the PreludeDaoImpl.updateExtPetInfo()   ::::::   extPetId  : " + extPetId);

		try {
			update(UPDATE_NOTIFICATION_B_EXTERNAL_PLAN_EXTRACT_INFO, new Object[] { extPetId });
			LOGGER.info("Pey record updated successfully ::::    extPetId : " + extPetId);
		} catch (Exception e) {
			LOGGER.error("Error oocured inside updateExtPetInfo() while updating prt record ", e);
		}
	}

	public int insertOrUpdateExtStudyExtractInfo(JSONObject patientDemographicJson) {

		// LOGGER.info("Inside the PreludeDaoImpl.insertOrUpdateExtStudyExtractInfo()
		// ::::::::::::::::: ");
		// LOGGER.info("patientDemographicJson ::: " + patientDemographicJson);
		int i = 0;
		try {

			List<Map<String, Object>> list = select(CHK_QUERY_B_EXTERNAL_PLAN_EXTRACT_INFO, new Object[] {
					patientDemographicJson.get("extPatientID"), patientDemographicJson.get("extractDefID") });

			// LOGGER.info("list size ::: " + list.size());
			if (list.size() == 0) {
				try {
					insertNew(INSERT_QUERY_B_EXTERNAL_PLAN_EXTRACT_INFO,
							new Object[] { patientDemographicJson.get("extractDefID"),
									patientDemographicJson.get("extPatientID"), patientDemographicJson.get("category"),
									patientDemographicJson.get("fieldName"), patientDemographicJson.get("fieldValue"),
									patientDemographicJson.toString() });
					// LOGGER.info("Pet record inserted successfully :::: id : " + id);
					i = 1;
				} catch (Exception e) {
					LOGGER.error(
							"Error oocured inside insertOrUpdateExtStudyExtractInfo() while inserting site record :::   ",
							e);
				}
			} else {

				List<Map<String, Object>> uniqueList = select(CHK_UNIQUE_REC_QUERY_B_EXTERNAL_PLAN_EXTRACT_INFO,
						new Object[] { patientDemographicJson.get("extPatientID"),
								patientDemographicJson.get("extractDefID"), patientDemographicJson.get("fieldName"),
								patientDemographicJson.get("fieldValue"), patientDemographicJson.get("category") });

				// LOGGER.info("uniqueList size :::  " + uniqueList.size());

				if (uniqueList.size() == 0) {
					try {
						update(UPDATE_QUERY_B_EXTERNAL_PLAN_EXTRACT_INFO,
								new Object[] { patientDemographicJson.get("fieldName"),
										patientDemographicJson.get("fieldValue"),
										patientDemographicJson.get("category"), patientDemographicJson.toString(),
										patientDemographicJson.get("extPatientID"),
										patientDemographicJson.get("extractDefID") });
						LOGGER.info("Pey record updated successfully ::::    extPetId : "
								+ patientDemographicJson.get("extPatientID") + " extractDefID : "
								+ patientDemographicJson.get("extractDefID"));
						i = 1;
					} catch (Exception e) {
						LOGGER.error("Error oocured inside updateExtPetInfo() while updating prt record ", e);
					}
				}

			}

		} catch (Exception e) {
			LOGGER.error("Error oocured inside insertExtPetInfo ", e);
		}
		return i;
	}

	/**
	 * Calling the Prelude-Portal sync procedures
	 */
	public void callCreateExtPetParentProc(String extStudyId) {
		LOGGER.info(" ******** Start Of callExtPetParentSyncProc() *********** ");

		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_ext_study_id", extStudyId);
			Map<String, Object> outParams = callStoredProcedure(PRELUDE_GET_NEW_PET_PARENT_LIST, inputParams);

			String errorMsg = (String) outParams.get("out_error_msg");

			int statusFlag = (int) outParams.get("out_flag");

			if (StringUtils.isNotEmpty(errorMsg) || (int) statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("deleteRole validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList());
					// Arrays.asList(new WearablesError("Error while creating Prelude Pet
					// Parents")));
				} else if (statusFlag == -3) {
					throw new ServiceExecutionException("Inserta Pet Parent validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList());
					// Arrays.asList(new WearablesError("Insert Pet Parent validation failed")));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing callCreateExtPetParentProc ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	/**
	 * Calling the Prelude-Portal sync procedures
	 */
	public void callCreateExtPetProc(String extStudyId) {
		LOGGER.info(" ******** Start Of callCreateExtPetProc() *********** ");

		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_ext_study_id", extStudyId);
			Map<String, Object> outParams = callStoredProcedure(PRELUDE_GET_NEW_PET_LIST, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");

			int statusFlag = (int) outParams.get("out_flag");

			if (StringUtils.isNotEmpty(errorMsg) || (int) statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("deleteRole validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList());
					// Arrays.asList(new WearablesError("Error while creating Prelude Pet
					// Parents")));
				} else if (statusFlag == -3) {
					throw new ServiceExecutionException("Inserta Pet Parent validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList());
					// Arrays.asList(new WearablesError("Insert Pet Parent validation failed")));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing callCreateExtPetProc ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	/**
	 * Calling the Prelude-Portal sync procedures
	 */
	public void callUpdateExtPetProc(String extStudyId) {
		LOGGER.info(" ******** Start Of callUpdateExtPetProc() *********** ");

		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_ext_study_id", extStudyId);
			Map<String, Object> outParams = callStoredProcedure(PRELUDE_UPDATE_PET_STUDY, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");

			int statusFlag = (int) outParams.get("out_flag");

			if (StringUtils.isNotEmpty(errorMsg) || (int) statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("deleteRole validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList());
					// Arrays.asList(new WearablesError("Error while creating Prelude Pet
					// Parents")));
				} else if (statusFlag == -3) {
					throw new ServiceExecutionException("Inserta Pet Parent validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList());
					// Arrays.asList(new WearablesError("Insert Pet Parent validation failed")));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing callUpdateExtPetProc ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}
	
	
	/**
	 * Calling the Prelude-Portal sync procedures
	 */
	public void callUpdateExtPetAddressProc(String extStudyId) {
		LOGGER.info(" ******** Start Of callUpdateExtPetAddressProc() *********** ");

		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_ext_study_id", extStudyId);
			Map<String, Object> outParams = callStoredProcedure(PRELUDE_UPDATE_PET_ADDRESS, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");

			int statusFlag = (int) outParams.get("out_flag");

			if (StringUtils.isNotEmpty(errorMsg) || (int) statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("deleteRole validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList());
					// Arrays.asList(new WearablesError("Error while creating Prelude Pet
					// Parents")));
				} else if (statusFlag == -3) {
					throw new ServiceExecutionException("Inserta Pet Parent validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList());
					// Arrays.asList(new WearablesError("Insert Pet Parent validation failed")));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing callUpdateExtPetProc ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	public void savePreludeInfo(String extPatientID, String string) {
		try {
			insert("insert into temp_prelude_data (patient_id, json_value) values (?,?)", extPatientID, string);
			LOGGER.info("Insert into temp_prelude_data " + extPatientID + " json : " + string);
		} catch (Exception e) {
			LOGGER.info("Error oocured while insert into  temp_prelude_data", e);
		}

	}

	public List<Map<String, Object>> getPetParentsAddressHaveNoTimeZoneList() {
		LOGGER.info("Inside the PreludeDaoImpl.getPetParentsAddressHaveNoTimeZoneList() ::   "
				+ PRELUDE_PET_PARENT_GET_ADDRESS_HAVE_NO_TIMEZONE);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = select(PRELUDE_PET_PARENT_GET_ADDRESS_HAVE_NO_TIMEZONE);
		return list;
	}

	/**
	 * Calling the updateAddressWithTimeZone
	 */
	public void updateAddressWithTimeZone(String requestJson) {
		LOGGER.info(" ******** Start Of updateAddressWithTimeZone() *********** ");

		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_reqeust_json", requestJson);

			Map<String, Object> outParams = callStoredProcedure(PRELUDE_PET_PARENT_UPDATE_ADDRESS_WITH_TIMEZONE,
					inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			if (StringUtils.isNotEmpty(errorMsg)) {
				LOGGER.info("successfully updateAddressWithTimeZone");
			} else {
				LOGGER.info("failded to updateAddressWithTimeZone ", errorMsg);
			}
			/*
			 * if (StringUtils.isNotEmpty(errorMsg) || (int) statusFlag <
			 * NumberUtils.INTEGER_ONE) { if (statusFlag == -2) { throw new
			 * ServiceExecutionException("deleteRole validation failed cannot proceed further"
			 * , Status.BAD_REQUEST.getStatusCode(), Arrays.asList()); // Arrays.asList(new
			 * WearablesError("Error while creating Prelude Pet // Parents"))); } else if
			 * (statusFlag == -3) { throw new
			 * ServiceExecutionException("Inserta Pet Parent validation failed cannot proceed further"
			 * , Status.BAD_REQUEST.getStatusCode(), Arrays.asList()); // Arrays.asList(new
			 * WearablesError("Insert Pet Parent validation failed"))); } else { throw new
			 * ServiceExecutionException(errorMsg); } }
			 */
		} catch (SQLException e) {
			LOGGER.error("error while executing callUpdateExtPetProc ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	public List<Map<String, Object>> getExternalStudyList() {
		List<Map<String, Object>> studyList = new ArrayList<>();
		LOGGER.debug("getExternalStudyList called");
		try {
			jdbcTemplate.query(PRELUDE_GET_STUDY_INFO, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					Map<String, Object> studyInfo = new HashMap<String, Object>();
					studyInfo.put("EXT_STUDY_ID", rs.getString("EXT_STUDY_ID"));
					studyInfo.put("STUDY_ID", rs.getString("STUDY_ID"));
					studyInfo.put("EXT_STUDY_STATUS", rs.getString("EXT_STUDY_STATUS"));
					studyInfo.put("URL", rs.getString("URL"));
					studyList.add(studyInfo);
				}
			});

		} catch (Exception e) {
			LOGGER.error("error while fetching getExternalStudyList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return studyList;
	}

}
