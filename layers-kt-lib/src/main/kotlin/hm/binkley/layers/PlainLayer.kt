package hm.binkley.layers

open class PlainLayer(
    protected val map: MutableMap<String, Entry<*>> = mutableMapOf(),
) : Layer, Map<String, Entry<*>> by map {
    override fun toString() = map.toString()
}
