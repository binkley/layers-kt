package hm.binkley.labs

interface LayerView {
    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(key: Any): T
}
