package com.betomorrow.msbuild.tools.descriptors.project

import com.betomorrow.msbuild.tools.Files.FileUtils
import org.junit.Test

import java.nio.file.Paths

class ProjectDescriptorTest {

    def SAMPLE_DROID = FileUtils.getResourcePath('Sample.Droid.csproj')
    def SAMPLE_IOS = FileUtils.getResourcePath('Sample.iOS.csproj')

    def androidProject = new ProjectDescriptor('Sample', SAMPLE_DROID)
    def iosProject = new ProjectDescriptor('Sample', SAMPLE_IOS)

    @Test
     void testIsAndroidReturnsTrueWithAndroidCSProj() {
        assert androidProject.isAndroidApplication()
    }

    @Test
     void testIsAndroidReturnsFalseWithIosCSProj() {
        assert !androidProject.isIosApplication()
    }

    @Test
     void testIsIPhoneReturnsFalseWithAndroidCSProj() {
        assert !iosProject.isAndroidApplication()
    }

    @Test
     void testIsIPhoneReturnsTrueWithIosCSProj() {
        assert iosProject.isIosApplication()
    }

    @Test
     void testGetAndroidManifestReturnsManifestValue() {
        assert 'Properties/AndroidManifest.xml' == androidProject.getAndroidManifest()
    }

    @Test
     void testGetAndroidManifestPathReturnsCompletePathOfManifest() {
        assert SAMPLE_DROID.parent.resolve("Properties/AndroidManifest.xml") == androidProject.getAndroidManifestPath()
    }

    @Test
     void testGetAssemblyNameReturnsNameOfAssembly() {
        assert 'Sample.Droid' == androidProject.getAssemblyName()
    }

    @Test
     void testGetOutputDirForDebugReturnsDebutPath() {
        assert SAMPLE_DROID.parent.resolve('bin/Debug') == androidProject.getOutputDir("Debug")
    }

    @Test
     void testGetOutputDirForReleaseReturnsDebutPath() {
        assert SAMPLE_DROID.parent.resolve('bin/Release') == androidProject.getOutputDir("Release")
    }

    @Test
     void testReferencesContainsGivenReference() {
        def expectedReference =  new Reference('Xamarin.Forms.Core', '..\\packages\\Xamarin.Forms.2.0.0.6490\\lib\\MonoAndroid10\\Xamarin.Forms.Core.dll')
        assert(androidProject.getReference()).contains(expectedReference)
    }

}
