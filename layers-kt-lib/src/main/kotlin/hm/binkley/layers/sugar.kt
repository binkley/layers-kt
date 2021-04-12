package hm.binkley.layers

typealias ValueMap = Map<String, Any>
internal typealias EntryPair = Pair<String, Entry<*>>
internal typealias EntryMap = Map<String, Entry<*>>
internal typealias EditableMap = MutableMap<String, Entry<*>>
internal typealias EditingBlock = EditableMap.() -> Unit
