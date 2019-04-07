package ru.tetraquark.pathfinderlib.core.graph.impl

import ru.tetraquark.pathfinderlib.core.graph.Edge
import ru.tetraquark.pathfinderlib.core.graph.MutableGraph
import ru.tetraquark.pathfinderlib.core.graph.Node

class SimpleGraph<NodeDataT, EdgeWeightT> : MutableGraph<NodeDataT, EdgeWeightT> {
    private val edges = mutableMapOf<Int, Edge<EdgeWeightT>>()
    private val nodes = mutableMapOf<Int, Node<NodeDataT>>()

    override fun nodesCount(): Int = nodes.size

    override fun edgesCount(): Int = edges.size

    private fun findFirstFreeId(usedIds: List<Int>): Int {
        var id = 0
        while (id < Int.MAX_VALUE) {
            if (!usedIds.contains(id))
                return id
            id++
        }
        throw IndexOutOfBoundsException("No more ids left!")
    }

    override fun addNode(data: NodeDataT): Node<NodeDataT>? {
        return try {
            val id = if (!nodes.contains(nodes.size)) nodes.size else findFirstFreeId(nodes.keys.toList())
            Node(id, data).apply {
                nodes[id] = this
            }
        } catch (e: IndexOutOfBoundsException) {
            null
        }
    }

    override fun addEdge(
        from: Node<NodeDataT>,
        to: Node<NodeDataT>,
        weight: EdgeWeightT
    ): Edge<EdgeWeightT>? {
        return try {
            val id = if (!edges.contains(edges.size)) edges.size else findFirstFreeId(edges.keys.toList())
            Edge(id, from, to, weight).apply {
                edges[id] = this
            }
        } catch (e: IndexOutOfBoundsException) {
            null
        }
    }

    override fun removeNode(node: Node<NodeDataT>) {
        nodes.remove(node.id)
    }

    override fun removeNode(id: Int) {
        nodes[id].let { n ->
            val markedForDelete = ArrayList<Int>()
            for ((eKey, eValue) in edges) {
                if (eValue.contains(n))
                    markedForDelete.add(eKey)
            }
            markedForDelete.forEach { removeEdge(it) }
        }
        nodes.remove(id)
    }

    override fun getNode(id: Int): Node<NodeDataT>? {
        return nodes[id]
    }

    override fun removeEdge(edge: Edge<EdgeWeightT>) {
        edges.remove(edge.id)
    }

    override fun removeEdge(id: Int) {
        edges.remove(id)
    }

    override fun clear() {
        edges.clear()
        nodes.clear()
    }

    override fun getEdge(from: Node<NodeDataT>, to: Node<NodeDataT>): Edge<EdgeWeightT>? {
        return if(from in this && to in this) {
            edges.values.firstOrNull { from in it && to in it }
        } else {
            null
        }
    }

    override fun getEdgesOfNode(node: Node<NodeDataT>): List<Edge<EdgeWeightT>>? {
        return if(node in this) {
            edges.values.filter { it.contains(node) }
        } else {
            null
        }
    }

    override operator fun contains(node: Node<NodeDataT>?): Boolean = node?.id in nodes.keys

    override operator fun contains(edge: Edge<EdgeWeightT>?): Boolean = edge?.id in edges.keys

    override fun iterator(): Iterator<Node<NodeDataT>> = nodes.values.iterator()

    // test
    fun getNodes() = nodes
    fun getEdges() = edges
}