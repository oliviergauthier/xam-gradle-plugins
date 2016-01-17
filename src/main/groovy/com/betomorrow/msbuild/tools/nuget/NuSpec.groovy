package com.betomorrow.msbuild.tools.nuget

/**
 * Created by olivier on 17/01/16.
 */
class NuSpec {

    /**
     * Nuspec Source file
     */
    String source
    String output

    String packageId
    String version
    String authors
    String owner
    String licenseUrl
    String projectUrl
    String iconUrl
    boolean requireLicenceAcceptance
    String description
    String releaseNotes
    String copyright
    String tags

    List<Dependency> dependencies = []

    public void addDependency(String packageId, String version) {
        dependencies.add(new Dependency(id : packageId, version : version))
    }

    class Dependency {
        String id
        String version
    }

    class Assembly {
        String assemblyPath
        String targetDirectory
    }

    /**
     * Edit or Generate nuspec file and returns file path
     * @return
     */
    public String generate() {
        if (!source) {
            // generate new one
        }

        if (!output) {
            output = source
        }

        return output
    }
}
