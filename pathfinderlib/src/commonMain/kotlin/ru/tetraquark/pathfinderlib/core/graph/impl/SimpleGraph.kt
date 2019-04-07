package ru.tetraquark.pathfinderlib.core.graph.impl

import ru.tetraquark.pathfinderlib.core.graph.Edge
import ru.tetraquark.pathfinderlib.core.graph.MutableGraph
import ru.tetraquark.pathfinderlib.core.graph.Node
import ru.tetraquark.pathfinderlib.core.graph.UniqueIdFactory

class SimpleGraph<NodeIdT, NodeDataT, EdgeIdT, EdgeWeightT>(
    private val nodeIdFactory: UniqueIdFactory<NodeIdT>?,
    private val edgeIdFactory: UniqueIdFactory<EdgeIdT>?
) : MutableGraph<NodeIdT, NodeDataT, EdgeIdT, EdgeWeightT> {
    private val edges = mutableMapOf<EdgeIdT, Edge<EdgeIdT, EdgeWeightT>>()
    private val nodes = mutableMapOf<NodeIdT, Node<NodeIdT, NodeDataT>>()

    override fun nodesCount(): Int = nodes.size

    override fun edgesCount(): Int = edges.size

    override fun addNode(data: NodeDataT): Node<NodeIdT, NodeDataT>? {
        return if (nodeIdFactory != null) {
            val uniqueId = generateUniqueId({
                nodeIdFactory.getUniqueId()
            }, {
                nodes.containsKey(it)
            })

            return Node(uniqueId, data).apply {
                nodes[id] = this
            }
        } else {
            null
        }
    }

    override fun putNode(id: NodeIdT, data: NodeDataT): Node<NodeIdT, NodeDataT>? {
        return if(nodes.containsKey(id)) {
            // rewrite data of the existing node
            nodes[id]?.apply {
                this.data = data
            }
        } else {
            // create new node
            Node(id, data).apply {
                nodes[id] = this
            }
        }
    }

    override fun addEdge(
        from: Node<NodeIdT, NodeDataT>,
        to: Node<NodeIdT, NodeDataT>,
        weight: EdgeWeightT
    ): Edge<EdgeIdT, EdgeWeightT>? {
        return if(edgeIdFactory != null) {
            val uniqueId = generateUniqueId({
                edgeIdFactory.getUniqueId()
            }, {
                edges.containsKey(it)
            })

            putEdge(uniqueId, from, to, weight)
        } else {
            null
        }
    }

    override fun putEdge(
        id: EdgeIdT,
        from: Node<NodeIdT, NodeDataT>,
        to: Node<NodeIdT, NodeDataT>,
        weight: EdgeWeightT
    ): Edge<EdgeIdT, EdgeWeightT>? {
        return Edge(id, from, to, weight).apply {
            edges[id] = this
        }
    }

    override fun removeNode(node: Node<NodeIdT, NodeDataT>) {
        nodes.remove(node.id)
    }

    override fun removeNode(id: NodeIdT) {
        nodes[id].let { node ->
            edges
                .filterValues { node in it }
                .forEach { removeEdge(it.value) }
        }
        nodes.remove(id)
    }

    override fun getNode(id: NodeIdT): Node<NodeIdT, NodeDataT>? = nodes[id]

    override fun removeEdge(edge: Edge<EdgeIdT, EdgeWeightT>) {
        edges.remove(edge.id)
    }

    override fun removeEdge(id: EdgeIdT) {
        edges.remove(id)
    }

    override fun clear() {
        edges.clear()
        nodes.clear()
    }

    override fun getEdge(from: Node<NodeIdT, NodeDataT>, to: Node<NodeIdT, NodeDataT>): Edge<EdgeIdT, EdgeWeightT>? {
        return if(from in this && to in this) {
            // TODO: change firstOrNull predicate for directed graph
            edges.values.firstOrNull { (from in it && to in it) || (to in it && from in it) }
        } else {
            null
        }
    }

    override fun getEdgesOfNode(node: Node<NodeIdT, NodeDataT>): List<Edge<EdgeIdT, EdgeWeightT>>? {
        return if(node in this) {
            edges.values.filter { it.contains(node) }
        } else {
            null
        }
    }

    override operator fun contains(node: Node<NodeIdT, NodeDataT>?): Boolean = node?.id in nodes.keys

    override operator fun contains(edge: Edge<EdgeIdT, EdgeWeightT>?): Boolean = edge?.id in edges.keys

    override fun iterator(): Iterator<Node<NodeIdT, NodeDataT>> = nodes.values.iterator()

    // TODO: test
    fun getNodes() = nodes
    fun getEdges() = edges

    private inline fun <T> generateUniqueId(
        idGenerator: () -> T,
        isIdExist: (id: T) -> Boolean): T {
        var uniqueId: T
        do {
            uniqueId = idGenerator()
        } while (isIdExist(uniqueId))
        return uniqueId
    }
}