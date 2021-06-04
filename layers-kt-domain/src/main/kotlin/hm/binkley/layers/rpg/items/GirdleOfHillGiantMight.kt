package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.RpgLayersEditMap
import hm.binkley.layers.rpg.Stat.MIGHT

class GirdleOfHillGiantMight(
    private val layers: RpgLayersEditMap,
    active: Boolean = false,
) : ActiveItem<GirdleOfHillGiantMight>(
    "Girdle of Might of the Hill Giant",
    active,
    layers
) {
    init {
        edit {
            this[MIGHT.name] = activeFloorRule(19)
        }
    }

    override fun new(active: Boolean) = GirdleOfHillGiantMight(layers, active)
}
