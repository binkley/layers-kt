package hm.binkley.layers

class MutableLayer(
    map: MutableMap<String, Entry<*>> = mutableMapOf(),
) : Layer(map) {
    fun edit(block: MutableMap<String, Entry<*>>.() -> Unit): Layer = apply {
        map.block()
    }
}
