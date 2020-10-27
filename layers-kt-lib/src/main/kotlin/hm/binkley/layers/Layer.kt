package hm.binkley.layers

open class Layer(
    protected val map: MutableMap<String, Any> = mutableMapOf(),
) : Map<String, Any> by map
