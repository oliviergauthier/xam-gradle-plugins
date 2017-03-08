package com.betomorrow.gradle.application.extensions

import com.betomorrow.gradle.commons.extensions.AbstractXamarinExtension
import groovy.transform.InheritConstructors

@InheritConstructors
class XamarinApplicationExtension extends AbstractXamarinExtension {

    String appName
    String appVersion
    String storeVersion
    String packageName

}
