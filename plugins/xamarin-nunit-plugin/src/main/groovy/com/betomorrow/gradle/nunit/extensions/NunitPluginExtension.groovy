package com.betomorrow.gradle.nunit.extensions

import com.betomorrow.xamarin.descriptors.solution.SolutionLoader
import org.gradle.api.Project

class NunitPluginExtension {

    private SolutionLoader loader = new SolutionLoader()
    private static final String SUFFIX_TEST = ".Test"

    def projects
    def assemblies
    String format

    Project gradleProject

    NunitPluginExtension(Project gradleProject) {
        this.gradleProject = gradleProject
    }

    List<String> getProjects() {
        if (this.projects != null) {
            if (this.projects in Collection) {
                return this.projects
            } else {
                return [this.projects]
            }
        } else {
            if (assemblies != null) {
                return null
            }
            def sd = loader.load(gradleProject.file(gradleProject.solution))
            return sd.projects.findAll { it.name.endsWith(SUFFIX_TEST)}.name
        }
    }


    List<String> getAssemblies() {
        if (assemblies != null) {
            if (assemblies in Collection) {
                return assemblies
            } else {
                return [assemblies]
            }
        }
    }

}
