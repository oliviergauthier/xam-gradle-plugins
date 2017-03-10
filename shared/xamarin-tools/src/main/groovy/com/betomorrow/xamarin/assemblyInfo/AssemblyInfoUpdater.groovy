package com.betomorrow.xamarin.assemblyInfo

import java.nio.file.Path

interface AssemblyInfoUpdater {

    AssemblyInfoUpdaterTask from(Path path)

    static interface AssemblyInfoUpdaterTask {

        AssemblyInfoUpdaterTask withVersion(String version)

        void update()
    }
}

