package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.RpgEditMap
import hm.binkley.layers.rpg.RpgRule
import hm.binkley.layers.rpg.rules.FloorRule
import hm.binkley.layers.rpg.rules.WornRule

interface Wearable<I> where I : Item<I>, I : Wearable<I> {
    val worn: Boolean

    /**
     * Lists this item, and earlier versions of it.  For example,
     * donning/doffing a wearable item adds layers, and each layer represents
     * the _same_ item.
     *
     * @todo Should this belong to a type higher up the hierarchy?
     */
    fun same(): List<I>

    /** Puts on this item, and applies its rules. */
    fun don(): I

    /** Takes off this item, and prevents its rules from being applied. */
    fun doff(): I
}

abstract class WearableItem<I : WearableItem<I>>(
    name: String,
    weight: Float,
    override val worn: Boolean,
    private val previous: I?,
) : Item<I>(name, weight), Wearable<I> {
    /** Creates a layer _copy_ linked to the parent it is copied from. */
    protected abstract fun change(previous: I, worn: Boolean): I

    override fun same(): List<I> =
        generateSequence(self) { it.previous }.toList()

    override fun don() =
        if (worn) throw IllegalStateException("Already donned: $this")
        else change(self, true)

    override fun doff() =
        if (!worn) throw IllegalStateException("Already doffed: $this")
        else change(self, false)

    /**
     * Provides simpler rule syntax specific to RPG.  The "this" pointer to
     * [RpgEditMap] is unused, but specified to limit scope.  It is not an
     * extension function to retain access to the layer's `this`.
     */
    @Suppress("unused")
    fun RpgEditMap.floorRuleIfWorn(value: Int): RpgRule<Int> =
        FloorRule(value, self).ifWorn()

    override fun toString(): String =
        if (worn) "[+]${super.toString()} -> ${previous?.name}"
        else "[-]${super.toString()} -> ${previous?.name}"

    private fun <T : Any> RpgRule<T>.ifWorn(): RpgRule<T> =
        WornRule(name, this, self)
}
