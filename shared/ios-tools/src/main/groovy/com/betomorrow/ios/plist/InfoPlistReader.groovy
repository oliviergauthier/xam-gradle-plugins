package com.betomorrow.ios.plist

import java.nio.file.Path

interface InfoPlistReader {

    InfoPlist read(String source)

    InfoPlist read(Path source)

}