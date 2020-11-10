package hm.binkley.layers.domain

import hm.binkley.layers.MutablePlainLayer

class Domain(
    name: String,
    val fakeForMutation: Boolean,
) : MutablePlainLayer(name)
