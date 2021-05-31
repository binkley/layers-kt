package hm.binkley.layers.rpg.item

import hm.binkley.layers.rpg.BaseStat.MIGHT
import hm.binkley.layers.rpg.RpgLayer

class GirdleOfHillGiantMight : RpgLayer("Girdle of Might of the Hill Giant") {
    init {
        edit {
            this[MIGHT.name] = constantRule(20)
        }
    }
}
