package com.betomorrow.msbuild.tools.nuspec.assemblies

import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode

@Canonical
class Assembly {
    String assemblyPath
    String targetDirectory

    Assembly(String assemblyPath, String targetDirectory) {
        this.assemblyPath = assemblyPath
        this.targetDirectory = targetDirectory
    }
}
