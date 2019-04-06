package ru.tetraquark.pathfinderlib.core.pathfinder

import ru.tetraquark.pathfinderlib.core.map.GraphMap
import ru.tetraquark.pathfinderlib.core.map.Path

class PathFinder<CellDataT, EdgeWeight>(var pathFinderAlgorithm: PathFinderAlgorithm<EdgeWeight>) {

    fun findPath(map: GraphMap<CellDataT, EdgeWeight>): Path = pathFinderAlgorithm.findPath(map)

}