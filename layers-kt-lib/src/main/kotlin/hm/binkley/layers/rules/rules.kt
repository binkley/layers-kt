// Extension receiver unused but needed for:
// - Scope management
// - Type inference
@file:Suppress("unused")

package hm.binkley.layers.rules

import hm.binkley.layers.EditMap

/** Convenience for creating a new constant rule. */
fun <K : Any, V : Any, T : V>
EditMap<K, V>.constantRule(value: T): ConstantRule<K, V, T> =
    ConstantRule(value)

/** Convenience for creating a new latest-with-default rule. */
fun <K : Any, V : Any, T : V>
EditMap<K, V>.lastOrDefaultRule(default: T): LastOrDefaultRule<K, V, T> =
    LastOrDefaultRule(default)
