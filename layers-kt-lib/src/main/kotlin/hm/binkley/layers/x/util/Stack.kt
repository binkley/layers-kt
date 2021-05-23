package hm.binkley.layers.x.util

import lombok.Generated

interface XStack<out T> : List<T>, RandomAccess {
    fun peek() = last()
}

@Generated // TODO: How to test?  Kotlin complains check is useless
@Suppress("UNUSED")
fun <T> XStack<T>.toMutableStack(): XMutableStack<T> =
    XArrayMutableStack((this as List<T>).toMutableList())

fun <T> stackOf(): XStack<T> = XArrayStack(listOf())
fun <T> stackOf(vararg elements: T): XStack<T> =
    XArrayStack(listOf(*elements))
