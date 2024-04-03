package com.kif.cardgen.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ApiKeyUtil {

	public ApiKeyUtil() {
		// TODO Auto-generated constructor stub
	}
	public String calculateSHA256Hash(String input) {
		Date date = new Date();
		String seededKey = input + date.toString().substring(0, 16);

		try {
			// Create a MessageDigest object with the SHA-256 algorithm
			MessageDigest digest = MessageDigest.getInstance("SHA-256");

			// Convert the input string to bytes
			byte[] encodedHash = digest.digest(seededKey.getBytes(StandardCharsets.UTF_8));

			// Convert the byte array to a hexadecimal string
			StringBuilder hexString = new StringBuilder();
			for (byte b : encodedHash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}

			// Return the SHA-256 hash as a string
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;
	}
}