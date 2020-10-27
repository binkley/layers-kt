package hm.binkley.layers

class MutableLayer(
    map: MutableMap<String, Any> = mutableMapOf(),
) : Layer(map) {
    fun edit(block: MutableMap<String, Any>.() -> Unit): Layer = apply {
        map.block()
    }
}
