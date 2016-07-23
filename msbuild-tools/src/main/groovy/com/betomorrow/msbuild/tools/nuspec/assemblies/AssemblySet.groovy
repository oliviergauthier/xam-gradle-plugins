package com.betomorrow.msbuild.tools.nuspec.assemblies
/**
 * Created by olivier on 29/01/16.
 */
class AssemblySet implements Set<Assembly> {

    @Delegate Set<Assembly> dependencies = new HashSet<>();

}
