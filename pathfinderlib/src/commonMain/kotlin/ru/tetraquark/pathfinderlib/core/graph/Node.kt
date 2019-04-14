package ru.tetraquark.pathfinderlib.core.graph

data class Node<DataT>(
    val id: Int,
    var data: DataT
)