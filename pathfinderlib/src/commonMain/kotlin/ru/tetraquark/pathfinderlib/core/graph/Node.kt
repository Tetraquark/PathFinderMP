package ru.tetraquark.pathfinderlib.core.graph

data class Node<IdT, DataT>(
    val id: IdT,
    var data: DataT
)