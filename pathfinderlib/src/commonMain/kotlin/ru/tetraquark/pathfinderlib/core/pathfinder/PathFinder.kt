package ru.tetraquark.pathfinderlib.core.pathfinder

import ru.tetraquark.pathfinderlib.core.map.Map
import ru.tetraquark.pathfinderlib.core.map.Path

class PathFinder<CellDataT, NodeIdT, NodeDataT, EdgeIdT, EdgeWeightT>(var pathFinderAlgorithm: PathFinderAlgorithm<NodeIdT, NodeDataT, EdgeIdT, EdgeWeightT>) {

    fun findPath(map: Map<NodeIdT, NodeDataT, EdgeIdT, EdgeWeightT>): Path<NodeDataT> = pathFinderAlgorithm.findPath(map)

}