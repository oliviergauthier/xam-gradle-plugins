package com.betomorrow.msbuild.tools.files

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class DefaultFileCopier implements FileCopier {

    void replace(String src, String dst) {
        replace(Paths.get(src), Paths.get(dst))
    }

    void replace(Path src, Path dst) {
        println "Copy $src.fileName to $dst"
        Files.createDirectories(dst.parent)
        Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING)
    }

}
