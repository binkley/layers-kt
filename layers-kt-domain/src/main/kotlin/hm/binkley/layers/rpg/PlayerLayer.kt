package hm.binkley.layers.rpg

/**
 * This layer tracks basic player details.
 * Supported keys are:
 * - "PLAYER_NAME" &mdash; the (real world) player name
 * - "CHARACTER_NAME" &mdash; the (in game) character name
 */
class PlayerLayer : RpgLayer<PlayerLayer>("Character") {
    init {
        edit {
            this["PLAYER_NAME"] = latestRule("")
            this["CHARACTER_NAME"] = latestRule("")
        }
    }
}
