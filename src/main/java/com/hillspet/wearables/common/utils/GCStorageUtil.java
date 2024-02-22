package com.hillspet.wearables.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GCStorageUtil {

	@Autowired
	private GCPClientUtil gcpClientUtil;

	public List<String> getMediaSignedUrlList(String mediaPathCSV, String mediaDir) {	
		List<String> mediaPathList = new ArrayList<>();
		if (!StringUtils.isEmpty(mediaPathCSV)) {
			String[] mediaPathCSVValues = mediaPathCSV.split(",");
			for (String mediaPathValue : mediaPathCSVValues) {
				if (StringUtils.isNotEmpty(mediaPathValue)) {
					if (!mediaPathValue.contains("firebasestorage")) {
						String mediaSignedUrl = gcpClientUtil.getDownloaFiledUrl(mediaPathValue, mediaDir);
						mediaPathList.add(mediaSignedUrl);
					} else if (StringUtils.equals(mediaPathValue, "##")) { // for video that do not have thumbnails send
																			// empty string
						mediaPathList.add(StringUtils.EMPTY);
					} else {
						mediaPathList.add(mediaPathValue);
					}
				}
			}
		}
		return mediaPathList;
	}

	public String getSignedMediaUrl(String mediaName, String mediaDir) {
		if (StringUtils.isNotEmpty(mediaName)) {
			if (!mediaName.contains("firebasestorage")) {
				return gcpClientUtil.getDownloaFiledUrl(mediaName, mediaDir);
			} else if (StringUtils.equals(mediaName, "##")) { // for video that do not have thumbnails send empty string
				return StringUtils.EMPTY;
			} else {
				return mediaName;
			}
		}
		return mediaName;
	}

}
