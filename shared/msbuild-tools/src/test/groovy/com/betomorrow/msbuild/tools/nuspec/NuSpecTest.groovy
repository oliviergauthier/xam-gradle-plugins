package com.betomorrow.msbuild.tools.nuspec

import com.betomorrow.msbuild.tools.nuspec.assemblies.Assembly
import com.betomorrow.msbuild.tools.nuspec.dependencies.Dependency
import groovy.util.slurpersupport.NodeChild
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class NuSpecTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder()

    def SAMPLE_NUSPEC = ClassLoader.getSystemResource('Sample.nuspec').file
    def output

    NuSpec nuspec

    @Before
     void setUp() {
        output = testFolder.newFile("out.nuspec")

//        nuspec = new NuSpec()
        nuspec = new NuSpec(SAMPLE_NUSPEC)
        nuspec.output = output.getAbsolutePath()
    }

    @Test
     void testReadAndWriteWithoutModification() {
        nuspec.process()

        assert new XmlSlurper().parse(SAMPLE_NUSPEC) == new XmlSlurper().parse(output)
    }

    @Test
     void testUpdateId() {
        nuspec.packageId = "new packageId"
        nuspec.process()

        assert field("id") == "new packageId"
    }

    @Test
     void testUpdateVersion() {
        nuspec.version = "new version"
        nuspec.process()

        assert field("version") == "new version"
    }


    @Test
     void testUpdateAuthor() {
        nuspec.authors = "new authors"
        nuspec.process()

        assert field("authors") == "new authors"
    }

    @Test
     void testUpdateOwner() {
        nuspec.owners = "new owners"
        nuspec.process()

        assert field("owners") == "new owners"
    }


    @Test
     void testUpdateLicenceUrl() {
        nuspec.licenseUrl = "new licence url"
        nuspec.process()

        assert field("licenseUrl") == "new licence url"
    }

    @Test
     void testUpdateProjectUrl() {
        nuspec.projectUrl = "new project url"
        nuspec.process()

        assert field("projectUrl") == "new project url"
    }

    @Test
     void testUpdateIconUrl() {
        nuspec.iconUrl = "new icon url"
        nuspec.process()

        assert field("iconUrl") == "new icon url"
    }

    @Test
     void testUpdateRequireLicenseAcceptance() {
        nuspec.requireLicenseAcceptance = true
        nuspec.process()

        assert field("requireLicenseAcceptance") == "true"
    }

    @Test
     void testUpdateDescription() {
        nuspec.description = "new description"
        nuspec.process()

        assert field("description") == "new description"
    }

    @Test
     void testUpdateReleaseNote() {
        nuspec.releaseNotes = "new release note"
        nuspec.process()

        assert field("releaseNotes") == "new release note"
    }

    @Test
     void testUpdateCopyright() {
        nuspec.copyright = "new copyright"
        nuspec.process()

        assert field("copyright") == "new copyright"
    }

    @Test
     void testUpdateTags() {
        nuspec.tags = "new tags"
        nuspec.process()

        assert field("tags") == "new tags"
    }

    @Test
     void testUpdateDependency() {
        nuspec.dependencySet.add("SampleDependency:version")
        nuspec.process()

        assertContainsDependency(new Dependency("SampleDependency:version"))
    }

    @Test
     void testAddDependency() {
        nuspec.dependencySet.add("CustomDependency:version")
        nuspec.process()

        assertContainsDependency(new Dependency("CustomDependency:version"))
    }

    @Test
     void testUpdateDependencyWithGroup() {
        nuspec.dependencySet.add("net40:jQuery:version")
        nuspec.process()

        assertContainsDependency(new Dependency("net40:jQuery:version"))
    }

    @Test
     void testAddDependencyWithGroup() {
        nuspec.dependencySet.add("net40:CustomDependency:version")
        nuspec.process()

        assertContainsDependency(new Dependency("net40:CustomDependency:version"))
    }

    @Test
     void testAddAssembly() {
        nuspec.assemblySet.add(new Assembly("build/Release/MyAssembly.dll", "libs/"))
        nuspec.process()

        assert assemblyNode(new Assembly("build/Release/MyAssembly.dll", "libs/")).size() > 0
    }

    @Test
     void testAddAssemblyTwiceDontAddTwoNode() {
        nuspec.assemblySet.add(new Assembly("build/Release/MyAssembly.dll", "libs/"))
        nuspec.process()

        assert assemblyNode(new Assembly("build/Release/MyAssembly.dll", "libs/")).size() == 1
    }

    private String field(String name) {
        return new XmlSlurper().parse(output).metadata."${name}"
    }

    private void assertContainsDependency(Dependency dependency) {
        def result = new XmlSlurper().parse(output)

        if (dependency.group == null) {
            def node = result.metadata.dependencies.group.dependency.find { it.@id == dependency.id }
            assert dependency.version.toString() == node.@version.toString()
        } else {
            def node = result.metadata.dependencies.group.find { it.@targetFramework == dependency.group }.dependency.find { it.@id == dependency.id }
            assert dependency.version.toString() == node.@version.toString()
        }
    }

    private NodeChild assemblyNode(Assembly assembly) {
        def result = new XmlSlurper().parse(output)
        def node = result.files.file.find { it.@src == assembly.assemblyPath && it.@target == assembly.targetDirectory}
    }

}
