package com.betomorrow.msbuild.tools.nuspec.dependencies
/**
 * Created by olivier on 29/01/16.
 */
public class DependencySet implements Set<Dependency> {

    @Delegate Set<Dependency> dependencies = new HashSet<>();

    void add(String packageId, String version) {
        dependencies.add(new Dependency(id : packageId, version : version))
    }

    void add(String group, String packageId, String version) {
        dependencies.add(new Dependency(group : group, id : packageId, version : version))
    }

    void add(String dependency) {
        dependencies.add(new Dependency(dependency))
    }
}
