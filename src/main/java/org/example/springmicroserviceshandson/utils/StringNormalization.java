package org.example.springmicroserviceshandson.utils;

public class StringNormalization {

    public static String normalize(String str) {
        return str.replaceAll("[^a-zA-Z0-9]", "");
    }

}
