package hm.binkley.layers.rpg.items

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings

/**
 * Base type for container items.
 *
 * @todo what is [contents] _vs_ [_contents] about?
 * @todo Capacity for containers: mass and dimensions
 */
@SuppressFBWarnings(
    "BC_BAD_CAST_TO_ABSTRACT_COLLECTION",
    "EI_EXPOSE_REP",
    "EI_EXPOSE_REP2",
)
abstract class Container<I : Item<I>, C : Container<I, C>>(
    name: String,
    weight: Float,
    worn: Boolean,
    previous: C?,
    /** Containers start empty by default (empty list). */
    val contents: List<I> = emptyList(),
) : WearableItem<C>(
    name,
    weight,
    worn,
    previous,
) {
    private val _contents = contents.toMutableList()

    init {
        edit {
            this["$name-WEIGHT"] =
                rule<Float>("Sum[Float]") { _, _, _ ->
                    contents.map { it.weight }.sum()
                }
        }
    }

    /** Creates a new, updated layer for this container. */
    protected abstract fun updateContainer(
        worn: Boolean,
        previous: C?,
        contents: List<I>,
    ): C

    /**
     * Adds [item] to this container, returning a new container.
     * The original container remains unchanged.
     * A container is a `Layer`, so the mutated copy needs to be saved.
     */
    fun stow(item: I): C {
        val updatedContents = contents.toMutableList()
        updatedContents.add(item)
        return updateContainer(worn, self, updatedContents)
    }

    /**
     * Removes [item] to this container, returning a new container.
     * The original container remains unchanged.
     * A container is a `Layer`, so the mutated copy needs to be saved.
     */
    fun unstow(item: I): C {
        val updatedContents = contents.toMutableList()
        updatedContents.remove(item)
        return updateContainer(worn, self, updatedContents)
    }

    override fun toString(): String = "${super.toString()}: $contents"
}
