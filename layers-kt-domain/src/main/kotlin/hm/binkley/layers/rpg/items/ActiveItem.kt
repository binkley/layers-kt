package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.RpgEditMap
import hm.binkley.layers.rpg.RpgLayersEditMap
import hm.binkley.layers.rpg.RpgRule
import hm.binkley.layers.rpg.rules.FloorRule
import hm.binkley.layers.rpg.rules.PassThruRule

abstract class ActiveItem<I : ActiveItem<I>>(
    name: String,
    val active: Boolean,
    private val layers: RpgLayersEditMap,
) : Item(name) {
    protected abstract fun new(active: Boolean): I

    override fun toString(): String =
        if (active) "[+]${super.toString()}" else "[-]${super.toString()}"

    fun don() = new(true)
    fun doff() = new(false)

    // The "this" pointers are unused, however it restricts scope
    fun RpgEditMap.activeFloorRule(value: Int): RpgRule<Int> {
        val rule = FloorRule(value, this@ActiveItem, layers)

        return if (active) rule else passThruRule(rule)
    }

    private fun <T : Any> passThruRule(rule: RpgRule<T>): RpgRule<T> =
        PassThruRule(rule.name, this, layers)
}
