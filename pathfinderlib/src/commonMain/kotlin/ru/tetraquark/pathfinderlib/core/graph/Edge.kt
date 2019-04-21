package ru.tetraquark.pathfinderlib.core.graph

data class Edge<NodeDataT>(
    val id: Int,
    val from: Node<NodeDataT>,
    val to: Node<NodeDataT>,
    val weight: Int
) {

    operator fun contains(node: Node<*>?): Boolean =
        node == from || node == to
}