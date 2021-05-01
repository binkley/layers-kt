package hm.binkley.layers.rpg.item

import hm.binkley.layers.rpg.BaseStat.MIGHT
import hm.binkley.layers.rpg.RpgLayer
import hm.binkley.layers.rules.ConstantRule.Companion.constantRule

class GirdleOfHillGiantMight :
    RpgLayer("Girdle of Might of the Hill Giant") {
    init {
        edit {
            this[MIGHT.name] = constantRule(MIGHT.name, 20)
        }
    }
}
