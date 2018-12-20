package com.betomorrow.gradle.application.tasks

import com.betomorrow.gradle.application.context.PluginContext
import com.betomorrow.xamarin.files.FileCopier
import com.betomorrow.xamarin.descriptors.project.XamarinProjectDescriptor
import com.betomorrow.xamarin.tools.xbuild.XBuild
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction

class SignAndroidPackageTask extends DefaultTask {

    protected XBuild xBuild = PluginContext.current.getXbuild()
    protected FileCopier fileCopier = PluginContext.current.getFileCopier()

    @Input @Optional
    String output

    @Input @Optional
    String projectFile

    @Input @Optional
    String configuration

    @TaskAction
    void build() {
        invokeMSBuild()

        copyBuiltAssemblyToOutput()
    }

    private XamarinProjectDescriptor getProjectDescriptor() {
        return new XamarinProjectDescriptor("", projectFile)
    }

    private void invokeMSBuild() {
        def result = xBuild.signAndroidPackage(configuration, projectFile)
        if (result > 0) {
            throw new GradleException("Can't sign Android package")
        }
    }

    private void copyBuiltAssemblyToOutput() {
        fileCopier.copy(getProjectDescriptor().getApplicationOutputPath(configuration).toString(), output)
    }

}
