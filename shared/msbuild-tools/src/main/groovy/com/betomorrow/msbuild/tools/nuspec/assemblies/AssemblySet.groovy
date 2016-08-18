package com.betomorrow.msbuild.tools.nuspec.assemblies

class AssemblySet implements Set<Assembly> {

    @Delegate Set<Assembly> dependencies = new HashSet<>();

}
