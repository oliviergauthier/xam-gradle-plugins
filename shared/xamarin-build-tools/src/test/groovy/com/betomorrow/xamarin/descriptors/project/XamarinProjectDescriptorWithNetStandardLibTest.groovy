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
    void testGetOutputDirForDebugReturnsDebugPath() {
        assert "bin/Debug/netstandard2.0" == csproj.getOutputDir("Debug")
    }

    @Test
    void testGetOutputDirForReleaseReturnsReleasePath() {
        assert "bin/Release/netstandard2.0" == csproj.getOutputDir("Release")
    }

    @Test
    void testGetOutputDirForPortableNet45ReturnsPortableNet45Path() {
        assert "bin/Release/portable-net45" == csproj.getOutputDir("Release", "AnyCPU", "portable-net45")
    }

    @Test
    void testGetOutputDirForNetStandardReturnsPortableNetStandardPath() {
        assert "bin/Release/netstandard2.0" == csproj.getOutputDir("Release", "AnyCPU", "netstandard2.0")
    }



}
