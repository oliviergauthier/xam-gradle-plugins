package com.betomorrow.msbuild.tools.descriptors.project

import com.betomorrow.msbuild.tools.Files.FileUtils
import groovy.transform.Canonical
import sun.reflect.generics.reflectiveObjects.NotImplementedException

import java.nio.file.Path
import java.nio.file.Paths

@Canonical
class ProjectDescriptor {

    private def content;
    private String name;
    private Path path;

    protected ProjectDescriptor() {
    }

    public ProjectDescriptor(String name, String file) {
        this.name = name;
        this.path = Paths.get(file);
        content = new XmlSlurper().parse(file);
    }

    public ProjectDescriptor(String name, Path path) {
        this.name = name;
        this.path = path;
        content = new XmlSlurper().parse(path.toFile());
    }

    public String getName() {
        return name;
    }

    public boolean isAndroidApplication() {
        return content.PropertyGroup.AndroidApplication == 'True';
    }

    public boolean isIosApplication(){
        return content.PropertyGroup.IPhoneResourcePrefix.size() > 0;
    }

    public String getAndroidManifest() {
        return FileUtils.toUnixPath(content.PropertyGroup.AndroidManifest.toString());
    }

    public Path getAndroidManifestPath() {
        return path.parent.resolve(getAndroidManifest());
    }

    public String getInfoPlist() {
        return "Info.plist";
    }

    public Path getInfoPlistPath() {
        return  path.parent.resolve(getInfoPlist());
    }

    public String getAssemblyName() {
        return  content.PropertyGroup.AssemblyName;
    }

    public Path getPath() {
        return path;
    }

    private Path getOutputDir(String configuration, String platform = null) {
        def pattern = platform == null ? ~/.*${configuration}.*/ : ~/.*'\s*${configuration}\s*\|\s*${platform}\s*'.*/
        def nodes = content.PropertyGroup.findAll{
            it.@Condition =~ pattern
        }
        return path.parent.resolve(FileUtils.toUnixPath(nodes[0].OutputPath.toString()))
    }

    public Path getOutputPath(String configuration, String platform = null) {
        return getOutputDir(configuration, platform).resolve(getAssemblyName() + "." + getExtension());
    }

    public static Reference[] getReference() {
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
