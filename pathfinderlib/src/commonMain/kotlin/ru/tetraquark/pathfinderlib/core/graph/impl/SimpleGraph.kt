package ru.tetraquark.pathfinderlib.core.graph.impl

import ru.tetraquark.pathfinderlib.core.graph.Edge
import ru.tetraquark.pathfinderlib.core.graph.MutableGraph
import ru.tetraquark.pathfinderlib.core.graph.Node

class SimpleGraph<NodeDataT, EdgeWeightT> : MutableGraph<NodeDataT, EdgeWeightT> {
    private val edges = mutableMapOf<Int, Edge<EdgeWeightT>>()
    private val nodes = mutableMapOf<Int, Node<NodeDataT>>()

    override fun nodesCount(): Int = nodes.size

    override fun edgesCount(): Int = edges.size

    override fun addNode(node: Node<NodeDataT>) {
        nodes[node.index] = node
    }

    override fun addNode(data: NodeDataT): Node<NodeDataT> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addEdge(from: Node<NodeDataT>, to: Node<NodeDataT>, weight: EdgeWeightT): Edge<EdgeWeightT>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeNode(node: Node<NodeDataT>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeNode(index: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeEdge(edge: Edge<EdgeWeightT>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeEdge(index: Int) {
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