package hm.binkley.layers.sets

import hm.binkley.layers.Layer
import hm.binkley.layers.rules.Rule
import java.util.*

class LayerSet<L : Layer<L>>(private val fullness: FullnessFunction<L>,
        private val set: MutableSet<L> = LinkedHashSet())
    : Set<L> by set {
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

    companion object {
        fun <L : Layer<L>> rule(fullness: FullnessFunction<L>): Rule<LayerSet<L>> = LayerSetRule(fullness)
    }
}
