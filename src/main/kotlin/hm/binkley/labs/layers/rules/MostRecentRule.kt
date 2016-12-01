package hm.binkley.labs.layers.rules

import hm.binkley.labs.layers.Layers

class MostRecentRule<T>(val defaultValue: T) : Rule<T, T>("Most recent") {
    override fun invoke(layers: Layers.RuleSurface): T {
        val values = layers.values<T>()
        return when {
            values.isEmpty() -> defaultValue
            else -> values.last()
        }
    }
}
