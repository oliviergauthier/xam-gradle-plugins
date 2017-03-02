package com.betomorrow.xamarin.descriptors.assemblyinfo

import com.betomorrow.msbuild.tools.files.FileUtils
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class AssemblyInfoWriterTest extends Specification {

    def SAMPLE = FileUtils.getResourcePath('AssemblyInfo.cs').toString()

    AssemblyInfoReader reader
    AssemblyInfoWriter writer

    def "setup"() {
        reader = new AssemblyInfoReader()
        writer = new AssemblyInfoWriter()
    }

    def "write should update version"() {
        given:
        AssemblyInfo info = reader.read(SAMPLE)
        Path p = Files.createTempFile("prefix", "suffix")

        when:
        info.version = "2.0.*"
        writer.write(info, p)

        then:
        reader.read(p).version == "2.0.*"
    }

}
