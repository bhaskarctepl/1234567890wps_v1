/**
 * 
 */
package com.hillspet.wearables.concurrent;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.hillspet.wearables.common.constants.Constants;

/**
 * @author radepu
 * Date: Nov 22, 2024
 */
public class QuestionnaireThreadPoolExecutor {

	private QuestionnaireThreadPoolExecutor() {

	}

	private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(Constants.APP_INDEX_ONE,
			Constants.APP_INDEX_FIVE, 5000, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(),
			new ThreadPoolExecutor.CallerRunsPolicy());

	public static ThreadPoolExecutor getExportThreadExecutor() {
		return threadPoolExecutor;
	}
}
