package hm.binkley.layers

import hm.binkley.layers.rules.SumRule.Companion.sumOfRule
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.junit.jupiter.api.Test
import java.util.AbstractMap.SimpleImmutableEntry

internal class LayersTest {
    @Test
    fun `should have a debuggable presentation`() {
        Layers.new().toString() shouldBe "0: (EditableLayer) {}"
    }

    @Test
    fun `should be a virtual map`() {
        val fredKey = "fred"
        val layers = Layers.new(
            bobKey to bobRule,
            fredKey to object : Rule<String>(fredKey) {
                override fun invoke(values: List<String>) =
                    if (values.isEmpty()) "" else values.first()

                override fun description() =
                    "Test Rule which does not explode"
            }
        )
        layers.saveAndNew {
            this[bobKey] = 4.toEntry()
        }
        layers.saveAndNew {
            this[fredKey] = "Happy clam".toEntry()
        }
        val entries = layers.entries

        entries shouldBe setOf(
            SimpleImmutableEntry(bobKey, 8),
            SimpleImmutableEntry(fredKey, "Happy clam"),
        )
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
            this[bobKey] = 4.toEntry()
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
            this[bobKey] = 4.toEntry()
        }

        layers.current.edit {
            this[bobKey] = 3.toEntry()
        }

        layers.current shouldBe EditableLayer(
            mutableMapOf(bobKey to 3.toEntry())
        )
    }

    @Test
    fun `should read latest computed values`() {
        val layers = Layers.new {
            this[bobKey] = object : Rule<Int>(bobKey) {
                override fun invoke(values: List<Int>) =
                    values.last() * 1

                override fun description() = "Broken Bob Rule"
            }
            this[maryKey] = maryRule
        }
        layers.saveAndNew {
            this[bobKey] = 4.toEntry()
            this[maryKey] = "Something else".toEntry()
        }
        layers.saveAndNew {
            this[bobKey] = bobRule
        }

        layers[bobKey] as Int shouldBe 8
    }

    @Test
    fun `should complain on a missing rule`() {
        shouldThrow<IllegalStateException> {
            Layers.new()[bobKey]
        }
    }

    @Test
    fun `should lookup value based on rule`() {
        val layers = Layers.new {
            this[bobKey] = bobRule
        }
        layers.saveAndNew {
            this[bobKey] = 4.toEntry()
        }

        layers[bobKey] shouldBe 8
    }

    @Test
    fun `should ignore layers with no relevant entries`() {
        val layers = Layers.new(
            bobKey to bobRule,
            maryKey to maryRule
        )

        layers.saveAndNew {
            this[maryKey] = "Inconceivable".toEntry()
        }
        layers.saveAndNew {
            this[bobKey] = 4.toEntry()
        }

        layers[bobKey] shouldBe 8
    }

    @Test
    fun `should ignore ordering of rules vs values`() {
        val layers = Layers.new(
            bobKey to bobRule
        )

        layers.saveAndNew {
            this[bobKey] = 1.toEntry()
        }

        layers.saveAndNew {
            this[bobKey] = sumOfRule(bobKey, 0)
        }

        layers.saveAndNew {
            this[bobKey] = 2.toEntry()
        }

        layers[bobKey] shouldBe 3
    }
}

private const val bobKey = "bob"
private const val maryKey = "mary"

private val bobRule = object : Rule<Int>(bobKey) {
    override fun invoke(values: List<Int>) = 2 * values.last()
    override fun description() = "Test rule for doubling last"
}

private val maryRule = object : Rule<String>(maryKey) {
    override fun invoke(values: List<String>) =
        throw NullPointerException()

    override fun description() = "Impossible Rule"
}
