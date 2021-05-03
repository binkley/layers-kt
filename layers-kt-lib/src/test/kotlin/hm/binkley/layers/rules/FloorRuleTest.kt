package hm.binkley.layers.rules

import hm.binkley.layers.rules.FloorRule.Companion.floorRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.Collections.emptyMap

internal class FloorRuleTest {
    @Test
    fun `should have a debuggable presentation`() =
        "${FloorRule(19)}" shouldBe
            "<Rule>: Floor[Int](min=19)"

    @Test
    fun `should calculate rule with floor defaulted`() =
        floorRule(19)(
            "BOB",
            listOf(),
            emptyMap(),
        ) shouldBe 19

    @Test
    fun `should calculate rule with floor used`() =
        floorRule(19)(
            "BOB",
            listOf(1, 2, 3),
            emptyMap(),
        ) shouldBe 19

    @Test
    fun `should calculate rule with floor not used`() =
        floorRule(19)(
            "BOB",
            listOf(11, 21, 31),
            emptyMap(),
        ) shouldBe 31
}
