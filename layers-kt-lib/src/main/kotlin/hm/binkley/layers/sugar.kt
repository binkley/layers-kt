package hm.binkley.layers

/* public */ typealias ValueMap = Map<String, Any>
typealias LayerPair = Pair<String, ValueOrRule<*>>
typealias LayerMap = Map<String, ValueOrRule<*>>
typealias LayerMutableMap = MutableMap<String, ValueOrRule<*>>
typealias EditBlock = LayerMutableMap.() -> Unit
