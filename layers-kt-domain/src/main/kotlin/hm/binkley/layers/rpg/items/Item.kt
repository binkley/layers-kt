package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.RpgLayer

abstract class Item<I : Item<I>>(
    name: String,
    val active: Boolean,
) : RpgLayer(name) {
    protected abstract fun new(active: Boolean): I

    fun don() = new(true)
    fun doff() = new(false)
}
