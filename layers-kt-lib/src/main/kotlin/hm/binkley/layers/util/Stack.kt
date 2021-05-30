package hm.binkley.layers.util

import lombok.Generated

interface XStack<out T> : List<T> {
    fun peek() = last()
}

@Generated // TODO: How to test?  Kotlin complains check is useless
@Suppress("UNUSED")
fun <T> XStack<T>.toMutableStack(): XMutableStack<T> =
    XArrayMutableStack((this as List<T>).toMutableList())

fun <T> stackOf(): XStack<T> = XArrayStack(listOf())
fun <T> stackOf(vararg elements: T): XStack<T> =
    XArrayStack(listOf(*elements))

@Generated // TODO: How to test?  Kotlin complains check is useless
fun <T> List<T>.toStack() = XArrayStack(this)
