package com.betomorrow.msbuild.tools.Files

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class DefaultFileCopier implements FileCopier {

    void replace(String src, String dst) {
        Files.copy(Paths.get(src), Paths.get(dst), StandardCopyOption.REPLACE_EXISTING)
    }

}
