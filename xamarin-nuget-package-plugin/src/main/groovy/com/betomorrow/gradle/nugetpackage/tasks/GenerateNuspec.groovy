package com.betomorrow.gradle.nugetpackage.tasks

import com.betomorrow.msbuild.tools.nuspec.assemblies.AssemblySet
import com.betomorrow.msbuild.tools.nuspec.dependencies.DependencySet
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by olivier on 26/02/16.
 */
class GenerateNuspec extends DefaultTask{

    def String authors;
    def String owners;
    def String description;
    def DependencySet dependencies;
    def AssemblySet assemblies;

    @TaskAction
    public void generateNuspec() {

    }
}
