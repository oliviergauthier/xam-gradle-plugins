package com.betomorrow.msbuild.tools.nuspec.nodes

class NodesUtils {

    public static Node getNodeOrCreate(Node node, String qname) {
        def child = node."${qname}"
        if (child == null || child.isEmpty()) {
            return node.appendNode(qname)
        }
        return child[0];
    }
}
