package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.items.Backpack.Companion.backpack

internal class BackpackTest :
    ContainerTestBase<Backpack<TestItem>>(backpack())
