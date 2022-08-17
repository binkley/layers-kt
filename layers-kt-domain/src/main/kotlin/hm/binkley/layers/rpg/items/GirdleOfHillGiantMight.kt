package hm.binkley.layers.rpg.items

import hm.binkley.layers.rpg.Stat.MIGHT

/** Sets your [MIGHT] to a minimum of 19. */
class GirdleOfHillGiantMight private constructor(
    worn: Boolean,
    previous: GirdleOfHillGiantMight?,
) : WearableItem<GirdleOfHillGiantMight>(
    "Girdle of Might of the Hill Giant",
    1.weight,
    worn,
    previous,
) {
    init {
        edit {
            this[MIGHT.name] = floorRuleIfWorn(19)
        }
    }

    companion object {
        /** Creates a new, unworn [GirdleOfHillGiantMight]. */
        fun girdleOfHillGiantMight(): GirdleOfHillGiantMight =
            GirdleOfHillGiantMight(false, null)
    }

    override fun change(
        previous: GirdleOfHillGiantMight,
        worn: Boolean,
    ): GirdleOfHillGiantMight = GirdleOfHillGiantMight(worn, previous)
}
