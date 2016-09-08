package com.betomorrow.android.tools

interface AndroidManifestEditor {

    String versionCode
    String versionName
    String packageName

    void write(String destination);
}
