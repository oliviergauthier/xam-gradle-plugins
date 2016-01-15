package com.betomorrow.msbuild.tools.csproj

/**
 * Created by Olivier on 18/12/2015.
 */
class CSProjTest extends GroovyTestCase {

    def SAMPLE_DROID = ClassLoader.getSystemResource('Sample.Droid.csproj').file;
    def SAMPLE_IOS = ClassLoader.getSystemResource('Sample.iOS.csproj').file;

    def androidProject = new CSProj(SAMPLE_DROID);
    def iosProject = new CSProj(SAMPLE_IOS);

    public void testIsAndroidReturnsTrueWithAndroidCSProj() {
        assertTrue(androidProject.isAndroidApplication())
    }

    public void testIsAndroidReturnsFalseWithIosCSProj() {
        assertFalse(androidProject.isIosApplication())
    }

    public void testIsIPhoneReturnsFalseWithAndroidCSProj() {
        assertFalse(iosProject.isAndroidApplication())
    }

    public void testIsIPhoneReturnsTrueWithIosCSProj() {
        assertTrue(iosProject.isIosApplication())
    }

    public void testGetAndroidManifestReturnsPathOfManifest() {
        assertEquals('Properties\\AndroidManifest.xml', androidProject.getAndroidManifest());
    }

    public void testGetAssemblyNameReturnsNameOfAssembly() {
        assertEquals('Sample.Droid', androidProject.getAssemblyName())
    }

    public void testGetOutputPathForDebugReturnsDebutPath() {
        assertEquals('bin\\Debug', androidProject.getOutputPath("Debug"))
    }

    public void testGetOutputPathForReleaseReturnsDebutPath() {
        assertEquals('bin\\Release', androidProject.getOutputPath("Release"))
    }

    public void testReferencesContainsGivenReference() {
        def expectedReference =  new Reference('Xamarin.Forms.Core', '..\\packages\\Xamarin.Forms.2.0.0.6490\\lib\\MonoAndroid10\\Xamarin.Forms.Core.dll');
        assert(androidProject.getReference()).contains(expectedReference);
    }

}
