package hm.binkley.util

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings

interface MutableStack<T> : Stack<T>, MutableList<T> {
    fun push(element: T) = add(element)
    fun pop() = removeLast()
}

open class ArrayMutableStack<T>(
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    private val elements: Collection<T> = emptyList(),
) : MutableStack<T>, MutableList<T> by elements.toMutableList()

fun <T> emptyMutableStack(): MutableStack<T> = mutableStackOf()
fun <T> mutableStackOf(vararg elements: T): MutableStack<T> =
    ArrayMutableStack(elements.toList())

/**
 * Returns a new [MutableStack] filled with all elements of this collection.
 *
 * This is a _shallow_ copy.
 */
fun <T> Collection<T>.toMutableStack(): MutableStack<T> =
    ArrayMutableStack(this)
