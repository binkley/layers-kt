package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.RpgLayersEditMap
import hm.binkley.layers.rpg.Stat.MIGHT
import hm.binkley.layers.rpg.rules.FloorRule
import hm.binkley.layers.rpg.rules.PassThruRule

class GirdleOfHillGiantMight(
    private val layers: RpgLayersEditMap,
    active: Boolean = false,
) : Item<GirdleOfHillGiantMight>("Girdle of Might of the Hill Giant", false) {
    init {
        edit {
            this[MIGHT.name] =
                if (active) FloorRule(19, this@GirdleOfHillGiantMight, layers)
                else PassThruRule(this@GirdleOfHillGiantMight, layers)
        }
    }

    override fun new(active: Boolean): GirdleOfHillGiantMight {
        return GirdleOfHillGiantMight(layers, active)
    }
}
