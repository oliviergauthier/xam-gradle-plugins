package com.betomorrow.msbuild.tools.descriptors.solution

import groovy.transform.Canonical

@Canonical
class SolutionProject {

    String name
    String path
    Collection<BuildConfiguration> configurations

    SolutionProject(String name, String path, Collection<BuildConfiguration> configurations) {
        this.name = name
        this.path = path
        this.configurations = configurations
    }

}
