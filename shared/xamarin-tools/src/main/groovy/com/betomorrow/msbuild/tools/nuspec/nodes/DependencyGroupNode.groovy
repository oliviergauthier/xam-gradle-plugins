package com.betomorrow.msbuild.tools.nuspec.nodes

class DependencyGroupNode {

    Node dependencyGroupNode

    DependencyGroupNode(Node dependencyGroupNode) {
        this.dependencyGroupNode = dependencyGroupNode
    }

    Node dependency(String id) {
        def node = dependencyGroupNode.dependency.find { it.@id == id }
        if (node == null) {
            return dependencyGroupNode.appendNode("dependency", [id:id])
        } else {
            return node
        }
    }
}
