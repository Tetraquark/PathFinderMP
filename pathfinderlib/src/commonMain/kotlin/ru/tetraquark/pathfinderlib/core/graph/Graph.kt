package ru.tetraquark.pathfinderlib.core.graph

interface Graph<NodeDataT> : Iterable<Node<NodeDataT>> {

    fun getEdge(from: Node<NodeDataT>, to: Node<NodeDataT>): Edge<NodeDataT>?

    fun getEdgesOfNode(node: Node<*>): List<Edge<NodeDataT>>

    fun nodesCount(): Int

    fun edgesCount(): Int

    fun getNode(id: Int): Node<NodeDataT>?

    fun getNodes(): Map<Int, Node<NodeDataT>>

    fun getEdges(): Map<Int, Edge<NodeDataT>>

    fun getNodeByData(data: NodeDataT): Node<NodeDataT>?

    operator fun contains(node: Node<NodeDataT>?): Boolean

    operator fun contains(edge: Edge<NodeDataT>?): Boolean
}