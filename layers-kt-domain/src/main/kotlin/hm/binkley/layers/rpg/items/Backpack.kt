package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.items.Weight.Companion.weight

/**
 * A wearable backpack container weighing 5#.
 *
 * @param worn default unworn (`false`)
 * @param previous default no link to this item in an earlier state (`null`)
 * @param contents default no items in the backpack (empty list)
 */
class Backpack<I : Item<I>>(
    worn: Boolean = false,
    previous: Backpack<I>? = null,
    contents: List<I> = emptyList(),
) : Container<I, Backpack<I>>(
    "Backpack",
    5.weight,
    worn,
    previous,
    contents,
) {
    companion object {
        /** Creates a new, unworn, empty [Backpack]. */
        /** Creates a new, unworn, empty [Backpack]. */
        fun <I : Item<I>> backpack(): Backpack<I> = Backpack(false)
    }

    override fun updateContainer(
        worn: Boolean,
        previous: Backpack<I>?,
        contents: List<I>,
    ): Backpack<I> = Backpack(worn, previous, contents)

    override fun change(previous: Backpack<I>, worn: Boolean): Backpack<I> =
        Backpack(worn, previous, contents)
}
