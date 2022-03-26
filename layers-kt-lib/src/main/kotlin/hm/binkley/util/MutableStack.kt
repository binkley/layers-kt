package hm.binkley.util

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings

interface MutableStack<T> : Stack<T>, MutableList<T> {
    fun push(element: T) = add(element)
    fun pop() = removeLast()
}

fun <T> MutableStack<T>.toStack(): Stack<T> = this

fun <T> emptyMutableStack(): MutableStack<T> = ArrayMutableStack()
fun <T> mutableStackOf(vararg elements: T): MutableStack<T> =
    ArrayMutableStack(elements.toList())

open class ArrayMutableStack<T>(
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    private val elements: List<T> = emptyList(),
) : MutableStack<T>, MutableList<T> by elements.toMutableList()

fun <T> List<T>.toMutableStack() = ArrayMutableStack(this)
