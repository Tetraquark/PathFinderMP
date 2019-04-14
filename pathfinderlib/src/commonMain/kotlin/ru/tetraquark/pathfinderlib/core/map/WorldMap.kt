package ru.tetraquark.pathfinderlib.core.map

import ru.tetraquark.pathfinderlib.core.graph.MutableGraph
import ru.tetraquark.pathfinderlib.core.pathfinder.PathFinderAlgorithm

abstract class WorldMap(
    protected val pathGraph: MutableGraph<MapCell, Int>,
    protected var adapter: MapAdapter<Int>
) : Iterable<MapCell> {

    var width = 0
        protected set
    var height = 0
        protected set

    abstract fun reloadMap()

    abstract fun findPath(
        startPoint: Pair<Int, Int>,
        finishPoint: Pair<Int, Int>,
        algorithm: PathFinderAlgorithm<Int>
    ): Path
}