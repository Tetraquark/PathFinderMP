package ru.tetraquark.pathfinderlib.core.graph

interface MutableGraph<NodeDataT, EdgeWeightT> :
    Graph<NodeDataT, EdgeWeightT> {

    fun addNode(data: NodeDataT): Node<NodeDataT>?

    fun putNode(id: Int, data: NodeDataT): Node<NodeDataT>?

    fun addEdge(
        from: Node<NodeDataT>,
        to: Node<NodeDataT>,
        weight: EdgeWeightT
    ): Edge<EdgeWeightT, NodeDataT>?

    fun putEdge(
        id: Int,
        from: Node<NodeDataT>,
        to: Node<NodeDataT>,
        weight: EdgeWeightT
    ): Edge<EdgeWeightT, NodeDataT>?

    fun removeNode(node: Node<NodeDataT>)

    fun removeNode(id: Int)

    fun removeEdge(edge: Edge<EdgeWeightT, NodeDataT>)

    fun removeEdge(id: Int)

    fun clear()
}