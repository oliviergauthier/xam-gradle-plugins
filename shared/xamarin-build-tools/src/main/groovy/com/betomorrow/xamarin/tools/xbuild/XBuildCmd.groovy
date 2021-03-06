package com.betomorrow.xamarin.tools.xbuild

import com.betomorrow.xamarin.commands.CommandRunner

class XBuildCmd implements CommandRunner.Cmd {

    // https://developer.xamarin.com/guides/android/under_the_hood/build_process/#Build_Actions

    private Map<String, String> properties = new HashMap<>()

    String msBuildPath
    String target
    String configuration
    String projectPath

    XBuildCmd(String msBuildPath) {
        this.msBuildPath = msBuildPath
    }

    void addProperty(String key, String value) {
        properties[key] = value
    }

    List<String> build() {
        def cmd = [msBuildPath]

        if (target) {
            cmd.add("/t:${target}")
        }

        def allProperties = properties.clone()
        if (configuration) {
            allProperties[AndroidBuildProperties.Configuration] = configuration
        }

        allProperties.findAll { it.value } .each { k, v -> cmd.add("/p:${k}=${v}") }

        cmd.add(projectPath)

        return (cmd as String[]).toList()
    }


}
