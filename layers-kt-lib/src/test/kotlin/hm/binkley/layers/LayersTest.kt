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
        Layers.new().toString() shouldBe "0: [MutablePlainLayer] <INIT>: {}"
    }

    @Test
    fun `should be a virtual map`() {
        val layers = Layers.new(
            bobKey to bobRule,
            fredKey to fredRule
        )
        layers.commitAndNext("BOB") {
            this[bobKey] = 4.toValue()
        }
        layers.commitAndNext("FRED") {
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

        newLayers.layers shouldBe listOf(PlainLayer("<INIT>"))
        newLayers.current.shouldBeInstanceOf<MutableLayer>()
    }

    @Test
    fun `should start from a list of layers`() {
        val layers = listOf(
            MutablePlainLayer("<INIT>").edit {
                this[bobKey] = bobRule
            },
        )
        val newLayers = Layers.new(layers)

        newLayers.layers shouldBe layers
    }

    @Test
    fun `should not start from an empty list of layers`() {
        shouldThrow<java.lang.IllegalArgumentException> {
            Layers.new(listOf())
        }
    }

    @Test
    fun `should start from a block`() {
        val newLayers = Layers.new {
            this[bobKey] = bobRule
        }

        newLayers.layers shouldBe listOf(
            MutablePlainLayer("<INIT>", mutableMapOf(bobKey to bobRule))
        )
    }

    @Test
    fun `should save current layer and create a new layer`() {
        val ruleLayer = MutablePlainLayer("BOB RULE").edit {
            this[bobKey] = bobRule
        }
        val layers = Layers.new(listOf(ruleLayer))
        val firstValueLayer = layers.commitAndNext("BOB") {
            this[bobKey] = 4.toValue()
        }

        layers.layers shouldBe listOf(firstValueLayer, ruleLayer)
        layers.current shouldBe firstValueLayer
        firstValueLayer shouldNotBeSameInstanceAs ruleLayer
    }

    @Test
    fun `should edit while creating new layer`() {
        val ruleLayer = MutablePlainLayer("BOB RULE").edit {
            this[bobKey] = bobRule
        }
        val layers = Layers.new(listOf(ruleLayer))
        layers.commitAndNext("BOB") {
            this[bobKey] = 4.toValue()
        }
        layers.current.edit {
            this[bobKey] = 3.toValue()
        }
        layers.current shouldBe MutablePlainLayer(
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
        layers.commitAndNext("BOB AND MARY") {
            this[bobKey] = 4.toValue()
            this[maryKey] = "Something else".toValue()
        }
        layers.commitAndNext("BOB") {
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
        shouldThrow<IllegalArgumentException> {
            Layers.new {
                this[bobKey] = 4.toValue()
            }
        }
    }

    @Disabled("TODO: IMPLEMENT")
    @Test
    fun `should complain on a value before a rule in a later layer`() {
        shouldThrow<IllegalStateException> {
            Layers.new().commitAndNext("NEXT") {
                this[bobKey] = 4.toValue()
            }
        }
    }

    @Test
    fun `should lookup value based on rule`() {
        val layers = Layers.new {
            this[bobKey] = bobRule
        }
        layers.commitAndNext("BOB") {
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

        layers.commitAndNext("BOB") {
            this[bobKey] = 4.toValue()
        }
        layers.commitAndNext("<THIS LAYER INTENTIONALLY LEFT EMPTY>")

        layers[bobKey] shouldBe 8
    }

    @Test
    fun `should ignore layers with no relevant entries`() {
        val layers = Layers.new(
            bobKey to bobRule,
            maryKey to maryRule
        )

        layers.commitAndNext("MARY") {
            this[maryKey] = "Inconceivable".toValue()
        }
        layers.commitAndNext("BOB") {
            this[bobKey] = 4.toValue()
        }

        layers[bobKey] shouldBe 8
    }

    @Test
    fun `should use most recent rule for a key`() {
        val layers = Layers.new(
            bobKey to bobRule
        )

        layers.commitAndNext("BOB") {
            this[bobKey] = 1.toValue()
        }
        layers.commitAndNext("NEW BOB RULE") {
            this[bobKey] = sumOfRule(bobKey, 0)
        }
        layers.commitAndNext("BOB") {
            this[bobKey] = 2.toValue()
        }

        layers[bobKey] shouldBe 3
    }

    @Test
    fun `should not alter original in what-if scenarios`() {
        val layers = Layers.new(
            bobKey to bobRule
        )

        layers.commitAndNext("BOB") {
            this[bobKey] = 1.toValue()
        }
        val whatIf = layers.whatIf {
            this[bobKey] = 2.toValue()
        }

        layers[bobKey] shouldBe 2
        whatIf[bobKey] shouldBe 4
    }
}

private const val bobKey = "bob"
private const val maryKey = "mary"
private const val fredKey = "fred"

private val bobRule = object : Rule<Int>(bobKey) {
    override fun invoke(values: List<Int>, allValues: Map<String, Any>) =
        2 * values.first()

    override fun description() = "Test rule to double the most recent value"
}

private val maryRule = object : Rule<String>(maryKey) {
    override fun invoke(values: List<String>, allValues: Map<String, Any>) =
        throw NullPointerException()

    override fun description() = "Impossible Rule"
}

private val fredRule = latestOfRule(fredKey, "MISSING")
