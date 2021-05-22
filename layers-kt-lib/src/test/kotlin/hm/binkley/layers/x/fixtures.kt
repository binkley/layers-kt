package hm.binkley.layers.x

import hm.binkley.layers.x.XDefaultMutableLayer.Companion.defaultMutableLayer

internal val simpleMutableLayer = defaultMutableLayer<String, Any>()

internal class TestNamedLayer :
    XDefaultMutableLayer<String, Any, TestNamedLayer>("FRED")
