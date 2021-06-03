package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.RpgLayersEditMap
import hm.binkley.layers.rpg.Stat.MIGHT
import hm.binkley.layers.rpg.rules.FloorRule

class GirdleOfHillGiantMight(layers: RpgLayersEditMap) :
    Item("Girdle of Might of the Hill Giant") {
    init {
        edit {
            this[MIGHT.name] = FloorRule(19, layers)
        }
    }
}
