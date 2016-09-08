package com.betomorrow.android.tools

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class FakeAndroidManifestEditor implements AndroidManifestEditor {

    private Logger logger = LoggerFactory.getLogger(AndroidManifestEditor)

    private String source;

    String versionCode
    String versionName
    String packageName


    FakeAndroidManifestEditor(String source) {
        this.source = source;
    }

    @Override
    void write(String destination) {
        println "Update Android Manifest, from ${source} set version=${versionName}, versionCode=${versionCode}, " +
                "package=${packageName} to ${destination}"
    }
}
