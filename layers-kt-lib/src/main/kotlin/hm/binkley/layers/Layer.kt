package hm.binkley.layers

open class Layer(
    protected val map: MutableMap<String, Entry<*>> = mutableMapOf(),
) : Map<String, Any> by map
