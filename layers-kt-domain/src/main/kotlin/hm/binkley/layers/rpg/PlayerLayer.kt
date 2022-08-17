package hm.binkley.layers.rpg

import hm.binkley.layers.rules.lastOrDefaultRule

/**
 * This layer tracks basic player details.
 * Supported keys are:
 * - "PLAYER-NAME" &mdash; the (real world) player name
 * - "CHARACTER-NAME" &mdash; the (in game) character name
 */
class PlayerLayer : RpgLayer<PlayerLayer>("Character") {
    init {
        edit {
            this["PLAYER-NAME"] = lastOrDefaultRule("")
            this["CHARACTER-NAME"] = lastOrDefaultRule("")
        }
    }
}
