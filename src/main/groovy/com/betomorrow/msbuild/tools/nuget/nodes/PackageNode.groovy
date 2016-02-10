package com.betomorrow.msbuild.tools.nuget.nodes

/**
 * Created by olivier on 10/02/16.
 */
class PackageNode {

    Node packageNode

    PackageNode(Node packageNode) {
        this.packageNode = packageNode
    }

    MetadataNode metadata() {
        return new MetadataNode(NodesUtils.getNodeOrCreate(packageNode, "metadata"));
    }
}
