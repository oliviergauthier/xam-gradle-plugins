package com.betomorrow.xamarin.tools.nuspec.assemblies

class AssemblySet implements Set<Assembly> {

    @Delegate List<Assembly> assemblies = new ArrayList<>()

    AssemblySet(List<Assembly> assemblies) {
        if (assemblies != null) {
            this.assemblies.addAll(assemblies)
        }
    }

}
