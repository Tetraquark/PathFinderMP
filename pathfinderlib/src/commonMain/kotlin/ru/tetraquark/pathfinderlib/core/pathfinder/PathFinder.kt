package ru.tetraquark.pathfinderlib.core.pathfinder

import ru.tetraquark.pathfinderlib.core.map.Map
import ru.tetraquark.pathfinderlib.core.map.Path

class PathFinder<CellDataT, NodeIdT, NodeDataT, EdgeIdT, EdgeWeightT>(var pathFinderAlgorithm: PathFinderAlgorithm<NodeIdT, NodeDataT, EdgeIdT, EdgeWeightT>) {

    fun findPath(map: Map): Path = pathFinderAlgorithm.findPath(map)

}