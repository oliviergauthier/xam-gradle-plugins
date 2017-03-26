package com.betomorrow.ios.plist

import com.betomorrow.msbuild.tools.files.FileUtils
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class DefaultInfoPlistWriterTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder()

    private File copy
    private InfoPlistWriter writer
    private InfoPlistReader reader

    @Before
    void setUp() {
        copy = testFolder.newFile("copy.plist")
        FileUtils.copyResource('Info.plist', copy.toPath())
        writer = new DefaultInfoPlistWriter()
        reader = new DefaultInfoPlistReader()
    }

    @Test
    void testWriteShouldUpdateData() {
        def infoPlist = new InfoPlist(
                bundleShortVersion: "5.0",
                bundleVersion: "4.0",
                bundleIdentifier:"com.acme.my.sample.app" )

        writer.write(infoPlist, copy.path)

        InfoPlist saved = reader.read(copy.path)

        assert saved == infoPlist
    }
}
