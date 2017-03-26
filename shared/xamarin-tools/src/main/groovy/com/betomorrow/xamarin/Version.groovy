package com.betomorrow.xamarin

class Version {

    String version

    Version(String version) {
        this.version = version
    }

    String getFull() {
        return version;
    }

    String getBase() {
        int lastDotIndex = version.lastIndexOf(".")
        return version.substring(0, lastDotIndex)
    }
}
