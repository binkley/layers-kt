package hm.binkley.layers.rpg.items

class Backpack<I : Item<I>>(
    worn: Boolean = false,
    previous: Backpack<I>? = null,
    contents: List<I> = listOf(),
) : Container<I, Backpack<I>>(
    "Backpack",
    5.0f,
    worn,
    previous,
    contents,
) {
    companion object {
        fun <I : Item<I>> backpack(): Backpack<I> = Backpack(false)
    }

    override fun updateContainer(
        worn: Boolean,
        previous: Backpack<I>?,
        contents: List<I>,
    ): Backpack<I> = Backpack(worn, previous, contents)

    override fun change(previous: Backpack<I>, worn: Boolean) =
        Backpack(worn, previous, contents)
}
