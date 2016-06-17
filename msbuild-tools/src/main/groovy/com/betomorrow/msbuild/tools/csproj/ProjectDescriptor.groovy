package com.betomorrow.msbuild.tools.csproj

import java.nio.file.Path

/**
 * Created by Olivier on 18/12/2015.
 */
class ProjectDescriptor {

    private def content;
    private String name;

    public ProjectDescriptor(String name, String file) {
        content = new XmlSlurper().parse(file);
    }

    public ProjectDescriptor(String name, Path path) {
        content = new XmlSlurper().parse(path.toFile());
    }

    public boolean isAndroidApplication() {
        return content.PropertyGroup.AndroidApplication == 'True';
    }

    public boolean isIosApplication(){
        return content.PropertyGroup.IPhoneResourcePrefix.size() > 0;
    }

    public String getAndroidManifest() {
        return  content.PropertyGroup.AndroidManifest;
    }

    public String getAssemblyName() {
        return  content.PropertyGroup.AssemblyName;
    }

    public String getOutputPath(String configuration, String platform = null) {
        def pattern = platform == null ? ~/.*${configuration}.*/ : ~/.*'\s*${configuration}\s*\|\s*${platform}\s*'.*/
        def nodes = content.PropertyGroup.findAll{
            println(it.@Condition)
            it.@Condition =~ pattern
        }
        println(nodes.size())
        return nodes[0].OutputPath
    }

    public Reference[] getReference() {
        return Arrays.asList(new Reference('Xamarin.Forms.Core', '..\\packages\\Xamarin.Forms.2.0.0.6490\\lib\\MonoAndroid10\\Xamarin.Forms.Core.dll'))
    }


}
