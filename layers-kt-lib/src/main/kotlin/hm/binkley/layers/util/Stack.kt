package hm.binkley.layers.util

import lombok.Generated

interface Stack<out T> : List<T> {
    fun peek() = last()
}

/**
 * Returns a new [MutableStack] filled with all elements of this collection.
 *
 * This is a _shallow_ copy.
 */
@Generated // TODO: How to test?  Kotlin complains check is useless
@Suppress("UNUSED")
fun <T> Stack<T>.toMutableStack(): MutableStack<T> =
    ArrayMutableStack((this as List<T>).toMutableList())

fun <T> emptyStack(): Stack<T> = ArrayStack(emptyList())
fun <T> stackOf(vararg elements: T): Stack<T> =
    ArrayStack(listOf(*elements))

/**
 * Returns a new [Stack] filled with all elements of this collection.
 *
 * This is a _shallow_ copy.
 */
@Generated // TODO: How to test?  Kotlin complains check is useless
@Suppress("UNUSED")
fun <T> List<T>.toStack() = ArrayStack(this)
