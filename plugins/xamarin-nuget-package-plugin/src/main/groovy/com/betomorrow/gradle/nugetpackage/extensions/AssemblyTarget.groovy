package com.betomorrow.gradle.nugetpackage.extensions

/**
 * Created by olivier on 26/02/16.
 */
class AssemblyTarget {

    private String _dest
    private List<String> _includes

    AssemblyTarget() {
    }

    AssemblyTarget(dest, includes) {
        this.dest = dest
        this.includes = includes
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
