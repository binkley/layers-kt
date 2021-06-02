package hm.binkley.layers.rpg.rules

import hm.binkley.layers.rpg.RpgEditMap

/**
 * Stats are cumulative, not absolute.  To fix a stat at a value, add a new
 * constant rule.
 */
fun RpgEditMap.statRule(stat: String) =
    rule<Int>("Stat[Int](stat=$stat)") { _, values, _ ->
        values.sum()
    }

fun RpgEditMap.statBonusRule(stat: String) =
    rule<Int>("Stat-Bonus[Int](stat=$stat)") { _, _, view ->
        (view[stat] as Int - 10) / 2
    }
