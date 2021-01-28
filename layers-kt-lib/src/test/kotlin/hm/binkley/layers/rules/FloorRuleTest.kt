package hm.binkley.layers.rules

import hm.binkley.layers.rules.FloorRule.Companion.floorRule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.Collections.emptyMap

internal class FloorRuleTest {
    @Test
    fun `should have a debuggable presentation`() =
        "${FloorRule("bob", 19)}" shouldBe
            "<Rule>[bob]: Floor(min=19)"

    @Test
    fun `should calculate rule with floor defaulted`() =
        floorRule("bob", 19)(listOf(), emptyMap()) shouldBe 19

    @Test
    fun `should calculate rule with floor used`() =
        floorRule("bob", 19)(listOf(1, 2, 3), emptyMap()) shouldBe 19

    @Test
    fun `should calculate rule with floor not used`() =
        floorRule("bob", 19)(listOf(11, 21, 31), emptyMap()) shouldBe 31
}
