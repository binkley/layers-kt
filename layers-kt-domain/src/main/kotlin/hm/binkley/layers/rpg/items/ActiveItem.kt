package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.RpgEditMap
import hm.binkley.layers.rpg.RpgLayersEditMap
import hm.binkley.layers.rpg.RpgRule
import hm.binkley.layers.rpg.rules.FloorRule
import hm.binkley.layers.rpg.rules.InactiveRule

abstract class ActiveItem<I : ActiveItem<I>>(
    name: String,
    val active: Boolean,
    private val previous: I?,
    private val layers: RpgLayersEditMap,
) : Item<I>(name) {
    protected abstract fun new(active: Boolean, previous: I): I

    override fun toString(): String =
        if (active) "[+]${super.toString()} -> ${previous?.name}"
        else "[-]${super.toString()} -> ${previous?.name}"

    fun same(): List<I> {
        val items = mutableListOf<I>()
        var current: I? = self
        while (null != current) {
            items += current
            current = current.previous
        }
        return items
    }

    fun don() = new(true, self)
    fun doff() = new(false, self)

    // The "this" pointers are unused, however it restricts scope
    fun RpgEditMap.activeFloorRule(value: Int): RpgRule<Int> {
        val rule = FloorRule(value, this@ActiveItem, layers)

        return if (active) rule else inactiveRule(rule)
    }

    private fun <T : Any> inactiveRule(rule: RpgRule<T>): RpgRule<T> =
        InactiveRule(rule.name, this, layers)
}
