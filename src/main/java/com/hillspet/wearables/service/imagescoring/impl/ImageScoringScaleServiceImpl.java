package com.hillspet.wearables.service.imagescoring.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.imagescoring.ImageScoringScaleDao;
import com.hillspet.wearables.dto.ImageScoringScale;
import com.hillspet.wearables.dto.ImageScoringScaleRequest;
import com.hillspet.wearables.dto.filter.ImageScaleFilter;
import com.hillspet.wearables.response.ImageScoringScaleListResponse;
import com.hillspet.wearables.service.imagescoring.ImageScoringScaleService;

@Service
public class ImageScoringScaleServiceImpl implements ImageScoringScaleService {

	private static final Logger LOGGER = LogManager.getLogger(ImageScoringScaleServiceImpl.class);

	@Autowired
	private ImageScoringScaleDao imageScoringScaleDao;

	@Override
	public void addImageScoringScale(ImageScoringScaleRequest imageScoringScaleRequest)
			throws ServiceExecutionException {
		LOGGER.debug("addImageScoringScale called");
		imageScoringScaleDao.addImageScoringScale(imageScoringScaleRequest);
		LOGGER.debug("addImageScoringScale called");
	}

	@Override
	public void updateImageScoringScale(ImageScoringScaleRequest imageScoringScaleRequest)
			throws ServiceExecutionException {
		LOGGER.debug("updateImageScoringScale called");
		imageScoringScaleDao.updateImageScoringScale(imageScoringScaleRequest);
		LOGGER.debug("updateImageScoringScale completed successfully");
	}

	@Override
	public ImageScoringScaleListResponse getImageScoringScales(ImageScaleFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getImageScoringScales called");
		Map<String, Integer> mapper = imageScoringScaleDao.getImageScoringScaleCount(filter);
		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<ImageScoringScale> imageScoringScales = total > 0 ? imageScoringScaleDao.getImageScoringScales(filter)
				: new ArrayList<>();
		ImageScoringScaleListResponse response = new ImageScoringScaleListResponse();
		response.setImageScoringScales(imageScoringScales);
		response.setNoOfElements(imageScoringScales.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		LOGGER.debug("imageScoringScales count is {}", imageScoringScales);
		LOGGER.debug("getImageScoringScales completed successfully");
		return response;
	}

	@Override
	public ImageScoringScale getImageScoringScaleById(int imageScoringScaleId) throws ServiceExecutionException {
		LOGGER.info("getImageScoringScaleById called");
		ImageScoringScale imageScoringScale = imageScoringScaleDao.getImageScoringScaleById(imageScoringScaleId);
		LOGGER.debug("getImageScoringScaleById completed successfully");
		return imageScoringScale;
	}

	@Override
	public void deleteImageScoringScale(int imageScoringScaleId, int modifiedBy) throws ServiceExecutionException {
		LOGGER.debug("deleteImageScoringScale called");
		imageScoringScaleDao.deleteImageScoringScale(imageScoringScaleId, modifiedBy);
		LOGGER.debug("deleteImageScoringScale completed successfully");
	}
	
	@Override
	public List<ImageScoringScale> getImageScoringScaleList() throws ServiceExecutionException {
		LOGGER.debug("deleteImageScoringScale called");
		List<ImageScoringScale> imageScoringScales = imageScoringScaleDao.getImageScoringScaleList();
		LOGGER.debug("deleteImageScoringScale completed successfully");
		return imageScoringScales;
	}

}
