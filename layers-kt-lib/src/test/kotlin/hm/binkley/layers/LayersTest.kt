package hm.binkley.layers

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.Test

internal class LayersTest {
    @Test
    fun `should construct with a starting editable layer`() {
        Layers() shouldBe listOf(Layer())
        Layers().current.shouldBeInstanceOf<EditableLayer>()
    }
}
