package com.lirugo.sus_jw.config.auth;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TelegramAuthValidator {

    private static final String SECRET_KEY = "WebAppData";
    private static final String HMAC_SHA_256 = "";
    private static Mac SHA_256_HMAC;

    @Value("${app.auth.tg-bot-token}")
    private String botToken;

    static {
        try {
            SHA_256_HMAC = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to initialize HMAC instance: " + e.getMessage(), e);
        }
    }

    public boolean verifyInitData(String telegramInitData) {
        var urlParams = parseUrlParams(telegramInitData);
        var hash = urlParams.remove("hash");  // Extract the hash and remove it from parameters

        // Sort parameters by key
        var dataCheckString = urlParams.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("\n"));

        // Generate the HMAC SHA-256 hash using the secret key and the concatenated dataCheckString
        var calculatedHash = Strings.EMPTY;
        try {
            calculatedHash = calculateHash(dataCheckString);
        } catch (Exception e) {
            log.warn("Error validation telegram user data with hash");
            return false;
        }

        // Compare the calculated hash with the provided hash
        return !calculatedHash.equals(Strings.EMPTY) && calculatedHash.equals(hash);
    }

    private Map<String, String> parseUrlParams(String telegramInitData) {
        Map<String, String> urlParams = new HashMap<>();
        String[] pairs = telegramInitData.split("&");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                urlParams.put(keyValue[0], URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8));
            }
        }

        return urlParams;
    }

    private String calculateHash(String dataCheckString) throws Exception {
        // Create HMAC with 'sha256' and the secret 'WebAppData'

        var secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        SHA_256_HMAC.init(secretKey);
        byte[] firstHmac = SHA_256_HMAC.doFinal(botToken.getBytes(StandardCharsets.UTF_8));

        // Step 2: Create the second HMAC using the result from the first HMAC as the key
        var secondKey = new SecretKeySpec(firstHmac, HMAC_SHA_256);
        SHA_256_HMAC.init(secondKey);

        // Step 3: Update the second HMAC with the dataCheckString
        byte[] secondHmac = SHA_256_HMAC.doFinal(dataCheckString.getBytes(StandardCharsets.UTF_8));

        // Step 4: Convert the final HMAC to hex
        return bytesToHex(secondHmac);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

}
