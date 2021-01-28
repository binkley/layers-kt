package hm.binkley.layers

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
open class MutablePlainLayer(
    name: String,
    map: MutableMap<String, Entry<*>> = mutableMapOf(),
) : PlainLayer(name, map),
    MutableLayer,
    MutableMap<String, Entry<*>> by map {
    override fun edit(
        block: MutableMap<String, Entry<*>>.() -> Unit,
    ): MutableLayer = apply {
        map.block()
    }
}
