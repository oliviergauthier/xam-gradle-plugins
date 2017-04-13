package com.betomorrow.xamarin.assemblyInfo

import com.betomorrow.xamarin.files.FileUtils
import com.betomorrow.xamarin.descriptors.assemblyinfo.AssemblyInfoReader
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

class DefaultAssemblyInfoUpdaterTest extends Specification {

    Path SAMPLE = FileUtils.getResourcePath('AssemblyInfo.cs')

    Path file

    def "setup"() {
        file = Files.createTempFile("assembly_info", "_test")
        Files.copy(SAMPLE, file, StandardCopyOption.REPLACE_EXISTING)
    }

    def "write should update version"() {
        when:
        new DefaultAssemblyInfoUpdater().from(file).withVersion("1.0.2").update()

        then:
        new AssemblyInfoReader().read(file).version == "1.0.2"
        file.text.trim() == '''﻿using System.Reflection;
using System.Runtime.CompilerServices;

// Information about this assembly is defined by the following attributes. 
// Change them to the values specific to your project.

[assembly: AssemblyTitle("SampleApp")]
[assembly: AssemblyDescription("")]
[assembly: AssemblyConfiguration("")]
[assembly: AssemblyCompany("")]
[assembly: AssemblyProduct("")]
[assembly: AssemblyCopyright("(c) Olivier Gauthier")]
[assembly: AssemblyTrademark("")]
[assembly: AssemblyCulture("")]

// The assembly version has the format "{Major}.{Minor}.{Build}.{Revision}".
// The form "{Major}.{Minor}.*" will automatically update the build and revision,
// and "{Major}.{Minor}.{Build}.*" will update just the revision.

[assembly: AssemblyVersion("1.0.2")]

// The following attributes are used to specify the signing key for the assembly, 
// if desired. See the Mono documentation for more information about signing.

//[assembly: AssemblyDelaySign(false)]
//[assembly: AssemblyKeyFile("")]

[assembly: InternalsVisibleTo("Com.Acme.CustomLib.IOS")]
[assembly: InternalsVisibleTo("Com.Acme.CustomLib.Droid")]'''
    }

}
