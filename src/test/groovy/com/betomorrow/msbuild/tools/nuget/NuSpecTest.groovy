package com.betomorrow.msbuild.tools.nuget

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

/**
 * Created by olivier on 17/01/16.
 */
class NuSpecTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    def SAMPLE_NUSPEC = ClassLoader.getSystemResource('Sample.nuspec').file;
    def output;

    NuSpec nuspec

    @Before
    public void setUp() {
        output = testFolder.newFile("out.nuspec")

        nuspec = new NuSpec(SAMPLE_NUSPEC)
        nuspec.output = output.getAbsolutePath()
    }

    @Test
    public void testReadAndWriteWithoutModification() {
        nuspec.process()

        assert new XmlSlurper().parse(SAMPLE_NUSPEC) == new XmlSlurper().parse(output)
    }

    @Test
    public void testUpdateId() {
        nuspec.packageId = "toto"
        nuspec.process()

        assert field("id") == "toto"
    }

    @Test
    public void testUpdateVersion() {
        nuspec.version = "toto"
        nuspec.process()

        assert field("version") == "toto"
    }


    @Test
    public void testUpdateAuthor() {
        nuspec.authors = "toto"
        nuspec.process()

        assert field("authors") == "toto"
    }

    @Test
    public void testUpdateOwner() {
        nuspec.owners = "toto"
        nuspec.process()

        assert field("owners") == "toto"
    }


    @Test
    public void testUpdateLicenceUrl() {
        nuspec.licenseUrl = "toto"
        nuspec.process()

        assert field("licenseUrl") == "toto"
    }

    @Test
    public void testUpdateProjectUrl() {
        nuspec.projectUrl = "toto"
        nuspec.process()

        assert field("projectUrl") == "toto"
    }

    @Test
    public void testUpdateIconUrl() {
        nuspec.iconUrl = "toto"
        nuspec.process()

        assert field("iconUrl") == "toto"
    }

    @Test
    public void testUpdateRequireLicenseAcceptance() {
        nuspec.requireLicenseAcceptance = true
        nuspec.process()

        assert field("requireLicenseAcceptance") == "true"
    }

    private String field(String name) {
        return new XmlSlurper().parse(output).metadata."${name}"
    }

}
