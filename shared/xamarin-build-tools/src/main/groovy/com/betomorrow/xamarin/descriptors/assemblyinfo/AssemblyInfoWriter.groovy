package com.betomorrow.xamarin.descriptors.assemblyinfo

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.regex.Pattern

class AssemblyInfoWriter {

    Pattern pattern = Pattern.compile("\\[assembly:\\s*([A-z]*)\\s*\\(\\s*\"([^\"]*)\"\\s*\\)\\s*\\]")

    void write(AssemblyInfo info) {
        write(info, info.path)
    }

    void write(AssemblyInfo info, String output) {
        write(info, Paths.get(output))
    }

    void write(AssemblyInfo info, Path output) {

        List<String> lines = Files.readAllLines(Paths.get(info.path))

        List<String> newLines = []

        lines.each {
            newLines.add(it.replaceAll(pattern) { all, key, value ->
                def sanitizedKey = key.trim()
                "[assembly: $sanitizedKey(\"${info.getValue(sanitizedKey, value)}\")]"
            })
        }

        Files.write(output, newLines)
    }

}
