package com.betomorrow.msbuild.tools.files

import java.nio.file.Path

class FakeFileCopier implements FileCopier {

    @Override
    void copy(String src, String dst) {
        println "Copy $src to ${FileUtils.toUnixPath(dst)}"
    }

    @Override
    void copy(Path src, Path dst) {
        println "Copy $src to $dst"
    }

    @Override
    void move(Path src, Path dst) {
         println "move ${src} to ${dst}"
    }

    @Override
    void moveTo(Path src, Path directory) {
        println "move ${src} to ${directory}"
    }

    @Override
    void download(URL url, Path dst) {
        println "download ${url} to ${dst.toString()}"
    }
}
