package com.betomorrow.xamarin.descriptors.assemblyinfo

import com.betomorrow.msbuild.tools.files.FileUtils
import spock.lang.Specification

class AssemblyInfoReaderTest extends Specification {

    def SAMPLE = FileUtils.getResourcePath('AssemblyInfo.cs').toString()

    AssemblyInfoReader reader

    def "setup"() {
        reader = new AssemblyInfoReader()
    }

    def "read should parse version"() {
        expect:
        reader.read(SAMPLE).version == "1.0.*"
    }

}
