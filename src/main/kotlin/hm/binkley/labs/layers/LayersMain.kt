package hm.binkley.labs.layers

import hm.binkley.labs.layers.Layers.Companion.firstLayer
import hm.binkley.labs.layers.rules.Rule.Companion.floor
import hm.binkley.labs.layers.rules.Rule.Companion.sumAll

class LayersMain

fun main(args: Array<String>) {
    val (layers, firstLayer) = firstLayer(::ScratchLayer)
    firstLayer.put("A", sumAll()).
            saveAndNext(::ScratchLayer).
            put("A", 1).
            saveAndNext(::ScratchLayer).
            put("A", 2).
            saveAndNext(::ScratchLayer).
            put("A", floor(4)).
            saveAndNext(::ScratchLayer)
    println(layers)
    println(layers["A"])
}
