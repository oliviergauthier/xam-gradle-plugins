package com.betomorrow.msbuild.tools.nuspec

import com.betomorrow.msbuild.tools.nuspec.assemblies.Assembly
import com.betomorrow.msbuild.tools.nuspec.dependencies.Dependency
import com.betomorrow.msbuild.tools.nuspec.nodes.PackageNode
import groovy.xml.XmlUtil

class XmlNuSpecWriter implements NuSpecWriter {

    static def DEFAULT_CONTENT = "<package><metadata></metadata></package>"


    /**
     * Edit or Generate nuspec file and returns file path
     * @return
     */
    @Override
    void write(NuSpec nuspec) {

        XmlParser parser = new XmlParser()
        def content = nuspec.source != null ? parser.parse(nuspec.source) : parser.parseText(DEFAULT_CONTENT)
        def packageNode = new PackageNode(content)

        def output = nuspec.output ?: nuspec.source

        updateMetadata(packageNode, "id", nuspec.packageId)
        updateMetadata(packageNode, "version", nuspec.version)
        updateMetadata(packageNode, "authors", nuspec.authors)
        updateMetadata(packageNode, "owners", nuspec.owners)
        updateMetadata(packageNode, "licenseUrl", nuspec.licenseUrl)
        updateMetadata(packageNode, "projectUrl", nuspec.projectUrl)
        updateMetadata(packageNode, "iconUrl", nuspec.iconUrl)
        updateMetadata(packageNode, "requireLicenseAcceptance", nuspec.requireLicenseAcceptance)
        updateMetadata(packageNode, "description", nuspec.description)
        updateMetadata(packageNode, "releaseNotes", nuspec.releaseNotes)
        updateMetadata(packageNode, "copyright", nuspec.copyright)
        updateMetadata(packageNode, "tags", nuspec.tags)

        if (nuspec.dependencySet != null) {
            nuspec.dependencySet.forEach { updateDependency(packageNode, it) }
        }

        if (nuspec.assemblySet != null) {
            nuspec.assemblySet.forEach { updateAssembly(packageNode, it) }
        }

        println(XmlUtil.serialize(content))

        new FileOutputStream(output).withStream { out ->
            XmlUtil.serialize(content, out)
        }
    }


    private static void updateMetadata(PackageNode packageNode, String field, Object value) {
        if (value != null) {
            packageNode.metadata().property(field).value = value
        }
    }

    private static void updateDependency(PackageNode packageNode, Dependency dep) {
        if (dep != null) {
            packageNode.metadata().dependencies().group(dep.group).dependency(dep.id).@version = dep.version
        }
    }

    private static void updateAssembly(PackageNode packageNode, Assembly assembly) {
        if (assembly != null) {
            packageNode.files().add(assembly)
        }
    }

}
