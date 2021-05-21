package hm.binkley.layers.x

data class XValue<V : Any>(val value: V) : XValueOrRule<V> {
    override fun toString() = "<Value[${value::class.simpleName}]>: $value"
}
