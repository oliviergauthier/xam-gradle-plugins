package com.betomorrow.gradle.application.context

import com.betomorrow.android.tools.AndroidManifestEditorFactory
import com.betomorrow.msbuild.tools.Files.FileCopier
import com.betomorrow.msbuild.tools.commands.CommandRunner

interface ApplicationContext {

    FileCopier getFileCopier();
    CommandRunner getCommandRunner();
    AndroidManifestEditorFactory getAndroidManifestEditorFactory();

}