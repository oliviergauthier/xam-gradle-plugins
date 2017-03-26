package com.betomorrow.xamarin.android.manifest

import groovy.xml.Namespace

import java.nio.file.Path

class DefaultAndroidManifestReader implements AndroidManifestReader {

    AndroidManifest read(Path source) {
      return read(source.toAbsolutePath().toString())
    }

    AndroidManifest read(String source) {

        AndroidManifest manifest = new AndroidManifest()
        def ns = new Namespace("http://schemas.android.com/apk/res/android", "android")

        XmlParser parser = new XmlParser()
        def content = parser.parse(source)

        manifest.packageName = content.attributes()['package']
        manifest.versionName = content.attributes()[ns.versionName]
        manifest.versionCode = content.attributes()[ns.versionCode]

        return manifest
    }
}
