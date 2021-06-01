package hm.binkley.layers.rpg.item

import hm.binkley.layers.rpg.RpgLayer
import hm.binkley.layers.rpg.Stat.MIGHT
import kotlin.math.max

/**
 * @todo Do not repeat oneself calculating current might sans this item
 * @todo The rule is subtly mistaken.  Say a character had 2 magic items:
 *       1) An earlier item granted 20 strength
 *       2) This later item grants 19 strength
 *       The 20 should prevail.  A smarter rule would try what-if scenarios
 *       comparing without this item, and with this item
 */
class GirdleOfHillGiantMight :
    RpgLayer("Girdle of Might of the Hill Giant") {
    init {
        edit {
            this[MIGHT.name] =
                rule<Int>("Floor[Int](min=19)") { _, values, _ ->
                    max(19, values.sum())
                }
        }
    }
}
