package com.betomorrow.gradle.application.context

import com.betomorrow.android.manifest.AndroidManifestWriter
import com.betomorrow.ios.plist.InfoPlistWriter
import com.betomorrow.msbuild.tools.files.FileCopier
import com.betomorrow.xamarin.xbuild.XBuild

interface ApplicationContext {

    FileCopier getFileCopier()
    AndroidManifestWriter getAndroidManifestWriter()
    InfoPlistWriter getInfoPlistWriter()
    XBuild getXbuild()

}