package com.hillspet.wearables.common.constants;

/**
 * 
 * @author vvodyaram
 *
 */
public final class Constants {

	public static final String WEARABLES_BASE_PACKAGE = "com.hillspet.wearables";
	public static final String WEARABLES_DAO_PACKAGE = "com.hillspet.wearables.dao";
	public static final String WEARABLES_TRANSACTION_MANAGER = "wearablesTransactionManager";
	public static final String DATA_SOURCE = "dataSource";
	public static final String CONTEXT_PATH = "/wearables";
	public static final String APPLICATION_PATH = "/api";
	public static final String SWAGGER_JSON_PATH = "/api/swagger.json";
	public static final String WEARABLES_RESOURCES_PACKAGE = "com.hillspet.wearables.jaxrs.resource";

	public static final int DELETE_STATUS_FLAG = 0;

	public static final int ROLE_ID_FUNCTIONAL_ADMINISTRATOR = 1;
	public static final int ROLE_ID_INVENTORY_ADMINISTRATOR = 2;
	public static final int ROLE_ID_FULFILLMENT_MANAGER = 3;
	public static final int ROLE_ID_SHIPPING_RECEIVING_MANAGER = 4;
	public static final int ROLE_ID_CUSTOMER = 5;

	public static final int APP_INDEX_ZERO = 0;
	public static final int APP_INDEX_ONE = 1;
	public static final int APP_INDEX_TWO = 2;
	public static final int APP_INDEX_THREE = 3;
	public static final int APP_INDEX_FOUR = 4;
	public static final int APP_INDEX_FIVE = 5;
	public static final int APP_INDEX_SIX = 6;
	public static final int APP_INDEX_SEVEN = 7;
	public static final int APP_INDEX_EIGHT = 8;
	public static final int APP_INDEX_NINE = 9;
	public static final int APP_INDEX_TEN = 10;
	public static final int APP_INDEX_ELEVEN = 11;
	public static final int APP_INDEX_TWELVE = 12;
	public static final int APP_INDEX_THIRTEEN = 13;
	public static final int APP_INDEX_FOURTEEN = 14;
	public static final int APP_INDEX_FIFTEEN = 15;

	public static final int STATUS_INACTIVE_FLAG = 0;
	public static final int STATUS_ACTIVE_FLAG = 1;
	public static final int STATUS_RESET_FLAG = 2;

	public static final int NO_OF_CAPS_ALPHA = 1;
	public static final int NO_OF_DIGITS = 1;
	public static final int NO_OF_SPL_CHARS = 1;
	public static final int PASSWORD_MIN_LENGTH = 8;
	public static final int PASSWORD_MAX_LENGTH = 15;

	public static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
	public static final String NUM = "0123456789";
	public static final String SPL_CHARS = "!@#$&^";

	// Pattern regex Constants
	public static final String REGEX_CHECK_ONLY_DOT = "[.]";
	public static final String REGEX_PIPE_DELIMETER = "\\|";
	public static final String REGEX_HTML = "\\<.*?\\>";

	public static final String REGEX_EMAIL = "^[A-Za-z0-9+_.-]+@(.+)$";
	public static final String REGEX_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$&^])[A-Za-z\\d!@#$&^]{8,}$";
	public static final String REGEX_MOBILE = "^[0-9]{10}$";
	public static final String REGEX_PINCODE = "^[0-9]{5}$";
	public static final String REGEX_ALPHANUM_DOT = "[a-zA-Z0-9.]+";
	public static final String REGEX_FEDEX_TRACKING_NO = "\\d{12}";

	public static final String NEW_LINE = System.lineSeparator();
	public static final String PAY_LOAD = "Payload:";

	// Constants for ReloadbaleResourceBundle
	public static final Integer RESOURCE_BUNDLE_CACHE_INTERVAL_SEC = 3600;

	public static final String APP_RESOURCE_BUNDLE_NAME = "classpath:messages";
	public static final String CORE_RESOURCE_BUNDLE_NAME = "classpath:messages-core";

	public static final String MEDIA_TYPE_APPLICATION_JSON_INITIAL_VERSION1 = "application/vnd.wearables.v1.0+json";

	public static final String APP_RESOURCE_PROPERTY_SOURCE = "classpath:appinfo.properties";
	public static final String APP_RESOURCE_CONFIG_PROPERTY_NAME = "info";
	public static final String API_CONFIG = "apiConfig";
	public static final String INTEGRATION_SVCS_CONFIG = "integrationServicesConfig";
	public static final String MOCKDATA_CONFIG = "mockdataconfig";
	public static final String COMMON_CONFIG = "Common";

	public static final String ACTIVITY_DATA_CONFIG = "activities";
	public static final String TIMEOUT_CONFIG_DATA_CONFIG = "network";
	public static final String RETRY_CONFIG_DATA_CONFIG = "retry";
	public static final String SFTP_CONFIG_ROOT = "sftpclient";

	public static final String DEFAULT_CHARSET = "UTF-8";
	public static final String OPERATION_NAME_AUTHENTICATE = "authenticate";

	public static final Integer MASKING_LENGTH = 15;
	public static final String ROUTE_ID_DEFAULT_VALUE = "00";

	public static final String USER_ID_UNAUTH = "UNAUTH";

	public static final String GETUSERDETAILS_SEARCH_ON = "UserId";
	public static final String APP_CONFIG_MAP_ORDERTRACKING_DATE_FORMAT = "yyyy-MM-dd HH:mm a";
	public static final String ZONE_DATE_TIME_OFFSET_WITH_ZERO = "yyyy-MM-dd'T'HH:mm:ssxxx";
	public static final String ZONE_DATE_TIME_OFFSET_WITH_Z = "dd/MM/yyyy 'at' hh:mma z";
	public static final String EASTERN_TIME_ZONE_LOCALE = "America/New_York";

	public static final String ERROR_INTEGRATION_SERVICE_CONFIG = "IntegrationService enum is not configured for operation {} which is present in application.yml";
	public static final String ERROR_INTEGRATION_APP_CONFIG = "IntegrationServiceType enum is not configured for app {} which is present in application.yml";
	public static final String ERROR_API_OPERATION_CONFIG = "ApiOperationDefinition enum is not configured for operation {} which is present in application.yml";
	public static final String ERROR_INTEGRATION_SERVICE_URL_CONFIG = "Error in constructing url ";

	public static final String WEBSITE_SITE_TITLE = "Wearables Clinical Portal";
	public static final String WEBSITE_SITE_TITLE_AP = "Wearables Analytical Platform";

	public static final String FORGOT_PASSWORD_MAIL_SUBJECT = "Wearables Clinical Portal | Reset Password Request";
	public static final String FORGOT_PASSWORD_MAIL_SUBJECT_AP = "Wearables Analytical Platform | Reset Password Request";
	
	public static final String FORGOT_PASSWORD_MAIL_BODY = "<p>Dear {0}, </p><p> We have received a request to reset your password for {1}.  To reset your password, please use below temporary password. </p><p style=\"font-size:10 px;font-weight:600;background-color:#3e3 e3e;\">{2}</p><p>Thanks, <br/>  {3}</p>";
	public static final String FORGOT_PASSWORD_MESSAGE = "Thank you for contacting us. We have sent a temporary password to your registered email id";
	public static final String UPDATE_PASSWORD_MESSAGE = "Your Password has been updated successfully.";
	
	public static final String UPDATE_PASSWORD_MAIL_SUBJECT = "Wearables Clinical Portal | Your password has been updated";
	public static final String UPDATE_PASSWORD_MAIL_SUBJECT_AP = "Wearables Analytical Platform | Your password has been updated";
	
	public static final String UPDATE_PASSWORD_MAIL_BODY = "<p>Hi {0},</p><p>This is a confirmation that you changed your password for your {1} account.</p><p>If you made this change, you are all set. If you did not make this change, please let us know.</p><p>Thanks, <br/>  {2}</p>";

	public static final String CREATE_USER_MAIL_SUBJECT = "Wearables Clinical Portal | New Account Created";
	// public static final String CREATE_USER_MAIL_BODY = "<p>Dear {0}, </p><p> Your
	// account is created with User Id {1} for the {2} with below password. </p><p
	// style=\"font-size:10 px;font-weight:600;background-color:#3e3
	// e3e;\">{3}</p><p>Thanks, <br/> {4}</p>";
	public static final String CREATE_USER_MAIL_BODY = "<p>Hi {0}!</p>Welcome to the {2}.<br>A new account has been created for you with the below credentials:<br>· Username&nbsp;: {1}<br>· Password&nbsp;&nbsp;: {3}<br><br> Thanks,<br>{4} Support";

	public static final String UPDATED_USER_MAIL_SUBJECT = "Wearables Clinical Portal | Your account details have been updated";
	public static final String UPDATED_USER_MAIL_BODY = "<p>Hi {0},</p>This is to confirm that the following information has been updated :</p> <br> {2} <br><p>Kindly contact your administrator if the above mentioned changes are incorrect. </p><p> Thanks,</p>{4}";

	/**
	 * The below constants cannot be defined in application.yml as they are needed
	 * too early in spring lifecycle
	 */
	public static final String RESOURCE_URL_PLACEHOLDER_REGEX = "(.*?)(\\{.*\\})(.*)";

	public static final String MATCH_ANYTHING_REGEX = "(.*)";

	public static final String CONNECTION_TIMEOUT = "CONNECTION_TIMEOUT";

	public static final String READ_TIMEOUT = "READ_TIMEOUT";

	public static final String SOAP_CONTEXT_PACKAGE = "SOAP_CONTEXT_PACKAGE";
	public static final String SOAP_HEADERS = "SOAP_HEADERS";
	public static final String REST_HEADERS = "REST_HEADERS";
	public static final String APP_VERSION = "APP_VERSION";

	public static final String REQUEST_MAPPING = "request";
	public static final String RESPONSE_MAPPING = "response";
	public static final String SOAP_BASEPATH_KEY = "SOAP";

	public static final Integer NUMBER_ZERO = 0;
	public static final Integer NUMBER_ONE = 1;
	public static final Integer NUMBER_TWO = 2;
	public static final Integer NUMBER_THREE = 3;
	public static final Integer NUMBER_FOUR = 4;
	public static final Integer NUMBER_FIVE = 5;
	public static final Integer NUMBER_NINE = 9;
	public static final Integer NUMBER_TWELVE = 12;

	// RC2 related constants
	public static final String BASIC_AUTH_HEADER_VALUE = "Basic ";

	// Constants for Cache regions
	public static final String CACHE_FILE = "ehcache.xml";
	public static final String CACHE_24_HRS = "24HrsCache";
	public static final String CACHE_KEY_GENERATOR = "cacheKeyGenerator";
	public static final String CACHE_MANAGER = "cacheManager";
	public static final String CACHE_MANAGER_FACTORY = "cacheManagerFactory";

	// Quartz configuration information
	public static final String PROPERTIES_FILE = "config.properties";

	public static final String AUTHORIZATION = "Authorization";

	// Constants for Global and theater specific languages

	public static final String GLOBAL_LANG_ID = "global";

	public static final String WEARABLES_SUPPORTED_LOCALES = "core.locales";

	public static final String REPLICATION_CONFIG_DATA_ROOT = "replication";

	public static final String APP_CONFIG_MAP_KEY_FOR_CUSTOMER_ID_LENGTH = "customerIdMaxLength";

	public static final String BUILDING_ID_LENGTH = "buildingIdLength";

	public static final String DISTRICT_ID_MAX_LENGTH = "districtIdMaxLength";

	public static final String DISTRICT_ID_MIN_LENGTH = "districtIdMinLength";

	public static final Integer BUILDING_ID_MAX_LENGTH = 2;

	public static final String APP_NAME_ENV_PROP_NAME = "appName";

	// cypher provider constants
	public static final String ALGORITHM_AES = "AES";
	public static final String AES_PADDING = "AES/ECB/PKCS5Padding";
	public static final String ALGORITHM_RSA = "RSA";
	public static final String RSA_PADDING = "RSA/ECB/PKCS1Padding";

	public static final Integer ACTIVITY_RESULTS_BOX_TO_SCAN_MIN_LENGTH = 1;
	public static final Integer ACTIVITY_RESULTS_DOWNLOAD_BATCH_ID_MIN_LENGTH = 1;
	public static final Integer ACTIVITY_RESULTS_BOX_TO_SCAN_MAX_LENGTH = 99;

	public final static String HANDLER_SUFFIX = "Handler";
	public static final String PROCESSOR_SUFFIX = "Processor";

	public static final String ERROR_DETAIL_BEAN = "ErrorDetailBean";

	public static final int DEFAULT_MAX_RESULTS = 1000;

	public static final int MANDRILL_MAX_ATTEMPTS = 3;
	public static final int MANDRILL_BACKOFF_VALUE = 5000;
	public static final int MANDRILL_BACKOFF_MULTIPLIER = 2;

	public static final int CUSTOMER_TESTIMONIALS_COUNT = 3;

	public static final int APP_SOURCE_CODE_WEBSITE = 1;
	public static final int APP_SOURCE_CODE_ADMIN_PORTAL = 2;

	// Shipment company tracking url constants
	public static final String FEDEX_TRACKING_URL = "https://www.fedex.com/apps/fedextrack/index.html?tracknumbers={0}&cntry_code=us";
	public static final String UPS_TRACKING_URL = "https://www.ups.com/track?loc=en_US&tracknum={0}&requester=WT/trackdetails";
	public static final String USPS_TRACKING_URL = "https://tools.usps.com/go/TrackConfirmAction?tRef=fullpage&tLc=2&text28777=&tLabels={0},";
	public static final String DHL_TRACKING_URL = "https://www.dhl.com/us-en/home/tracking.html?tracking-id={0}";

	public static final String DEVICE_BUIK_UPLOAD_SHEET_NAME = "Device Details Bulk Upload";

	public static final String GCP_PETPHOTO_PATH = "PetPhoto";
	public static final String GCP_OBSERVATION_PHOTO_PATH = "ObservationPhoto";
	public static final String GCP_OBSERVATION_VIDEO_PATH = "ObservationVideo";
	public static final String GCP_OBSERVATION_VIDEO_THUMBNAIL_PATH = "ObservationVideoThumbnail";
	public static final String GCP_CUSTOMER_SUPPORT_PATH = "CustomerSupport";
	public static final String GCP_IMAGE_SCORING_PATH = "ImageScoring";
	public static final String GCP_QUESTIONNAIRE_QUESTION_IMAGE_PATH = "Questionnaire";
	
	public static final String GCP_QUESTIONNAIRE_ANSWER_IMAGE_PATH = "QuestionnaireAnswer/Images";
	public static final String GCP_QUESTIONNAIRE_ANSWER_VIDEO_PATH = "QuestionnaireAnswer/Videos";
	public static final String GCP_QUESTIONNAIRE_ANSWER_VIDEO_THUMBNAIL_PATH = "QuestionnaireAnswer/VideoThumbnail";

	public static final String GCP_SUPPORT_MATERIAL_FILE_PATH = "SupportMaterial";
	public static final String GCP_SUPPORT_MATERIAL_THUMBNAIL_PATH = "SupportMaterialThumbnail";
	
	// Google TimeZone API
	public static final String GCLOUD_GEO_LOCATION_API = "https://maps.googleapis.com/maps/api/geocode/json?address={0}&key={1}";
	public static final String GCLOUD_TIME_ZONE_API = "https://maps.googleapis.com/maps/api/timezone/json?location={0},{1}&timestamp={2}&key={3}";
	
	public static final String ADDRESS_STRING = "address";
	
	public static final String REQUEST_FROM_WEARABLES = "WP";
	public static final String REQUEST_FROM_ANALYTICAL = "AP";
}
