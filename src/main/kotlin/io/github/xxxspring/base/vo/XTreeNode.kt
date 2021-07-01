package io.github.xxxspring.base.vo

import java.util.*

/**
 * Created by xponly on 18/2/15.
 */
open class XTreeNode<K, T> {
    var data: T? = null
        get() = field
        set(value) { field = value }

    var id: K? = null
        get() = field
        set(value) { field = value }

    var parentId: K? = null
        get() = field
        set(value) { field = value }

    var children : ArrayList<XTreeNode<K, T>>
        get() = field

    constructor() {
        children = ArrayList<XTreeNode<K, T>>()
    }

    constructor(id: K, parentId: K?, data: T) {
        this.id = id
        this.parentId = parentId
        this.data = data
        children = ArrayList<XTreeNode<K, T>>()
    }

    fun addChild(child: XTreeNode<K, T>) {
        this.children.add(child)
    }

    fun iterator(): TreeNodeIterator<K, T> {
        val it = TreeNodeIterator<K, T>(this)
        return it
    }

    class TreeNodeIterator<K, T> : Iterator<XTreeNode<K, T>> {
        lateinit var t : XTreeNode<K, T>
        lateinit var list: LinkedList<XTreeNode<K, T>>

        constructor(t: XTreeNode<K, T>) {
            this.t = t
            list = LinkedList<XTreeNode<K, T>>()

            this.build(t)
        }

        fun build(t: XTreeNode<K, T>) {
            list.add(t)
            t.children.forEach {
                build(it)
            }
        }

        override fun hasNext(): Boolean {
            return !list.isEmpty()
        }

        override fun next(): XTreeNode<K, T> {
            return list.poll()
        }
    }
}
