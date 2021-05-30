package hm.binkley.layers.util

import lombok.Generated

interface Stack<out T> : List<T> {
    fun peek() = last()
}

@Generated // TODO: How to test?  Kotlin complains check is useless
@Suppress("UNUSED")
fun <T> Stack<T>.toMutableStack(): MutableStack<T> =
    ArrayMutableStack((this as List<T>).toMutableList())

fun <T> stackOf(): Stack<T> = ArrayStack(listOf())
fun <T> stackOf(vararg elements: T): Stack<T> =
    ArrayStack(listOf(*elements))

@Generated // TODO: How to test?  Kotlin complains check is useless
fun <T> List<T>.toStack() = ArrayStack(this)
