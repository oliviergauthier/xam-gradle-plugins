package com.betomorrow.gradle.nugetpackage.extensions

/**
 * Created by olivier on 26/02/16.
 */
class AssemblyTarget {

    def _dest
    def _includes = new ArrayList<String>()

    AssemblyTarget() {
    }

    AssemblyTarget(dest, includes) {
        this.dest = dest
        this.includes = includes
    }
//    def String _dest
//    def List<String> includes = new ArrayList<>()
//
    def dest(String destination) {
        this._dest = destination
    }

    def includes(String... includes) {
        this._includes.addAll(includes)
    }
//
//    def includes(Object includes) {
////        this.includes.addAll(includes)
//    }
//
//    def includes(String include) {
//        this.includes.add(include)
//    }
//
//    def Object fakeIncludes;
//
//    Object getIncludes() {
//        return fakeIncludes
//    }
//
//    void setIncludes(Object includes) {
//        this.fakeIncludes = includes
//    }
}
