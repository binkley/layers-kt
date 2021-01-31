package hm.binkley.layers.dnd

import hm.binkley.layers.MutableLayer
import lombok.Generated
import java.util.Objects.hash

class DndLayer(
    name: String,
    val fakeForMutation: Boolean,
) : MutableLayer(name) {
    @Generated
    override fun equals(other: Any?) = this === other ||
        super.equals(other) &&
        other is DndLayer &&
        fakeForMutation == other.fakeForMutation

    override fun hashCode() = 31 * super.hashCode() + hash(fakeForMutation)
}
