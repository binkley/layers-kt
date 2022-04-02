package hm.binkley.layers.rpg

import hm.binkley.layers.EditMap
import hm.binkley.layers.EditMapDelegate
import hm.binkley.layers.Layers

typealias RpgEditMap = EditMap<String, Any>
typealias RpgLayers = Layers<String, Any, *>

var RpgEditMap.PLAYER_NAME: String by EditMapDelegate { name }
var RpgEditMap.CHARACTER_NAME: String by EditMapDelegate { name }
var RpgEditMap.MIGHT: Int by EditMapDelegate { name }
var RpgEditMap.DEFTNESS: Int by EditMapDelegate { name }
var RpgEditMap.GRIT: Int by EditMapDelegate { name }
var RpgEditMap.WIT: Int by EditMapDelegate { name }
var RpgEditMap.FORESIGHT: Int by EditMapDelegate { name }
var RpgEditMap.PRESENCE: Int by EditMapDelegate { name }
var RpgEditMap.`ITEM-WEIGHT`: Float by EditMapDelegate { name }
// TODO: Encumbrance
