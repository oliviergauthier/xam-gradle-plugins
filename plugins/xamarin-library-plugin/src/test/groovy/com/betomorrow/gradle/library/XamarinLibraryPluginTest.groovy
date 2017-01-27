package com.betomorrow.gradle.library

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before

class XamarinLibraryPluginTest {

    Project project

    @Before
    void setUp() {
        project = ProjectBuilder.builder().withProjectDir( new File('src/test/resources')).build()
    }

}
