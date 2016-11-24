package com.betomorrow.gradle.application.context

import com.betomorrow.android.tools.manifest.AndroidManifestWriter
import com.betomorrow.ios.tools.plist.InfoPlistWriter
import com.betomorrow.msbuild.tools.Files.FileCopier
import com.betomorrow.msbuild.tools.commands.CommandRunner

interface ApplicationContext {

    FileCopier getFileCopier()
    CommandRunner getCommandRunner()
    AndroidManifestWriter getAndroidManifestWriter()
    InfoPlistWriter getInfoPlistWriter()

}