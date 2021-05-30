package hm.binkley.layers.util

import lombok.Generated

interface XMutableStack<T> : XStack<T>, MutableList<T> {
    fun push(element: T) = add(element)
    fun pop() = removeLast()
}

@Generated // TODO: How to test?  Kotlin complains check is useless
@Suppress("UNUSED")
fun <T> XMutableStack<T>.toStack(): XStack<T> = this

open class XArrayStack<T>(
    private val elements: List<T> = listOf(),
) : XStack<T>, List<T> by elements.toList()

fun <T> mutableStackOf(): XMutableStack<T> =
    XArrayMutableStack(mutableListOf())

fun <T> mutableStackOf(vararg elements: T): XMutableStack<T> =
    XArrayMutableStack(mutableListOf(*elements))

open class XArrayMutableStack<T>(
    private val elements: MutableList<T> = mutableListOf(),
) : XMutableStack<T>, MutableList<T> by elements.toMutableList()

@Generated // TODO: How to test?  Kotlin complains check is useless
fun <T> List<T>.toMutableStack() = XArrayMutableStack(toMutableList())
