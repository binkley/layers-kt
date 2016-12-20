package hm.binkley.layers

import hm.binkley.layers.Layers.Companion.firstLayer
import hm.binkley.layers.rules.Rule.Companion.floor
import hm.binkley.layers.rules.Rule.Companion.mostRecent
import hm.binkley.layers.rules.Rule.Companion.sumAll

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
            put(String::class.java, mostRecent("Zaphod")).
            saveAndNext(::ScratchLayer).
            put(String::class.java, "Bob").
            saveAndNext(::ScratchLayer).
            put(String::class.java, "Nancy").
            saveAndNext(::ScratchLayer)
    println(layers)
    println(layers["A"])
    println(layers[String::class.java])
}