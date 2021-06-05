package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.RpgLayersEditMap
import hm.binkley.layers.rpg.Stat.MIGHT

class GirdleOfHillGiantMight private constructor(
    private val layers: RpgLayersEditMap,
    worn: Boolean,
    previous: GirdleOfHillGiantMight?,
) : WearableItem<GirdleOfHillGiantMight>(
    "Girdle of Might of the Hill Giant",
    worn,
    previous,
    layers
) {
    init {
        edit {
            this[MIGHT.name] = floorRuleIfWorn(19)
        }
    }

    companion object {
        fun girdleOfHillGiantMight(layers: RpgLayersEditMap) =
            GirdleOfHillGiantMight(layers, false, null)
    }

    override fun new(
        active: Boolean,
        previous: GirdleOfHillGiantMight,
    ) = GirdleOfHillGiantMight(layers, active, previous)
}
