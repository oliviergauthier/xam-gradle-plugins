# Xamarin Library Plugin 

This plugin provides the ability to build PCL Libraries and Nuget Packages with Gradle. 
It can be used to integrate build in Continus Integration System like Jenkins

# Tasks

- build : build library with xbuild
- package : create nuget package
- install : install package locally
- deploy : deploy package on nuget server like (NugetServer, Sonatype Nexus, ...)

# Configuration

**Notes :**
* By default, use first solution file found in project folder
* In dsl : `packages.*.assemblies.target.includes` , you can specify assembly path or csproj name references in solution. The plugin will resolve assemblies of project

```
name = "Xam.ACME.CrossLib"
version = "1.0.0-SNAPSHOT"

library {
    solution      // default : first sln file found in current folder
    configuration // default : Release
}

nuspec {

     // Global nuspec properties

    version // default : version number from project.version
    suffix // default : suffix version from project.version
    authors
    owners
    licenseUrl
    projectUrl
    iconUrl
    requireLicenseAcceptance
    description
    releaseNotes
    copyright
    tags

    packages {
        SampleLib {

            // Following properties are inherited from global nuspec properties

            version
            suffix
            authors
            owners
            licenseUrl
            projectUrl
            iconUrl
            requireLicenseAcceptance
            description
            releaseNotes
            copyright
            tags

            dependencies {
                dependency "Xamarin.Forms:[1.4.3,)" // <Name>:<Version Restriction>
                [...]
            }

            assemblies {
                target {
                    dest "lib/portable-net45+wp8+wpa81+win8+MonoAndroid10+MonoTouch10+Xamarin.iOS10" // directory in nuget package
                    includes "CrossLib.Abstractions", "CrossLib" // Project assemblies to include
                }
                [...]
            }
        }
    }
}

deploy {
    local {
        path "fake/directory"   // install directory, default : ~/.nuget/packages
        format "nuget2"         // nuget format (nuget2|nuget3), default : nuget3
    }
    remote {
        url = "http://fake.url" // remote repository url
        apiKey = "12345789"     // nuget apiKey
    }
}

```
