package hm.binkley.layers.rpg

import hm.binkley.layers.EditMap
import hm.binkley.layers.Layers
import hm.binkley.layers.Value
import hm.binkley.layers.toValue
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

typealias RpgEditMap = EditMap<String, Any>
typealias RpgLayers = Layers<String, Any, *>

var RpgEditMap.MIGHT: Int by EditDelegate()
var RpgEditMap.DEFTNESS: Int by EditDelegate()
var RpgEditMap.GRIT: Int by EditDelegate()
var RpgEditMap.WIT: Int by EditDelegate()
var RpgEditMap.FORESIGHT: Int by EditDelegate()
var RpgEditMap.PRESENCE: Int by EditDelegate()

private class EditDelegate<T : Any> : ReadWriteProperty<RpgEditMap, T> {
    @Suppress("UNCHECKED_CAST")
    override operator fun getValue(
        thisRef: RpgEditMap,
        property: KProperty<*>
    ) = (thisRef[property.name]!! as Value<T>).value

    override operator fun setValue(
        thisRef: RpgEditMap,
        property: KProperty<*>,
        value: T
    ) {
        thisRef[property.name] = value.toValue()
    }
}
