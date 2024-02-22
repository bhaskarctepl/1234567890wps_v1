package com.hillspet.wearables.dao.analyticalReports.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.constants.SQLConstants;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.utils.GCPClientUtil;
import com.hillspet.wearables.common.utils.WearablesUtils;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.analyticalReports.AnalyticalReportsDao;
import com.hillspet.wearables.dto.AnalyticalReport;
import com.hillspet.wearables.dto.AnalyticalReportGroup;
import com.hillspet.wearables.dto.ManageReports;
import com.hillspet.wearables.dto.PreludeReport;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.PreludeReportFilter;
import com.hillspet.wearables.security.Authentication;

@Repository
public class AnalyticalReportsDaoImpl extends BaseDaoImpl implements AnalyticalReportsDao {
	
	private static final Logger LOGGER = LogManager.getLogger(AnalyticalReportsDaoImpl.class);
	
	@Autowired
	private Authentication authentication;

	@Value("${prelude_bucket}")
	private String preludeBucketName;

	@Autowired
	private GCPClientUtil gcpClientUtil;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public List<AnalyticalReportGroup> getReportGroups() throws ServiceExecutionException {
		List<AnalyticalReportGroup> analyticalReportGroupList = new ArrayList<>();
		LOGGER.debug("getReportGroups called");
		try {
			jdbcTemplate.query(SQLConstants.GET_ANALYTICAL_REPORTS_GROUP, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					AnalyticalReportGroup analyticalReportGroup = new AnalyticalReportGroup();
					// set the column values to fields like below
					analyticalReportGroup.setReportGroupId(resultSet.getInt("MENU_ID"));
					analyticalReportGroup.setReportName(resultSet.getString("MENU_NAME"));
					analyticalReportGroupList.add(analyticalReportGroup);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getReportGroups", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		return analyticalReportGroupList;
	}

	@Override
	public ManageReports addReport(ManageReports manageReports, String platform) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		ManageReports manageReport = new ManageReports();

		inputParams.put("p_report_group_id", manageReports.getReportGroupId());
		inputParams.put("p_report_name", manageReports.getReportName());
		try {
			inputParams.put("p_report_url", URLDecoder.decode(manageReports.getReportUrl().trim(), "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		inputParams.put("p_report_visible_to", manageReports.getReportVisibleTo());
		inputParams.put("p_status", manageReports.getStatus());
		inputParams.put("p_created_by", authentication.getAuthUserDetails().getUserId());
		inputParams.put("p_reqeust_from", WearablesUtils.getRequestFrom(platform));
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.MANAGE_REPORT_INSERT, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				Integer reportId = (int) outParams.get("last_insert_id");
				manageReports.setReportId(reportId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("addReport service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.REPORT_NAME_ALREADY_EXISTS,
									manageReports.getReportName())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			throw new ServiceExecutionException(e.getMessage());
		}
		return manageReport;
	}

	@Override
	public ManageReports updateReport(ManageReports manageReports, String platform) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		ManageReports manageReport = new ManageReports();

		inputParams.put("p_report_group_id", manageReports.getReportGroupId());
		inputParams.put("p_report_name", manageReports.getReportName());
		try {
			inputParams.put("p_report_url", URLDecoder.decode(manageReports.getReportUrl().trim(), "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		inputParams.put("p_report_visible_to", manageReports.getReportVisibleTo());
		inputParams.put("p_status", manageReports.getStatus());
		inputParams.put("p_modified_by", authentication.getAuthUserDetails().getUserId());
		inputParams.put("p_report_id", manageReports.getReportId());
		inputParams.put("p_reqeust_from", WearablesUtils.getRequestFrom(platform));
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.MANAGE_REPORT_UPDATE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("updateReport service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.REPORT_NAME_ALREADY_EXISTS,
									manageReports.getReportName())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			throw new ServiceExecutionException(e.getMessage());
		}
		return manageReport;
	}	

	@Override
	public void deleteReport(int reportId, int modifiedBy, String platform) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_report_id", reportId);
		inputParams.put("p_modified_by", modifiedBy);
		inputParams.put("p_reqeust_from", WearablesUtils.getRequestFrom(platform));
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.MANAGE_REPORT_DELETE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("deleteReport service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.PLAN_ALREADY_MAPPED_TO_STUDY)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing deleteReport ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}
	
	@Override
	public ManageReports getReportById(int reportId, String platform) throws ServiceExecutionException {
		ManageReports manageReports = new ManageReports();
		try {
			LOGGER.debug("getPointTrackerById called");
			jdbcTemplate.query(SQLConstants.GET_MANAGE_REPORT_GET_BY_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {

					manageReports.setReportGroupId(rs.getInt("REPORT_GROUP_ID"));
					manageReports.setReportName(rs.getString("REPORT_NAME"));
					manageReports.setReportUrl(rs.getString("REPORT_URL"));
					manageReports.setStatus(rs.getInt("IS_ACTIVE"));
					manageReports.setReportId(rs.getInt("REPORT_ID"));
					manageReports.setReportVisibleTo(rs.getInt("REPORT_VISIBLE_TO"));
				}
			}, reportId,platform);
		} catch (Exception e) {
			LOGGER.error("error while fetching getReportById", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return manageReports;
	}
	
	@Override
	public Map<String, Integer> getReportsByReportGroupIdCount(BaseFilter filter, String platform) throws ServiceExecutionException {
		int totalCount = NumberUtils.INTEGER_ZERO;
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		LOGGER.debug("getReportsByReportGroupIdCount called");
		try {
			counts = selectForObject(SQLConstants.MANAGE_REPORT_GET_LIST_BY_ID_COUNT, String.class,
					filter.getFilterType(), filter.getFilterValue(), platform);
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getReportsByReportGroupIdCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}
	
	@Override
	public List<AnalyticalReport> getReportsByReportGroupId(BaseFilter filter, String platform) throws ServiceExecutionException {
		List<AnalyticalReport> reportList = new ArrayList<>();
		LOGGER.debug("getReportsByReportGroupId called");
		try {
			jdbcTemplate.query(SQLConstants.MANAGE_REPORT_GET_LIST_BY_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					AnalyticalReport manageReports = new AnalyticalReport();

					manageReports.setReportGroupId(rs.getInt("REPORT_GROUP_ID"));
					manageReports.setReportName(rs.getString("REPORT_NAME"));
					manageReports.setReportUrl(rs.getString("REPORT_URL"));
					manageReports.setStatus(rs.getInt("IS_ACTIVE"));
					manageReports.setReportId(rs.getInt("REPORT_ID"));
					manageReports.setCreatedDate(rs.getString("CREATED_DATE"));
					manageReports.setModifiedDate(rs.getString("MODIFIED_DATE"));
					manageReports.setReportGroupName(rs.getString("MENU_NAME"));
					//manageReports.setReportVisibleTo(rs.getString("REPORT_VISIBLE_TO"));
					reportList.add(manageReports);
				}
			}, filter.getFilterType(), filter.getFilterValue(), platform);

		} catch (Exception e) {
			LOGGER.error("error while fetching getReportsByReportGroupId", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return reportList;
	}


	@Override
	public Map<String, Integer> getReportListCount(BaseFilter filter, String platform) throws ServiceExecutionException {
		int totalCount = NumberUtils.INTEGER_ZERO;
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		LOGGER.debug("getReportListCount called");
		try {
			counts = selectForObject(SQLConstants.MANAGE_REPORT_LIST_COUNT, String.class, filter.getFilterType(),
					filter.getFilterValue(), filter.getSearchText(), platform);
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getReportListCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<AnalyticalReport> getReportList(BaseFilter filter, String platform) throws ServiceExecutionException {
		List<AnalyticalReport> reportList = new ArrayList<>();
		LOGGER.debug("getReportList called");
		try {
			jdbcTemplate.query(SQLConstants.MANAGE_REPORT_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					AnalyticalReport manageReports = new AnalyticalReport();

					manageReports.setReportGroupId(rs.getInt("REPORT_GROUP_ID"));
					manageReports.setReportName(rs.getString("REPORT_NAME"));
					manageReports.setReportUrl(rs.getString("REPORT_URL"));
					manageReports.setStatus(rs.getInt("IS_ACTIVE"));
					manageReports.setReportId(rs.getInt("REPORT_ID"));
					manageReports.setCreatedDate(rs.getString("CREATED_DATE"));
					manageReports.setModifiedDate(rs.getString("MODIFIED_DATE"));
					manageReports.setReportGroupName(rs.getString("MENU_NAME"));
					manageReports.setReportVisibleTo(rs.getString("REPORT_VISIBLE_TO"));
					reportList.add(manageReports);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getFilterType(),
					filter.getFilterValue(), filter.getSearchText(), platform);

		} catch (Exception e) {
			LOGGER.error("error while fetching getReportList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return reportList;
	}

	@Override
	public Map<String, Integer> getPreludeReportCount(PreludeReportFilter filter, String platform) throws ServiceExecutionException {
		int totalCount = NumberUtils.INTEGER_ZERO;
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		LOGGER.debug("getPreludeReportCount called");
		try {
			counts = selectForObject(SQLConstants.PRELUDE_REPORT_GET_LIST_BY_ID_COUNT, String.class,
					filter.getFilterType(), filter.getFilterValue(), filter.getSearchText(),
					filter.getPreludeStudyId(), platform);
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPreludeReportCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<PreludeReport> getPreludeReport(PreludeReportFilter filter, String platform) throws ServiceExecutionException {
		List<PreludeReport> reportList = new ArrayList<>();
		LOGGER.debug("getPreludeReport called");
		try {
			jdbcTemplate.query(SQLConstants.PRELUDE_REPORT_GET_LIST_BY_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PreludeReport preludeReport = new PreludeReport();
					preludeReport.setExtractId(rs.getInt("EXTRACT_ID"));
					preludeReport.setExtractName(rs.getString("EXTRACT_NAME"));
					// preludeReport.setExtractFileUrl(rs.getString("EXTRACT_FILE_URL"));
					preludeReport.setExtractFileUrl(gcpClientUtil.getPreludeDownloadFileUrl(preludeBucketName,
							rs.getString("EXTRACT_FILE_URL")));
					preludeReport.setExtractDate(rs.getString("EXTRACT_DATE"));
					preludeReport.setExtractFileCategory(rs.getString("EXTRACT_FILE_CATEGORY"));
					preludeReport.setModifiedDate(rs.getString("MODIFIED_DATE"));
					preludeReport.setStudyId(rs.getInt("STUDY_ID"));
					preludeReport.setStudyName(rs.getString("STUDY_NAME"));
					preludeReport.setCreateDate(rs.getString("CREATED_DATE"));
					preludeReport.setModifiedDate(rs.getString("MODIFIED_DATE"));
					reportList.add(preludeReport);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getFilterType(),
					filter.getFilterValue(), filter.getSearchText(), filter.getPreludeStudyId(), platform);

		} catch (Exception e) {
			LOGGER.error("error while fetching getPreludeReport", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return reportList;
	}
}
