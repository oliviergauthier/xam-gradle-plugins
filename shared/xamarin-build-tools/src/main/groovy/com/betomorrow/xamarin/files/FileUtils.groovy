package com.betomorrow.xamarin.files

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption


class FileUtils {

    static Path getResourcePath(String resourceName) {
        return Paths.get(ClassLoader.getSystemResource(resourceName).toURI())
    }

    static String toUnixPath(String path) {
        return path.replace("\\", "/")
    }

    static void copyResource(String resourceName, Path destinationPath) {
        def resourcePath = getResourcePath(resourceName)
        Files.copy(resourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING)
    }

    static void copyResource(String resourceName, File destinationFile) {
        copyResource(resourceName, destinationFile.toPath())
    }


}
