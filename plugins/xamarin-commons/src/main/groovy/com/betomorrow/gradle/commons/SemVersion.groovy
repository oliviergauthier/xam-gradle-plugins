package com.betomorrow.gradle.commons

class SemVersion {

    private String versionNumber
    private String suffix

    static SemVersion parse(String versionStr) {
        SemVersion sv = new SemVersion()
        int separatorIndex = versionStr.indexOf("-");
        if (separatorIndex > 0) {
            sv.versionNumber = versionStr.substring(0, separatorIndex)
            sv.suffix = versionStr.substring(separatorIndex + 1, versionStr.length())
        } else {
            sv.versionNumber = versionStr
        }
        return sv
    }

    static SemVersion parse(Object object) {
        return parse(object.toString())
    }


    String getVersionNumber() {
        return versionNumber
    }

    String getSuffix() {
        return suffix
    }

    String getFullVersion() {
       if (suffix) {
           return "${versionNumber}-${suffix}"
       } else {
           return versionNumber
       }
    }

}
