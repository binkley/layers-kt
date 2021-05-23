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

interface XMutableStack<T> : XStack<T>, MutableList<T> {
    fun push(element: T) = add(element)
    fun pop() = removeLast()
}

@Generated // TODO: How to test?  Kotlin complains check is useless
@Suppress("UNUSED")
fun <T> XMutableStack<T>.toStack(): XStack<T> = this

open class XArrayStack<T>(
    private val elements: List<T> = listOf(),
) : XStack<T>, List<T> by elements

fun <T> mutableStackOf(): XMutableStack<T> =
    XArrayMutableStack(mutableListOf())

fun <T> mutableStackOf(vararg elements: T): XMutableStack<T> =
    XArrayMutableStack(mutableListOf(*elements))

open class XArrayMutableStack<T>(
    elements: MutableList<T> = mutableListOf(),
) : XMutableStack<T>, MutableList<T> by elements
