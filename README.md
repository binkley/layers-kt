<a href="LICENSE.md">
<img src="https://unlicense.org/pd-icon.png" alt="Public Domain" align="right"/>
</a>

# Layers KT

[![build](https://github.com/binkley/layers-kt/workflows/build/badge.svg)](https://github.com/binkley/layers-kt/actions)
[![issues](https://img.shields.io/github/issues/binkley/layers-kt.svg)](https://github.com/binkley/layers-kt/issues/)
[![Public Domain](https://img.shields.io/badge/license-Public%20Domain-blue.svg)](http://unlicense.org/)
[![made with kotlin](https://img.shields.io/badge/made%20with-Kotlin-1f425f.svg)](https://kotlinlang.org/)

_An experiment in style and technique in Kotlin_.

(See [Layers Java](https://github.com/binkley/layers-java) for an equivalent
in Java.)

## Build and try

To build, use `./mvnw clean verify`.

There are no run-time dependencies.

## Platform

This code has been built for JDK 11.

## Build

* [DependencyCheck](https://github.com/jeremylong/DependencyCheck) scans for
  dependency security issues
* [detekt](https://github.com/arturbosch/detekt) runs static code analysis for
  Kotlin
* [JUnit](https://github.com/junit-team/junit5) runs tests
* [JaCoCo](https://github.com/jacoco/jacoco) measures code coverage
* [ktlint](https://github.com/pinterest/ktlint) keeps code tidy

Use `./mvnw` (Maven) or `./batect build` (Batect) to build, run tests, and
create a demo program. Use `./run.sh` or `./batect run` to run the demo.

[Batect](https://batect.dev/) works "out of the box", however, an important
optimization is to avoid redownloading plugins and dependencies from within a
Docker container.

This shares Maven plugin and dependency downloads with the Docker container
run by Batect.
