package hm.binkley.layers.x

import hm.binkley.layers.x.XDefaultMutableLayer.Companion.defaultMutableLayer

internal val simpleMutableLayer = defaultMutableLayer<String, Any>()

internal class XLatestOfRule<V : Any, T : V>(
    private val default: T,
) : XRule<V, T>("Latest[default=$default]") {
    override fun invoke(
        values: List<T>,
        layers: XLayers<*, V, *>,
    ) = values.firstOrNull() ?: default
}

internal class XSumOfRule : XRule<Any, Int>("Sum") {
    override fun invoke(
        values: List<Int>,
        layers: XLayers<*, Any, *>,
    ) = values.sum()
}

internal class TestNamedLayer :
    XDefaultMutableLayer<String, Any, TestNamedLayer>("FRED")
