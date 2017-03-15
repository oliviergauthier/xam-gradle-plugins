package com.betomorrow.msbuild.tools.nuget

import spock.lang.Specification

class NugetPackCmdTest extends Specification {

    def "should add suffix when not empty"() {
        given:
        def cmd = new NugetPackCmd("nuget", "generated.nuspec", "pre", "dist")

        expect:
        assert cmd.build() == ['mono', "nuget", "pack", "generated.nuspec", "-suffix", "pre", "-outputdirectory", "dist"]
    }

    def "shouldn't add suffix when is empty"() {
        given:
        def cmd = new NugetPackCmd("nuget", "generated.nuspec", "", "dist")

        expect:
        assert cmd.build() == ['mono', "nuget", "pack", "generated.nuspec", "-outputdirectory", "dist"]
    }
}
