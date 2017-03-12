package com.betomorrow.msbuild.tools.nuget

import spock.lang.Specification

class NugetPackCmdTest extends Specification {

    def "should add suffix when not empty"() {
        given:
        def cmd = new NugetPackCmd("nuget", "generated.nuspec", "pre")

        expect:
        assert cmd.build() == ['mono', "nuget", "pack", "generated.nuspec", "-suffix", "pre"]
    }

    def "shouldn't add suffix when is empty"() {
        given:
        def cmd = new NugetPackCmd("nuget", "generated.nuspec", "")

        expect:
        assert cmd.build() == ['mono', "nuget", "pack", "generated.nuspec"]
    }
}
