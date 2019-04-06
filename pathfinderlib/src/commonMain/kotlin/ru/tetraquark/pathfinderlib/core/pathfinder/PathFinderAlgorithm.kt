package ru.tetraquark.pathfinderlib.core.pathfinder

import ru.tetraquark.pathfinderlib.core.map.GraphMap
import ru.tetraquark.pathfinderlib.core.map.Path

interface PathFinderAlgorithm<EdgeWeightT> {

    fun findPath(graph: GraphMap<*, EdgeWeightT>): Path

}