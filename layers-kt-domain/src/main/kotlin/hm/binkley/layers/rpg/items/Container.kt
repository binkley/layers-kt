package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.RpgLayersEditMap

abstract class Container<I : Item<I>, C : Container<I, C>>(
    name: String,
    layers: RpgLayersEditMap,
    worn: Boolean,
    previous: C?,
    contents: List<I>,
) : WearableItem<C>(
    name,
    worn,
    previous,
    layers
) {
    private val _contents = contents.toMutableList()
    val contents: List<I> get() = _contents

    protected abstract fun updateContainer(
        worn: Boolean,
        previous: C?,
        contents: List<I>,
    ): C

    fun add(item: I): C {
        val contents = contents.toMutableList() // Make a copy
        contents.add(item)
        return updateContainer(worn, self, contents)
    }

    fun remove(item: I): C {
        val contents = contents.toMutableList() // Make a copy
        contents.remove(item)
        return updateContainer(worn, self, contents)
    }

    override fun toString() = "${super.toString()}: $contents"
}
