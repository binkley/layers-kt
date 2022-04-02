package hm.binkley.layers

import hm.binkley.layers.rules.ConstantRule
import hm.binkley.layers.rules.LatestRule
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface EditMap<K : Any, V : Any> : MutableMap<K, ValueOrRule<V>> {
    fun <T : V> rule(
        name: String,
        block: (K, Sequence<T>, Layers<K, V, *>) -> T,
    ): Rule<K, V, T> = object : Rule<K, V, T>(name) {
        override fun invoke(
            key: K,
            values: Sequence<T>,
            layers: Layers<K, V, *>,
        ): T = block(key, values, layers)
    }

    fun <T : V> constantRule(value: T) = ConstantRule<K, V, T>(value)
    fun <T : V> latestRule(default: T) = LatestRule<K, V, T>(default)
}

fun interface EditMapDelegate<
        K : Any,
        V : Any,
        T : V,
        > : ReadWriteProperty<EditMap<K, V>, T?> {
    /** Finds or creates a suitable edit map key. */
    fun KProperty<*>.toKey(): K

    @Suppress("UNCHECKED_CAST")
    override operator fun getValue(
        thisRef: EditMap<K, V>,
        property: KProperty<*>
    ): T {
        val key = property.toKey()
        return when (val it = thisRef[key]) {
            null -> error("No such key: $key")
            is Rule<*, *, *> ->
                throw ClassCastException("Not a value for key: $key")
            else -> (it as Value<T>).value
        }
    }

    override operator fun setValue(
        thisRef: EditMap<K, V>,
        property: KProperty<*>,
        value: T?
    ) {
        if (null == value) thisRef.remove(property.toKey())
        else thisRef[property.toKey()] = value.toValue()
    }
}
