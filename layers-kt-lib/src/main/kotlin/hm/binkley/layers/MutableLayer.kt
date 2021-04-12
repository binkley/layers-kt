package hm.binkley.layers

interface MutableLayer : Layer, MutableMap<String, Entry<*>> {
    fun edit(vararg changes: Pair<String, Entry<*>>): MutableLayer {
        changes.forEach {
            this[it.first] = it.second
        }
        return this
    }

    fun edit(changes: Map<String, Entry<*>>): MutableLayer {
        this += changes
        return this
    }

    fun edit(block: MutableMap<String, Entry<*>>.() -> Unit): MutableLayer
}
