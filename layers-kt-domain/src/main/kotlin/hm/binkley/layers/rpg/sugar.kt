package hm.binkley.layers.rpg

import hm.binkley.layers.EditMap
import hm.binkley.layers.EditMapDelegate
import hm.binkley.layers.Layers
import hm.binkley.layers.rpg.rules.TotalWeightRule.name

typealias RpgEditMap = EditMap<String, Any>
typealias RpgLayers = Layers<String, Any, *>

var RpgEditMap.`PLAYER-NAME`: String by EditMapDelegate { name }
var RpgEditMap.`CHARACTER-NAME`: String by EditMapDelegate { name }
var RpgEditMap.MIGHT: Int by EditMapDelegate { name }
var RpgEditMap.DEFTNESS: Int by EditMapDelegate { name }
var RpgEditMap.GRIT: Int by EditMapDelegate { name }
var RpgEditMap.WIT: Int by EditMapDelegate { name }
var RpgEditMap.FORESIGHT: Int by EditMapDelegate { name }
var RpgEditMap.PRESENCE: Int by EditMapDelegate { name }
@Suppress("ObjectPropertyName")
var RpgEditMap.`ITEM-WEIGHT`: Float by EditMapDelegate { name }
// TODO: Encumbrance
