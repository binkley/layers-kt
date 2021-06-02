package hm.binkley.layers.rpg

import hm.binkley.layers.Rule

abstract class RpgRule<T : Any>(
    name: String,
) : Rule<String, Any, T>(name)
