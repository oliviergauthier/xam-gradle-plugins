package com.betomorrow.xamarin.assemblyInfo

import com.betomorrow.xamarin.descriptors.assemblyinfo.AssemblyInfo
import com.betomorrow.xamarin.descriptors.assemblyinfo.AssemblyInfoReader
import com.betomorrow.xamarin.descriptors.assemblyinfo.AssemblyInfoWriter

import java.nio.file.Path

class DefaultAssemblyInfoUpdater implements AssemblyInfoUpdater {

    @Override
    AssemblyInfoUpdaterTask from(Path path) {
        return new Updater(path)
    }

    private class Updater implements AssemblyInfoUpdaterTask {

        protected AssemblyInfoWriter writer = new AssemblyInfoWriter()

        private Path path
        private String version

        Updater(Path path) {
            this.path = path
        }

        @Override
        AssemblyInfoUpdaterTask withVersion(String version) {
            this.version = version
            return this
        }

        @Override
        void update() {
            AssemblyInfo info = new AssemblyInfo(path.toAbsolutePath().toString(), [:])
            info.version = version
            writer.write(info)
        }
    }


}
