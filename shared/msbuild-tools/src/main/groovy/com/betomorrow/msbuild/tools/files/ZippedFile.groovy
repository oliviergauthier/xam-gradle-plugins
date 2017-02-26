package com.betomorrow.msbuild.tools.files

import java.util.zip.ZipInputStream

class ZippedFile {

    private File source

    ZippedFile(File source) {
        this.source = source
    }

    File unzip(createRootDir = false) {
        return unzip(source.parentFile, createRootDir)
    }

    File unzip(String dst, boolean createRootDir = false) {
        return unzip(new File(dst), createRootDir)
    }

    File unzip(File dst, boolean createRootDir = false) {

        if (createRootDir) {
            dst = new File(dst, source.nameWithoutExtension)
        }

        def result = new ZipInputStream(new FileInputStream(source))

        result.withStream {
            def entry
            while (entry = result.nextEntry) {
                if (!entry.isDirectory()) {
                    def out = new File(dst, entry.name)
                    out.parentFile?.mkdirs()

                    def output = new FileOutputStream(out)

                    output.withStream {
                        int len = 0
                        byte[] buffer = new byte[4096]
                        while ((len = result.read(buffer)) > 0) {
                            output.write(buffer, 0, len)
                        }
                    }
                } else {
                    new File(dst, entry.name).mkdir()
                }
            }
        }

        return dst
    }

}