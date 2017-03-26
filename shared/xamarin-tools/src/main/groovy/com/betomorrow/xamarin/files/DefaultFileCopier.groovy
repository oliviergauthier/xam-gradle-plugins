package com.betomorrow.xamarin.files

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class DefaultFileCopier implements FileCopier {

    void copy(String src, String dst) {
        copy(Paths.get(src), Paths.get(dst))
    }

    void copy(Path src, Path dst) {
        println "Copy $src.fileName to $dst"
        Files.createDirectories(dst.parent)
        Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING)
    }

    @Override
    void move(Path src, Path dst) {
        Files.createDirectories(dst.parent)
        Files.move(src, dst, StandardCopyOption.REPLACE_EXISTING)
    }

    @Override
    void moveTo(Path src, Path directory) {
        move(src, directory.resolve(src.fileName))
    }

    @Override
    void download(URL url, Path dst) {
        println "download ${url} to ${dst.toString()}"
        File file = dst.toFile()
        file.parentFile.mkdirs()
        def ous = file.newOutputStream()
        ous << url.openStream()
        ous.close()
    }
}
