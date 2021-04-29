package hm.binkley.layers.rpg

import hm.binkley.layers.MutablePlainLayer
import lombok.Generated
import java.util.Objects.hash

class Rpg(
    name: String,
    val fakeForMutation: Boolean,
) : MutablePlainLayer(name) {
    @Generated
    override fun equals(other: Any?) = this === other ||
        super.equals(other) &&
        other is Rpg &&
        fakeForMutation == other.fakeForMutation

    override fun hashCode() = hash(super.hashCode(), fakeForMutation)
}
