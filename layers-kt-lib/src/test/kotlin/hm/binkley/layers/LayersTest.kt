package hm.binkley.layers

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.Test

internal class LayersTest {
    @Test
    fun `should have a debuggable presentation`() {
        Layers().toString() shouldBe "0: (EditableLayer) {}"
    }

    @Test
    fun `should construct with a starting editable layer`() {
        Layers() shouldBe listOf(Layer())
        Layers().current.shouldBeInstanceOf<EditableLayer>()
    }

    @Test
    fun `should save current layer and create new layer`() {
        val layers = Layers()
        val originalCurrent = layers.current
        originalCurrent.edit {
            this["bob"] = 3.asEntry
        }
        val newCurrent = layers.saveAndNew()
        newCurrent.edit {
            this["bob"] = 4.asEntry
        }

        layers shouldBe listOf(newCurrent, originalCurrent)
        layers.current shouldBe newCurrent
        newCurrent shouldNotBe originalCurrent
    }

    @Test
    fun `should edit while creating new layer`() {
        val layers = Layers()
        val originalCurrent = layers.current
        originalCurrent.edit {
            this["bob"] = 3.asEntry
        }
        val newCurrent = layers.saveAndNew {
            this["bob"] = 4.asEntry
        }

        layers.current shouldBe newCurrent
    }
}
