package hm.binkley.layers.rpg

import hm.binkley.layers.DefaultMutableLayer

open class RpgLayer<R : RpgLayer<R>>(
    name: String,
) : DefaultMutableLayer<String, Any, R>(name)
