package ru.tetraquark.pathfinderlib.core.graph

data class Edge<IdT, WeightT>(
    val id: IdT,
    private val from: Node<*, *>,
    private val to: Node<*, *>,
    val weight: WeightT
) {

    operator fun contains(node: Node<*, *>?): Boolean =
        node == from || node == to

}