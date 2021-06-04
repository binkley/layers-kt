package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.RpgLayer

abstract class Item<I : Item<I>>(
    name: String,
) : RpgLayer<I>(name)
