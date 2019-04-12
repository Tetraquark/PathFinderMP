package ru.tetraquark.pathfinderlib.core.pathfinder

import ru.tetraquark.pathfinderlib.core.graph.Graph
import ru.tetraquark.pathfinderlib.core.graph.Node
import ru.tetraquark.pathfinderlib.core.map.Map
import ru.tetraquark.pathfinderlib.core.map.Path

interface PathFinderAlgorithm<NodeDataT, EdgeWeightT> {

    fun findPath(map: Map<NodeDataT, EdgeWeightT>): Path<NodeDataT>

    fun findPath(graph: Graph<NodeDataT, EdgeWeightT>, startId: Int, finishId: Int): List<Node<NodeDataT>>

}