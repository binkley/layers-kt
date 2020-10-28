package hm.binkley.layers

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.Test

internal class LayersTest {
    @Test
    fun `should have a debuggable presentation`() {
        Layers.new().toString() shouldBe "0: (EditableLayer) {}"
    }

    @Test
    fun `should start with a blank editable layer`() {
        val newLayers = Layers.new()

        newLayers.layers shouldBe listOf(Layer())
        newLayers.current.shouldBeInstanceOf<EditableLayer>()
    }

    @Test
    fun `should start with an initialized editable layer`() {
        val layers = listOf(
            EditableLayer().edit {
                this["bob"] = 4.asEntry
            },
            EditableLayer().edit {
                this["bob"] = 3.asEntry
            }
        )
        val newLayers = Layers.new(layers)

        newLayers.layers shouldBe layers
    }

    @Test
    fun `should start with a pre-initialized editable layer`() {
        val newLayers = Layers.new {
            this["bob"] = 4.asEntry
        }

        newLayers.layers shouldBe listOf(
            EditableLayer().edit {
                this["bob"] = 4.asEntry
            }
        )
    }

    @Test
    fun `should save current layer and create new layer`() {
        val layers = Layers.new()
        val originalCurrent = layers.current
        originalCurrent.edit {
            this["bob"] = 3.asEntry
        }
        val newCurrent = layers.saveAndNew()
        newCurrent.edit {
            this["bob"] = 4.asEntry
        }

        layers.layers shouldBe listOf(newCurrent, originalCurrent)
        layers.current shouldBe newCurrent
        newCurrent shouldNotBe originalCurrent
    }

    @Test
    fun `should edit while creating new layer`() {
        val layers = Layers.new()
        val originalCurrent = layers.current
        originalCurrent.edit {
            this["bob"] = 3.asEntry
        }
        val newCurrent = layers.saveAndNew {
            this["bob"] = 4.asEntry
        }

        layers.current shouldBe newCurrent
    }

    @Test
    fun `should read latest computed values`() {
        val layers = Layers.new {
            this["bob"] = object : Rule<Int>() {
                override fun invoke(values: List<Int>) =
                    values.last() * 1

                override fun description() = "Test Original Rule"
            }
        }
        layers.saveAndNew {
            this["bob"] = 4.asEntry
            this["mary"] = "Something else".asEntry
        }
        layers.saveAndNew {
            this["bob"] = object : Rule<Int>() {
                override fun invoke(values: List<Int>) =
                    values.last() * 2

                override fun description() = "Test Replacement Rule"
            }
        }

        layers["bob"] as Int shouldBe 8
    }

    /** This test increases coverage. */
    @Test
    fun `should complain on a missing rule`() {
        val layers = Layers.new {
            this["mary"] = object : Rule<String>() {
                override fun invoke(values: List<String>) = values.last()
                override fun description() = "Inapplicable Rule"
            }
        }

        shouldThrow<IllegalArgumentException> {
            layers["bob"] as Int shouldBe 16
        }
    }
}
