package com.betomorrow.android.tools

import groovy.xml.Namespace
import groovy.xml.XmlUtil

class DefaultAndroidManifestEditor implements AndroidManifestEditor {

    private String source;

    String versionCode
    String versionName
    String packageName

    DefaultAndroidManifestEditor(String source) {
        this.source = source
    }

    void write(String destination) {

        def ns = new Namespace("http://schemas.android.com/apk/res/android", "android")

        XmlParser parser = new XmlParser()
        def content = parser.parse(source);

        if (!isNullOrEmpty(packageName)) {
            content.@package = packageName;
        }

        if (!isNullOrEmpty(versionName)) {
            content.attributes()[ns.versionName] = versionName;
        }

        if (!isNullOrEmpty(versionCode)) {
            content.attributes()[ns.versionCode] = versionCode;
        }


        String dst = destination == null ? source : destination
        new FileOutputStream(dst).withStream { out ->
            XmlUtil.serialize(content, out)
        }
    }

    private static boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }


}