package com.betomorrow.msbuild.tools.nuget.dependencies;

/**
 * Created by olivier on 29/01/16.
 */
public class Dependency {

    String group
    String id
    String version

    Dependency(String group, String id, String version) {
        this.group = group
        this.id = id
        this.version = version
    }

    Dependency(String dependency) {
        def tokens = dependency.split(":")
        if (tokens != null) {
            if (tokens.length == 3) {
                group = tokens[0]
                id = tokens[1]
                version = tokens[2]
            } else if (tokens.length == 2) {
                id = tokens[0]
                version = tokens[1]
            } else {
                id = tokens[0]
            }
        }
    }
}
