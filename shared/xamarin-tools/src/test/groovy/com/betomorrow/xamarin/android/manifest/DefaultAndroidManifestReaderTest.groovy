package com.betomorrow.xamarin.android.manifest

import org.junit.Before
import org.junit.Test

class DefaultAndroidManifestReaderTest {

    String SAMPLE_MANIFEST = ClassLoader.getSystemResource('AndroidManifest.xml').file

    DefaultAndroidManifestReader reader

    @Before
     void setUp() {
        reader = new DefaultAndroidManifestReader()
    }

    @Test
     void testReadShouldReturnsCompleteManifest() {
        AndroidManifest manifest = reader.read(SAMPLE_MANIFEST)

        assert manifest.packageName == "com.sample.crossapp"
        assert manifest.versionCode == "1"
        assert manifest.versionName == "1.0"
    }
}
