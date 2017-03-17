package com.betomorrow.gradle.commons

import spock.lang.Specification

class SemVersionTest extends Specification {


    def "test with limit cases"() {
        expect:
        assertHasValues(SemVersion.parse(""), "", null)
        assertHasValues(SemVersion.parse("unspecified"), "unspecified", null)
        assertHasValues(SemVersion.parse("1.2.3-pre-1-beta"), "1.2.3", "pre-1-beta")
    }

    def "test getFullVersion returns version with suffix if present"() {
        expect:
        SemVersion.parse(a).fullVersion == b

        where:
        a || b
        "1" || "1"
        "1.0" || "1.0"
        "1.0.0" || "1.0.0"
        "1.0.0.0" || "1.0.0.0"

        "1-SNAPSHOT" || "1-SNAPSHOT"
        "1.0-SNAPSHOT" || "1.0-SNAPSHOT"
        "1.0.0-SNAPSHOT" || "1.0.0-SNAPSHOT"
        "1.0.0.0-SNAPSHOT" || "1.0.0.0-SNAPSHOT"
    }

    def "test getSuffix returns suffix if present"() {
        expect:
        SemVersion.parse(a).suffix == b

        where:
        a || b
        "1" || null
        "1.0" || null
        "1.0.0" || null
        "1.0.0.0" || null

        "1-SNAPSHOT" || "SNAPSHOT"
        "1.0-SNAPSHOT" || "SNAPSHOT"
        "1.0.0-SNAPSHOT" || "SNAPSHOT"
        "1.0.0.0-SNAPSHOT" || "SNAPSHOT"
    }

    def "test getVersionNumber returns version  number without"() {
        expect:
        SemVersion.parse(a).versionNumber == b

        where:
        a || b
        "1" || "1"
        "1.0" || "1.0"
        "1.0.0" || "1.0.0"
        "1.0.0.0" || "1.0.0.0"

        "1-SNAPSHOT" || "1"
        "1.0-SNAPSHOT" || "1.0"
        "1.0.0-SNAPSHOT" || "1.0.0"
        "1.0.0.0-SNAPSHOT" || "1.0.0.0"
    }

    def assertHasValues(SemVersion v, String versionNumber, String suffix) {
        assert v.versionNumber == versionNumber
        assert v.suffix == suffix
        return true
    }

}
