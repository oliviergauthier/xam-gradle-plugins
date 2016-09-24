package com.betomorrow.android.tools.manifest

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class DefaultAndroidManifestWriterTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private File copy;
    private DefaultAndroidManifestWriter manifestWriter
    private DefaultAndroidManifestReader manifestReader

    @Before
    public void setUp() {
        copy = testFolder.newFile("copy.manifest")
        FileUtils.copyResource('AndroidManifest.xml', copy)
        manifestWriter = new DefaultAndroidManifestWriter();
        manifestReader = new DefaultAndroidManifestReader();
    }

    @Test
    public void testWriteUpdatesAttributes() {
        // Given
        AndroidManifest manifest = new AndroidManifest(
                versionCode: "14",
                versionName: "1.1.0",
                packageName: "another.package.name");

        // When
        manifestWriter.write(manifest, copy.path);

        // Then
        def saved = manifestReader.read(copy.path);
        assert saved == manifest;
    }

}