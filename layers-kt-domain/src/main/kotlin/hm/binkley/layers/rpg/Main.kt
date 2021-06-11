package hm.binkley.layers.rpg

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import hm.binkley.layers.DefaultMutableLayer
import hm.binkley.layers.DefaultMutableLayers
import hm.binkley.layers.DefaultMutableLayers.Companion.defaultMutableLayers
import hm.binkley.layers.Value
import hm.binkley.layers.rpg.Character.Companion.character
import hm.binkley.layers.rpg.items.GirdleOfHillGiantMight.Companion.girdleOfHillGiantMight
import hm.binkley.layers.toValue
import lombok.Generated

/**
 * @todo Test `main()`, a kind of user-journey test at unit level
 * @todo SpotBugs confuses the local variable, `newGirdle`, for a field;
 *       perhaps Kotlin is saying the wrong things in the .class file?
 */
@SuppressFBWarnings("SE_BAD_FIELD")
@Generated // Lie to JaCoCo
fun main() {
    println("== USING DEFAULT TYPES")

    val c = defaultMutableLayers<String, Number>("C")
    c.edit @Generated {
        this["ALICE"] = latestRule(0)
        this["BOB"] = rule<Double>("Sum[Int]") @Generated { _, values, _ ->
            values.sum()
        }
    }
    c.commitAndNext("First")
    c.edit @Generated {
        this["ALICE"] = Value(3)
    }
    val a = c.commitAndNext("Second")
    a.edit @Generated {
        this["BOB"] = Value(4.0)
    }

    println(c)

    println()
    println("== USING CUSTOM TYPES")

    val d =
        DefaultMutableLayers<String, Number, DefaultMutableLayer<String, Number, *>>(
            "D"
        ) @Generated { DefaultMutableLayer(it) }
    d.edit @Generated {
        this["CAROL"] = constantRule(2)
    }

    @Generated
    class Bob : DefaultMutableLayer<String, Number, Bob>("BOB") {
        fun foo() = println("I AM FOCUTUS OF BOB")
    }

    val b = d.commitAndNext @Generated { Bob() }
    b.foo()

    println(d)

    println()
    println("== USING LATEST RULE FOR A KEY")

    b.edit @Generated {
        this["CAROL"] = 17.toValue()
    }
    d.commitAndNext("Second")
    d.edit @Generated {
        this["CAROL"] = 19.toValue()
    }
    d.commitAndNext("Third")
    d.edit @Generated {
        this["CAROL"] = rule<Int>("Product[Int]") @Generated { _, values, _ ->
            values.fold(1) { a, b -> a * b }
        }
    }

    println(d)

    println()
    println("== WHAT-IF SCENARIO")

    val e = d.whatIfWith @Generated {
        this["CAROL"] = (-1).toValue()
    }

    println("- WHAT-IF")
    println(e)
    println("- ORIGINAL")
    println(d)

    println()
    println("== USING A RULE DEPENDANT ON THE VALUE OF ANOTHER KEY")

    d.edit @Generated {
        this["DAVE"] =
            rule<Int>("Halve[Int](other=CAROL)") @Generated { _, _, layers ->
                layers.getAs<Int>("CAROL") / 2
            }
    }

    println(d)

    println()
    println("== USING A RULE NEEDING A FULL VIEW OF LAYERS")

    d.edit @Generated {
        this["DAVE"] =
            rule<Int>("Count of non-DAVE keys") @Generated { _, _, layers ->
                layers.keys.size
            }
        this["EVE"] = constantRule(31)
    }

    println(d)

    println()
    println("==SAMPLE CHARACTER")
    val character = character("BOB")
    val newGirdle = character.commitAndNext @Generated {
        girdleOfHillGiantMight(it)
    }
    character.commitAndNext @Generated { newGirdle.don() }

    println(character)
}
