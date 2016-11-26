package com.betomorrow.xamarin.descriptors.project

import com.betomorrow.msbuild.tools.files.FileUtils
import org.junit.Test

class ProjectDescriptorTest {

    def SAMPLE_PROJECT = FileUtils.getResourcePath('Sample.Droid.csproj')

    def project = new ProjectDescriptor('Sample', SAMPLE_PROJECT)

    @Test
    void testGetAssemblyNameReturnsNameOfAssembly() {
        assert 'Sample.Droid' == project.getAssemblyName()
    }

    @Test
    void testReferencesContainsGivenReference() {
        def expectedReference =  new Reference('Xamarin.Forms.Core', '..\\packages\\Xamarin.Forms.2.0.0.6490\\lib\\MonoAndroid10\\Xamarin.Forms.Core.dll')
        assert(project.getReference()).contains(expectedReference)
    }

}
