package hm.binkley.layers

internal typealias EntryMap = Map<String, Entry<*>>
internal typealias EditMap = MutableMap<String, Entry<*>>
internal typealias EditBlock = EditMap.() -> Unit
