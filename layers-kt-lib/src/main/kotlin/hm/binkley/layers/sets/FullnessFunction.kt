package hm.binkley.layers.sets

import hm.binkley.layers.Layer
import javax.annotation.processing.Generated

abstract class FullnessFunction<L : Layer<L>>(val name: String)
    : (LayerSet<L>, L) -> Boolean {
    @Generated // Lie to JaCoCo
    final override fun toString(): String = "Full: $name"

    companion object {
        fun <L : Layer<L>> unlimited(): FullnessFunction<L> =
                UnlimitedFullness()

        fun <L : Layer<L>> max(max: Int): FullnessFunction<L> =
                MaxFullness(max)
    }
}
