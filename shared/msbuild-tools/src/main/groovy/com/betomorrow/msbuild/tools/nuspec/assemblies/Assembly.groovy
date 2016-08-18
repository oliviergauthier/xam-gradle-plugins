package com.betomorrow.msbuild.tools.nuspec.assemblies

class Assembly {
    String assemblyPath
    String targetDirectory

    Assembly(String assemblyPath, String targetDirectory) {
        this.assemblyPath = assemblyPath
        this.targetDirectory = targetDirectory
    }
}
