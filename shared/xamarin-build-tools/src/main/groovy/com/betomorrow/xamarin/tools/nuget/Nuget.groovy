package com.betomorrow.xamarin.tools.nuget

interface Nuget {

    int pack(String packagePath, String suffix, String outputDirectory)
    int push(String packagePath, String source, String apiKey)
    int install(String packagePath, String source)
    int restore()

}