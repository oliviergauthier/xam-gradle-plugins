package com.betomorrow.msbuild.tools.nunit

import com.betomorrow.msbuild.tools.commands.CommandRunner
import spock.lang.Specification

class DefaultNunitConsoleTest extends Specification {

    CommandRunner runner

    def "setup"() {
        runner = Mock()
    }

    def "run should call system command"() {
        given:
        def nunitConsole = new DefaultNunitConsole()
        nunitConsole.runner = runner
        def cmd

        when:
        nunitConsole.run(['a.dll', 'b.dll'], 'nunit2')

        then:
        1 * runner.run(_) >> { arg ->
            cmd = arg[0]
            return 1
        }
        assert cmd.build() == ['nunit-console.exe', 'a.dll', 'b.dll', '--result:TestResult.xml;format=nunit2']

    }
}
