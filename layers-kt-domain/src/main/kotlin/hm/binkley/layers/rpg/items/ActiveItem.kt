package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.RpgLayersEditMap
import hm.binkley.layers.rpg.rules.FloorRule
import hm.binkley.layers.rpg.rules.PassThruRule

abstract class ActiveItem<I : ActiveItem<I>>(
    name: String,
    val active: Boolean,
    private val layers: RpgLayersEditMap,
) : Item(name) {
    protected abstract fun new(active: Boolean): I

    fun don() = new(true)
    fun doff() = new(false)

    fun floorRule(value: Int) = FloorRule(value, this, layers)
    fun <T : Any> passThruRule() = PassThruRule<T>(this, layers)
}
