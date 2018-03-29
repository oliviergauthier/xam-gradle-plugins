package com.betomorrow.utils

class StringUtils {

    static boolean isNullOrWhiteSpace(String str) {
        return str == null || str.trim().isEmpty()
    }

}
