package com.betomorrow.msbuild.tools.nuspec.nodes

class DependenciesNode {

    Node dependenciesNode

    DependenciesNode(Node dependenciesNode) {
        this.dependenciesNode = dependenciesNode
    }

    DependencyGroupNode group(String targetFramework) {
        def group = dependenciesNode.group.find { it.attribute('targetFramework') == targetFramework }
        if (group == null) {
            if (targetFramework == null) {
                return new DependencyGroupNode(dependenciesNode.appendNode("group"))
            } else {
                return new DependencyGroupNode(dependenciesNode.appendNode("group", [targetFramework: targetFramework]))
            }
        } else {
            return new DependencyGroupNode(group)
        }
    }
}
