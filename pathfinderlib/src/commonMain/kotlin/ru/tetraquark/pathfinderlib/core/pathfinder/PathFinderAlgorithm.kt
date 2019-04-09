package ru.tetraquark.pathfinderlib.core.pathfinder

import ru.tetraquark.pathfinderlib.core.graph.Graph
import ru.tetraquark.pathfinderlib.core.graph.Node
import ru.tetraquark.pathfinderlib.core.map.Map
import ru.tetraquark.pathfinderlib.core.map.Path

interface PathFinderAlgorithm<NodeIdT, NodeDataT, EdgeIdT, EdgeWeightT> {

    fun findPath(map: Map<NodeIdT, NodeDataT, EdgeIdT, EdgeWeightT>): Path<NodeDataT>

    fun findPath(graph: Graph<NodeIdT, NodeDataT, EdgeIdT, EdgeWeightT>, startId: NodeIdT, finishId: NodeIdT): List<Node<NodeIdT, NodeDataT>>

}