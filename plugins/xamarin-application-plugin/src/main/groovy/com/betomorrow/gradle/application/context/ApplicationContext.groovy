package com.betomorrow.gradle.application.context

import com.betomorrow.android.manifest.AndroidManifestWriter
import com.betomorrow.ios.plist.InfoPlistWriter
import com.betomorrow.msbuild.tools.files.FileCopier
import com.betomorrow.msbuild.tools.commands.CommandRunner

interface ApplicationContext {

    FileCopier getFileCopier()
    CommandRunner getCommandRunner()
    AndroidManifestWriter getAndroidManifestWriter()
    InfoPlistWriter getInfoPlistWriter()

}