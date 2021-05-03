package hm.binkley.layers

interface Layer<L : Layer<L>> : EntryMap {
    val name: String

    @Suppress("UNCHECKED_CAST")
    val self: L
        get() = this as L
}
