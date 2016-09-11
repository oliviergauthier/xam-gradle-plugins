package com.betomorrow.android.tools.manifest

import groovy.transform.PackageScope

class AndroidManifest {

    private String versionCode;
    private String versionName;
    private String packageName;

    String getVersionCode() {
        return versionCode;
    }

    String getVersionName() {
        return versionName;
    }

    String getPackageName() {
        return packageName;
    }

    @PackageScope
    void setVersionCode(String versionCode) {
        this.versionCode = versionCode
    }

    @PackageScope
    void setVersionName(String versionName) {
        this.versionName = versionName
    }

    @PackageScope
    void setPackageName(String packageName) {
        this.packageName = packageName
    }
}
