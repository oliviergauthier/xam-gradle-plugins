package com.betomorrow.msbuild.tools.nunit

interface NUnitConsole {

    void run(List<String> assemblies, String format)
    void run(List<String> assemblies, String format, String version)

}