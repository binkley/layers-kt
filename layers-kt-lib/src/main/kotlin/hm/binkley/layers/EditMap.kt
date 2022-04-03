package hm.binkley.layers

import hm.binkley.layers.rules.ConstantRule
import hm.binkley.layers.rules.LastOrDefaultRule
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/** A constrained mixin for editing a [MutableLayer] or [MutableLayers]. */
interface EditMap<K : Any, V : Any> : MutableMap<K, ValueOrRule<V>> {
    /** Convenience for creating a new [Rule] with a [block]. */
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

    /** Convenience for creating a new constant rule. */
    fun <T : V> constantRule(value: T): ConstantRule<K, V, T> =
        ConstantRule(value)

    /** Convenience for creating a new latest-with-default rule. */
    fun <T : V> lastOrDefaultRule(default: T): LastOrDefaultRule<K, V, T> =
        LastOrDefaultRule(default)
}

/**
 * Convenience for defining map keys as compilable syntax.
 * An example:
 * ```
 * var TestEditMap.BOB: Int by EditMapDelegate { name }
 * // Elsewhere ...
 * val editMap = TestEditMap()
 * editMap.BOB = 17
 * ```
 */
fun interface EditMapDelegate<
    K : Any,
    V : Any,
    T : V,
    > : ReadWriteProperty<EditMap<K, V>, T?> {
    /** The edit map key of this property. */
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

/** Convenience for converting the value to a [Value]. */
operator fun <
    K : Any,
    V : Any,
    T : V,
    > EditMap<K, V>.set(key: K, value: T) = put(key, value.toValue())
