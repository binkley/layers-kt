package hm.binkley.layers.rpg.items

import hm.binkley.layers.Layer
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
    /** Creates a layer _copy_ linked to the parent it copied from. */
    protected abstract fun new(active: Boolean, previous: I): I

    /**
     * Lists this item, and earlier versions of it.  For example,
     * donning/doffing a wearable item adds layers, and each layer represents
     * the _same_ item.
     */
    fun same(): List<Layer<String, Any, *>> {
        val items = mutableListOf<I>()
        var current: I? = self
        while (null != current) {
            items += current
            current = current.previous
        }
        return items
    }

    /** Puts on this item, and applies its rules. */
    fun don() =
        if (!worn) new(true, self)
        else throw IllegalStateException("Already donned: $this")

    /** Takes off this item, and prevents its rules from being applied. */
    fun doff() =
        if (worn) new(false, self)
        else throw IllegalStateException("Already doffed: $this")

    /**
     * Provides simpler rule syntax specific to RPG.  The "this" pointer to
     * `RpgEditMap` is unused, but specified to limit scope.
     */
    fun RpgEditMap.floorRuleIfWorn(value: Int): RpgRule<Int> =
        FloorRule(value, this@WearableItem, layers).ifWorn()

    override fun toString(): String =
        if (worn) "[+]${super.toString()} -> ${previous?.name}"
        else "[-]${super.toString()} -> ${previous?.name}"

    private fun <T : Any> RpgRule<T>.ifWorn(): RpgRule<T> =
        if (worn) this else NotWornRule(name, self, layers)
}
