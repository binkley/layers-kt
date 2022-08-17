package hm.binkley.layers.rpg.items

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import hm.binkley.layers.rpg.RpgEditMap
import hm.binkley.layers.rpg.RpgRule
import hm.binkley.layers.rpg.rules.FloorRule
import hm.binkley.layers.rpg.rules.WornRule

/**
 * Marks an item as wearable.
 * Use [WearableItem] for a base implementation.
 */
interface Wearable<I> where I : Item<I>, I : Wearable<I> {
    /** If the item is worn. */
    val worn: Boolean

    /**
     * Lists this item, and earlier versions of it.
     * For example, donning/doffing a wearable item adds layers, and each
     * layer represents the _same_ item.
     *
     * @todo Should this belong to a type higher up the hierarchy?
     */
    fun versions(): List<I>

    /** Puts on this item, and applies its rules. */
    fun don(): I

    /** Takes off this item, and prevents its rules from being applied. */
    fun doff(): I
}

/**
 * Base type for items that are [Wearable].
 * Use [don] and [doff] to toggle if worn.
 */
abstract class WearableItem<I : WearableItem<I>>(
    name: String,
    weight: Weight,
    override val worn: Boolean,
    // TODO: This is a queer warning: `previous` is both private and final
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    private val previous: I?,
) : Item<I>(name, weight), Wearable<I> {
    /** Creates a layer _copy_ linked to the parent it is copied from. */
    protected abstract fun change(previous: I, worn: Boolean): I

    override fun versions(): List<I> =
        generateSequence(self) { it.previous }.toList()

    override fun don(): I =
        if (worn) throw IllegalStateException("Already donned: $this")
        else change(self, true)

    override fun doff(): I =
        if (!worn) throw IllegalStateException("Already doffed: $this")
        else change(self, false)

    /**
     * Provides simpler rule syntax specific to RPG.
     * The "this" pointer to [RpgEditMap] is unused, but specified to limit
     * scope.
     * It is not an extension function to retain access to the layer's `this`.
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
