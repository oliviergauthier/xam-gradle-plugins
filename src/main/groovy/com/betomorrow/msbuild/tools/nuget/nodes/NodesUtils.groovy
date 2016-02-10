package com.betomorrow.msbuild.tools.nuget.nodes

/**
 * Created by olivier on 10/02/16.
 */
class NodesUtils {

    public static Node getNodeOrCreate(Node node, String qname) {
        def child = node."${qname}"
        if (child == null || child.isEmpty()) {
            return node.appendNode(qname)
        }
        return child[0];
    }
}
