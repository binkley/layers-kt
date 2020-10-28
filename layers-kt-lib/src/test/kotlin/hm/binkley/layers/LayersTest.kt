package hm.binkley.layers

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
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
    fun `should start from a list of layers`() {
        val layers = listOf(
            EditableLayer().edit {
                this[bobKey] = bobRule
            },
        )
        val newLayers = Layers.new(layers)

        newLayers.layers shouldBe layers
    }

    @Test
    fun `should start from a block`() {
        val newLayers = Layers.new {
            this[bobKey] = bobRule
        }

        newLayers.layers shouldBe listOf(
            EditableLayer(mutableMapOf(bobKey to bobRule))
        )
    }

    @Test
    fun `should save current layer and create a new layer`() {
        val ruleLayer = EditableLayer().edit {
            this[bobKey] = bobRule
        }
        val layers = Layers.new(listOf(ruleLayer))
        val firstValueLayer = layers.saveAndNew {
            this[bobKey] = 4.asEntry
        }

        layers.layers shouldBe listOf(firstValueLayer, ruleLayer)
        layers.current shouldBe firstValueLayer
        firstValueLayer shouldNotBeSameInstanceAs ruleLayer
    }

    @Test
    fun `should edit while creating new layer`() {
        val ruleLayer = EditableLayer().edit {
            this[bobKey] = bobRule
        }
        val layers = Layers.new(listOf(ruleLayer))
        layers.saveAndNew {
            this[bobKey] = 4.asEntry
        }

        layers.current.edit {
            this[bobKey] = 3.asEntry
        }

        layers.current shouldBe EditableLayer(
            mutableMapOf(bobKey to 3.asEntry)
        )
    }

    @Test
    fun `should read latest computed values`() {
        val layers = Layers.new {
            this[bobKey] = object : Rule<Int>() {
                override fun invoke(values: List<Int>) =
                    values.last() * 1

                override fun description() = "Original Bob Rule"
            }
        }
        layers.saveAndNew {
            this[bobKey] = 4.asEntry
            this["mary"] = "Something else".asEntry
        }
        layers.saveAndNew {
            this[bobKey] = bobRule
        }

        layers[bobKey] as Int shouldBe 8
    }

    @Test
    fun `should complain on a missing rule`() {
        val layers = Layers.new {
            this[maryKey] = maryRule
        }

        layers.saveAndNew {
            this[maryKey] = "Unknown value".asEntry
        }

        shouldThrow<IllegalArgumentException> {
            layers[bobKey] as Int shouldBe 16
        }
    }

    @Test
    fun `should lookup value based on rule`() {
        val layers = Layers.new {
            this[bobKey] = bobRule
        }
        layers.saveAndNew {
            this[bobKey] = 4.asEntry
        }

        layers[bobKey] shouldBe 8
    }
}

private const val bobKey = "bob"
private const val maryKey = "mary"

private val bobRule = object : Rule<Int>() {
    override fun invoke(values: List<Int>) =
        values.last() * 2

    override fun description() = "Test Bob Rule"
}

private val maryRule = object : Rule<String>() {
    override fun invoke(values: List<String>) =
        throw NullPointerException()

    override fun description() = "Impossible Rule"
}
