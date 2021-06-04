package hm.binkley.layers.rpg

class StatLayer : RpgLayer<StatLayer>("Stats") {
    init {
        edit { addStatRules() }
    }
}
