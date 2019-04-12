package ru.tetraquark.pathfinderlib.core.graph

interface Graph<NodeDataT, EdgeWeightT> :
    Iterable<Node<NodeDataT>> {

    fun getEdge(from: Node<NodeDataT>, to: Node<NodeDataT>): Edge<EdgeWeightT, NodeDataT>?

    fun getEdgesOfNode(node: Node<NodeDataT>): List<Edge<EdgeWeightT, NodeDataT>>

    fun nodesCount(): Int

    fun edgesCount(): Int

    fun getNode(id: Int): Node<NodeDataT>?

    operator fun contains(node: Node<NodeDataT>?): Boolean

    operator fun contains(edge: Edge<EdgeWeightT, NodeDataT>?): Boolean

    fun getNodes(): Map<Int, Node<NodeDataT>>

    fun getEdges(): Map<Int, Edge<EdgeWeightT, NodeDataT>>

    fun getNodeByData(data: NodeDataT): Node<NodeDataT>?
}