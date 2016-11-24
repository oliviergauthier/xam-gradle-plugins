package com.betomorrow.msbuild.tools.descriptors.project

class Reference {

    String include
    String hintPath

    Reference(String include, String hintPath) {
        this.include = include
        this.hintPath = hintPath
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Reference reference = (Reference) o

        if (hintPath != reference.hintPath) return false
        if (include != reference.include) return false

        return true
    }

    int hashCode() {
        int result
        result = include.hashCode()
        result = 31 * result + hintPath.hashCode()
        return result
    }
}
