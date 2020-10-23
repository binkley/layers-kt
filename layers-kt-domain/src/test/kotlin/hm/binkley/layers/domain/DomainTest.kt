package hm.binkley.layers.domain

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class DomainTest {
    @Test
    fun `should test`() {
        Domain(true).fakeForMutation shouldBe true
    }
}
