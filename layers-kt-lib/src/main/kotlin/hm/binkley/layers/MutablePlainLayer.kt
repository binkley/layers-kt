package hm.binkley.layers

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
open class MutablePlainLayer<L : MutablePlainLayer<L>>(
    name: String,
    map: EntryMap = mapOf(),
) : PlainLayer<L>(name, map),
    MutableLayer<L>,
    EditableMap by map.toMutableMap() // TODO: ?? use PlainLayer?

class DefaultMutableLayer(
    name: String,
    map: EntryMap = mapOf(),
) : MutablePlainLayer<DefaultMutableLayer>(name, map)
