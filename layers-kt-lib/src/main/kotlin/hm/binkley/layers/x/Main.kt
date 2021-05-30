package hm.binkley.layers.x

import hm.binkley.layers.x.DefaultMutableLayers.Companion.defaultMutableLayers
import lombok.Generated

/** @todo Test `main()`, a kind of user-journey test at unit level */
@Generated // Lie to JaCoCo
fun main() {
    println("== USING DEFAULT TYPES")

    val c = defaultMutableLayers<String, Number>("C")
    c.edit {
        this["ALICE"] = latestRule(0)
        this["BOB"] = rule<Double>("Sum[Int]") { _, values, _ ->
            values.sum()
        }
    }
    c.commitAndNext("First")
    c.edit {
        this["ALICE"] = Value(3)
    }
    val a = c.commitAndNext("Second")
    a.edit {
        this["BOB"] = Value(4.0)
    }

    println(c)

    println()
    println("== USING CUSTOM TYPES")

    val d =
        DefaultMutableLayers<String, Number, DefaultMutableLayer<String, Number, *>>(
            "D"
        ) { DefaultMutableLayer(it) }
    d.edit {
        this["CAROL"] = constantRule(2)
    }

    class Bob : DefaultMutableLayer<String, Number, Bob>("BOB") {
        fun foo() = println("I AM FOCUTUS OF BOB")
    }

    val b = d.commitAndNext { Bob() }
    b.foo()

    println(d)

    println()
    println("== USING LATEST RULE FOR A KEY")

    b.edit {
        this["CAROL"] = 17.toValue()
    }
    d.commitAndNext("Second")
    d.edit {
        this["CAROL"] = 19.toValue()
    }
    d.commitAndNext("Third")
    d.edit {
        this["CAROL"] = rule<Int>("Product[Int]") { _, values, _ ->
            values.fold(1) { a, b -> a * b }
        }
    }

    println(d)

    println()
    println("== WHAT-IF SCENARIO")

    val e = d.whatIf {
        this["CAROL"] = (-1).toValue()
    }

    println("- WHAT-IF")
    println(e)
    println("- ORIGINAL")
    println(d)

    println()
    println("== USING A RULE DEPENDANT ON THE VALUE OF ANOTHER KEY")

    d.edit {
        this["DAVE"] = rule<Int>("Halve[Int](other=CAROL)") { _, _, _ ->
            getOtherValueAs<Int>("CAROL") / 2
        }
    }

    println(d)

    println()
    println("== USING A RULE NEEDING A FULL VIEW OF LAYERS")

    d.edit {
        this["DAVE"] = rule<Int>("Count of non-DAVE keys") { _, _, view ->
            view.keys.size
        }
        this["EVE"] = constantRule(31)
    }

    println(d)
}
