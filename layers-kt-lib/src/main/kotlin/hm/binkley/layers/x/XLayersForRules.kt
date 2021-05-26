package hm.binkley.layers.x

import hm.binkley.layers.x.util.XStack

interface XLayersForRules<K : Any, V : Any, M : XMutableLayer<K, V, M>> :
    Map<K, V> {
    val history: XStack<XLayer<K, V, M>>
    fun peek(): M
    fun whatIf(
        name: String = "<WHAT-IF>",
        block: XEditBlock<K, V>,
    ): Map<K, V>
}
