package hm.binkley.layers.x.rules

import hm.binkley.layers.x.XLayers
import hm.binkley.layers.x.XRule

class XLatestOfRule<V : Any, T : V>(
    private val default: T,
) : XRule<V, T>("Latest[default=$default]") {
    override fun invoke(
        values: List<T>,
        layers: XLayers<*, V, *>,
    ) = values.firstOrNull() ?: default

    companion object {
        fun <V : Any, T : V> latestOfRule(default: T) = XLatestOfRule(default)
    }
}
