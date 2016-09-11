package com.betomorrow.msbuild.tools;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by olivi on 24/08/2016.
 */
public class FileUtils {

    public static String getResourcePath(String resourceName) {
        try {
            return Paths.get(ClassLoader.getSystemResource(resourceName).toURI()).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
