package com.betomorrow.ios.plist

import org.junit.Test

class DefaultInfoPlistReaderTest {


    String SAMPLE = ClassLoader.getSystemResource('Info.plist').file

    DefaultInfoPlistReader reader = new DefaultInfoPlistReader()

    @Test
    void testReadShouldReturnsCompleteInfoPlist() {
        InfoPlist infoPlist = reader.read(SAMPLE)

        assert infoPlist.bundleIdentifier == "com.sample.crossapp"
        assert infoPlist.bundleShortVersion == "2.0"
        assert infoPlist.bundleVersion == "3.0"
    }
}
