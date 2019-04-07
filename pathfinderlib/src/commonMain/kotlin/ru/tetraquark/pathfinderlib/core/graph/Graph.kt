package ru.tetraquark.pathfinderlib.core.graph

interface Graph<NodeIdT, NodeDataT, EdgeIdT, EdgeWeightT> :
    Iterable<Node<NodeIdT, NodeDataT>> {

    fun getEdge(from: Node<NodeIdT, NodeDataT>, to: Node<NodeIdT, NodeDataT>): Edge<EdgeIdT, EdgeWeightT>?

    fun getEdgesOfNode(node: Node<NodeIdT, NodeDataT>): List<Edge<EdgeIdT, EdgeWeightT>>?

    fun nodesCount(): Int

    fun edgesCount(): Int

    fun getNode(id: NodeIdT): Node<NodeIdT, NodeDataT>?

    operator fun contains(node: Node<NodeIdT, NodeDataT>?): Boolean

    operator fun contains(edge: Edge<EdgeIdT, EdgeWeightT>?): Boolean
}