package com.betomorrow.msbuild.tools.files

import java.nio.file.Path

class FakeFileCopier implements FileCopier {

    @Override
    void replace(String src, String dst) {
        println "Copy $src to ${FileUtils.toUnixPath(dst)}"
    }

    @Override
    void replace(Path src, Path dst) {
        println "Copy $src to $dst"
    }

}
