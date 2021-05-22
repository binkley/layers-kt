package hm.binkley.layers.x.util

interface XStack<out T> : List<T>, RandomAccess {
    fun peek() = last()
}

fun <T> XStack<T>.toMutableList() =
    XArrayMutableStack((this as List<T>).toMutableList())

fun <T> stackOf(): XStack<T> = XArrayStack(listOf())
fun <T> stackOf(vararg elements: T): XStack<T> =
    XArrayStack(listOf(*elements))

interface XMutableStack<T> : XStack<T>, MutableList<T> {
    fun push(element: T) = add(element)
    fun pop() = removeLast()
}

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
