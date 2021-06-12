package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.RpgEditMap

class Backpack<I : Item<I>>(
    layers: RpgEditMap,
    worn: Boolean = false,
    previous: Backpack<I>? = null,
    contents: List<I> = listOf(),
) : Container<I, Backpack<I>>(
    "Backpack",
    5.0f,
    worn,
    previous,
    layers,
    contents,
) {
    companion object {
        fun <I : Item<I>> backpack(layers: RpgEditMap): Backpack<I> {
            return Backpack(layers, false)
        }
    }

    override fun updateContainer(
        worn: Boolean,
        previous: Backpack<I>?,
        contents: List<I>,
    ): Backpack<I> = Backpack(layers, worn, previous, contents)

    override fun activateNext(worn: Boolean, previous: Backpack<I>) =
        Backpack(layers, worn, previous, contents)
}
