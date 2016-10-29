package com.betomorrow.gradle.library.extensions

class XamarinLibraryExtension {

    private static String DEFAULT_CONFIGURATION = 'Release'

    def String solution;
    def String configuration;

    public String getConfiguration() {
        if (configuration != null) {
            return configuration
        }
        return DEFAULT_CONFIGURATION
    }

}
