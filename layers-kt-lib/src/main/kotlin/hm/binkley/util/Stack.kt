package hm.binkley.util

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings

interface Stack<out T> : List<T> {
    /**
     * Peeks at top element.
     *
     * This is logically equivalent to:
     * ```
     * val top = pop()
     * push(top)
     * return top
     * ```
     * but does not mutate the stack.
     */
    fun peek() = last()
}

open class ArrayStack<T>(
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    private val elements: Collection<T> = emptyList(),
) : Stack<T>, List<T> by elements.toList() {
    // TODO: equals and hashCode
    override fun toString() = elements.toString()
}

fun <T> emptyStack(): Stack<T> = stackOf()
fun <T> stackOf(vararg elements: T): Stack<T> = ArrayStack(elements.toList())

/**
 * Returns a new [Stack] filled with all elements of this collection.
 *
 * This is a _shallow_ copy.
 */
fun <T> Collection<T>.toStack(): Stack<T> = ArrayStack(this)
