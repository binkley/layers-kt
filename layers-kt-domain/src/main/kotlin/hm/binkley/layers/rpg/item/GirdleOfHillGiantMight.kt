package hm.binkley.layers.rpg.item

import hm.binkley.layers.rpg.BaseStat.MIGHT
import hm.binkley.layers.rpg.RpgLayer
import kotlin.math.max

/**
 * @todo Do not repeat oneself calculating current might sans this item
 * @todo The rule is subtly mistaken.  Say a character had 2 magic items:
 *       1) An earlier item granted 22 strength
 *       2) This later item grants 21 strength
 *       The 22 should prevail.  A smarter rule would try what-if scenarios
 *       comparing without this item, and with this item
 */
class GirdleOfHillGiantMight : RpgLayer("Girdle of Might of the Hill Giant") {
    init {
        edit {
            this[MIGHT.name] =
                rule<Int>("Floor[Int](min=21)") { _, values, _ ->
                    max(21, 8 + values.sum())
                }
        }
    }
}
