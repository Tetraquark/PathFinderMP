package ru.tetraquark.pathfinderlib.core.graph.impl

import ru.tetraquark.pathfinderlib.core.graph.*

class SimpleGraph<NodeDataT>(
    private val nodeIdFactory: UniqueIdFactory<Int>?,
    private val edgeIdFactory: UniqueIdFactory<Int>?
) : MutableGraph<NodeDataT> {
    private val edges = mutableMapOf<Int, Edge<NodeDataT>>()
    private val nodes = mutableMapOf<Int, Node<NodeDataT>>()

    override fun nodesCount(): Int = nodes.size

    override fun edgesCount(): Int = edges.size

    override fun addNode(data: NodeDataT): Node<NodeDataT>? {
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

    override fun putNode(id: Int, data: NodeDataT): Node<NodeDataT>? {
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
        from: Node<NodeDataT>,
        to: Node<NodeDataT>,
        weight: Int
    ): Edge<NodeDataT>? {
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
        id: Int,
        from: Node<NodeDataT>,
        to: Node<NodeDataT>,
        weight: Int
    ): Edge<NodeDataT>? {
        return Edge(id, from, to, weight).apply {
            edges[id] = this
        }
    }

    override fun removeNode(node: Node<NodeDataT>) {
        nodes.remove(node.id)
    }

    override fun removeNode(id: Int) {
        nodes[id].let { node ->
            edges
                .filterValues { node in it }
                .forEach { removeEdge(it.value) }
        }
        nodes.remove(id)
    }

    override fun getNode(id: Int): Node<NodeDataT>? = nodes[id]

    override fun removeEdge(edge: Edge<NodeDataT>) {
        edges.remove(edge.id)
    }

    override fun removeEdge(id: Int) {
        edges.remove(id)
    }

    override fun clear() {
        edges.clear()
        nodes.clear()
    }

    override fun getEdge(from: Node<NodeDataT>, to: Node<NodeDataT>): Edge<NodeDataT>? {
        return if(from in this && to in this) {
            // TODO: change firstOrNull predicate for directed graph
            edges.values.firstOrNull { from == it.from && to == it.to }
        } else {
            null
        }
    }

    override fun getEdgesOfNode(node: Node<*>): List<Edge<NodeDataT>> {
        return if(node in this) {
            edges.values.filter { it.from == node }
        } else {
            listOf()
        }
    }

    override operator fun contains(node: Node<NodeDataT>?): Boolean = node?.id in nodes.keys

    override operator fun contains(edge: Edge<NodeDataT>?): Boolean = edge?.id in edges.keys

    override fun iterator(): Iterator<Node<NodeDataT>> = nodes.values.iterator()

    override fun getNodes() = nodes
    override fun getEdges() = edges

    private inline fun <T> generateUniqueId(
        idGenerator: () -> T,
        isIdExist: (id: T) -> Boolean): T {
        var uniqueId: T
        do {
            uniqueId = idGenerator()
        } while (isIdExist(uniqueId))
        return uniqueId
    }

    override fun getNodeByData(data: NodeDataT): Node<NodeDataT>? {
        for (node in nodes)
            if (node.value.data == data)
                return node.value
        return null
    }

}