package hm.binkley.layers.rpg.rules

import hm.binkley.layers.EditMap

/**
 * Stats are cumulative, not absolute.  To fix a stat at a value, add a new
 * constant rule.
 */
fun EditMap<String, Any>.statRule(stat: String) =
    rule<Int>("Stat[Int](stat=$stat)") { _, values, _ ->
        values.sum()
    }

fun EditMap<String, Any>.statBonusRule(stat: String) =
    rule<Int>("Stat-Bonus[Int](stat=$stat)") { _, _, view ->
        (view[stat] as Int - 10) / 2
    }
