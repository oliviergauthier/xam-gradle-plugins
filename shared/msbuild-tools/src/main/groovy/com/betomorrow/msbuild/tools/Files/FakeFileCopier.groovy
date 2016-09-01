package com.betomorrow.msbuild.tools.Files

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class FakeFileCopier implements FileCopier {

    private Logger logger = LoggerFactory.getLogger(FileCopier)

    @Override
    void replace(String src, String dst) {
        println "Copy $src to $dst";
    }
}
