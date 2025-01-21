package com.lirugo.sus_jw.util;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {
    private Utils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Map<String, String> parseAuthHeader(String authHeader) {
        return Arrays.stream(authHeader.split("&"))
                .map(part -> part.split("="))
                .filter(parts -> parts.length == 2)
                .collect(Collectors.toMap(parts -> parts[0], parts -> parts[1]));
    }
}
