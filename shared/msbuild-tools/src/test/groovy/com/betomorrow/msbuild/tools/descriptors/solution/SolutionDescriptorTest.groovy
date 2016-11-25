package com.betomorrow.msbuild.tools.descriptors.solution

import com.betomorrow.msbuild.tools.descriptors.project.ProjectDescriptor
import com.betomorrow.msbuild.tools.descriptors.project.XamarinProjectDescriptor
import org.junit.Test

/**
 * Created by olivier on 24/07/16.
 */
class SolutionDescriptorTest {

    @Test
     void testHasSingleAndroidProjectReturnsTrueWithOnlyOneAndroidProject() {
        SolutionDescriptor sd = new SolutionDescriptor(['sample' : createAndroidProject()])
        assert sd.hasSingleAndroidProject() == true
    }

    @Test
     void testHasSingleAndroidProjectReturnsFalseWithManyAndroidProject() {
        SolutionDescriptor sd = new SolutionDescriptor(['sample' : createAndroidProject(),
                                                        'sample2' : createAndroidProject()])
        assert sd.hasSingleAndroidProject() == false
    }


    @Test
     void testHasSingleIosProjectReturnsTrueWithOnlyOneAndroidProject() {
        SolutionDescriptor sd = new SolutionDescriptor(['sample' : createIosProject()])
        assert sd.hasSingleIosProject() == true
    }

    @Test
     void testHasSingleIosProjectReturnsFalseWithManyAndroidProject() {
        SolutionDescriptor sd = new SolutionDescriptor(['sample' : createIosProject(),
                                                        'sample2' : createIosProject()])
        assert sd.hasSingleIosProject() == false
    }


    @Test
     void testContainsApplication() {
        SolutionDescriptor sd = new SolutionDescriptor(['sample' : createAndroidProject(),
                                                        'sample2' : createAndroidProject()])
        assert sd.containsProject('sample') == true
        assert sd.containsProject('sample3') == false
    }

    @Test
     void testGetFirstAndroidProject() {
        def firstAndroidProject = createAndroidProject()
        SolutionDescriptor sd = new SolutionDescriptor(['sample' : createIosProject(),
                                                        'sample2' :firstAndroidProject,
                                                        'sample3' : createAndroidProject()])
        assert sd.firstAndroidProject == firstAndroidProject
    }

    @Test
     void testGetFirsIosProject() {
        def firstIosProject = createIosProject()
        SolutionDescriptor sd = new SolutionDescriptor(['sample' : createAndroidProject(),
                                                        'sample2' :firstIosProject,
                                                        'sample3' : createIosProject()])
        assert sd.firstAndroidProject == firstIosProject
    }


    XamarinProjectDescriptor createAndroidProject() {
        return [isAndroidApplication: { true }, isIosApplication: { false }] as XamarinProjectDescriptor
    }

    XamarinProjectDescriptor createIosProject() {
        return [isIosApplication: { true }, isAndroidApplication: { false }] as XamarinProjectDescriptor
    }

}