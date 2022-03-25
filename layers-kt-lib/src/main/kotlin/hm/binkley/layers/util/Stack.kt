package hm.binkley.layers.util

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
fun <T> stackOf(vararg elements: T): Stack<T> = ArrayStack(listOf(*elements))

/**
 * Returns a new [Stack] filled with all elements of this collection.
 *
 * This is a _shallow_ copy.
 */
fun <T> List<T>.toStack() = ArrayStack(this)
