package com.hillspet.wearables.service.study;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;

/**
 * This is the service class for implementing load data from prelude.
 * 
 * @author sgorle
 * @since w1.0
 * @version w1.0
 * 
 */
public interface PreludeService {
	
	void loadPreludeData() throws ServiceExecutionException;

}
