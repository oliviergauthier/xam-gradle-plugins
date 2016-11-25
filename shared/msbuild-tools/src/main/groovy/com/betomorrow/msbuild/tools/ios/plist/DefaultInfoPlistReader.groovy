package com.betomorrow.msbuild.tools.ios.plist

class DefaultInfoPlistReader implements InfoPlistReader {

    private PlistXmlParser parser = new PlistXmlParser()

    @Override
    InfoPlist read(String source) {

        def root = parser.parse(source)

        InfoPlist infoPlist = new InfoPlist()
        infoPlist.bundleIdentifier = root.getValueNode("CFBundleIdentifier").text()
        infoPlist.bundleShortVersion = root.getValueNode("CFBundleShortVersionString").text()
        infoPlist.bundleVersion = root.getValueNode("CFBundleVersion").text()

        return infoPlist
    }

}
