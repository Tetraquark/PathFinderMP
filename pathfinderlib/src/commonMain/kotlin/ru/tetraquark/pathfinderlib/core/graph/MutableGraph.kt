package ru.tetraquark.pathfinderlib.core.graph

interface MutableGraph<NodeDataT> :
    Graph<NodeDataT> {

    fun addNode(data: NodeDataT): Node<NodeDataT>?

    fun putNode(id: Int, data: NodeDataT): Node<NodeDataT>?

    fun addEdge(
        from: Node<NodeDataT>,
        to: Node<NodeDataT>,
        weight: Int
    ): Edge<NodeDataT>?

    fun putEdge(
        id: Int,
        from: Node<NodeDataT>,
        to: Node<NodeDataT>,
        weight: Int
    ): Edge<NodeDataT>?

    fun removeNode(node: Node<NodeDataT>)

    fun removeNode(id: Int)

    fun removeEdge(edge: Edge<NodeDataT>)

    fun removeEdge(id: Int)

    fun clear()
}