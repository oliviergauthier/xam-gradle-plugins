package com.betomorrow.msbuild.tools.xbuild

/**
 * Created by Olivier on 15/01/2016.
 */
class XBuild {

    // https://developer.xamarin.com/guides/android/under_the_hood/build_process/#Build_Actions

    public build() {
        // build /t:Build /p:Configuration=Release /p:DebugSymbols=false /p:DebugType=Full <csproj>
        println "cmd /c echo build /t:Build /p:Configuration=Release /p:DebugSymbols=false /p:DebugType=Full <csproj>".execute().text;
    }


}
