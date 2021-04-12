package hm.binkley.layers

import lombok.Generated
import java.util.Objects.hash

open class PlainLayer(
    override val name: String,
    map: EntryMap = emptyMap(),
    protected val editMap: EditMap = map.toMutableMap(),
) : Layer, EntryMap by editMap {
    @Generated
    override fun equals(other: Any?) = this === other ||
        other is PlainLayer &&
        name == other.name &&
        editMap == other.editMap

    override fun hashCode() = hash(name, editMap)
    override fun toString() = "$name: $editMap"
}
