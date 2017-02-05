package com.betomorrow.xamarin.descriptors.project

import com.betomorrow.ios.plist.DefaultInfoPlistReader
import com.betomorrow.msbuild.tools.files.FileUtils
import com.betomorrow.android.manifest.DefaultAndroidManifestReader
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

    // Commons

    String getOutputDir(String configuration, String platform = null) {
        def pattern = platform == null ? ~/.*${configuration}.*/ : ~/.*'\s*${configuration}\s*\|\s*${platform}\s*'.*/
        def nodes = content.PropertyGroup.findAll{
            it.@Condition =~ pattern
        }
        return FileUtils.toUnixPath(nodes[0].OutputPath.toString())
    }

    Path getOutputPath(String configuration, String platform = null) {
        if (isAndroidApplication()) {
            def packageName = new DefaultAndroidManifestReader().read(androidManifestPath).packageName
            path.parent.resolve(getOutputDir(configuration, platform)).resolve("${packageName}.apk")
        } else if (isIosApplication()) {
            def bundleIdentifier = new DefaultInfoPlistReader().read(infoPlistPath).bundleIdentifier
            path.parent.resolve(getOutputDir(configuration, platform)).resolve("${bundleIdentifier}.ipa")
        }
    }

}
