package com.betomorrow.xamarin.tools.nuspec.nodes

class MetadataNode {

    Node metadataNode

    MetadataNode(Node metadataNode) {
        this.metadataNode = metadataNode
    }

    DependenciesNode dependencies() {
        return new DependenciesNode(NodesUtils.getNodeOrCreate(metadataNode, "dependencies"))
    }

    Node property(String name) {
        return NodesUtils.getNodeOrCreate(metadataNode, name)
    }



}
