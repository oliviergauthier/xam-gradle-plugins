package com.betomorrow.xamarin.descriptors.assemblyinfo

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.regex.Matcher
import java.util.regex.Pattern

class AssemblyInfoReader {

    Pattern pattern = Pattern.compile("\\[assembly:\\s([A-z]*)\\(\"([^\"]*)\"\\)\\]")

    AssemblyInfo read(String path) {
        return read(Paths.get(path))
    }

    AssemblyInfo read(Path path) {
        Map<String, String> info = [:]

        List<String> lines = Files.readAllLines(path)

        lines.each {
            Matcher matcher = pattern.matcher(it)
            if (matcher.matches()) {
                info[matcher.group(1)] = matcher.group(2)
            }
        }

        return new AssemblyInfo(path.toString(), info)
    }

}
