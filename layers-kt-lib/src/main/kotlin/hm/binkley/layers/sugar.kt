package hm.binkley.layers

internal typealias EditMap = MutableMap<String, Entry<*>>
internal typealias EditBlock = EditMap.() -> Unit
