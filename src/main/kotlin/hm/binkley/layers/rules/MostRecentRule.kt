package hm.binkley.layers.rules

import hm.binkley.layers.Layers

class MostRecentRule<T>(val defaultValue: T) : Rule<T, T>("Most recent") {
    override fun invoke(layers: Layers.RuleSurface): T {
        val values = layers.values<T>()
        return when {
            values.isEmpty() -> defaultValue
            else -> values.last()
        }
    }
}
