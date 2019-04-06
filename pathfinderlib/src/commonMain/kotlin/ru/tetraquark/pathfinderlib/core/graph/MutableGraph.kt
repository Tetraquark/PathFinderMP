package ru.tetraquark.pathfinderlib.core.graph

interface MutableGraph<NodeDataT, EdgeWeightT> : Graph<NodeDataT, EdgeWeightT> {

    fun addNode(node: Node<NodeDataT>)
    fun addNode(data: NodeDataT): Node<NodeDataT>
    fun addEdge(from: Node<NodeDataT>, to: Node<NodeDataT>, weight: EdgeWeightT): Edge<EdgeWeightT>?
    fun removeNode(node: Node<NodeDataT>)
    fun removeNode(index: Int)
    fun removeEdge(edge: Edge<EdgeWeightT>)
    fun removeEdge(index: Int)
    fun clear()

}