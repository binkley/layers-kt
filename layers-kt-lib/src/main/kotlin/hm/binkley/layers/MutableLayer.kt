package hm.binkley.layers

interface MutableLayer<L : MutableLayer<L>> : Layer<L>, EditableMap {
    fun edit(vararg changes: EntryPair): L = edit(changes.toMap())

    fun edit(changes: EntryMap): L {
        this += changes
        return self
    }

    fun edit(block: EditingBlock): L {
        block()
        return self
    }
}
