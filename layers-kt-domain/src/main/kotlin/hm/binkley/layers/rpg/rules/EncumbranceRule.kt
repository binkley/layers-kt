package hm.binkley.layers.rpg.rules

import hm.binkley.layers.rpg.RpgRule
import hm.binkley.layers.rpg.RpgView

class EncumbranceRule : RpgRule<Float>("Encumbrance[Float]") {
    override fun invoke(
        key: String,
        values: List<Float>,
        view: RpgView,
    ): Float = values.sum()
}
