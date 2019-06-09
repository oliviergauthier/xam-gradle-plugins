package com.betomorrow.xamarin.commands

interface CommandRunner {

    void setVerbose(boolean verbose)

    int run(Cmd cmd)

    interface Cmd {
        List<String> build()
    }
}
