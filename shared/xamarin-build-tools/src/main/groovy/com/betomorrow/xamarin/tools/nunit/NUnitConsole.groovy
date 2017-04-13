package com.betomorrow.xamarin.tools.nunit

interface NUnitConsole {

    int run(List<String> assemblies, String format)
    int run(List<String> assemblies, String format, String version)

}