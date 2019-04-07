package ru.tetraquark.pathfinderlib.core.graph

interface Graph<NodeDataT, EdgeWeightT> : Iterable<Node<NodeDataT>> {

    fun getEdge(from: Node<NodeDataT>, to: Node<NodeDataT>): Edge<EdgeWeightT>?
    fun getEdgesOfNode(node: Node<NodeDataT>): List<Edge<EdgeWeightT>>?
    fun nodesCount(): Int
    fun edgesCount(): Int
    fun getNode(id: Int): Node<NodeDataT>?

    operator fun contains(node: Node<NodeDataT>?): Boolean
    operator fun contains(edge: Edge<EdgeWeightT>?): Boolean
}