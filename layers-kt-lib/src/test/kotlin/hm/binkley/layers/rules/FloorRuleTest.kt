package hm.binkley.layers.rules

import hm.binkley.layers.defaultValue
import hm.binkley.layers.rules.FloorRule.Companion.floorRule
import hm.binkley.layers.rules.FloorRule.Companion.initFloorRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.Collections.emptyMap

internal class FloorRuleTest {
    @Test
    fun `should have a debuggable presentation`() =
        "${FloorRule("BOB", 19)}" shouldBe
            "<Rule>: Floor[Int](min=19)"

    @Test
    fun `should calculate rule with floor defaulted`() =
        floorRule("BOB", 19)(
            "BOB",
            listOf(),
            emptyMap(),
        ) shouldBe 19

    @Test
    fun `should calculate rule with floor used`() =
        floorRule("BOB", 19)(
            "BOB",
            listOf(1, 2, 3),
            emptyMap(),
        ) shouldBe 19

    @Test
    fun `should calculate rule with floor not used`() =
        floorRule("BOB", 19)(
            "BOB",
            listOf(11, 21, 31),
            emptyMap(),
        ) shouldBe 31

    @Test
    fun `should be usable for initializing layers`() {
        val (key, rule) = initFloorRule("BOB", 10)

        key shouldBe "BOB"
        rule.defaultValue() shouldBe 10
    }
}
