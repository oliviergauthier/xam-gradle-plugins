package com.betomorrow.xamarin.commands

interface CommandRunner {

    int run(Cmd cmd)

    interface Cmd {
        List<String> build()
    }
}
