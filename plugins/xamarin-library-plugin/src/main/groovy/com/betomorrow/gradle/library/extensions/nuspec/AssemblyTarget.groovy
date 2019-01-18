package com.betomorrow.gradle.library.extensions.nuspec

import groovy.transform.Canonical

@Canonical
class AssemblyTarget {

    private String _dest
    private String _target
    private List<String> _includes

    AssemblyTarget() {
    }

    AssemblyTarget(dest, target, includes) {
        this._dest = dest
        this._target = target
        this._includes = includes
    }

    def dest(String destination) {
        this._dest = destination
    }

    def getDest() {
        return _dest
    }

    def target(String target) {
        this._target = target
    }

    def getTarget() {
        return _target
    }

    def includes(String... includes) {
        this._includes = includes
    }

    def getIncludes() {
        return _includes
    }

}
