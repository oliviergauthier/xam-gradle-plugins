package com.betomorrow.xamarin.assemblyInfo

import com.betomorrow.xamarin.descriptors.assemblyinfo.AssemblyInfo
import com.betomorrow.xamarin.descriptors.assemblyinfo.AssemblyInfoReader
import com.betomorrow.xamarin.descriptors.assemblyinfo.AssemblyInfoWriter

import java.nio.file.Path

class DefaultAssemblyInfoUpdater implements AssemblyInfoUpdater {

    @Override
    AssemblyInfoUpdater.AssemblyInfoUpdaterTask from(Path path) {
        return new Updater(path)
    }

    private class Updater implements AssemblyInfoUpdater.AssemblyInfoUpdaterTask {

        protected AssemblyInfoReader reader = new AssemblyInfoReader()
        protected AssemblyInfoWriter writer = new AssemblyInfoWriter()

        private Path path
        private String version

        Updater(Path path) {
            this.path = path
        }

        @Override
        AssemblyInfoUpdater.AssemblyInfoUpdaterTask withVersion(String version) {
            this.version = version
            return this
        }

        @Override
        void update() {
            AssemblyInfo info = reader.read(path)
            info.version = version
            writer.write(info)
        }
    }


}
