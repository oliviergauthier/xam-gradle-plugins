package com.betomorrow.msbuild.tools.nunit

import com.betomorrow.msbuild.tools.commands.CommandRunner
import spock.lang.Specification

import java.nio.file.Paths

class DefaultNunitConsoleTest extends Specification {

    CommandRunner runner
    NUnitConsole3Downloader downloader

    def "setup"() {
        runner = Mock()
        downloader = Mock()
    }

    def "run should call system command"() {
        given:
        def nunitConsole = new DefaultNunitConsole()
        nunitConsole.runner = runner
        nunitConsole.downloader = downloader
        def cmd

        when:
        nunitConsole.run(['a.dll', 'b.dll'], 'nunit2')

        then:
        1 * downloader.download(_) >> "sample/path"
        1 * runner.run(_) >> { arg ->
            cmd = arg[0]
            return 1
        }
        assert cmd.build() == ['mono', Paths.get('sample/path/nunit3-console.exe').toString(),
                               'a.dll', 'b.dll', '--result:TestResult.xml;format=nunit2']

    }
}
