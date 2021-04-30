package hm.binkley.layers

/* public */ typealias ValueMap = Map<String, Any>
typealias EntryPair = Pair<String, Entry<*>>
typealias EntryMap = Map<String, Entry<*>>
typealias EditableMap = MutableMap<String, Entry<*>>
typealias EditingBlock = EditableMap.() -> Unit
