package hm.binkley.layers

open class PlainLayer(
    override val name: String,
    protected val map: MutableMap<String, Entry<*>> = mutableMapOf(),
) : Layer, Map<String, Entry<*>> by map {
    override fun equals(other: Any?) = when {
        this === other -> true
        other !is PlainLayer -> false
        name != other.name -> false
        map != other.map -> false
        else -> true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + map.hashCode()
        return result
    }

    override fun toString() = "$name: $map"
}
