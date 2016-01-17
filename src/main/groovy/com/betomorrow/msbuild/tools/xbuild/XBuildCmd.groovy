package com.betomorrow.msbuild.tools.xbuild

import com.betomorrow.msbuild.tools.commands.CommandExecutor

/**
 * Created by Olivier on 15/01/2016.
 */
class XBuildCmd implements CommandExecutor.Cmd {

    // https://developer.xamarin.com/guides/android/under_the_hood/build_process/#Build_Actions

    private Map<String, String> properties = new HashMap<>()

    String xBuildPath = 'xbuild'
    String target
    String configuration
    String projectPath

    public void addProperty(String key, String value) {
        properties[key] = value
    }

    public Collection<String> build() {
        def cmd = [xBuildPath]

        if (target) {
            cmd.add("/t:${target}")
        }

        def allProperties = properties.clone();
        if (configuration) {
            allProperties[AndroidBuildProperties.Configuration] = configuration
        }

        allProperties.findAll { it.value } .each { k, v -> cmd.add("/p:${k}=${v}") }

        cmd.add(projectPath)

        return cmd;
    }


}
