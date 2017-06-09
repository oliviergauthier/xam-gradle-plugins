package com.betomorrow.xamarin.descriptors.assemblyinfo

import com.betomorrow.xamarin.files.FileUtils
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path

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
        info.title = "aTitle"
        info.configuration = "aConfiguration"
        info.description = "aDescription"
        info.product = "aProduct"
        info.company = "aCompany"
        info.copyright = "aCopyright"
        info.trademark = "aTradmark"

        writer.write(info, p)

        then:
        def result = reader.read(p)
        result.version == "2.0.*"
        result.title == "aTitle"
        info.configuration == "aConfiguration"
        info.description == "aDescription"
        info.product == "aProduct"
        info.company == "aCompany"
        info.copyright == "aCopyright"
        info.trademark == "aTradmark"
    }

}
