package com.hillspet.wearables.common.constants;

public enum WearablesMessageKey implements MessageKeyInterface {

	SERVICE_RESP_INVALID("wearables.service.response.invalid"),
	INTERNAL_SERVER_ERROR("wearables.internal.server.error"),
	WEARABLES_WEB_APP_EXCEPTION("wearables.service.web.application.unexpected"),
	INVALID_JSON_INPUT("wearables.service.request.json.invalid"),
	READ_TIME_OUT("integration.service.read.timeout"),
	CONNECTION_TIME_OUT("integration.service.connection.timeout"),
	
	ORDER_NUMBER_REQUIRED("service.user.orderNumber.required"),
	USER_NAME_REQUIRED("service.user.userName.required"),
	PASSWORD_REQUIRED("service.customer.password.required"),
	NEW_PASSWORD_REQUIRED("service.customer.newPassword.required"),
	CONFIRM_PASSWORD_REQUIRED("service.customer.confirmPassword.required"),
	EMAIL_REQUIRED("service.user.email.required"),
	MOBILE_NUMBER_REQUIRED("service.customer.phoneNumber.required"),
	USER_ID_REQUIRED("service.customer.userId.required"),
	ADDRESS_REQUIRED("service.address.required"),
	
	DEVICE_NUMBER_REQUIRED("service.asset.deviceNumber.required"),
	FIRMWARE_VERSION_NUMBER_REQUIRED("service.asset.firmwareVersionNumber.required"),
	FIRMWARE_VERSION_ALREADY_EXISTS("service.asset.invalid.firmwareVersionNumber"),
	DEVICE_ALREADY_EXISTS("service.asset.invalid.deviceNumber"),
	USER_NAME_ALREADY_EXISTS("service.user.invalid.userName"),
	EMAIL_ALREADY_EXISTS("service.user.invalid.email"),
	TRACKER_NAME_ALREADY_EXISTS("service.pointTracker.invalid.trackerName"),
	PLAN_NAME_ALREADY_EXISTS("service.plan.invalid.planName"),
	PLAN_MAPPED_TO_STUDY_ACTIVE("service.plan.already.mapped.to.study.in.active"),
	ROLE_ALREADY_EXISTS("service.role.invalid.roleName"),
	STUDY_NAME_ALREADY_EXISTS("service.study.invalid.studyName"),
	STUDY_PUSH_NOTIFICATION_NAME_ALREADY_EXISTS("service.study.invalid.studyPushNotifName"),
	STUY_PUSH_NOTIFICATION_CANNOT_INACTIVE("service.studyPushNotification.cannot.inactive"),
	STUY_PUSH_NOTIFICATION_CANNOT_DELETE("service.studyPushNotification.cannot.delete"),
	PLAN_ALREADY_MAPPED_TO_STUDY("service.plan.already.mapped.to.study"),
	PLAN_ALREADY_MAPPED_TO_PET("service.plan.already.mapped.to.pet"),
	PET_PARENT_EMAIL_ALREADY_EXISTS("service.pet.parent.email.already.exist"),
	PET_PARENT_SECONDARY_EMAIL_ALREADY_EXISTS("service.pet.parent.secondary.email.already.exist"),
	SUPPORT_TICKET_ALREADY_CLOSED("service.customer.support.ticket.already.closed"),
	FAVOURITE_ALREADY_EXISTS("service.user.favourite.already.exist"),
	FAVOURITE_DOES_NOT_EXISTS("service.user.favourite.does.not.exist"),
	INVALID_PLAN_STUDY_ASSOCAITION("service.plan.study.association.does.not.exists"),
	QUESTIONNAIRE_NAME_ALREADY_EXISTS("service.questionnaire.name.already.exists"),
	QUESTIONNAIRE_INSTRUCTION_ALREADY_EXISTS("service.questionnaire.instruction.already.exists"),
	QUESTIONNAIRE_INSTRUCTION_ID_INVALID("service.questionnaire.instruction.id.invalid"),
	QUESTIONNAIRE_QUESTION_ALREADY_EXISTS("service.questionnaire.question.already.exists"),
	QUESTIONNAIRE_QUESTION_ID_INVALID("service.questionnaire.question.id.invalid"),
	PET_NOTE_ALREADY_EXISTS("service.pet.note.already.exist"),
	INVALID_STUDY_QUESTIONNAIRE_ASSOCAITION("service.questionnaire.study.association.does.not.exists"),
	QUESTIONNAIRE_ALREADY_MAPPED_TO_STUDY("service.questionnaire.already.mapped.to.study"),
	INVALID_STUDY_PUSH_NOTIFICATION_ASSOCAITION("service.pushnotification.study.association.does.not.exists"),
	PUSH_NOTIFICATION_ALREADY_MAPPED_TO_STUDY("service.pushnotification.already.mapped.to.study"),
	STUDY_ALREADY_ASSIGNED_TO_PET("service.study.already.mapped.to.pet"),
	PET_PARENT_ALREADY_EXISTS("service.pet.invalid.petParent"),
	DEVICE_ALREADY_MAPPED("service.pet.invalid.deviceNumber"),
	DEVICE_TYPES_SHOULD_BE_SAME("service.asset.shouldBe.deviceType"),
	DEVICE_MODELS_SHOULD_BE_SAME("service.asset.shouldBe.deviceModel"),
	ALREADY_BEING_REFERENCED("service.entity.cannot.delete"),
	PET_CANNOT_DELETE("service.pet.cannot.delete"),
	STUDY_CANNOT_DELETE_ACTIVE("service.study.cannot.delete"),
	DEVICE_MODEL_ALREADY_EXISTS("service.asset.invalid.deviceModel"),
	DEVICE_MFG_SERIAL_NUMBER_ALREADY_EXISTS("service.asset.invalid.ManufacturerSerialNumber"),
	DEVICE_MFG_MFG_MAC_ADDR_ALREADY_EXISTS("service.asset.invalid.ManufacturerMACAdders"),
	DEVICE_WIFI_MAC_ADDR_ALREADY_EXISTS("service.asset.invalid.wifiMacAddress"),
	DEVICE_CANNOT_DELETE("service.asset.cannot.delete"),
	FIRMWARE_CANNOT_DELETE("service.asset.firmware.cannot.delete"),
	FIRMWARE_VERSION_REFERENCED("service.asset.firmware.cannot.edit"),
	REPORT_NAME_ALREADY_EXISTS("service.analyticalReports.invalid.reportName"),
	STUDY_CAMPAIGN_EXISTS("service.pointTracker.invalid.study.campaign"),
	DEVICE_SHIPMENT_ALREADY_EXISTS("service.shipment.already.exists"),
	DEVICE_SHIPMENT_TRACKING_NUMBER_ALREADY_EXISTS("service.shipment.tracking.number.already.exists"),
	QUESTIONNAIRE_CATEGORY_ALREADY_EXISTS("service.question,category.already.exists"),
	QUESTIONNAIRE_CATEGORY_ALREADY_MAPPED("service.question,category.already.mapped"),
	STUDY_DIET_ALREADY_MAPPED_CANNOT_DELETE("service.study.diet.already.mappped"),
	
	
	INVALID_ADDRESS("service.validate.address.invalid"),	
	IMAGE_SCORING_SCALE_ALREADY_EXISTS("service.image.scoring.invalid.imageScaleName"),
	
	ROLE_CANNOT_INACTIVE("service.role.cannot.inactive"),
	
	ROLE_CANNOT_DELETE("service.role.cannot.delete"),
	USER_CANNOT_DELETE("service.user.cannot.delete"),
	ROLE_CANNOT_DELETE_ACTIVE("service.role.cannot.delete.active"),
	
	INVALID_EMAIL("service.customer.invalid.email"),
	INVALID_PASSWORD("service.customer.invalid.password"),
	INVALID_PASSWORD_LENGTH("service.customer.invalid.password.length"),
	INVALID_NEW_PASSWORD("service.customer.invalid.newPassword"),
	INVALID_NEW_PASSWORD_LENGTH("service.customer.invalid.newPassword.length"),
	INVALID_CONFIRM_PASSWORD("service.customer.invalid.confirmPassword"),
	INVALID_CONFIRM_PASSWORD_LENGTH("service.customer.invalid.confirmPassword.length"),
	INVALID_PASSWORD_NEW_PASSWORD("service.customer.old.new.password.invalid"),
	INVALID_NEW_CONFIRM_PASSWORD("service.customer.new.confirm.password.invalid"),
	INCORRECT_PASSWORD("service.customer.incorrect.password"),
	INCORRECT_NEW_PASSWORD("service.customer.incorrect.newPassword"),
	
	
	USERNAME_NOT_FOUND("service.customer.email.notFound"),
	
	APP_ID_NOT_FOUND("request.header.WEARABLES_APP_ID.notFound"),
	APP_ID_NOT_VALID("request.header.WEARABLES_APP_ID.invalid"),
	APP_ID_BLANK("request.header.WEARABLES_APP_ID.required"),
	APP_ENV_NOT_VALID("request.header.WEARABLES_APP_ENV.invalid"),
	APP_ENV_BLANK("request.header.WEARABLES_APP_ENV.required"),
	CONTENT_TYPE_INVALID("request.header.Content-Type.invalid"),
	APP_VERSION_INVALID("request.header.App_Version.invalid"),
	APP_VERSION_INVALID_LENGTH("request.header.APP_VERSION.length"),
	APP_VERSION_BLANK("request.header.App_Version.required"),
	USERID_INVALID_LENGTH("request.header.WEARABLES_USR_ID.length"),
	USERID_INVALID("request.header.WEARABLES_USR_ID.invalid"),
	USR_ID_REQUIRED("request.header.WEARABLES_USR_ID.required"),
	AUTHENTICATION_FAILED("request.service.authentication.failed"),
	AUTHORIZATION_FAILED("request.service.authorization.failed"),
	WEARABLES_VAL_ERROR("wearables.conditional.validation.failure"),
	CACHE_REGION_REQUIRED("cache.region.required"),
	CACHE_REGION_INVALID("cache.region.invalid"),
	CACHE_REGION_KEY_INVALID("cache.region.key.invalid"),

	EXEC_SVC_INPROGRESS("executor.service.inprogress"),
	
	STUDY_FEEDING_SCHEDULE_ALREADY_EXISTS("service.study.invalid.feedingschedule"),
	STUDY_UPDATE_STATUS_DRAFT("service.study.status.update.invalid"),
	STUDY_UPDATE_STATUS_SCHEDULED("service.study.status.update.invalid"),
	STUDY_UPDATE_STATUS_INPROGRESS("service.study.status.update.invalid"),
	STUDY_UPDATE_STATUS_FINALIZE_GROUPS("service.study.status.update.invalid"),
	STUDY_UPDATE_MANDATORY_CHECK_FAIL("service.study.status.update.invalid"),
	STUDY_MOVE_TO_NEXT_PHASE_NO_STUDY_NEXT_PHASE("service.study.movetonextphase.no.study.next.phase"),
	STUDY_MOVE_TO_NEXT_PHASE_NO_PET_NEXT_PHASE("service.study.movetonextphase.no.pet.next.phase"),
	STUDY_MOVE_TO_NEXT_PHASE_PET_INVALID_MOVE_DATE("service.study.movetonextphase.pet.invalid.move.date"),
	STUDY_MAX_PETS_IN_TREATMENT_GROUP_VALIDATION_FAILED("service.study.max.pets.in.treatment.group.validation.falied"),
	STUDY_FEEDING_SCHEDULE_VALIDATION_FAILED("service.study.feeding.schedule.validation.failed"),
	STUDY_REMOVE_PET_INVALID_DATE("service.study.removepet.date.validation.falied"),
	STUDY_REMOVE_PET_INVALID_PHASE_DAY("service.study.removepet.phaseday.validation.falied"),
	STUDY_PET_CANNOT_ADD_IN_CURRENT_OR_PREVIOUS_PHASES("service.study.addpet.phase.validation.falied"),
	;
	

	private String value;

	private WearablesMessageKey(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}
}
