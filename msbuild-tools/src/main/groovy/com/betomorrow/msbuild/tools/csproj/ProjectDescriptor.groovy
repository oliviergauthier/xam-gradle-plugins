package com.betomorrow.msbuild.tools.csproj

/**
 * Created by Olivier on 18/12/2015.
 */
class ProjectDescriptor {

    def content;

    public ProjectDescriptor(String file) {
        content = new XmlSlurper().parse(file);
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

    public String getOutputPath(String configuration) {
        def pattern = ~/.*${configuration}.*/
        def nodes = content.PropertyGroup.findAll{
            it.@Condition =~ pattern
        }
        return nodes[0].OutputPath
    }

    public Reference[] getReference() {
        return Arrays.asList(new Reference('Xamarin.Forms.Core', '..\\packages\\Xamarin.Forms.2.0.0.6490\\lib\\MonoAndroid10\\Xamarin.Forms.Core.dll'))
    }


}
