package com.betomorrow.android.tools.manifest

import groovy.xml.Namespace

class DefaultAndroidManifestReader implements AndroidManifestReader {

    public AndroidManifest read(String source) {

        AndroidManifest manifest = new AndroidManifest();
        def ns = new Namespace("http://schemas.android.com/apk/res/android", "android")

        XmlParser parser = new XmlParser()
        def content = parser.parse(source);

        manifest.packageName = content.attributes()['package'];
        manifest.versionName = content.attributes()[ns.versionName];
        manifest.versionCode = content.attributes()[ns.versionCode];

        return manifest;
    }
}
