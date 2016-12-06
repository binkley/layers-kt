package hm.binkley.layers.dnd.items

import hm.binkley.layers.Layers.RuleSurface
import hm.binkley.layers.rules.Rule

class AttunementItemRule<L : AttunementItem<L>, out R>(
        private val layer: L,
        private val rule: Rule<R>)
    : Rule<R>("Attunable item") {
    override fun invoke(layers: RuleSurface): R = if (layer.isAttuned())
        rule.invoke(layers)
    else
        layers.without()
}
