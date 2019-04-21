package ru.tetraquark.pathfinderlib.core.pathfinder

import ru.tetraquark.pathfinderlib.core.graph.Graph
import ru.tetraquark.pathfinderlib.core.graph.Node

interface PathFinderAlgorithm {

    suspend fun findPath(
        graph: Graph<*>,
        startNode: Node<*>,
        finishNode: Node<*>
    ): List<Node<*>>

    suspend fun findPathIncrementally(
        graph: Graph<*>,
        startNode: Node<*>,
        finishNode: Node<*>,
        callback: ResultsCallback
    )

    interface ResultsCallback {

        suspend fun onNodeHandled(node: Node<*>)

        suspend fun onPathFound(path: List<Node<*>>)

    }

}