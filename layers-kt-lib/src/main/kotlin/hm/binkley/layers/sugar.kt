package hm.binkley.layers

/* public */ typealias ValueMap = Map<String, Any>
typealias EntryPair = Pair<String, ValueOrRule<*>>
typealias EntryMap = Map<String, ValueOrRule<*>>
typealias EditableMap = MutableMap<String, ValueOrRule<*>>
typealias EditingBlock = EditableMap.() -> Unit
