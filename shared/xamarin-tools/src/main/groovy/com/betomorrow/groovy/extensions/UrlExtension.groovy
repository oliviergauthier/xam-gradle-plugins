package com.betomorrow.groovy.extensions

class UrlExtension {

    static String getFileName(final URL self) {
        return new File(self.file).name
    }

    static String getFileNameWithoutExtension(final URL self) {
        return FileExtension.getNameWithoutExtension(new File(self.file))
    }

}
