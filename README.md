<a href="LICENSE.md">
<img src="https://unlicense.org/pd-icon.png" alt="Public Domain" align="right"/>
</a>

# Layers KT

[![build](https://github.com/binkley/layers-kt/workflows/build/badge.svg)](https://github.com/binkley/layers-kt/actions)
[![issues](https://img.shields.io/github/issues/binkley/layers-kt.svg)](https://github.com/binkley/layers-kt/issues/)
[![Public Domain](https://img.shields.io/badge/license-Public%20Domain-blue.svg)](http://unlicense.org/)

_An experiment in style and technique in Kotlin_.

`Layers` is a list of maps that appears as a single map. It uses _rules_
to provide a single value for each key, based on all values for that key in
the list of maps. For example, if the key "bob" has values 1, 2, and 3 in
different layers, and the rule were to sum these, then the value of the
"bob" key in a layers would be 6.

(See [Layers Java](https://github.com/binkley/layers-java) for an older
approach in Java.)

## Build and try

To build, use `./mvnw verify` or `./batect build` (for a CI-like experience).

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

## API

### General patterns

It is difficult to express in Kotlin with types (meta-programming may be 
required), but for all functions which express map state (construction or 
mutation), functions come in three styles:

- `foo(vararg state: Pair<String, Entry<*>>): T`
```kotlin
x = x.foo("a" to 3.toValue()) // Merged into existing mutable map
```
- `foo(state: Map<String, Entry<*>>): T`
```kotlin
x = x.foo(mapOf("a" to 3.toValue())) // Merged into existing mutable map
```
- `foo(state: MutableMap<String, Entry<*>>.() -> Unit): T`
```kotlin
x = x.foo {
    this["a"] = 3.toValue() // Mutates existing mutable map
}
```

### Layers

[`Layers`](./layers-kt-lib/src/main/kotlin/hm/binkley/layers/Layers.kt) is a
subtype of
[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/)
.

#### Properties

- `layers` &mdash; an immutable list of layers, ordered from most recent to
  oldest. The top-most in the list is the current layer
- `current` &mdash; the current, _editable_ layer. Make updates against the _
  current_ layer

### Layer

[`Layer`](./layers-kt-lib/src/main/kotlin/hm/binkley/layers/Layer.kt) is a
subtype of
[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/)
.

### Editable layer

[`EditableLayer`](./layers-kt-lib/src/main/kotlin/hm/binkley/layers/EditableLayer.kt)
is a subtype of
[`Layer`](./layers-kt-lib/src/main/kotlin/hm/binkley/layers/Layer.kt).

#### Methods

- `edit` &mdash; edits with a block. All `Map` methods apply. A sample:
  ```kotlin
        editableLayer.edit {
            this["bob"] = 3.toEntry()
        }

  ```

### Standard rule: Constant

A layers rule which returns a constant value.

See
[`ConstantRule`](./layers-kt-lib/src/main/kotlin/hm/binkley/layers/rules/ConstantRule.kt)
.

### Standard rule: Latest

A layers rule which returns the latest value (the value in the most recently
layer to a `Layers`).

See
[`LatestOfRule`](./layers-kt-lib/src/main/kotlin/hm/binkley/layers/rules/LatestOfRule.kt)
.

### Standard rule: Sum

A layers rule which sums all values added to a `Layers`.

See
[`SumOfRule`](./layers-kt-lib/src/main/kotlin/hm/binkley/layers/rules/SumOfRule.kt)
.

## TODO

* Rationalize uses of `Map` as input _vs_ `MutableMap` reused as a 
  property or delegated argument
