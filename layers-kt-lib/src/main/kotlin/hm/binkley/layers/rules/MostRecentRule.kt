package hm.binkley.layers.rules

import hm.binkley.layers.Layers.RuleSurface

internal class MostRecentRule<out R>(val defaultValue: R) : Rule<R>(
    "Most recent"
) {
    override fun invoke(layers: RuleSurface): R {
        val values = layers.values<R>()
        return when {
            values.isEmpty() -> defaultValue
            else -> values.last()
        }
    }
}
