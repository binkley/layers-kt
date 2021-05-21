package hm.binkley.layers.x

abstract class XRule<V : Any, T : V>(
    private val name: String,
) : XValueOrRule<V>, (List<T>, XLayers<*, V, *>) -> V {
    final override fun toString() = "<Rule>: $name"
}
