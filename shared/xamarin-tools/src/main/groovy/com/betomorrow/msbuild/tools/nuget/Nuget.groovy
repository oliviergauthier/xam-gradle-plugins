package com.betomorrow.msbuild.tools.nuget

interface Nuget {

    void pack(String packagePath, String suffix, String outputDirectory)
    void push(String packagePath, String source, String apiKey)
    void install(String packagePath, String source)
    void restore()

}