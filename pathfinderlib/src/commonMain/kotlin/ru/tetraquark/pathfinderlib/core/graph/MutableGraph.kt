package ru.tetraquark.pathfinderlib.core.graph

interface MutableGraph<NodeDataT, EdgeWeightT> : Graph<NodeDataT, EdgeWeightT> {

    fun addNode(id: Int, data: NodeDataT): Node<NodeDataT>
    fun addEdge(id: Int, from: Node<NodeDataT>, to: Node<NodeDataT>, weight: EdgeWeightT): Edge<EdgeWeightT>?
    fun removeNode(node: Node<NodeDataT>)
    fun removeNode(id: Int)
    fun removeEdge(edge: Edge<EdgeWeightT>)
    fun removeEdge(id: Int)
    fun clear()

}