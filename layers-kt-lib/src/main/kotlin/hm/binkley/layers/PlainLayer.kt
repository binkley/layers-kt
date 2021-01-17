package hm.binkley.layers

import java.util.Objects.hash

open class PlainLayer(
    override val name: String,
    protected val map: MutableMap<String, Entry<*>> = mutableMapOf(),
) : Layer, Map<String, Entry<*>> by map {
    override fun equals(other: Any?) = this === other ||
        other is PlainLayer &&
        name == other.name &&
        map == other.map

    override fun hashCode() = hash(name, map)

    override fun toString() = "$name: $map"
}
