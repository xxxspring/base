package io.github.xxxspring.base.tree

import org.junit.Test

/**
 * Created by xponly on 18/2/15.
 */
class XTreeNodeTest {
    data class Group(
        val id: Long,
        val name: String,
        val pid: Long? = null
    ) {}


    @Test
    fun testBuild() {
        /*val groups = mutableListOf<Group>()
        groups.add(Group(id = 1, name = "美国区"))
        groups.add(Group(id = 2, name = "中国区"))
        groups.add(Group(id = 3, name = "上海分公司", pid = 2))
        groups.add(Group(id = 4, name = "北京分公司", pid = 2))
        groups.add(Group(id = 5, name = "上海IT部门", pid = 3))
        groups.add(Group(id = 6, name = "上海IT部iOS组", pid = 5))
        groups.add(Group(id = 7, name = "上海IT部android组", pid = 5))
        groups.add(Group(id = 8, name = "北京IT部门", pid = 4))
        groups.add(Group(id = 9, name = "北京IT部iOS组", pid = 8))
        groups.add(Group(id = 10, name = "北京IT部android组", pid = 8))

        val groupNodes = groups?.map { XTreeNode<Group>(it) }
        if (groupNodes == null) return

        val gid2groupNode = groupNodes.associateBy { it.data!!.id!! }
        if (gid2groupNode == null) return

        groupNodes?.forEach {
            val pid = it.data!!.pid
            val parentNode = gid2groupNode[pid]
            parentNode?.addChild(it)
        }


        val n = gid2groupNode[2]
        val it = n?.iterator()
        it.forEach {
            println("${it.data!!.id} - ${it.data!!.name} - ${it.data!!.pid}")
        }*/
    }
}
