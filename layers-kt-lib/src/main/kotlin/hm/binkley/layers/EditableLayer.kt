package hm.binkley.layers

class EditableLayer(
    map: MutableMap<String, Entry<*>> = mutableMapOf(),
) : Layer(map) {
    fun edit(block: MutableMap<String, Entry<*>>.() -> Unit) = apply {
        map.block()
    }
}
