package com.betomorrow.msbuild.tools.nuspec.nodes
/**
 * Created by olivier on 10/02/16.
 */
public class MetadataNode {

    Node metadataNode;

    MetadataNode(Node metadataNode) {
        this.metadataNode = metadataNode
    }

    DependenciesNode dependencies() {
        return new DependenciesNode(NodesUtils.getNodeOrCreate(metadataNode, "dependencies"));
    }

    Node property(String name) {
        return NodesUtils.getNodeOrCreate(metadataNode, name)
    }



}
