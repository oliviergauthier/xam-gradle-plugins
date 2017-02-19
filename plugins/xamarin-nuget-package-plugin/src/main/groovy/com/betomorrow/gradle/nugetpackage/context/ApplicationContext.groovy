package com.betomorrow.gradle.nugetpackage.context

import com.betomorrow.android.manifest.AndroidManifestWriter
import com.betomorrow.ios.plist.InfoPlistWriter
import com.betomorrow.msbuild.tools.files.FileCopier
import com.betomorrow.msbuild.tools.nuget.Nuget
import com.betomorrow.xamarin.xbuild.XBuild

interface ApplicationContext {

    Nuget getNuget()

}