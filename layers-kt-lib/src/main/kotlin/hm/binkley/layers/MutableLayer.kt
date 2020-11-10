package hm.binkley.layers

interface MutableLayer : Layer, MutableMap<String, Entry<*>> {
    fun edit(block: MutableMap<String, Entry<*>>.() -> Unit): MutableLayer
}
