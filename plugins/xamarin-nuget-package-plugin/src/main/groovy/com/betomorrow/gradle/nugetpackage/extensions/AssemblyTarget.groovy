package com.betomorrow.gradle.nugetpackage.extensions

import groovy.transform.Canonical

@Canonical
class AssemblyTarget {

    private String _dest
    private List<String> _includes

    AssemblyTarget() {
    }

    AssemblyTarget(dest, includes) {
        this._dest = dest
        this._includes = includes
    }

    def dest(String destination) {
        this._dest = destination
    }

    def getDest() {
        return _dest
    }

    def includes(String... includes) {
        this._includes = includes
    }

    def getIncludes() {
        return _includes
    }

}
