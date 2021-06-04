package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.RpgLayersEditMap
import hm.binkley.layers.rpg.Stat.MIGHT

class GirdleOfHillGiantMight(
    private val layers: RpgLayersEditMap,
    active: Boolean = false,
) : ActiveItem<GirdleOfHillGiantMight>(
    "Girdle of Might of the Hill Giant",
    false,
    layers
) {
    init {
        edit {
            this[MIGHT.name] =
                if (active) floorRule(19)
                else passThruRule()
        }
    }

    override fun new(active: Boolean) = GirdleOfHillGiantMight(layers, active)
}
