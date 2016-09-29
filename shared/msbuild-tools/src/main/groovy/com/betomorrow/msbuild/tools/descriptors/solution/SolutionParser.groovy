package com.betomorrow.msbuild.tools.descriptors.solution

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.regex.Matcher
import java.util.regex.Pattern

class SolutionParser {

    private static final String START_TAG = 'Project'
    private static final String END_TAG = 'EndProject'

    Pattern pattern = ~/(?m)Project[^=]*=\s"([^"]*)",\s"([^"]*)",\s"([^"]*)"\s*\nEndProject/

    public List<SolutionProject> parse(Path path) {
        List<SolutionProject> solutions = new ArrayList<>()
        String content = readFully(path)
        def projectLines = findProjectLines(content);
        projectLines.forEach { it ->
            Matcher matcher = pattern.matcher(it);
            def matches = matcher.matches()
            def groupCount = matcher.groupCount()
            if (groupCount == 3) {
                def projectName = matcher.group(1)
                def projectPath = matcher.group(2).replace('\\', '/')
                solutions.add(new SolutionProject(projectName, projectPath))
            }
        }

        return solutions
    }

    private static String readFully(Path p) {
        try {
            byte[] allBytes = Files.readAllBytes(p);
            return new String(allBytes, "utf-8");
        } catch (Exception e) {
            println("Can't open path " + p.toAbsolutePath().toString())
            throw  e;
        }
    }

    private static List<String> findProjectLines(String content) {
        List<String> lines = new ArrayList<>()

        int endTagIndex = 0;

        while(true) {
            int startTagIndex = content.indexOf(START_TAG, endTagIndex)
            endTagIndex = content.indexOf(END_TAG, startTagIndex)
            if (startTagIndex > 0 && endTagIndex > 0) {
                endTagIndex += END_TAG.length()
                lines.add(content.substring(startTagIndex, endTagIndex));
            } else {
                break;
            }
        }

        return lines;
    }
}
