package com.betomorrow.ios.tools.plist

class FakeInfoPlistWriter implements InfoPlistWriter {

    @Override
    void write(InfoPlist plist, String destination) {
        println "Update Inf.plist, set bundleShortVersion=${plist.bundleShortVersion}, bundleVersion=${plist.bundleVersion}, " +
                "package=${plist.bundleIdentifier} to ${destination}"
    }

}
