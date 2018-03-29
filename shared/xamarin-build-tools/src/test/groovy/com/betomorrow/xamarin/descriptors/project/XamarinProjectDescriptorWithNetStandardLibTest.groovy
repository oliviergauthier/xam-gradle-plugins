package com.betomorrow.xamarin.descriptors.project

import com.betomorrow.xamarin.files.FileUtils
import org.junit.Test

class XamarinProjectDescriptorWithNetStandardLibTest {

    def SAMPLE = FileUtils.getResourcePath('NetStandardLib/NetStandardLib/NetStandardLib.csproj')

    def csproj = new XamarinProjectDescriptor('NetStandardLib', SAMPLE)


    @Test
    void testGetAssemblyNameReturnsDefaultAssemblyName() {
        assert 'NetStandardLib' == csproj.getAssemblyName()
    }

    @Test
    void testGetAssemblyNameReturnsNameOfAssembly() {
        assert 'NetStandardLib' == csproj.getAssemblyName()
    }

    @Test
    void testGetOutputDirForDebugReturnsDebutPath() {
        assert "bin/Debug/netstandard2.0" == csproj.getOutputDir("Debug")
    }

    @Test
    void testGetOutputDirForReleaseReturnsDebutPath() {
        assert "bin/Release/netstandard2.0" == csproj.getOutputDir("Release")
    }



}
