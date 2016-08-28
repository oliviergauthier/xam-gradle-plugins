package com.betomorrow.gradle.application.extensions

class XamarinApplicationExtension {

    private static String DEFAULT_CONFIGURATION = 'Release'

    def String appName;
    def String appVersion;
    def String storeVersion;
    def String packageName;
    def String configuration;
    def String solution;
    def boolean dryRun;


    public String getConfiguration() {
        if (configuration != null) {
            return configuration
        }
        return DEFAULT_CONFIGURATION
    }

}
