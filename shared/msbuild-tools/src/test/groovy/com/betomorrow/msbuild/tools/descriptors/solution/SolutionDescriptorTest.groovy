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

    public ProjectDescriptor createAndroidProject() {
        return [isAndroidApplication: { true }, isIosApplication: { false }] as ProjectDescriptor
    }

    public ProjectDescriptor createIosProject() {
        return [isIosApplication: { true }, isAndroidApplication: { false }] as ProjectDescriptor
    }

}