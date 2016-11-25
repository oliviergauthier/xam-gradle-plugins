package com.betomorrow.msbuild.tools.ios.plist

import groovy.transform.Canonical

@Canonical
class InfoPlist {

    String bundleShortVersion
    String bundleVersion
    String bundleIdentifier

}
