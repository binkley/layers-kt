package hm.binkley.layers.util

import lombok.Generated

interface MutableStack<T> : Stack<T>, MutableList<T> {
    fun push(element: T) = add(element)
    fun pop() = removeLast()
}

@Generated // TODO: How to test?  Kotlin complains check is useless
@Suppress("UNUSED")
fun <T> MutableStack<T>.toStack(): Stack<T> = this

open class ArrayStack<T>(
    private val elements: List<T> = listOf(),
) : Stack<T>, List<T> by elements.toList()

fun <T> mutableStackOf(): MutableStack<T> =
    ArrayMutableStack(mutableListOf())

fun <T> mutableStackOf(vararg elements: T): MutableStack<T> =
    ArrayMutableStack(mutableListOf(*elements))

open class ArrayMutableStack<T>(
    private val elements: MutableList<T> = mutableListOf(),
) : MutableStack<T>, MutableList<T> by elements.toMutableList()

@Generated // TODO: How to test?  Kotlin complains check is useless
@Suppress("UNUSED")
fun <T> List<T>.toMutableStack() = ArrayMutableStack(toMutableList())
