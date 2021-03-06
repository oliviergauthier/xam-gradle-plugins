package com.betomorrow.xamarin.android.manifest

import groovy.xml.Namespace
import groovy.xml.XmlUtil

class DefaultAndroidManifestWriter implements AndroidManifestWriter {

    void write(AndroidManifest manifest, String destination) {

        println "Update Android Manifest, set version=${manifest.versionName}, versionCode=${manifest.versionCode}, " +
                "package=${manifest.packageName}"


        def ns = new Namespace("http://schemas.android.com/apk/res/android", "android")

        XmlParser parser = new XmlParser()
        def content = parser.parse(destination)

        if (!isNullOrEmpty(manifest.packageName)) {
            content.attributes()['package'] = manifest.packageName
        }

        if (!isNullOrEmpty(manifest.versionName)) {
            content.attributes()[ns.versionName] = manifest.versionName
        }

        if (!isNullOrEmpty(manifest.versionCode)) {
            content.attributes()[ns.versionCode] =manifest. versionCode
        }

        new FileOutputStream(destination).withStream { out ->
            XmlUtil.serialize(content, out)
        }
    }

    private static boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0
    }


}