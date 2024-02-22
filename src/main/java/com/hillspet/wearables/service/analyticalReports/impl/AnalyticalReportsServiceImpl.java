package com.hillspet.wearables.service.analyticalReports.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.utils.GCStorageUtil;
import com.hillspet.wearables.dao.analyticalReports.AnalyticalReportsDao;
import com.hillspet.wearables.dto.AnalyticalReport;
import com.hillspet.wearables.dto.AnalyticalReportGroup;
import com.hillspet.wearables.dto.ManageReports;
import com.hillspet.wearables.dto.PreludeReport;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.PreludeReportFilter;
import com.hillspet.wearables.response.AnalyticalReportResponse;
import com.hillspet.wearables.response.GCSignedUrlResponse;
import com.hillspet.wearables.response.PreludeReportResponse;
import com.hillspet.wearables.service.analyticalReports.AnalyticalReportsService;

@Service
public class AnalyticalReportsServiceImpl implements AnalyticalReportsService {

	private static final Logger LOGGER = LogManager.getLogger(AnalyticalReportsServiceImpl.class);

	@Autowired
	private AnalyticalReportsDao analyticalReportsDao;

	@Autowired
	private GCStorageUtil gcStorageUtil;

	@Override
	public List<AnalyticalReportGroup> getReportGroups() throws ServiceExecutionException {
		LOGGER.debug("getReportGroups called");
		List<AnalyticalReportGroup> analyticalReportGroupList = analyticalReportsDao.getReportGroups();
		LOGGER.debug("getReportGroups list", analyticalReportGroupList);
		return analyticalReportGroupList;
	}

	@Override
	public ManageReports addReport(ManageReports manageReports, String platform) throws ServiceExecutionException {

		LOGGER.debug("addPointTracker called");

		manageReports = analyticalReportsDao.addReport(manageReports, platform);
		LOGGER.debug("addPointTracker called");
		return manageReports;

	}

	@Override
	public ManageReports updateReport(ManageReports manageReports, String platform) throws ServiceExecutionException {

		LOGGER.debug("updatePointTracker called");

		manageReports = analyticalReportsDao.updateReport(manageReports, platform);
		LOGGER.debug("updatePointTracker called");
		return manageReports;

	}

	@Override
	public ManageReports getReportById(int reportId, String platform) throws ServiceExecutionException {
		LOGGER.info("getReportById called");
		return analyticalReportsDao.getReportById(reportId, platform);
	}

	@Override
	public void deleteReport(int reportId, int modifiedBy, String platform) throws ServiceExecutionException {
		LOGGER.info("deleteReport called");
		analyticalReportsDao.deleteReport(reportId, modifiedBy, platform);
	}

	@Override
	public AnalyticalReportResponse getReportList(BaseFilter filter, String platform) throws ServiceExecutionException {
		LOGGER.debug("getPlans called");
		Map<String, Integer> mapper = analyticalReportsDao.getReportListCount(filter, platform);
		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<AnalyticalReport> manageReportList = total > 0 ? analyticalReportsDao.getReportList(filter, platform)
				: new ArrayList<>();
		AnalyticalReportResponse response = new AnalyticalReportResponse();
		response.setAnalyticalReportList(manageReportList);
		response.setNoOfElements(manageReportList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		LOGGER.debug("getReportList  count is {}", manageReportList);
		LOGGER.debug("getReportList completed successfully");
		return response;
	}

	@Override
	public AnalyticalReportResponse getReportsByReportGroupId(BaseFilter filter, String platform)
			throws ServiceExecutionException {
		LOGGER.debug("getPlans called");
		Map<String, Integer> mapper = analyticalReportsDao.getReportsByReportGroupIdCount(filter, platform);
		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<AnalyticalReport> manageReportList = total > 0
				? analyticalReportsDao.getReportsByReportGroupId(filter, platform)
				: new ArrayList<>();
		AnalyticalReportResponse response = new AnalyticalReportResponse();
		response.setAnalyticalReportList(manageReportList);
		response.setNoOfElements(manageReportList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		LOGGER.debug("getReportList  count is {}", manageReportList);
		LOGGER.debug("getReportList completed successfully");
		return response;
	}

	@Override
	public PreludeReportResponse getPreludeReport(PreludeReportFilter filter, String platform)
			throws ServiceExecutionException {
		LOGGER.debug("getPreludeReport called");
		Map<String, Integer> mapper = analyticalReportsDao.getPreludeReportCount(filter, platform);
		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<PreludeReport> preludeReportList = total > 0 ? analyticalReportsDao.getPreludeReport(filter, platform)
				: new ArrayList<>();
		PreludeReportResponse response = new PreludeReportResponse();
		response.setPreludeReportList(preludeReportList);
		response.setNoOfElements(preludeReportList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		LOGGER.debug("getPreludeReport  count is {}", preludeReportList);
		LOGGER.debug("getPreludeReport completed successfully");
		return response;
	}

	@Override
	public GCSignedUrlResponse getPreludeMediaSignedUrl(String filePath) throws ServiceExecutionException {
		LOGGER.info("ServiceImpl getPreludeMediaSignedUrl called");
		String signedUrl = "";
		if (filePath.contains(".jpg") || filePath.contains(".JPG") || filePath.contains(".jpeg")
				|| filePath.contains(".JPEG") || filePath.contains(".png") || filePath.contains(".PNG")) {
			signedUrl = gcStorageUtil.getMediaSignedUrlList(filePath, Constants.GCP_OBSERVATION_PHOTO_PATH).get(0);
		} else if (filePath.contains(".mp4") || filePath.contains(".MP4")) {
			String result = removeBeforeSecondLastChar(filePath, '/');
			signedUrl = gcStorageUtil.getMediaSignedUrlList(result, Constants.GCP_OBSERVATION_VIDEO_PATH).get(0);
		}
		LOGGER.info("signedUrl::" + signedUrl);
		GCSignedUrlResponse response = new GCSignedUrlResponse();
		response.setSignedUrl(signedUrl);

		return response;
	}

	public static String removeBeforeSecondLastChar(String originalString, char delimiter) {

		if (!originalString.contains("firebasestorage")) {

			int lastCharIndex = originalString.lastIndexOf(delimiter);
			int secondLastCharIndex = originalString.lastIndexOf(delimiter, lastCharIndex - 1);

			if (secondLastCharIndex != -1) {
				return originalString.substring(secondLastCharIndex + 1);
			} else {
				return originalString;
			}
		} else {
			return originalString;
		}
	}
}
