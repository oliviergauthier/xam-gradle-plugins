package com.betomorrow.msbuild.tools.Files

import java.nio.file.Path

interface FileCopier {

    void replace(String src, String dst)
    void replace(Path src, Path dst)

}
