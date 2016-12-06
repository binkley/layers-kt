package hm.binkley.layers.dnd.items

import hm.binkley.layers.Layers.LayerSurface

abstract class MagicItem<L : MagicItem<L>>(layers: LayerSurface, name: String, weight: Weight) : Item<L>(layers, name, weight) {

}
