package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.RpgEditMap
import hm.binkley.layers.rpg.RpgLayersEditMap
import hm.binkley.layers.rpg.RpgRule
import hm.binkley.layers.rpg.rules.FloorRule
import hm.binkley.layers.rpg.rules.NotWornRule

/**
 * @todo Explore direct pointers to other layers ([previous]) _vs_ tracking
 *       layer indices; the older Java implementation used the indices
 *       approach
 */
abstract class WearableItem<I : WearableItem<I>>(
    name: String,
    val worn: Boolean,
    private val previous: I?,
    private val layers: RpgLayersEditMap,
) : Item<I>(name) {
    protected abstract fun new(active: Boolean, previous: I): I

    override fun toString(): String =
        if (worn) "[+]${super.toString()} -> ${previous?.name}"
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

    fun don() =
        if (!worn) new(true, self)
        else throw IllegalStateException("Already donned: $this")

    fun doff() =
        if (worn) new(false, self)
        else throw IllegalStateException("Already doffed: $this")

    // The "this" pointer is unused, however it restricts scope
    fun RpgEditMap.floorRuleIfWorn(value: Int): RpgRule<Int> {
        val rule = FloorRule(value, this@WearableItem, layers)
        return if (worn) rule else inactiveRule(rule)
    }

    private fun <T : Any> inactiveRule(rule: RpgRule<T>): RpgRule<T> =
        NotWornRule(rule.name, self, layers)
}
