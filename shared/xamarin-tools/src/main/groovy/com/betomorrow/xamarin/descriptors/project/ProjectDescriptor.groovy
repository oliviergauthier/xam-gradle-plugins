package com.betomorrow.xamarin.descriptors.project

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
        return  content.PropertyGroup.AssemblyName
    }

    static Reference[] getReference() {
        return Arrays.asList(new Reference('Xamarin.Forms.Core', '..\\packages\\Xamarin.Forms.2.0.0.6490\\lib\\MonoAndroid10\\Xamarin.Forms.Core.dll'))
    }

}
