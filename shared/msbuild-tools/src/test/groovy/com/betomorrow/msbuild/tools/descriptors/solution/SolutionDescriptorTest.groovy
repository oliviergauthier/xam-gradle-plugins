package com.betomorrow.msbuild.tools.descriptors.solution

import com.betomorrow.msbuild.tools.descriptors.project.ProjectDescriptor
import org.junit.Test

/**
 * Created by olivier on 24/07/16.
 */
public class SolutionDescriptorTest {

    @Test
    public void testHasSingleAndroidProjectReturnsTrueWithOnlyOneAndroidProject() {
        SolutionDescriptor sd = new SolutionDescriptor(['sample' : createAndroidProject()]);
        assert sd.hasSingleAndroidProject() == true
    }

    @Test
    public void testHasSingleAndroidProjectReturnsFalseWithManyAndroidProject() {
        SolutionDescriptor sd = new SolutionDescriptor(['sample' : createAndroidProject(),
                                                        'sample2' : createAndroidProject()]);
        assert sd.hasSingleAndroidProject() == false
    }


    @Test
    public void testHasSingleIosProjectReturnsTrueWithOnlyOneAndroidProject() {
        SolutionDescriptor sd = new SolutionDescriptor(['sample' : createIosProject()]);
        assert sd.hasSingleIosProject() == true
    }

    @Test
    public void testHasSingleIosProjectReturnsFalseWithManyAndroidProject() {
        SolutionDescriptor sd = new SolutionDescriptor(['sample' : createIosProject(),
                                                        'sample2' : createIosProject()]);
        assert sd.hasSingleIosProject() == false
    }


    @Test
    public void testContainsApplication() {
        SolutionDescriptor sd = new SolutionDescriptor(['sample' : createAndroidProject(),
                                                        'sample2' : createAndroidProject()]);
        assert sd.containsProject('sample') == true
        assert sd.containsProject('sample3') == false
    }

    @Test
    public void testGetFirstAndroidProject() {
        def firstAndroidProject = createAndroidProject()
        SolutionDescriptor sd = new SolutionDescriptor(['sample' : createIosProject(),
                                                        'sample2' :firstAndroidProject,
                                                        'sample3' : createAndroidProject()]);
        assert sd.firstAndroidProject == firstAndroidProject
    }

    @Test
    public void testGetFirsIosProject() {
        def firstIosProject = createIosProject()
        SolutionDescriptor sd = new SolutionDescriptor(['sample' : createAndroidProject(),
                                                        'sample2' :firstIosProject,
                                                        'sample3' : createIosProject()]);
        assert sd.firstAndroidProject == firstIosProject
    }


    public ProjectDescriptor createAndroidProject() {
        return [isAndroidApplication: { true }, isIosApplication: { false }] as ProjectDescriptor
    }

    public ProjectDescriptor createIosProject() {
        return [isIosApplication: { true }, isAndroidApplication: { false }] as ProjectDescriptor
    }

}