package hm.binkley.layers

open class EditableLayer(
    map: MutableMap<String, Entry<*>> = mutableMapOf(),
) : Layer(map) {
    fun edit(block: MutableMap<String, Entry<*>>.() -> Unit) = apply {
        map.block()
    }
}
