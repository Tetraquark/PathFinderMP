package ru.tetraquark.pathfinderlib.core.pathfinder

import ru.tetraquark.pathfinderlib.core.graph.Node

interface AlgorithmResult {
    val path: List<Node<*>>
}