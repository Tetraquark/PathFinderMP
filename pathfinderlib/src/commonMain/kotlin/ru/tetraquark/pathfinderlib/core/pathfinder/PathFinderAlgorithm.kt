package ru.tetraquark.pathfinderlib.core.pathfinder

import ru.tetraquark.pathfinderlib.core.graph.Graph
import ru.tetraquark.pathfinderlib.core.graph.Node

interface PathFinderAlgorithm<EdgeWeightT> {

    fun findPath(graph: Graph<*, EdgeWeightT>, startNode: Node<*>, finishNode: Node<*>): List<Node<*>>

}