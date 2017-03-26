package com.betomorrow.xamarin.commands

class SystemCommandRunner implements CommandRunner {

    def workingDirectory =  new File(System.properties['user.dir'].toString())

    @Override
    int run(CommandRunner.Cmd cmd) {
        println "Execute command : ${cmd.build().join(" ")}"

        StringBuilder out = new StringBuilder()
        StringBuilder err = new StringBuilder()

        def process = new ProcessBuilder(cmd.build() as String[])
                .directory(workingDirectory)
                .start()

        process.consumeProcessOutput(out, err)

        process.waitFor()

        System.out << out
        System.err << err

        return process.exitValue()
    }
}
