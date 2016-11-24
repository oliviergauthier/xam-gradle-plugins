package com.betomorrow.msbuild.tools.descriptors.project

import com.betomorrow.msbuild.tools.Files.FileUtils
import groovy.transform.Canonical
import sun.reflect.generics.reflectiveObjects.NotImplementedException

import java.nio.file.Path
import java.nio.file.Paths

@Canonical
class ProjectDescriptor {

    private def content
    private String name
    private Path path

    protected ProjectDescriptor() {
    }

    ProjectDescriptor(String name, String file) {
        this.name = name
        this.path = Paths.get(file)
        content = new XmlSlurper().parse(file)
    }

    ProjectDescriptor(String name, Path path) {
        this.name = name
        this.path = path
        content = new XmlSlurper().parse(path.toFile())
    }

    String getName() {
        return name
    }

    boolean isAndroidApplication() {
        return content.PropertyGroup.AndroidApplication == 'True'
    }

    boolean isIosApplication(){
        return content.PropertyGroup.IPhoneResourcePrefix.size() > 0
    }

    String getAndroidManifest() {
        return FileUtils.toUnixPath(content.PropertyGroup.AndroidManifest.toString())
    }

    Path getAndroidManifestPath() {
        return path.parent.resolve(getAndroidManifest())
    }

    String getInfoPlist() {
        return "Info.plist"
    }

    Path getInfoPlistPath() {
        return  path.parent.resolve(getInfoPlist())
    }

    String getAssemblyName() {
        return  content.PropertyGroup.AssemblyName
    }

    Path getPath() {
        return path
    }

    private Path getOutputDir(String configuration, String platform = null) {
        def pattern = platform == null ? ~/.*${configuration}.*/ : ~/.*'\s*${configuration}\s*\|\s*${platform}\s*'.*/
        def nodes = content.PropertyGroup.findAll{
            it.@Condition =~ pattern
        }
        return path.parent.resolve(FileUtils.toUnixPath(nodes[0].OutputPath.toString()))
    }

    Path getOutputPath(String configuration, String platform = null) {
        return getOutputDir(configuration, platform).resolve(getAssemblyName() + "." + getExtension())
    }

     static Reference[] getReference() {
        return Arrays.asList(new Reference('Xamarin.Forms.Core', '..\\packages\\Xamarin.Forms.2.0.0.6490\\lib\\MonoAndroid10\\Xamarin.Forms.Core.dll'))
    }

    private String getExtension() {
        if (isAndroidApplication()) {
            return "apk"
        } else {
            return "ipa"
        }
    }


}
