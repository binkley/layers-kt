package hm.binkley.layers.rpg

import hm.binkley.layers.EditMap
import hm.binkley.layers.Layers
import hm.binkley.layers.Value
import hm.binkley.layers.toValue
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

typealias RpgEditMap = EditMap<String, Any>
typealias RpgLayers = Layers<String, Any, *>

var EditMap<String, Any>.MIGHT: Int by StatDelegate()
var EditMap<String, Any>.DEFTNESS: Int by StatDelegate()
var EditMap<String, Any>.GRIT: Int by StatDelegate()
var EditMap<String, Any>.WIT: Int by StatDelegate()
var EditMap<String, Any>.FORESIGHT: Int by StatDelegate()
var EditMap<String, Any>.PRESENCE: Int by StatDelegate()

private class StatDelegate : ReadWriteProperty<EditMap<String, Any>, Int> {
    override operator fun getValue(
        thisRef: EditMap<String, Any>,
        property: KProperty<*>
    ): Int {
        @Suppress("UNCHECKED_CAST")
        return (thisRef[property.name]!! as Value<Int>).value
    }

    override operator fun setValue(
        thisRef: EditMap<String, Any>,
        property: KProperty<*>,
        value: Int
    ) {
        thisRef[property.name] = value.toValue()
    }
}
