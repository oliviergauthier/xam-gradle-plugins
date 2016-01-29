package com.betomorrow.msbuild.tools.nuget

import com.betomorrow.msbuild.tools.nuget.assemblies.AssemblySet
import com.betomorrow.msbuild.tools.nuget.dependencies.Dependency
import com.betomorrow.msbuild.tools.nuget.dependencies.DependencySet
import groovy.util.slurpersupport.NodeChild
import groovy.xml.XmlUtil

/**
 * Created by olivier on 17/01/16.
 */
class NuSpec {

    /**
     * Nuspec Source file
     */
    String source
    String output

    String packageId
    String version
    String authors
    String owners
    String licenseUrl
    String projectUrl
    String iconUrl
    Boolean requireLicenseAcceptance
    String description
    String releaseNotes
    String copyright
    String tags

    DependencySet dependencySet = new DependencySet()
    AssemblySet assemblySet = new AssemblySet()

    NuSpec() {
    }

    NuSpec(String source) {
        this.source = source
    }

    /**
     * Edit or Generate nuspec file and returns file path
     * @return
     */
    String process() {
        if (!source) {
            // generate new one
        }
        def content = new XmlSlurper().parse(source);

        if (!output) {
            output = source
        }

        updateMetadata(content, "id", packageId)
        updateMetadata(content, "version", version)
        updateMetadata(content, "authors", authors)
        updateMetadata(content, "owners", owners)
        updateMetadata(content, "licenseUrl", licenseUrl)
        updateMetadata(content, "projectUrl", projectUrl)
        updateMetadata(content, "iconUrl", iconUrl)
        updateMetadata(content, "requireLicenseAcceptance", requireLicenseAcceptance)
        updateMetadata(content, "description", description)
        updateMetadata(content, "releaseNotes", releaseNotes)
        updateMetadata(content, "copyright", copyright)
        updateMetadata(content, "tags", tags)

        dependencySet.forEach { updateDependency(content, it) }

        println(XmlUtil.serialize(content));

        new FileOutputStream(output).withStream { out ->
            XmlUtil.serialize(content, out)
        }
    }

    private void updateMetadata(NodeChild content, String field, Object value) {
        if (value != null) {
            content.metadata."${field}" = value
        }
    }

    private void updateDependency(NodeChild content, Dependency dependency) {
        if (dependency.group == null) {
            def nodes = content.metadata.dependencies.group.dependency.findAll { it.@id == dependency.id }
            nodes.forEach { it.@version = dependency.version }
        } else {

        }
    }
}
