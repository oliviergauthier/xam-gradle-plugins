package com.betomorrow.msbuild.tools.files

import com.betomorrow.groovy.extensions.UrlExtension

import java.nio.file.Files

class FileDownloader {

    private File downloadDir

    FileDownloader() {
        def tempDir = Files.createTempDirectory("xam-gradle-plugin-${System.nanoTime()}")
        downloadDir = tempDir.toFile()
    }

    FileDownloader(File downloadDir) {
        this.downloadDir = downloadDir
    }

    File download(URL url) {
        def file = new File(downloadDir, UrlExtension.getFileName(url))
        file.parentFile.mkdirs()
        def fos = file.newOutputStream()
        fos << url.openStream()
        fos.close()
        return file
    }

}
