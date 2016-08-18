package com.betomorrow.msbuild.tools.nuspec.nodes

class PackageNode {

    Node packageNode

    PackageNode(Node packageNode) {
        this.packageNode = packageNode
    }

    MetadataNode metadata() {
        return new MetadataNode(NodesUtils.getNodeOrCreate(packageNode, "metadata"));
    }

    FilesNode files() {
        return new FilesNode(NodesUtils.getNodeOrCreate(packageNode, "files"));
    }
}
