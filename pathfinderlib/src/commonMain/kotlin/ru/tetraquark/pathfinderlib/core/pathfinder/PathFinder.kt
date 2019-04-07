package ru.tetraquark.pathfinderlib.core.pathfinder

import ru.tetraquark.pathfinderlib.core.map.Map
import ru.tetraquark.pathfinderlib.core.map.Path

class PathFinder<CellDataT, EdgeWeight>(var pathFinderAlgorithm: PathFinderAlgorithm<EdgeWeight>) {

    fun findPath(map: Map): Path = pathFinderAlgorithm.findPath(map)

}