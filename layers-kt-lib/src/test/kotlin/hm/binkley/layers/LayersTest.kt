package hm.binkley.layers

import hm.binkley.layers.rules.LatestOfRule.Companion.latestOfRule
import hm.binkley.layers.rules.SumOfRule.Companion.sumOfRule
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.junit.jupiter.api.Test
import java.util.AbstractMap.SimpleImmutableEntry

internal class LayersTest {
    @Test
    fun `should have a debuggable presentation`() {
        Layers.new().toString() shouldBe "0: [DefaultMutableLayer] <INIT>: {}"
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

        newLayers.layers shouldBe listOf(DefaultLayer("<INIT>"))
        newLayers.current.shouldBeInstanceOf<MutableLayer<*>>()
    }

    @Test
    fun `should start from a list of layers`() {
        val layers = listOf(
            DefaultMutableLayer("<INIT>").edit {
                this[bobKey] = bobRule
            },
        )
        val newLayers = Layers.new(layers)

        newLayers.layers shouldBe layers
    }

    @Test
    fun `should start from an empty list of layers`() {
        Layers.new()
        Layers.new(listOf())
    }

    @Test
    fun `should start from a block`() {
        val newLayers = Layers.new {
            this[bobKey] = bobRule
        }

        newLayers.layers shouldBe listOf(
            DefaultMutableLayer("<INIT>", mutableMapOf(bobKey to bobRule))
        )
    }

    @Test
    fun `should complain if not started with rules`() {
        shouldThrow<IllegalArgumentException> {
            Layers.new {
                this[bobKey] = 1.toValue()
            }
        }
    }

    @Test
    fun `should edit the current layer with varargs`() {
        val layers = Layers.new().edit(bobKey to bobRule)

        layers shouldBe Layers.new(bobKey to bobRule)
    }

    @Test
    fun `should complain when editing varargs without rules`() {
        shouldThrow<IllegalArgumentException> {
            Layers.new().edit(bobKey to 1.toValue())
        }
    }

    @Test
    fun `should edit the current layer with a map`() {
        val layers = Layers.new().edit(mapOf(bobKey to bobRule))

        layers shouldBe Layers.new(bobKey to bobRule)
    }

    @Test
    fun `should complain when editing the a map without rules`() {
        shouldThrow<IllegalArgumentException> {
            Layers.new().edit(mapOf(bobKey to 1.toValue()))
        }
    }

    @Test
    fun `should edit the current layer with a block`() {
        val layers = Layers.new().edit {
            this[bobKey] = bobRule
        }

        layers shouldBe Layers.new(bobKey to bobRule)
    }

    @Test
    fun `should complain when editing a key without a rule`() {
        shouldThrow<IllegalArgumentException> {
            Layers.new().edit {
                this[bobKey] = 1.toValue()
            }
        }
    }

    @Test
    fun `should commit the current layer and edit a new layer`() {
        val ruleLayer = DefaultMutableLayer("BOB RULE").edit {
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
    fun `should complain when adding a new invalid edit`() {
        val layers = Layers.new()

        shouldThrow<IllegalArgumentException> {
            layers.commitAndNext("NOT-BOB") {
                this["NOT-BOB"] = 4.toValue()
            }
        }
    }

    @Test
    fun `should edit while adding new layer`() {
        val ruleLayer = DefaultMutableLayer("BOB RULE").edit {
            this[bobKey] = bobRule
        }
        val layers = Layers.new(listOf(ruleLayer))
        layers.commitAndNext("BOB") {
            this[bobKey] = 4.toValue()
        }
        layers.current.edit {
            this[bobKey] = 3.toValue()
        }
        layers.current shouldBe DefaultMutableLayer(
            "BOB",
            mutableMapOf(bobKey to 3.toValue())
        )
    }

    @Test
    fun `should complain when adding a new invalid layer`() {
        val layers = Layers.new()
        val invalidLayer = DefaultMutableLayer("NOT-BOB").edit {
            this["NOT-BOB"] = 4.toValue()
        }

        shouldThrow<IllegalArgumentException> {
            layers.commitAndNext(invalidLayer)
        }
    }

    @Test
    fun `should read latest computed values`() {
        val layers = Layers.new {
            this[bobKey] = ruleFor<Int>(bobKey) { _, values, _ ->
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
        shouldThrow<IllegalArgumentException> {
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

    @Test
    fun `should complain on a value before a rule in a later layer`() {
        shouldThrow<IllegalArgumentException> {
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
    fun `should not alter original in what-if scenarios with varargs`() {
        val layers = Layers.new(
            bobKey to bobRule
        )

        layers.commitAndNext("BOB") {
            this[bobKey] = 1.toValue()
        }
        val whatIf = layers.whatIf(bobKey to 2.toValue())

        layers[bobKey] shouldBe 2
        whatIf[bobKey] shouldBe 4
    }

    @Test
    fun `should not alter original in what-if scenarios with blocks`() {
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

private val bobRule = object : NamedRule<Int>("<Anonymous>", bobKey) {
    override fun invoke(key: String, values: List<Int>, allValues: ValueMap) =
        2 * (if (values.isEmpty()) 0 else values.first())
}

private val maryRule =
    object : NamedRule<String>("Impossible Rule", maryKey) {
        override fun invoke(
            key: String,
            values: List<String>,
            allValues: ValueMap,
        ) = throw NullPointerException()
    }

private val fredRule = latestOfRule(fredKey, "MISSING")
