package com.betomorrow.msbuild.tools.descriptors.project

import junit.framework.Assert

/**
 * Created by Olivier on 18/12/2015.
 */
class ProjectDescriptorTest extends GroovyTestCase {

    def SAMPLE_DROID = ClassLoader.getSystemResource('Sample.Droid.csproj').file;
    def SAMPLE_IOS = ClassLoader.getSystemResource('Sample.iOS.csproj').file;

    def androidProject = new ProjectDescriptor('Sample', SAMPLE_DROID);
    def iosProject = new ProjectDescriptor('Sample', SAMPLE_IOS);

    public void testIsAndroidReturnsTrueWithAndroidCSProj() {
        Assert.assertTrue(androidProject.isAndroidApplication())
    }

    public void testIsAndroidReturnsFalseWithIosCSProj() {
        Assert.assertFalse(androidProject.isIosApplication())
    }

    public void testIsIPhoneReturnsFalseWithAndroidCSProj() {
        Assert.assertFalse(iosProject.isAndroidApplication())
    }

    public void testIsIPhoneReturnsTrueWithIosCSProj() {
        Assert.assertTrue(iosProject.isIosApplication())
    }

    public void testGetAndroidManifestReturnsPathOfManifest() {
        GroovyTestCase.assertEquals('Properties\\AndroidManifest.xml', androidProject.getAndroidManifest());
    }

    public void testGetAssemblyNameReturnsNameOfAssembly() {
        GroovyTestCase.assertEquals('Sample.Droid', androidProject.getAssemblyName())
    }

    public void testGetOutputPathForDebugReturnsDebutPath() {
        GroovyTestCase.assertEquals('bin\\Debug', androidProject.getOutputPath("Debug"))
    }

    public void testGetOutputPathForReleaseReturnsDebutPath() {
        GroovyTestCase.assertEquals('bin\\Release', androidProject.getOutputPath("Release"))
    }

    public void testReferencesContainsGivenReference() {
        def expectedReference =  new Reference('Xamarin.Forms.Core', '..\\packages\\Xamarin.Forms.2.0.0.6490\\lib\\MonoAndroid10\\Xamarin.Forms.Core.dll');
        assert(androidProject.getReference()).contains(expectedReference);
    }

}
