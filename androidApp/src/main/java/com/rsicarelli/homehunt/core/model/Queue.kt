package com.rsicarelli.homehunt.core.model

class Queue<T>(list: MutableList<T>) {

    var items: MutableList<T> = list

    fun isNotEmpty(): Boolean = items.isNotEmpty()

    fun isEmpty(): Boolean = items.isEmpty()

    fun count(): Int = items.count()

    override fun toString() = items.toString()

    fun add(element: T) {
        items.add(element)
    }

    @Throws(Exception::class)
    fun remove(): T = when {
        this.isNotEmpty() -> items.removeAt(0)
        else -> error("Nothing to remove from queue")
    }

    fun remove(item: T): Boolean {
        return items.remove(item)
    }

    @Throws(Exception::class)
    fun element(): T {
        if (this.isEmpty()) error("No element to retrieve")
        return items[0]
    }

    fun offer(element: T): Boolean {
        try {
            items.add(element)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    fun poll(): T? {
        if (this.isEmpty()) return null
        return items.removeAt(0)
    }

    fun peek(): T? {
        if (this.isEmpty()) return null
        return items[0]
    }

    fun addAll(queue: Queue<T>) {
        this.items.addAll(queue.items)
    }

    fun clear() {
        items.removeAll { true }
    }

}
