package hm.binkley.labs

import java.util.function.Consumer

class Layers {
    companion object {
        fun <L : Layer<L>> firstLayer(holder: Consumer<Layers>,
                ctor: (Layers) -> Layer<L>): Layer<L> {
            val layers = Layers()
            holder.accept(layers)
            return ctor(layers)
        }
    }
}
