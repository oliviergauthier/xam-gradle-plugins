package com.betomorrow.android.tools.manifest

import org.junit.Before
import org.junit.Test

class DefaultAndroidManifestReaderTest {

    String SAMPLE_MANIFEST = ClassLoader.getSystemResource('AndroidManifest.xml').file;

    DefaultAndroidManifestReader reader;

    @Before
    public void setUp() {
        reader = new DefaultAndroidManifestReader();
    }

    @Test
    public void testReadShouldReturnsCompleteManifest() {
        AndroidManifest manifest = reader.read(SAMPLE_MANIFEST);

        assert manifest.packageName == "com.sample.crossapp"
        assert manifest.versionCode == "1"
        assert manifest.versionName == "1.0"
    }
}
