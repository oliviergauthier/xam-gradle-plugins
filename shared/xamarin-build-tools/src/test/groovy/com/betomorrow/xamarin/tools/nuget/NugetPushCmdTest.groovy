package com.betomorrow.xamarin.tools.nuget

import spock.lang.Specification

class NugetPushCmdTest extends Specification {

    def "push should adds all parameters key"() {
        given:
        def cmd = new NugetPushCmd("nuget", "package.nupkg", "repository", "123456789")

        expect:
        assert cmd.build() == ['mono', "nuget", "push", "package.nupkg", "-source", "repository", "-apikey", "123456789"]
    }

    def "push shouldn't adds optionals parameters"() {
        expect:
        def cmd = new NugetPushCmd("nuget", "package.nupkg", a, b)
        cmd.build() == c

        where:
        a | b || c
        "repository" | null || ['mono', "nuget", "push", "package.nupkg", "-source", "repository"]
        "repository" | "" || ['mono', "nuget", "push", "package.nupkg", "-source", "repository"]
        null | "123456789" || ['mono', "nuget", "push", "package.nupkg", "-apikey", "123456789"]
        "" | "123456789" || ['mono', "nuget", "push", "package.nupkg", "-apikey", "123456789"]
        null | null || ['mono', "nuget", "push", "package.nupkg"]
        "" | "" || ['mono', "nuget", "push", "package.nupkg"]
    }

}
