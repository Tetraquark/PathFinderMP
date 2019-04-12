package ru.tetraquark.pathfinderlib.core.pathfinder

import ru.tetraquark.pathfinderlib.core.map.Map
import ru.tetraquark.pathfinderlib.core.map.Path

class PathFinder<NodeDataT, EdgeWeightT>(var pathFinderAlgorithm: PathFinderAlgorithm<NodeDataT, EdgeWeightT>) {

    fun findPath(map: Map<NodeDataT, EdgeWeightT>): Path<NodeDataT> = pathFinderAlgorithm.findPath(map)

}