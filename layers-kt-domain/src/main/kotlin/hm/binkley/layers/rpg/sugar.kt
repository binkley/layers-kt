package hm.binkley.layers.rpg

import hm.binkley.layers.EditMap
import hm.binkley.layers.Layers
import hm.binkley.layers.Value
import hm.binkley.layers.toValue
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

typealias RpgEditMap = EditMap<String, Any>
typealias RpgLayers = Layers<String, Any, *>

var RpgEditMap.MIGHT: Int by StatDelegate()
var RpgEditMap.DEFTNESS: Int by StatDelegate()
var RpgEditMap.GRIT: Int by StatDelegate()
var RpgEditMap.WIT: Int by StatDelegate()
var RpgEditMap.FORESIGHT: Int by StatDelegate()
var RpgEditMap.PRESENCE: Int by StatDelegate()

private class StatDelegate : ReadWriteProperty<RpgEditMap, Int> {
    @Suppress("UNCHECKED_CAST")
    override operator fun getValue(
        thisRef: RpgEditMap,
        property: KProperty<*>
    ) = (thisRef[property.name]!! as Value<Int>).value

    override operator fun setValue(
        thisRef: RpgEditMap,
        property: KProperty<*>,
        value: Int
    ) {
        thisRef[property.name] = value.toValue()
    }
}
