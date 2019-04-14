package ru.tetraquark.pathfinderlib.core.graph

data class Edge<WeightT, NodeDataT>(
    val id: Int,
    private val from: Node<NodeDataT>,
    private val to: Node<NodeDataT>,
    val weight: WeightT
) {

    operator fun contains(node: Node<*>?): Boolean =
        node == from || node == to

    fun getFrom(): Node<NodeDataT> = from
    fun getTo(): Node<NodeDataT> = to

}