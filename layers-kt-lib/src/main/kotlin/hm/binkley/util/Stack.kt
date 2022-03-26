package hm.binkley.util

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings

interface Stack<out T> : List<T> {
    fun peek() = last()
}

/**
 * Returns a new [MutableStack] filled with all elements of this collection.
 *
 * This is a _shallow_ copy.
 */
fun <T> Stack<T>.toMutableStack(): MutableStack<T> =
    ArrayMutableStack((this as List<T>))

fun <T> emptyStack(): Stack<T> = ArrayStack()
fun <T> stackOf(vararg elements: T): Stack<T> = ArrayStack(elements.toList())

open class ArrayStack<T>(
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    private val elements: List<T> = emptyList(),
) : Stack<T>, List<T> by elements.toList()

/**
 * Returns a new [Stack] filled with all elements of this collection.
 *
 * This is a _shallow_ copy.
 */
fun <T> List<T>.toStack() = ArrayStack(this)
