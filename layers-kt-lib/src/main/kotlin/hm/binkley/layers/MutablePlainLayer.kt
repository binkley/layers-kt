package hm.binkley.layers

open class MutablePlainLayer(
    map: MutableMap<String, Entry<*>> = mutableMapOf(),
) : PlainLayer(map), MutableLayer, MutableMap<String, Entry<*>> {
    @Suppress("UNCHECKED_CAST")
    override fun edit(
        block: MutableMap<String, Entry<*>>.() -> Unit,
    ): MutableLayer =
        apply {
            map.block()
        }

    // TODO: How to avoid all the overrides
    override operator fun get(key: String) = map[key]
    override val entries get() = map.entries
    override val keys get() = map.keys
    override val values get() = map.values
    override fun clear() = map.clear()
    override fun put(key: String, value: Entry<*>) = map.put(key, value)
    override fun putAll(from: Map<out String, Entry<*>>) = map.putAll(from)
    override fun remove(key: String) = map.remove(key)
}
