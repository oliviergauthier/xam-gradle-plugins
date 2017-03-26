package com.betomorrow.xamarin.tools.nunit

import com.betomorrow.groovy.extensions.UrlExtension
import com.betomorrow.xamarin.Version
import com.betomorrow.xamarin.files.FileDownloader
import com.betomorrow.xamarin.files.ZippedFile

class NUnitConsole3Downloader {

    String urlTemplate = "https://github.com/nunit/nunit-console/releases/download/VERSION_BASE/NUnit.Console-FULL_VERSION.zip"
    FileDownloader downloader = new FileDownloader()

    String download(String version = "3.6.0") {
        def url = getUrl(version)
        def dst = new File(nugetCacheDir, UrlExtension.getFileNameWithoutExtension(url))
        if (!dst.exists()) {
            def file = downloader.download(url)
            new ZippedFile(file).unzip(dst)
        }
        return dst.absolutePath
    }

    String getNugetCacheDir() {
        def baseDir = System.getProperty('user.home')
        return "${baseDir}/.nuget/caches/"
    }

    URL getUrl(String version) {
        Version v = new Version(version)
        return new URL(urlTemplate.replaceAll('FULL_VERSION', v.full).replaceAll('VERSION_BASE', v.base))
    }

}
