package com.betomorrow.msbuild.tools.nuget

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

    List<Dependency> dependencies = []

    NuSpec() {
    }

    NuSpec(String source) {
        this.source = source
    }

    void addDependency(String packageId, String version) {
        dependencies.add(new Dependency(id : packageId, version : version))
    }

    class Dependency {
        String id
        String version
    }

    class Assembly {
        String assemblyPath
        String targetDirectory
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

        updateField(content, "id", packageId)
        updateField(content, "version", version)
        updateField(content, "authors", authors)
        updateField(content, "owners", owners)
        updateField(content, "licenseUrl", licenseUrl)
        updateField(content, "projectUrl", projectUrl)
        updateField(content, "iconUrl", iconUrl)
        updateField(content, "requireLicenseAcceptance", requireLicenseAcceptance)
        updateField(content, "description", description)
        updateField(content, "releaseNotes", releaseNotes)
        updateField(content, "copyright", copyright)
        updateField(content, "tags", tags)


        println(XmlUtil.serialize(content));

        new FileOutputStream(output).withStream { out ->
            XmlUtil.serialize(content, out)
        }
    }

    private void updateField(NodeChild content, String field, Object value) {
        if (value != null) {
            content.metadata."${field}" = value
        }
    }
}
