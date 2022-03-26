package hm.binkley.util

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import java.util.Collections.rotate

interface MutableStack<T> : Stack<T>, MutableList<T> {
    /**
     * Pushes [element] to the top of the stack.
     *
     * @return [element]
     */
    fun push(element: T): T = element.also { add(it) }

    /**
     * Pops the top element from the stack.
     *
     * @return the previously top element
     * @throws NoSuchElementException if the stack is empty
     */
    fun pop(): T = removeLast()
}

open class ArrayMutableStack<T>(
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    private val elements: Collection<T> = emptyList(),
) : MutableStack<T>, MutableList<T> by elements.toMutableList() {
    // TODO: equals and hashCode
    override fun toString() = elements.toString()
}

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

/** Duplicates (re-pushes) the top element. */
fun <T> MutableStack<T>.duplicate() {
    push(peek())
}

/**
 * Rotates the top [n] elements "clockwise".
 * Use negative [n] to counter-rotate.
 *
 * Examples given a stack, `[1, 2, 3, 4]`:
 * 1. `rotate()` mutates the stack to `[1, 4, 2, 3]`.
 * 2. `rotate(4)` mutates the stack to `[4, 1, 2, 3]`.
 * 3. `rotate(-3)` mutates the stack to `[1, 3, 4, 2]`.
 *
 * @param n number of elements to rotate, default 3
 */
fun <T> MutableStack<T>.rotate(n: Int = 3) {
    when {
        0 == n -> Unit
        0 > n -> push(removeAt(size + n))
        else -> add(size - n, pop())
    }
}

/**
 * Swaps (rotates) the top 2 elements.
 *
 * @see [rotate]
 */
fun <T> MutableStack<T>.swap() = rotate(2)
