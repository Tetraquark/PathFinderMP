package ru.tetraquark.pathfinderlib.core.graph.impl

import ru.tetraquark.pathfinderlib.core.graph.Edge
import ru.tetraquark.pathfinderlib.core.graph.MutableGraph
import ru.tetraquark.pathfinderlib.core.graph.Node
import ru.tetraquark.pathfinderlib.ext.nextString
import kotlin.random.Random

class SimpleGraph<NodeDataT, EdgeWeightT> : MutableGraph<NodeDataT, EdgeWeightT> {
    private val edges = mutableMapOf<String, Edge<EdgeWeightT>>()
    private val nodes = mutableMapOf<String, Node<NodeDataT>>()

    override fun nodesCount(): Int = nodes.size

    override fun edgesCount(): Int = edges.size

    override fun addNode(data: NodeDataT): Node<NodeDataT> {
        val id = Random.nextString()
        return addNode(id, data)
    }

    override fun addNode(id: String, data: NodeDataT): Node<NodeDataT> {
        // TODO: check id existence
        val node = Node(id, data)
        nodes[id] = node
        return node
    }

    override fun addEdge(
        id: String,
        from: Node<NodeDataT>,
        to: Node<NodeDataT>,
        weight: EdgeWeightT
    ): Edge<EdgeWeightT>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addEdge(from: Node<NodeDataT>, to: Node<NodeDataT>, weight: EdgeWeightT): Edge<EdgeWeightT>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeNode(node: Node<NodeDataT>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeNode(id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeEdge(edge: Edge<EdgeWeightT>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeEdge(id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clear() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEdge(from: Node<NodeDataT>, to: Node<NodeDataT>): Edge<NodeDataT>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEdgesOfNode(node: Node<NodeDataT>): MutableList<Edge<EdgeWeightT>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getNodesOfEdge(edge: Edge<EdgeWeightT>): Pair<Node<NodeDataT>, Node<NodeDataT>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun contains(node: Node<NodeDataT>?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun contains(edge: Edge<EdgeWeightT>?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun iterator(): Iterator<Node<NodeDataT>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}