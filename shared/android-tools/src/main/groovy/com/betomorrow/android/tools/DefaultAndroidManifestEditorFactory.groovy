package com.betomorrow.android.tools

class DefaultAndroidManifestEditorFactory implements AndroidManifestEditorFactory {

    AndroidManifestEditor create(String source) {
        return new DefaultAndroidManifestEditor(source);
    }

}
