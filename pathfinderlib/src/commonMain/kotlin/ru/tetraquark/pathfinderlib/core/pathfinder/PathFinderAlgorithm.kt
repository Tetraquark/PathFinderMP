package ru.tetraquark.pathfinderlib.core.pathfinder

import ru.tetraquark.pathfinderlib.core.map.Map
import ru.tetraquark.pathfinderlib.core.map.Path

interface PathFinderAlgorithm<EdgeWeightT> {

    fun findPath(graph: Map): Path

}