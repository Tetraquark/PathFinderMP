package ru.tetraquark.pathfinderlib.core.pathfinder

import ru.tetraquark.pathfinderlib.core.PathFinderAlgorithm
import ru.tetraquark.pathfinderlib.core.map.Map
import ru.tetraquark.pathfinderlib.core.map.Path

class PathFinder(var pathFinderAlgorithm: PathFinderAlgorithm) {

    fun findPath(map: Map): Path = pathFinderAlgorithm.findPath(map)

}