package com.betomorrow.msbuild.tools.commands

interface CommandRunner {

    int run(Cmd cmd);

    interface Cmd {
        public List<String> build();
    }
}
