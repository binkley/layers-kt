package hm.binkley.layers.rpg

import hm.binkley.layers.DefaultMutableLayer

/** Base type for RPG layers. */
open class RpgLayer<R : RpgLayer<R>>(
    name: String,
) : DefaultMutableLayer<String, Any, R>(name)
