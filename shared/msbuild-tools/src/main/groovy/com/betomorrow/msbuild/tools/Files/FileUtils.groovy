package com.betomorrow.msbuild.tools.Files

import java.nio.file.Paths

class FileUtils {

    static String getResourcePath(String resourceName) {
        return Paths.get(ClassLoader.getSystemResource(resourceName).toURI()).toString();
    }

    static String toUnixPath(String path) {
        return path.replace("\\", "/");
    }
}
