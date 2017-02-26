package com.betomorrow.msbuild.tools.files

class FileExtension {

    static String getNameWithoutExtension(final File self) {
        String name = self.name;
        int lastDotIndex = name.lastIndexOf(".")
        if (lastDotIndex <= 0) {
            return name
        }

        return name.substring(0, lastDotIndex)
    }
}
