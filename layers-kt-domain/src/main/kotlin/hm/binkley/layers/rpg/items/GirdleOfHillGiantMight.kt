package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.Stat.MIGHT

class GirdleOfHillGiantMight private constructor(
    worn: Boolean,
    previous: GirdleOfHillGiantMight?,
) : WearableItem<GirdleOfHillGiantMight>(
    "Girdle of Might of the Hill Giant",
    1.0f,
    worn,
    previous,
) {
    init {
        edit {
            this[MIGHT.name] = floorRuleIfWorn(19)
        }
    }

    companion object {
        fun girdleOfHillGiantMight() = GirdleOfHillGiantMight(false, null)
    }

    override fun activateNext(
        worn: Boolean,
        previous: GirdleOfHillGiantMight,
    ) = GirdleOfHillGiantMight(worn, previous)
}
