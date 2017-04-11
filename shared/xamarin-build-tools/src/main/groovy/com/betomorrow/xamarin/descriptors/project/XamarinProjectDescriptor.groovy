package com.betomorrow.xamarin.descriptors.project

import com.betomorrow.xamarin.android.manifest.DefaultAndroidManifestReader
import com.betomorrow.xamarin.files.FileUtils
import groovy.transform.InheritConstructors

import java.nio.file.Path

@InheritConstructors
class XamarinProjectDescriptor extends ProjectDescriptor {

    // Android

    boolean isAndroidApplication() {
        return content.PropertyGroup.AndroidApplication == 'True'
    }

    String getAndroidManifest() {
        return FileUtils.toUnixPath(content.PropertyGroup.AndroidManifest.toString())
    }

    Path getAndroidManifestPath() {
        return path.parent.resolve(getAndroidManifest())
    }

    // iOS

    boolean isIosApplication(){
        return content.PropertyGroup.IPhoneResourcePrefix.size() > 0
    }

    Path getInfoPlistPath() {
        return  path.parent.resolve(getInfoPlist())
    }

    String getInfoPlist() {
        return "Info.plist"
    }

    String getIpaPackageName(String configuration, String platform = null) {
        return getPropertyGroup(configuration, platform).IpaPackageName
    }

    boolean getBuildIpa(String configuration, String platform) {
        try {
            return getPropertyGroup(configuration, platform).BuildIpa
        } catch (Exception e) {
            return false
        }
    }

    // Commons

    String getOutputDir(String configuration, String platform = null) {
        return FileUtils.toUnixPath(getPropertyGroup(configuration, platform).OutputPath.toString())
    }

    Path getLibraryOutputPath(String configuration) {
        return path.parent.resolve(getOutputDir(configuration, 'AnyCPU')).resolve("${assemblyName}.dll")
    }

    Path getSymbolsOutputPath(String configuration) {
        return path.parent.resolve(getOutputDir(configuration, 'AnyCPU')).resolve("${assemblyName}.dll.mdb")
    }

    Path getApplicationOutputPath(String configuration, String platform = null) {
        if (isAndroidApplication()) {
            def packageName = new DefaultAndroidManifestReader().read(androidManifestPath).packageName
            return path.parent.resolve(getOutputDir(configuration, platform)).resolve("${packageName}.apk")
        } else {
            def packageName = getIpaPackageName(configuration, platform)
            if (!packageName) {
                throw new Exception("No IpaPackageName defined in csproj")
            }
            return path.parent.resolve(getOutputDir(configuration, platform)).resolve("${packageName}.ipa")
        }
    }

    def getPropertyGroup(String configuration, String platform) {
        def pattern = platform == null ? ~/.*${configuration}.*/ : ~/.*'\s*${configuration}\s*\|\s*${platform}\s*'.*/
        def nodes = content.PropertyGroup.findAll{
            it.@Condition =~ pattern
        }
        return nodes[0]
    }

    Path getAssemblyInfoPath() {
        return path.parent.resolve("Properties").resolve("AssemblyInfo.cs")
    }

    String getDebugMode(String configuration, String platform = null) {
        return getPropertyGroup(configuration, platform).DebugType
    }

    boolean hasDebugSymbols(String configuration, String platform = null) {
        def debugSymbols = getPropertyGroup(configuration, platform).DebugSymbols
        try {
            return Boolean.parseBoolean(debugSymbols.toString().trim())
        } catch (Exception e) {
            return false
        }
    }


}
