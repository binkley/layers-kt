package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.RpgLayersEditMap

/** @todo Capacity for containers */
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

    /**
     * Adds [item] to this container, returning a new container.  The
     * original container remains unmutated.  A container is a `Layer`, so the
     * mutated copy needs to be committed.
     */
    fun stow(item: I): C {
        val updatedContents = contents.toMutableList()
        updatedContents.add(item)
        return updateContainer(worn, self, updatedContents)
    }

    /**
     * Removes [item] to this container, returning a new container.  The
     * original container remains unmutated.  A container is a `Layer`, so the
     * mutated copy needs to be committed.
     */
    fun unstow(item: I): C {
        val updatedContents = contents.toMutableList()
        updatedContents.remove(item)
        return updateContainer(worn, self, updatedContents)
    }

    override fun toString() = "${super.toString()}: $contents"
}
