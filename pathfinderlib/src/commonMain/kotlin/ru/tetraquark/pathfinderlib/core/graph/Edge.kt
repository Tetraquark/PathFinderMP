package ru.tetraquark.pathfinderlib.core.graph

data class Edge<IdT, WeightT, NodeIdT, NodeDataT>(
    val id: IdT,
    private val from: Node<NodeIdT, NodeDataT>,
    private val to: Node<NodeIdT, NodeDataT>,
    val weight: WeightT
) {

    operator fun contains(node: Node<NodeIdT, NodeDataT>?): Boolean =
        node == from || node == to


    fun getFrom(): Node<NodeIdT, NodeDataT> = from
    fun getTo(): Node<NodeIdT, NodeDataT> = to

}