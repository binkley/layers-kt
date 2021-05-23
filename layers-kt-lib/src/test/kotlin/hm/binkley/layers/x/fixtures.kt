package hm.binkley.layers.x

import hm.binkley.layers.x.XDefaultMutableLayer.Companion.defaultMutableLayer

internal val testMutableLayer = defaultMutableLayer<String, Any>()

internal class TestNamedLayer :
    XDefaultMutableLayer<String, Any, TestNamedLayer>("FRED")

internal class TestSubtypeLayer(name: String) :
    XDefaultMutableLayer<String, Any, TestSubtypeLayer>(name) {
    @Suppress("UNCHECKED_CAST")
    fun foo(key: String) {
        this[key] = (2 * getValueAs<Int>(key)).toValue()
    }
}
