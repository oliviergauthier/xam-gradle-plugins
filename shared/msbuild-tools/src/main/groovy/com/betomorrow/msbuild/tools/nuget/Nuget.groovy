package com.betomorrow.msbuild.tools.nuget

interface Nuget {

    void pack(String packagePath, String suffix)
    void push(String packagePath, String source, String apiKey)
    void restore()

}