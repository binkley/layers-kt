package hm.binkley.layers

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
open class MutablePlainLayer(
    name: String,
    map: Map<String, Entry<*>> = mapOf(),
) : PlainLayer(name, map),
    MutableLayer,
    EditMap by map.toMutableMap()
