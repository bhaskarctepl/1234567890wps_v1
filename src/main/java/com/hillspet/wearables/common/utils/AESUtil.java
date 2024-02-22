package com.hillspet.wearables.common.utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AESUtil {

	private static final Logger LOGGER = LogManager.getLogger(AESUtil.class);

	private final static int KEY_LENGTH = 128;
	private final static int ITERATION_COUNT = 65536;
	private final static String TRANSFORMATION = "AES/CBC/PKCS5Padding";
	private final static String SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA256";
	private final static String ALGORITHM = "AES";

	private final static String password = System.getenv("AES_PASSWORD");
	private final static String salt = System.getenv("AES_SALT");
	private final static String IV = System.getenv("AES_IV");
	
	public static String encrypt(String plainText) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		try {
			cipher.init(Cipher.ENCRYPT_MODE, getKeyFromPassword(password, salt), generateIv());
		} catch (InvalidKeyException | InvalidAlgorithmParameterException | NoSuchAlgorithmException
				| InvalidKeySpecException e) {
			LOGGER.error("Error while encryption in AESUtil {}", e);
		}
		return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
	}

	public static String decrypt(String cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		try {
			cipher.init(Cipher.DECRYPT_MODE, getKeyFromPassword(password, salt), generateIv());
		} catch (InvalidKeyException | InvalidAlgorithmParameterException | NoSuchAlgorithmException
				| InvalidKeySpecException e) {
			LOGGER.error("Error while decrypt in AESUtil {}", e);
		}
		return new String(cipher.doFinal(Base64.getDecoder().decode(cipherText)));
	}
	
	private static SecretKey getKeyFromPassword(String password, String salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM);
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), ITERATION_COUNT, KEY_LENGTH);
		SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), ALGORITHM);
		return secret;
	}

	private static IvParameterSpec generateIv() {
		byte[] iv = new byte[IV.length()];
		for (int i = 0; i < IV.length(); i++) {
			iv[i] = (byte) IV.charAt(i);
		}
		return new IvParameterSpec(iv);
	}

}
