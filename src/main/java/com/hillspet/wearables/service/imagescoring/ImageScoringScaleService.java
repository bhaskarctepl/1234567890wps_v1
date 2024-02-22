package com.hillspet.wearables.service.imagescoring;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.ImageScoringScale;
import com.hillspet.wearables.dto.ImageScoringScaleRequest;
import com.hillspet.wearables.dto.filter.ImageScaleFilter;
import com.hillspet.wearables.response.ImageScoringScaleListResponse;

@Service
public interface ImageScoringScaleService {

	void addImageScoringScale(ImageScoringScaleRequest imageScoringScaleRequest) throws ServiceExecutionException;

	void updateImageScoringScale(ImageScoringScaleRequest imageScoringScaleRequest) throws ServiceExecutionException;

	ImageScoringScaleListResponse getImageScoringScales(ImageScaleFilter filter) throws ServiceExecutionException;

	ImageScoringScale getImageScoringScaleById(int imageScoringScaleId) throws ServiceExecutionException;

	void deleteImageScoringScale(int imageScoringScaleId, int modifiedBy) throws ServiceExecutionException;

	List<ImageScoringScale> getImageScoringScaleList() throws ServiceExecutionException;

}