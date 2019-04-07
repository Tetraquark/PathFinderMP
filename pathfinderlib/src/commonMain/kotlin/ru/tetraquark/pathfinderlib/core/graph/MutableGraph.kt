package ru.tetraquark.pathfinderlib.core.graph

interface MutableGraph<NodeIdT, NodeDataT, EdgeIdT, EdgeWeightT> :
    Graph<NodeIdT, NodeDataT, EdgeIdT, EdgeWeightT> {

    fun addNode(data: NodeDataT): Node<NodeIdT, NodeDataT>?

    fun putNode(id: NodeIdT, data: NodeDataT): Node<NodeIdT, NodeDataT>?

    fun addEdge(
        from: Node<NodeIdT, NodeDataT>,
        to: Node<NodeIdT, NodeDataT>,
        weight: EdgeWeightT
    ): Edge<EdgeIdT, EdgeWeightT>?

    fun putEdge(
        id: EdgeIdT,
        from: Node<NodeIdT, NodeDataT>,
        to: Node<NodeIdT, NodeDataT>,
        weight: EdgeWeightT
    ): Edge<EdgeIdT, EdgeWeightT>?

    fun removeNode(node: Node<NodeIdT, NodeDataT>)

    fun removeNode(id: NodeIdT)

    fun removeEdge(edge: Edge<EdgeIdT, EdgeWeightT>)

    fun removeEdge(id: EdgeIdT)

    fun clear()
}