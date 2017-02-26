package com.betomorrow.msbuild.tools.nunit

import spock.lang.Specification

class NunitConsole3DownloaderIT extends Specification {

    NUnitConsole3Downloader downloader

    def "setup"() {
        downloader = new NUnitConsole3Downloader()
    }

    def "test download should extract file"() {
        given:

        when:
        String runnerPath = downloader.download()

        then:
        println(runnerPath)
        def expected = System.getProperty("user.home") + File.separator +
                ".nuget" + File.separator + "cache"+ File.separator + "NUnit.Console-3.6.0"
        assert runnerPath == expected
    }
}
