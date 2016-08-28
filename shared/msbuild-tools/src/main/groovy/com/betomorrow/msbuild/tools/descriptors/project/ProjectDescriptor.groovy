package com.betomorrow.msbuild.tools.descriptors.project

import com.betomorrow.msbuild.tools.FileUtils
import groovy.transform.Canonical
import sun.reflect.generics.reflectiveObjects.NotImplementedException

import java.nio.file.Path
import java.nio.file.Paths

@Canonical
class ProjectDescriptor {

    private def content;
    private String name;
    private String path;

    protected ProjectDescriptor() {
    }

    public ProjectDescriptor(String name, String file) {
        this.name = name;
        this.path = file;
        content = new XmlSlurper().parse(file);
    }

    public ProjectDescriptor(String name, Path path) {
        this.name = name;
        this.path = path.toString();
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
        return Paths.get(path).parent.resolve(getAndroidManifest());
    }

    public String getAssemblyName() {
        return  content.PropertyGroup.AssemblyName;
    }

    public String getPath() {
        return path;
    }

    public String getOutputDir(String configuration, String platform = null) {
        def pattern = platform == null ? ~/.*${configuration}.*/ : ~/.*'\s*${configuration}\s*\|\s*${platform}\s*'.*/
        def nodes = content.PropertyGroup.findAll{
            it.@Condition =~ pattern
        }
        return FileUtils.toUnixPath(nodes[0].OutputPath.toString())
    }

    public Path getOutputPath(String configuration, String platform = null) {
        return Paths.get(path).parent
                .resolve(getOutputDir(configuration, platform))
                .resolve(getAssemblyName() + "." + getExtension());
    }

    public static String getPackageName() {
        throw new NotImplementedException()
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
