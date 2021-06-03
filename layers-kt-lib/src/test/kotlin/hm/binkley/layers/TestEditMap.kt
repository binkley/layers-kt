package hm.binkley.layers

internal class TestEditMap(
    private val map: MutableMap<String, ValueOrRule<Number>> = mutableMapOf(),
) : EditMap<String, Number>, MutableMap<String, ValueOrRule<Number>> by map
