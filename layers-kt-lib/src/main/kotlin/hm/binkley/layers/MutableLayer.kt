package hm.binkley.layers

interface MutableLayer : Layer, EditMap {
    fun edit(vararg changes: Pair<String, Entry<*>>): MutableLayer =
        edit(changes.toMap())

    fun edit(changes: EntryMap): MutableLayer {
        this += changes
        return this
    }

    fun edit(block: EditBlock): MutableLayer {
        block()
        return this
    }
}
