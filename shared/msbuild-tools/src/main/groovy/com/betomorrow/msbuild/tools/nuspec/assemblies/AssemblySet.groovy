package com.betomorrow.msbuild.tools.nuspec.assemblies

class AssemblySet implements Set<Assembly> {

    @Delegate Set<Assembly> assemblies = new HashSet<>()

    AssemblySet(List<Assembly> assemblies) {
        this.assemblies.addAll(assemblies)
    }

}
