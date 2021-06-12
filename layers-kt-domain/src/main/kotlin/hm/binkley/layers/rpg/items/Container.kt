package hm.binkley.layers.rpg.items

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import hm.binkley.layers.rpg.RpgEditMap

/** @todo Capacity for containers */
@SuppressFBWarnings("BC_BAD_CAST_TO_ABSTRACT_COLLECTION")
abstract class Container<I : Item<I>, C : Container<I, C>>(
    name: String,
    weight: Float,
    worn: Boolean,
    previous: C?,
    layers: RpgEditMap,
    contents: List<I>,
) : WearableItem<C>(
    name,
    weight,
    worn,
    previous,
    layers
) {
    private val _contents = contents.toMutableList()
    val contents: List<I> get() = _contents

    init {
        edit {
            this["$name-WEIGHT"] =
                rule<Float>("Sum[Float]") { _, _, _ ->
                    contents.map { it.weight }.sum()
                }
        }
    }

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
