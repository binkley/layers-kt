package hm.binkley.layers.rpg

import hm.binkley.layers.rules.lastOrDefaultRule

/**
 * This layer tracks basic player details.
 * Supported keys are:
 * - "PLAYER_NAME" &mdash; the (real world) player name
 * - "CHARACTER_NAME" &mdash; the (in game) character name
 */
class PlayerLayer : RpgLayer<PlayerLayer>("Character") {
    init {
        edit {
            this["PLAYER_NAME"] = lastOrDefaultRule("")
            this["CHARACTER_NAME"] = lastOrDefaultRule("")
        }
    }
}
