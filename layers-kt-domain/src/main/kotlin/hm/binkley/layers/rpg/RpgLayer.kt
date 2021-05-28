package hm.binkley.layers.rpg

import hm.binkley.layers.MutablePlainLayer

open class RpgLayer<M : RpgLayer<M>>(
    name: String,
) : MutablePlainLayer<M>(name)
