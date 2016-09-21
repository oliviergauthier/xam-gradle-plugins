package com.betomorrow.ios.tools.plist

class DefaultInfoPlistReader implements InfoPlistReader {

    @Override
    InfoPlist read(String source) {

        XmlParser parser = new XmlParser(false, false, true)
        parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        parser.setFeature("http://xml.org/sax/features/namespaces", false)
        def content = parser.parse(source);

        InfoPlist infoPlist = new InfoPlist()
        infoPlist.bundleIdentifier = getValueNode(content, "CFBundleIdentifier").text()
        infoPlist.bundleShortVersion = getValueNode(content, "CFBundleShortVersionString").text()
        infoPlist.bundleVersion = getValueNode(content, "CFBundleVersion").text()

        return infoPlist
    }

    private Node getValueNode(Node root, String key) {
        def dict = root.dict."*";

        def bundleIdentifierNode = dict.find { node ->
            return node.name() == "key" && node.text() == key
        }

        def idx = dict.indexOf(bundleIdentifierNode)
        return dict[idx+1];
    }

}
