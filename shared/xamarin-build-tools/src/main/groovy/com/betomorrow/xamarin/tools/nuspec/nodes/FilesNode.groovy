package com.betomorrow.xamarin.tools.nuspec.nodes

import com.betomorrow.xamarin.tools.nuspec.assemblies.Assembly

class FilesNode {

    private Node files

    FilesNode(Node files) {
        this.files = files
    }

    void add(Assembly assembly) {
        def node = files.file.find{ it.attribute('src') == assembly.assemblyPath && it.attribute('target') == assembly.targetDirectory}
        if (node == null) {
            files.appendNode("file", [src:assembly.assemblyPath, target:assembly.targetDirectory])
        }
    }

}
