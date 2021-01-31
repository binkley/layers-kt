package hm.binkley.layers

import java.util.Objects.hash

open class Layer(
    val name: String,
    protected val map: MutableMap<String, Entry<*>> = mutableMapOf(),
) : Map<String, Entry<*>> by map {
    override fun equals(other: Any?) = this === other ||
        other is Layer &&
        name == other.name &&
        map == other.map

    override fun hashCode() = hash(name, map)
    override fun toString() = "$name: $map"
}
