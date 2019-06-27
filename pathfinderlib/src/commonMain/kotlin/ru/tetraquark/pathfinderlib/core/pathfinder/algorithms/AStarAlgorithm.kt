package ru.tetraquark.pathfinderlib.core.pathfinder.algorithms

import ru.tetraquark.pathfinderlib.core.graph.Graph
import ru.tetraquark.pathfinderlib.core.graph.Node
import ru.tetraquark.pathfinderlib.core.pathfinder.PathFinderAlgorithm

class AStarAlgorithm : PathFinderAlgorithm {

    override suspend fun findPath(graph: Graph<*>, startNode: Node<*>, finishNode: Node<*>): List<Node<*>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findPathIncrementally(
        graph: Graph<*>,
        startNode: Node<*>,
        finishNode: Node<*>,
        callback: PathFinderAlgorithm.ResultsCallback
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}