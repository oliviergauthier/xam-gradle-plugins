package com.betomorrow.xamarin.tools.nuspec.nodes

class NodesUtils {

    static Node getNodeOrCreate(Node node, String qname) {
        def child = node."${qname}"
        if (child == null || child.isEmpty()) {
            return node.appendNode(qname)
        }
        return child[0]
    }
}
