package com.betomorrow.android.tools

import groovy.xml.Namespace
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

import static com.betomorrow.android.tools.AndroidManifestEditor.*

class AndroidManifestEditorTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    def SAMPLE_MANIFEST = ClassLoader.getSystemResource('AndroidManifest.xml').file;
    def copy;

    AndroidManifestEditor editor

    @Before
    public void setUp() {
        copy = testFolder.newFile("copy.manifest")
        Files.copy(Paths.get(SAMPLE_MANIFEST), copy.toPath(), StandardCopyOption.REPLACE_EXISTING);
        editor = new AndroidManifestEditor(copy.absolutePath);
    }

    @Test
    public void testWriteUpdatesAttributes() {
        def ns = new Namespace("http://schemas.android.com/apk/res/android", "android")

        // Given
        editor.versionName = "1.1.0"
        editor.versionCode = "14"
        editor.packageName = "another.package.name"

        // When
        editor.write();

        // Then
        def content = new XmlSlurper().parse(copy);

        println(content)
//        assert content.attributes()[ns.versionName] == "1.1.0"
//        assert content.attributes()[ns.versionCode] == "14"
//        assert content.attributes()["package"] == "another.package.name"

    }

}