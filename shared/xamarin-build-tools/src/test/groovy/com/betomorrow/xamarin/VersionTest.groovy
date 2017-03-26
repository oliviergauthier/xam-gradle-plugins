package com.betomorrow.xamarin

import spock.lang.Specification

class VersionTest extends Specification {


    def "base should return version without fix"() {
        expect:
        new Version(v).base == base

        where:
        v || base
        "3.5.6" || "3.5"
    }

    def "full should return version"() {
        expect:
        new Version(v).full == full

        where:
        v || full
        "3.5.6" || "3.5.6"
    }

}
