package hm.binkley.labs.layers

import hm.binkley.labs.layers.Layers.Companion.firstLayer

class LayersMain

fun main(args: Array<String>) {
    val (layers, firstLayer) = firstLayer(::ScratchLayer)
    firstLayer.saveAndNext(::ScratchLayer).
            put("A", 1).
            saveAndNext(::ScratchLayer).
            put("A", 2).
            saveAndNext(::ScratchLayer)

    println(layers)
    println(layers.get("A"))
}
