package com.betomorrow.msbuild.tools.files

class UrlExtension {

    static String getFileName(final URL self) {
        return new File(self.file).name
    }

    static String getFileNameWithoutExtension(final URL self) {
        return new File(self.file).nameWithoutExtension
    }

}
