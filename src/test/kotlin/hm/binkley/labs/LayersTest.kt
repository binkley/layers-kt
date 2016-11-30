package hm.binkley.labs

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.function.Consumer

class LayersTest {
    lateinit var layers: Layers
    lateinit var firstLayer: Layer<*>

    @BeforeEach
    fun setUpLayers() {
        firstLayer = Layers.firstLayer(
                Consumer<Layers> { layers -> this.layers = layers },
                ::ScratchLayer)
    }

    @Test
    fun shouldBootstrap() {
    }
}
