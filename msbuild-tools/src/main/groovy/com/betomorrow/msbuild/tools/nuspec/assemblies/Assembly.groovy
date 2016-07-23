package com.betomorrow.msbuild.tools.nuspec.assemblies

/**
 * Created by olivier on 29/01/16.
 */
class Assembly {
    String assemblyPath
    String targetDirectory

    Assembly(String assemblyPath, String targetDirectory) {
        this.assemblyPath = assemblyPath
        this.targetDirectory = targetDirectory
    }
}
