package com.betomorrow.msbuild.tools.nuspec.nodes

class DependenciesNode {

    Node dependenciesNode;

    DependenciesNode(Node dependenciesNode) {
        this.dependenciesNode = dependenciesNode
    }

    public DependencyGroupNode group(String targetFramework) {
        def group = dependenciesNode.group.find { it.@targetFramework == targetFramework }
        if (group == null) {
            return new DependencyGroupNode(dependenciesNode.appendNode("group", [targetFramework:targetFramework]))
        } else {
            return new DependencyGroupNode(group)
        }
    }
}
