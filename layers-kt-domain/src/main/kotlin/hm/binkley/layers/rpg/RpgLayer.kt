package hm.binkley.layers.rpg

import hm.binkley.layers.MutablePlainLayer

open class RpgLayer<L : RpgLayer<L>>(
    name: String,
) : MutablePlainLayer<L>(name)
