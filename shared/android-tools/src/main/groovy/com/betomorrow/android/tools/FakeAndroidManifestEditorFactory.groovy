package com.betomorrow.android.tools

class FakeAndroidManifestEditorFactory implements AndroidManifestEditorFactory {
    @Override
    AndroidManifestEditor create(String source) {
        return new FakeAndroidManifestEditor(source);
    }
}
