package hm.binkley.layers

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class LayersTest {
    @Test
    fun `should construct de novo`() {
        Layers().layers shouldBe listOf(mapOf())
    }
}
