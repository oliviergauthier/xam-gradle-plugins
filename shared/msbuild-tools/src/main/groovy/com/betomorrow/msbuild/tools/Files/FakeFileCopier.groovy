package com.betomorrow.msbuild.tools.Files

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.nio.file.Path

class FakeFileCopier implements FileCopier {

    private Logger logger = LoggerFactory.getLogger(FileCopier)

    @Override
    void replace(String src, String dst) {
        println "Copy $src to ${FileUtils.toUnixPath(dst)}";
    }

    @Override
    void replace(Path src, Path dst) {
        println "Copy $src to $dst";
    }

}
