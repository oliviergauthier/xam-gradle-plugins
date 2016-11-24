package com.betomorrow.msbuild.tools.descriptors.solution

import groovy.transform.Canonical

@Canonical
class BuildConfiguration {

    String configuration
    String platform

    BuildConfiguration(String configuration, String platform) {
        this.configuration = configuration
        this.platform = platform
    }

}
