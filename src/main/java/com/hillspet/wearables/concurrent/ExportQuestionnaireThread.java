/**
 * 
 */
package com.hillspet.wearables.concurrent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hillspet.wearables.common.utils.BeanUtil;
import com.hillspet.wearables.dto.CustomUserDetails;

/**
 * @author radepu Date: Nov 22, 2024
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ExportQuestionnaireThread implements Runnable {

	private static final Logger LOGGER = LogManager.getLogger(ExportQuestionnaireThread.class);

	private ExportQuestionnaireUtil exportQuestionnaireUtil = BeanUtil.getBean(ExportQuestionnaireUtil.class);

	private String questionnaireIds;
	private int exportId;
	private CustomUserDetails userDetails;

	public ExportQuestionnaireThread(String questionnaireIds, int exportId, CustomUserDetails userDetails) {
		this.questionnaireIds = questionnaireIds;
		this.exportId = exportId;
		this.userDetails = userDetails;
	}

	@Override
	public void run() {
		try {
			LOGGER.info("Thread Initiating Export Questionnaire Started", questionnaireIds);
			boolean isProcessCompleted = exportQuestionnaireUtil.generateQuestionnaire(questionnaireIds, exportId,
					userDetails);
			LOGGER.info("Thread Export Questionnaire Completed - isProcessCompleted is ", isProcessCompleted);

		} catch (Exception e) {
			LOGGER.error("Error while generating excel", e);
		}
	}

}
