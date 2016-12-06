package hm.binkley.layers.dnd.items

import hm.binkley.layers.Layer
import hm.binkley.layers.Layers.LayerSurface
import hm.binkley.layers.dnd.items.Attunement.Companion.attune
import hm.binkley.layers.rules.Rule
import hm.binkley.layers.sets.LayerSet

abstract class AttunementItem<L : AttunementItem<L>>(layers: LayerSurface,
        name: String,
        weight: Weight)
    : MagicItem<L>(layers, name, weight) {
    fun isAttuned(): Boolean {
        return layers.get<LayerSet<*>>(Attunement::class.java).contains(this)
    }

    fun <R> put(key: Any, value: Rule<R>): L
            = super.put(key, AttunementItemRule(self(), value))

    fun <K : Layer<K>> attuneSaveAndNext(next: (layers: LayerSurface) -> K): K {
        return saveAndNext(attune(self())).
                saveAndNext(next)
    }
}
