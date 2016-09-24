package com.betomorrow.ios.tools.plist

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class FileUtils {

    public static String getResourcePath(String resourceName) {
        return Paths.get(ClassLoader.getSystemResource(resourceName).toURI()).toString();
    }

    public static void copyResource(String resourceName, Path destinationPath) {
        def resourcePath = getResourcePath(resourceName);
        Files.copy(Paths.get(resourcePath), destinationPath, StandardCopyOption.REPLACE_EXISTING);
    }

    public static void copyResource(String resourceName, File destinationFile) {
        copyResource(resourceName, destinationFile.toPath());
    }
}
