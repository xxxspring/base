package com.lf.ec.common.base.util

import com.lf.ec.common.base.vo.XTreeNode

/**
 * Created by xponly on 18/3/12.
 */
object XTreeUtil {
    fun <K, T> build(nodeList: List<XTreeNode<K, T>>): List<XTreeNode<K, T>> {
        val treeList = mutableListOf<XTreeNode<K, T>>()

        val nodeMap = nodeList.associateBy { it.id }

        nodeList.forEach{
            val parent = nodeMap[it.parentId]
            if(parent == null) {
                treeList.add(it)
            } else {
                parent.addChild(it)
            }
        }

        return treeList
    }
}