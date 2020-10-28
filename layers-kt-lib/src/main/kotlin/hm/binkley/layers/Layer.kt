package hm.binkley.layers

open class Layer(
    protected val map: MutableMap<String, Entry<*>> = mutableMapOf(),
) : Map<String, Entry<*>> by map {
    override fun toString() = map.toString()
}
