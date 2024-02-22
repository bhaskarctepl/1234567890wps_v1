package com.hillspet.wearables.dao.imagescoring;

import java.util.List;
import java.util.Map;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.ImageScoringScale;
import com.hillspet.wearables.dto.ImageScoringScaleRequest;
import com.hillspet.wearables.dto.filter.ImageScaleFilter;

public interface ImageScoringScaleDao {

	void addImageScoringScale(ImageScoringScaleRequest imageScoringScaleRequest) throws ServiceExecutionException;

	void updateImageScoringScale(ImageScoringScaleRequest imageScoringScaleRequest) throws ServiceExecutionException;

	Map<String, Integer> getImageScoringScaleCount(ImageScaleFilter filter) throws ServiceExecutionException;

	List<ImageScoringScale> getImageScoringScales(ImageScaleFilter filter) throws ServiceExecutionException;

	ImageScoringScale getImageScoringScaleById(int imageScoringScaleId) throws ServiceExecutionException;

	void deleteImageScoringScale(int imageScoringScaleId, int modifiedBy) throws ServiceExecutionException;

	List<ImageScoringScale> getImageScoringScaleList() throws ServiceExecutionException;

}
