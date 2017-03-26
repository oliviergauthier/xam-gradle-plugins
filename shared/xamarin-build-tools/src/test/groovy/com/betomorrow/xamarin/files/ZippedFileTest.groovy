package com.betomorrow.xamarin.files

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import java.nio.file.Path

class ZippedFileTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder()

    @Test
    void testUnzipFile() {

        Path source = FileUtils.getResourcePath('sample.zip')

        new ZippedFile(source.toFile()).unzip(testFolder.root)

        Path sampleDir = testFolder.getRoot().toPath().resolve('sample')
        Path test1 = sampleDir.resolve('test1.txt')
        Path test2 = sampleDir.resolve('test2.txt')
        Path test3 = sampleDir.resolve('subdir').resolve('test3.txt')

        assert sampleDir.toFile().exists() && sampleDir.toFile().isDirectory()
        assert test1.toFile().exists()
        assert test2.toFile().exists()
        assert test3.toFile().exists()
    }

}
