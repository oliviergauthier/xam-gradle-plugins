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
        nuspec.packageId = "new packageId"
        nuspec.process()

        assert field("id") == "new packageId"
    }

    @Test
    public void testUpdateVersion() {
        nuspec.version = "new version"
        nuspec.process()

        assert field("version") == "new version"
    }


    @Test
    public void testUpdateAuthor() {
        nuspec.authors = "new authors"
        nuspec.process()

        assert field("authors") == "new authors"
    }

    @Test
    public void testUpdateOwner() {
        nuspec.owners = "new owners"
        nuspec.process()

        assert field("owners") == "new owners"
    }


    @Test
    public void testUpdateLicenceUrl() {
        nuspec.licenseUrl = "new licence url"
        nuspec.process()

        assert field("licenseUrl") == "new licence url"
    }

    @Test
    public void testUpdateProjectUrl() {
        nuspec.projectUrl = "new project url"
        nuspec.process()

        assert field("projectUrl") == "new project url"
    }

    @Test
    public void testUpdateIconUrl() {
        nuspec.iconUrl = "new icon url"
        nuspec.process()

        assert field("iconUrl") == "new icon url"
    }

    @Test
    public void testUpdateRequireLicenseAcceptance() {
        nuspec.requireLicenseAcceptance = true
        nuspec.process()

        assert field("requireLicenseAcceptance") == "true"
    }

    @Test
    public void testUpdateDescription() {
        nuspec.description = "new description"
        nuspec.process()

        assert field("description") == "new description"
    }

    @Test
    public void testUpdateReleaseNote() {
        nuspec.releaseNotes = "new release note"
        nuspec.process()

        assert field("releaseNotes") == "new release note"
    }

    @Test
    public void testUpdateCopyright() {
        nuspec.copyright = "new copyright"
        nuspec.process()

        assert field("copyright") == "new copyright"
    }

    @Test
    public void testUpdateTags() {
        nuspec.tags = "new tags"
        nuspec.process()

        assert field("tags") == "new tags"
    }

    private String field(String name) {
        return new XmlSlurper().parse(output).metadata."${name}"
    }

}
