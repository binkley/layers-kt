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
 * @todo Consider moving [worn] into a key-value map pair
 */
abstract class WearableItem<I : WearableItem<I>>(
    name: String,
    val worn: Boolean,
    private val previous: I?,
    protected val layers: RpgLayersEditMap,
) : Item<I>(name) {
    /** Creates a layer _copy_ linked to the parent it is copied from. */
    protected abstract fun activateNext(worn: Boolean, previous: I): I

    /**
     * Lists this item, and earlier versions of it.  For example,
     * donning/doffing a wearable item adds layers, and each layer represents
     * the _same_ item.
     *
     * @todo Should this belong to a type higher up the hierarchy?
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
        if (worn) throw IllegalStateException("Already donned: $this")
        else activateNext(true, self)

    /** Takes off this item, and prevents its rules from being applied. */
    fun doff() =
        if (!worn) throw IllegalStateException("Already doffed: $this")
        else activateNext(false, self)

    /**
     * Provides simpler rule syntax specific to RPG.  The "this" pointer to
     * `RpgEditMap` is unused, but specified to limit scope.
     */
    @Suppress("unused")
    fun RpgEditMap.floorRuleIfWorn(value: Int): RpgRule<Int> = ifWorn {
        FloorRule(value, this@WearableItem, layers)
    }

    override fun toString(): String =
        if (worn) "[+]${super.toString()} -> ${previous?.name}"
        else "[-]${super.toString()} -> ${previous?.name}"

    private fun <T : Any> ifWorn(
        rule: () -> RpgRule<T>,
    ): RpgRule<T> = if (worn) rule() else NotWornRule(name, self, layers)
}
