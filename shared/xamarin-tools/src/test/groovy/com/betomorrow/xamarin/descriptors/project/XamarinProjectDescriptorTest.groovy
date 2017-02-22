package com.betomorrow.xamarin.descriptors.project

import com.betomorrow.msbuild.tools.files.FileUtils
import org.junit.Test

class XamarinProjectDescriptorTest {

    def SAMPLE_DROID = FileUtils.getResourcePath('CrossApp/Droid/CrossApp.Droid.csproj')
    def SAMPLE_IOS = FileUtils.getResourcePath('CrossApp/iOS/CrossApp.iOS.csproj')

    def androidProject = new XamarinProjectDescriptor('Sample', SAMPLE_DROID)
    def iosProject = new XamarinProjectDescriptor('Sample', SAMPLE_IOS)


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
    void testBuildIpaReturnsRightValue() {
        assert iosProject.getBuildIpa('Release', 'iPhone')
    }

    @Test
    void testIpaPackageNameReturnsRightValue() {
        assert 'CrossApp.iOS' == iosProject.getIpaPackageName('Release', 'iPhone')
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
        assert 'CrossApp.Droid' == androidProject.getAssemblyName()
    }

    @Test
    void testGetOutputDirForDebugReturnsDebutPath() {
        assert 'bin/Debug' == androidProject.getOutputDir("Debug")
    }

    @Test
    void testGetOutputDirForReleaseReturnsDebutPath() {
        assert 'bin/Release' == androidProject.getOutputDir("Release")
    }

    @Test
    void testGetOutputPathReturnsIpaPath() {
        assert SAMPLE_IOS.parent.resolve('bin/iPhone/Release/CrossApp.iOS.ipa') == iosProject.getApplicationOutputPath('Release', 'iPhone')
    }

    @Test
    void testGetOutputPathReturnsApkPath() {
        assert SAMPLE_DROID.parent.resolve('bin/Release/com.acme.crossapp.alpha.apk') == androidProject.getApplicationOutputPath('Release')
    }

    @Test
    void testReferencesContainsGivenReference() {
        def expectedReference =  new Reference('Xamarin.Forms.Core', '..\\packages\\Xamarin.Forms.2.0.0.6490\\lib\\MonoAndroid10\\Xamarin.Forms.Core.dll')
        assert(androidProject.getReference()).contains(expectedReference)
    }

}
