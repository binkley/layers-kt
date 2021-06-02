package hm.binkley.layers.rpg.item

import hm.binkley.layers.rpg.RpgLayer
import hm.binkley.layers.rpg.RpgLayersEditMap
import hm.binkley.layers.rpg.Stat.MIGHT
import hm.binkley.layers.rpg.rules.FloorRule

class GirdleOfHillGiantMight(layers: RpgLayersEditMap) :
    RpgLayer("Girdle of Might of the Hill Giant") {
    init {
        edit {
            this[MIGHT.name] = FloorRule(19, layers)
        }
    }
}
