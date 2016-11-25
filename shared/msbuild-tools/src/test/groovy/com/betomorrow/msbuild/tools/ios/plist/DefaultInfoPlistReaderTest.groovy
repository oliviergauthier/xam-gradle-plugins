package com.betomorrow.msbuild.tools.ios.plist

class DefaultInfoPlistReaderTest extends GroovyTestCase {


    String SAMPLE = ClassLoader.getSystemResource('Info.plist').file

    DefaultInfoPlistReader reader = new DefaultInfoPlistReader()

    void testReadShouldReturnsCompleteInfoPlist() {
        InfoPlist infoPlist = reader.read(SAMPLE)

        assert infoPlist.bundleIdentifier == "com.sample.crossapp"
        assert infoPlist.bundleShortVersion == "2.0"
        assert infoPlist.bundleVersion == "3.0"
    }
}
