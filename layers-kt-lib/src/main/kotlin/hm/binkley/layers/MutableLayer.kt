package hm.binkley.layers

interface MutableLayer : Layer, EditableMap {
    fun edit(vararg changes: EntryPair): MutableLayer = edit(changes.toMap())

    fun edit(changes: EntryMap): MutableLayer {
        this += changes
        return this
    }

    fun edit(block: EditingBlock): MutableLayer {
        block()
        return this
    }
}
