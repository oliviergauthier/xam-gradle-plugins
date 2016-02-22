package com.betomorrow.gradle.nugetpackage.extensions

/**
 * Created by olivier on 22/02/16.
 */
class NuspecPluginExtension {

    def String authors;
    def String owners;
    def String description;

    def List<String> dependencies;
    def List<String> assemblies;


}
