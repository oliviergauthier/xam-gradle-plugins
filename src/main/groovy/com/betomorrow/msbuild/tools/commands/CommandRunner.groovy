package com.betomorrow.msbuild.tools.commands

/**
 * Created by olivier on 17/01/16.
 */
interface CommandRunner {

    def boolean dryRun = false;

    public int run(Cmd cmd);

    interface Cmd {
        public Collection<String> build();
    }
}
