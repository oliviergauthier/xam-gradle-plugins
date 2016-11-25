package com.betomorrow.msbuild.tools.files

import java.nio.file.Path
import java.nio.file.Paths

class FileUtils {

    static Path getResourcePath(String resourceName) {
        return Paths.get(ClassLoader.getSystemResource(resourceName).toURI())
    }

    static String toUnixPath(String path) {
        return path.replace("\\", "/")
    }

}
