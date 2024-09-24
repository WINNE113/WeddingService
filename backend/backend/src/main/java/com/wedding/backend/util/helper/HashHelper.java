package com.wedding.backend.util.helper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.UUID;

public class HashHelper {
    public static String generateEntityId() {
        // Generate a UUID (Universally Unique Identifier)
        UUID entityId = UUID.randomUUID();

        // Convert UUID to a string without hyphens
        String codeString = entityId.toString().replace("-", "");

        return codeString;
    }

    //Int ID
    public static int generateRandomNumbers() {
        Random rnd = new Random();
        return 100000 + rnd.nextInt(900000);
    }

    public static String hmacSHA512(final String key, final String data) {
        try {

            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }

    public static String hmacSHA256(String inputData, String key) {
        try {
            // Convert the key and input data to bytes
            byte[] keyByte = key.getBytes(StandardCharsets.UTF_8);
            byte[] messageBytes = inputData.getBytes(StandardCharsets.UTF_8);

            // Create an HMAC-SHA256 instance
            Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(keyByte, "HmacSHA256");

            // Initialize the Mac instance with the key
            hmacSHA256.init(secretKey);

            // Compute the HMAC hash
            byte[] hashmessage = hmacSHA256.doFinal(messageBytes);

            // Convert the byte array to a hex string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashmessage) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error while calculating HMAC-SHA256", e);
        }
    }
}
