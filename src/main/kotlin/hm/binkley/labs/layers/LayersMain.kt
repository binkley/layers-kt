package hm.binkley.labs.layers

import hm.binkley.labs.layers.Layers.Companion.firstLayer
import hm.binkley.labs.layers.rules.Rule.Companion.floor
import hm.binkley.labs.layers.rules.Rule.Companion.mostRecent
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
            saveAndNext(::ScratchLayer).
            put("B", mostRecent("Zaphod")).
            saveAndNext(::ScratchLayer).
            put("B", "Bob").
            saveAndNext(::ScratchLayer).
            put("B", "Nancy").
            saveAndNext(::ScratchLayer)
    println(layers)
    println(layers["A"])
    println(layers["B"])
}
