package com.betomorrow.msbuild.tools.android.manifest

class FakeAndroidManifestWriter implements AndroidManifestWriter {
    @Override
    void write(AndroidManifest manifest, String destination) {
        println "Update Android Manifest, set version=${manifest.versionName}, versionCode=${manifest.versionCode}, " +
                "package=${manifest.packageName} to ${destination}"
    }
}
