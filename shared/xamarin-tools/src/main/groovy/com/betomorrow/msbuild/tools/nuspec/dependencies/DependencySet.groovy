package com.betomorrow.msbuild.tools.nuspec.dependencies

class DependencySet implements Set<Dependency> {

    @Delegate Set<Dependency> dependencies = new HashSet<>()

    DependencySet() {}

    DependencySet(List<Dependency> deps) {
        dependencies.addAll(deps)
    }

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
