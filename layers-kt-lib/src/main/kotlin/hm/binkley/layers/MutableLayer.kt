package hm.binkley.layers

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
open class MutableLayer(
    name: String,
    map: MutableMap<String, Entry<*>> = mutableMapOf(),
) : Layer(name, map), MutableMap<String, Entry<*>> by map {
    fun edit(block: MutableMap<String, Entry<*>>.() -> Unit): MutableLayer {
        block()
        return this
    }
}
