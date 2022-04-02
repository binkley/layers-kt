package hm.binkley.layers.rpg

import hm.binkley.layers.Rule

/** Base type for rules in RPG layers. */
abstract class RpgRule<T : Any>(
    name: String,
) : Rule<String, Any, T>(name)
