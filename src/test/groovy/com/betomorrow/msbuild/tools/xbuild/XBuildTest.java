package com.betomorrow.msbuild.tools.xbuild;

import org.junit.Test;

/**
 * Created by Olivier on 15/01/2016.
 */
public class XBuildTest {

    @Test
    public void testBuild() throws Exception {
        new XBuild().build();
    }
}