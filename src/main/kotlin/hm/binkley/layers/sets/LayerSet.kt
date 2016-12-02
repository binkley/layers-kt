package hm.binkley.layers.sets

import hm.binkley.layers.Layer
import java.util.*

class LayerSet<L : Layer<L>>(val name: String,
        private val fullness: FullnessFunction<L>,
        private val set: MutableSet<L> = LinkedHashSet())
    : Set<L> by set {
    override fun toString(): String = "[Set: $name ($fullness)]"

    fun add(element: L) {
        if (fullness.invoke(this, element))
            throw IndexOutOfBoundsException(fullness.name)
        if (!set.add(element))
            throw IllegalStateException()
    }

    fun remove(element: L) {
        if (!set.remove(element))
            throw NoSuchElementException()
    }
}
