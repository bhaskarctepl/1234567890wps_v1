package com.hillspet.wearables.common.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

public class PushNotificationUtil {
	private static final Logger LOGGER = LogManager.getLogger(PushNotificationUtil.class);

	private static final String FIREBASE_SA_JSON_STRING = System.getenv("FIREBASE_SA_JSON_STRING");

	private static FirebaseMessaging firebaseMessaging;

	public static FirebaseMessaging getFirebaseMessaging() throws IOException {
		if (firebaseMessaging == null) {
			String FIREBASE_SA_JSON_PLAIN_TEXT = new String(Base64.getDecoder().decode(FIREBASE_SA_JSON_STRING));
			InputStream serviceAccount = new ByteArrayInputStream(FIREBASE_SA_JSON_PLAIN_TEXT.getBytes());

			GoogleCredentials googleCredentials = GoogleCredentials.fromStream(serviceAccount);
			FirebaseOptions firebaseOptions = FirebaseOptions.builder().setCredentials(googleCredentials).build();
			FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "WearablesPortal" + Math.random());
			serviceAccount.close();
			firebaseMessaging = FirebaseMessaging.getInstance(app);
		}
		return firebaseMessaging;
	}

	public static Boolean sendPushNotification(String subject, String content, String fcmToken) throws IOException {
		Map<String, String> fireMap = new HashMap<>();
		try {
			LOGGER.info("fcmToken is {}",fcmToken);
			Notification notification = Notification.builder().setTitle(subject).setBody(content).build();
			Message message = Message.builder().setToken(fcmToken).setNotification(notification).putAllData(fireMap)
					.build();
			getFirebaseMessaging().send(message);
		} catch (FirebaseMessagingException e) {
			LOGGER.error("failed fcmToken is {}",fcmToken);
			LOGGER.error("Error while sending push notification ", e);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
}
