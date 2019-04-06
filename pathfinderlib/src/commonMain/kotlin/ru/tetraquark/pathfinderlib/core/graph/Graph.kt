package ru.tetraquark.pathfinderlib.core.graph

interface Graph<NodeDataT, EdgeWeightT> : Iterable<Node<NodeDataT>> {

    fun getEdge(from: Node<NodeDataT>, to: Node<NodeDataT>): Edge<NodeDataT>?
    fun getEdgesOfNode(node: Node<NodeDataT>): MutableList<Edge<EdgeWeightT>>
    fun getNodesOfEdge(edge: Edge<EdgeWeightT>): Pair<Node<NodeDataT>, Node<NodeDataT>>
    fun nodesCount(): Int
    fun edgesCount(): Int

    operator fun contains(node: Node<NodeDataT>?): Boolean
    operator fun contains(edge: Edge<EdgeWeightT>?): Boolean
}