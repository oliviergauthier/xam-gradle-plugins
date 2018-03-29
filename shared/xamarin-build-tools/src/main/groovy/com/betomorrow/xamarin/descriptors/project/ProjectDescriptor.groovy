package com.betomorrow.xamarin.descriptors.project

import com.betomorrow.utils.StringUtils
import groovy.transform.Canonical

import java.nio.file.Path
import java.nio.file.Paths

@Canonical
class ProjectDescriptor {

    protected def content
    protected String name
    protected Path path

    protected ProjectDescriptor() {
    }

    ProjectDescriptor(String name, String file) {
        this.name = name
        this.path = Paths.get(file)
        content = new XmlSlurper().parse(file)
    }

    ProjectDescriptor(String name, Path path) {
        this.name = name
        this.path = path
        content = new XmlSlurper().parse(path.toFile())
    }

    Path getPath() {
        return path
    }

    String getName() {
        return name
    }

    String getAssemblyName() {
        String assemblyName = content.PropertyGroup.AssemblyName
        if (!StringUtils.isNullOrWhiteSpace(assemblyName)) {
            return assemblyName
        }
        return name
    }

    static Reference[] getReference() {
        return Arrays.asList(new Reference('Xamarin.Forms.Core', '..\\packages\\Xamarin.Forms.2.0.0.6490\\lib\\MonoAndroid10\\Xamarin.Forms.Core.dll'))
    }

}
