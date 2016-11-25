package com.betomorrow.msbuild.tools.ios.plist

class PlistXmlParser {

    Node parse(String source) {
        XmlParser parser = new XmlParser(false, false, true)
        parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
        parser.setFeature("http://xml.org/sax/features/namespaces", false)
        def root = parser.parse(source)
        Node.metaClass.getValueNode = { key -> getValueNode(delegate, key) }
        return root
    }

     static Node getValueNode(Node root, String key) {
        def dict = root.dict."*"

        def bundleIdentifierNode = dict.find { node ->
            return node.name() == "key" && node.text() == key
        }

        def idx = dict.indexOf(bundleIdentifierNode)
        return dict[idx+1]
    }

}
