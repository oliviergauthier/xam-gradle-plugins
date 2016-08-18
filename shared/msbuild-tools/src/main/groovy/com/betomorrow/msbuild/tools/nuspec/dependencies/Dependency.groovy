package com.betomorrow.msbuild.tools.nuspec.dependencies;

public class Dependency {

    String group
    String id
    String version

    Dependency(String group, String id, String version) {
        this.group = group
        this.id = id
        this.version = version
    }

    Dependency(String dependency) {
        def tokens = dependency.split(":")
        if (tokens != null) {
            if (tokens.length == 3) {
                group = tokens[0]
                id = tokens[1]
                version = tokens[2]
            } else if (tokens.length == 2) {
                id = tokens[0]
                version = tokens[1]
            } else {
                id = tokens[0]
            }
        }
    }


    @Override
    public String toString() {
        return group + ":" + id + ":" + version;
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Dependency that = (Dependency) o

        if (group != that.group) return false
        if (id != that.id) return false
        if (version != that.version) return false

        return true
    }

    int hashCode() {
        int result
        result = (group != null ? group.hashCode() : 0)
        result = 31 * result + (id != null ? id.hashCode() : 0)
        result = 31 * result + (version != null ? version.hashCode() : 0)
        return result
    }
}
