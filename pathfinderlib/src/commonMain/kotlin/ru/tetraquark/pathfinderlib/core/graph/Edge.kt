package ru.tetraquark.pathfinderlib.core.graph

data class Edge<WeightT>(
    val id: Int,
    private val from: Node<*>,
    private val to: Node<*>,
    val weight: WeightT
) {

    operator fun contains(node: Node<*>?): Boolean =
        node == from || node == to

}