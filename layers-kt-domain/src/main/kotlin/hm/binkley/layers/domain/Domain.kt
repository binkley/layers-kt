package hm.binkley.layers.domain

import hm.binkley.layers.MutablePlainLayer
import lombok.Generated
import java.util.Objects.hash

class Domain(
    name: String,
    val fakeForMutation: Boolean,
) : MutablePlainLayer(name) {
    @Generated
    override fun equals(other: Any?) = this === other ||
        super.equals(other) &&
        other is Domain &&
        fakeForMutation == other.fakeForMutation

    override fun hashCode() = 31 * super.hashCode() + hash(fakeForMutation)
}
