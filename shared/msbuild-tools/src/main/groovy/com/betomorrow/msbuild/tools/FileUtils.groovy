package com.betomorrow.msbuild.tools

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class FileUtils {

    static String getResourcePath(String resourceName) {
        return Paths.get(ClassLoader.getSystemResource(resourceName).toURI()).toString();
    }

    static void replace(String src, String dst) {
        Files.copy(Paths.get(src), Paths.get(dst), StandardCopyOption.REPLACE_EXISTING)
    }

    static String toUnixPath(String path) {
        return path.replace("\\", "/");
    }
}
