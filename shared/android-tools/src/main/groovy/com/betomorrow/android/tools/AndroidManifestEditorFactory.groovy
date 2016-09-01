package com.betomorrow.android.tools

class AndroidManifestEditorFactory {

    AndroidManifestEditor create(String source) {
        return new AndroidManifestEditor(source);
    }

}
