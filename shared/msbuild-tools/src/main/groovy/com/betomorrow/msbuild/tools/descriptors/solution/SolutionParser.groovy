package com.betomorrow.msbuild.tools.descriptors.solution

import groovy.util.logging.Slf4j

import java.nio.file.Files
import java.nio.file.Path
import java.util.regex.Matcher
import java.util.regex.Pattern

@Slf4j
class SolutionParser {

    private static final String START_TAG = 'Project'
    private static final String END_TAG = 'EndProject'

    Pattern projectPattern = ~/(?m)Project[^=]*=\s"([^"]*)",\s"([^"]*)",\s"([^"]*)"\s*\nEndProject/
    Pattern configurationPattern = ~/(.*)/

    List<SolutionProject> parse(Path path) {
        List<SolutionProject> solutions = new ArrayList<>()
        String content = readFully(path)
        def projectLines = findProjectLines(content);
        projectLines.forEach { it ->
            Matcher matcher = projectPattern.matcher(it);
            def matches = matcher.matches();
            def groupCount = matcher.groupCount();
            if (groupCount == 3) {
                def projectName = matcher.group(1);
                def projectPath = matcher.group(2).replace('\\', '/');
                def projectHash = matcher.group(3);
                def configurations = findConfifurations(projectHash, content);
                solutions.add(new SolutionProject(projectName, projectPath, configurations));
            }
        }

        return solutions
    }

    private String readFully(Path p) {
        try {
            byte[] allBytes = Files.readAllBytes(p);
            return new String(allBytes, "utf-8");
        } catch (Exception e) {
            println("Can't open path " + p.toAbsolutePath().toString())
            throw  e;
        }
    }

    private List<String> findProjectLines(String content) {
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

    private List<String> findConfigurationLines(String projectHash, String content) {
        List<String> lines = new ArrayList<>();

        int endTagIndex = 0;
        String startTag = "${projectHash}.";
        String endTag = "\n";

        while(true) {
            int startTagIndex = content.indexOf(startTag, endTagIndex)
            endTagIndex = content.indexOf(endTag, startTagIndex)
            if (startTagIndex > 0 && endTagIndex > 0) {
                endTagIndex += endTag.length()
                lines.add(content.substring(startTagIndex, endTagIndex));
            } else {
                break;
            }
        }

        return lines;
    }

    private Collection<BuildConfiguration> findConfifurations(String projectHash, String content) {
        Set<BuildConfiguration> result = new HashSet<>()
        def lines = findConfigurationLines(projectHash, content)
        lines.forEach {
            try {
                def pair = it.tokenize('=')[0].trim().tokenize('.')[1].tokenize('|')
                result.add(new BuildConfiguration(pair[0], pair[1]))
            } catch (Exception e) {
                log.error("Can't parse configuration line ${it}", e);
            }
        }
        return result
    }
}
