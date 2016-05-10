package com.betomorrow.gradle.base

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

/**
 * Created by olivier on 19/04/16.
 */
class XamarinBasePluginTest {

    @Test
    public void testApplySetSolution() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'xamarin-base-plugin'
        project.xamarin {
            solution "MyApplication.sln"
            configuration "Release"
        }
    }


}
