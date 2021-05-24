package hm.binkley.layers.x

abstract class XRule<K : Any, V : Any, T : V>(
    val key: K, // TODO: Rethink rules knowing their keys -- overkill?
    private val name: String,
) : XValueOrRule<V>, (K, List<T>, XLayers<*, V, *>) -> T {
    final override fun toString() = "<Rule/$key>: $name"
}
