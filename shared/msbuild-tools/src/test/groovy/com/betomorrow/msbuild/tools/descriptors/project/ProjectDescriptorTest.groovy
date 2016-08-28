package com.betomorrow.msbuild.tools.descriptors.project

import com.betomorrow.msbuild.tools.FileUtils
import org.junit.Test

class ProjectDescriptorTest {

    def SAMPLE_DROID = FileUtils.getResourcePath('Sample.Droid.csproj');
    def SAMPLE_IOS = FileUtils.getResourcePath('Sample.iOS.csproj');

    def androidProject = new ProjectDescriptor('Sample', SAMPLE_DROID);
    def iosProject = new ProjectDescriptor('Sample', SAMPLE_IOS);

    @Test
    public void testIsAndroidReturnsTrueWithAndroidCSProj() {
        assert androidProject.isAndroidApplication()
    }

    @Test
    public void testIsAndroidReturnsFalseWithIosCSProj() {
        assert !androidProject.isIosApplication()
    }

    @Test
    public void testIsIPhoneReturnsFalseWithAndroidCSProj() {
        assert !iosProject.isAndroidApplication()
    }

    @Test
    public void testIsIPhoneReturnsTrueWithIosCSProj() {
        assert iosProject.isIosApplication()
    }

    @Test
    public void testGetAndroidManifestReturnsPathOfManifest() {
        assert 'Properties\\AndroidManifest.xml' == androidProject.getAndroidManifest();
    }

    @Test
    public void testGetAssemblyNameReturnsNameOfAssembly() {
        assert 'Sample.Droid' == androidProject.getAssemblyName()
    }

    @Test
    public void testGetOutputPathForDebugReturnsDebutPath() {
        assert 'bin\\Debug' == androidProject.getOutputDir("Debug")
    }

    @Test
    public void testGetOutputPathForReleaseReturnsDebutPath() {
        assert 'bin\\Release' == androidProject.getOutputDir("Release")
    }

    @Test
    public void testReferencesContainsGivenReference() {
        def expectedReference =  new Reference('Xamarin.Forms.Core', '..\\packages\\Xamarin.Forms.2.0.0.6490\\lib\\MonoAndroid10\\Xamarin.Forms.Core.dll');
        assert(androidProject.getReference()).contains(expectedReference);
    }

}
