package com.betomorrow.gradle.library.extensions

class XamarinLibraryExtension {

    private static String DEFAULT_CONFIGURATION = 'Release'

    String solution
    String configuration
    boolean dryRun

    String getConfiguration() {
        if (configuration != null) {
            return configuration
        }
        return DEFAULT_CONFIGURATION
    }

}
