package ru.tetraquark.pathfinderlib.core.pathfinder

import ru.tetraquark.pathfinderlib.core.graph.Graph
import ru.tetraquark.pathfinderlib.core.graph.Node

interface PathFinderAlgorithm {

    fun findPath(
        graph: Graph<*>,
        startNode: Node<*>,
        finishNode: Node<*>
    ): List<Node<*>>

}