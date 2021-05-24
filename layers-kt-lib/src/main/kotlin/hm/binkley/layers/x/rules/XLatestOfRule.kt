package hm.binkley.layers.x.rules

import hm.binkley.layers.x.XLayers
import hm.binkley.layers.x.XRule

class XLatestOfRule<K : Any, V : Any, T : V>(
    key: K,
    private val default: T,
) : XRule<K, V, T>(key, "Latest(default=$default)") {
    override fun invoke(
        key: K,
        values: List<T>,
        layers: XLayers<*, V, *>,
    ) = values.firstOrNull() ?: default

    companion object {
        fun <K : Any, V : Any, T : V> latestOfRule(key: K, default: T) =
            XLatestOfRule(key, default)
    }
}
