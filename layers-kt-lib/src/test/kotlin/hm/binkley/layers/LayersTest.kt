package hm.binkley.layers

import hm.binkley.layers.rules.LatestOfRule.Companion.latestOfRule
import hm.binkley.layers.rules.SumOfRule.Companion.sumOfRule
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.util.AbstractMap.SimpleImmutableEntry

internal class LayersTest {
    @Test
    fun `should have a debuggable presentation`() {
        Layers.new().toString() shouldBe "0: [MutableLayer] <INIT>: {}"
    }

    @Test
    fun `should be a virtual map`() {
        val layers = Layers.new(
            bobKey to bobRule,
            fredKey to fredRule
        )
        layers.saveAndNew("BOB") {
            this[bobKey] = 4.toValue()
        }
        layers.saveAndNew("FRED") {
            this[fredKey] = "Happy clam".toValue()
        }
        val entries = layers.entries

        entries shouldBe setOf(
            SimpleImmutableEntry(bobKey, 8),
            SimpleImmutableEntry(fredKey, "Happy clam"),
        )
    }

    @Test
    fun `should start with a blank, mutable layer`() {
        val newLayers = Layers.new()

        newLayers.layers shouldBe listOf(Layer("<INIT>"))
        newLayers.current.shouldBeInstanceOf<MutableLayer>()
    }

    @Test
    fun `should start from a list of layers`() {
        val layers = listOf(
            MutableLayer("<INIT>").edit {
                this[bobKey] = bobRule
            },
        )
        val newLayers = Layers.new(layers)

        newLayers.layers shouldBe layers
    }

    @Test
    fun `should not start from an empty list of layers`() {
        shouldThrow<IllegalStateException> {
            Layers.new(listOf())
        }
    }

    @Test
    fun `should start from a block`() {
        val newLayers = Layers.new {
            this[bobKey] = bobRule
        }

        newLayers.layers shouldBe listOf(
            MutableLayer("<INIT>", mutableMapOf(bobKey to bobRule))
        )
    }

    @Test
    fun `should save current layer and create a new layer`() {
        val ruleLayer = MutableLayer("BOB RULE").edit {
            this[bobKey] = bobRule
        }
        val layers = Layers.new(listOf(ruleLayer))
        val firstValueLayer = layers.saveAndNew("BOB") {
            this[bobKey] = 4.toValue()
        }

        layers.layers shouldBe listOf(firstValueLayer, ruleLayer)
        layers.current shouldBe firstValueLayer
        firstValueLayer shouldNotBeSameInstanceAs ruleLayer
    }

    @Test
    fun `should edit while creating new layer`() {
        val ruleLayer = MutableLayer("BOB RULE").edit {
            this[bobKey] = bobRule
        }
        val layers = Layers.new(listOf(ruleLayer))
        layers.saveAndNew("BOB") {
            this[bobKey] = 4.toValue()
        }
        layers.current.edit {
            this[bobKey] = 3.toValue()
        }
        layers.current shouldBe MutableLayer(
            "BOB",
            mutableMapOf(bobKey to 3.toValue())
        )
    }

    @Test
    fun `should read latest computed values`() {
        val layers = Layers.new {
            this[bobKey] = ruleFor<Int>(bobKey) { values, _ ->
                values.last() * 1
            }
            this[maryKey] = maryRule
        }
        layers.saveAndNew("BOB AND MARY") {
            this[bobKey] = 4.toValue()
            this[maryKey] = "Something else".toValue()
        }
        layers.saveAndNew("BOB") {
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
    fun `should complain on a value before a rule`() {
        shouldThrow<IllegalStateException> {
            Layers.new {
                this[bobKey] = 4.toValue()
            }
        }
    }

    @Disabled("TODO: IMPLEMENT")
    @Test
    fun `should complain on a value before a rule in a later layer`() {
        shouldThrow<IllegalStateException> {
            Layers.new().saveAndNew("NEXT") {
                this[bobKey] = 4.toValue()
            }
        }
    }

    @Test
    fun `should lookup value based on rule`() {
        val layers = Layers.new {
            this[bobKey] = bobRule
        }
        layers.saveAndNew("BOB") {
            this[bobKey] = 4.toValue()
        }

        layers[bobKey] shouldBe 8
    }

    @Test
    fun `should ignore empty layers`() {
        val layers = Layers.new(
            bobKey to bobRule,
            maryKey to maryRule
        )

        layers.saveAndNew("BOB") {
            this[bobKey] = 4.toValue()
        }
        layers.saveAndNew("<THIS LAYER INTENTIONALLY LEFT EMPTY>")

        layers[bobKey] shouldBe 8
    }

    @Test
    fun `should ignore layers with no relevant entries`() {
        val layers = Layers.new(
            bobKey to bobRule,
            maryKey to maryRule
        )

        layers.saveAndNew("MARY") {
            this[maryKey] = "Inconceivable".toValue()
        }
        layers.saveAndNew("BOB") {
            this[bobKey] = 4.toValue()
        }

        layers[bobKey] shouldBe 8
    }

    @Test
    fun `should use most recent rule for a key`() {
        val layers = Layers.new(
            bobKey to bobRule
        )

        layers.saveAndNew("BOB") {
            this[bobKey] = 1.toValue()
        }
        layers.saveAndNew("NEW BOB RULE") {
            this[bobKey] = sumOfRule(bobKey, 0)
        }
        layers.saveAndNew("BOB") {
            this[bobKey] = 2.toValue()
        }

        layers[bobKey] shouldBe 3
    }
}

private const val bobKey = "bob"
private const val maryKey = "mary"
private const val fredKey = "fred"

private val bobRule = object : Rule<Int>(bobKey) {
    override fun invoke(values: List<Int>, allValues: Map<String, Any>) =
        2 * values.last()

    override fun description() = "Test rule for doubling last"
}

private val maryRule = object : Rule<String>(maryKey) {
    override fun invoke(values: List<String>, allValues: Map<String, Any>) =
        throw NullPointerException()

    override fun description() = "Impossible Rule"
}

private val fredRule = latestOfRule(fredKey, "MISSING")
