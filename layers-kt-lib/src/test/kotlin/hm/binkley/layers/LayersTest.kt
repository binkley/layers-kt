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
        val knownKey = "bob"
        val layers = Layers.new {
            this[knownKey] = object : Rule<Int>() {
                override fun invoke(values: List<Int>) =
                    values.last() * 1

                override fun description() = "Test Original Rule"
            }
        }
        layers.saveAndNew {
            this[knownKey] = 4.asEntry
            this["mary"] = "Something else".asEntry
        }
        layers.saveAndNew {
            this[knownKey] = object : Rule<Int>() {
                override fun invoke(values: List<Int>) =
                    values.last() * 2

                override fun description() = "Test Replacement Rule"
            }
        }

        layers["bob"] as Int shouldBe 8
    }

    @Test
    fun `should complain on a missing rule`() {
        val layers = Layers.new {
            this["mary"] = object : Rule<String>() {
                override fun invoke(values: List<String>) =
                    throw NullPointerException()

                override fun description() = "Impossible Rule"
            }
        }

        layers.saveAndNew {
            this["mary"] = "Unknown value".asEntry
        }

        shouldThrow<IllegalArgumentException> {
            layers["bob"] as Int shouldBe 16
        }
    }

    @Test
    fun `should read value from current (editable) layer`() {
        val knownKey = "bob"
        val layers = Layers.new {
            this[knownKey] = object : Rule<Int>() {
                override fun invoke(values: List<Int>) =
                    values.last() * 2

                override fun description() = "Test Rule"
            }
        }
        layers.saveAndNew {
            this[knownKey] = 4.asEntry
        }

        layers["bob"] shouldBe 8
    }
}
