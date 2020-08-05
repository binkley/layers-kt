package hm.binkley.layers

import hm.binkley.layers.Layers.Companion.firstLayer
import hm.binkley.layers.rules.Rule.Companion.atLeast
import hm.binkley.layers.rules.Rule.Companion.mostRecent
import hm.binkley.layers.rules.Rule.Companion.sumAll
import lombok.Generated

@Generated
fun main() {
    val (layers, firstLayer) = firstLayer(::ScratchLayer)
    firstLayer.put("A", sumAll())
        .saveAndNext(::ScratchLayer).put("A", 1)
        .saveAndNext(::ScratchLayer).put("A", 2)
        .saveAndNext(::ScratchLayer).put("A", atLeast(4))
        .saveAndNext(::ScratchLayer)
        .put(Sample::class, mostRecent("Zaphod"))
        .saveAndNext(::ScratchLayer).put(Sample::class, "Bob")
        .saveAndNext(::ScratchLayer).put(Sample::class, "Nancy")
        .saveAndNext(::ScratchLayer)
    println(layers)
    println(layers["A"])
    println(layers[Sample::class])
}

private class Sample
