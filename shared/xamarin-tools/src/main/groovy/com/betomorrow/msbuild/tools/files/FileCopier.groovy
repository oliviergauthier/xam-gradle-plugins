package com.betomorrow.msbuild.tools.files

import java.nio.file.Path

interface FileCopier {

    void copy(String src, String dst)
    void copy(Path src, Path dst)
    void move(Path src, Path dst)
    void moveTo(Path src, Path directory)
    void download(URL url, Path dst)

}
