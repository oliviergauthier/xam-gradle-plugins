package com.betomorrow.xamarin.assemblyInfo

import com.betomorrow.msbuild.tools.files.FileUtils
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
    }

}
