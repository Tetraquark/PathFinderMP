package ru.tetraquark.pathfinderlib.core.map

import ru.tetraquark.pathfinderlib.core.graph.MutableGraph
import ru.tetraquark.pathfinderlib.core.pathfinder.PathFinderAlgorithm

abstract class WorldMap(
    protected val pathGraph: MutableGraph<MapCell>,
    protected var adapter: MapAdapter<Int>
) : Collection<MapCell> {

    var width = 0
        protected set
    var height = 0
        protected set

    abstract fun reloadMap()

    abstract fun getCell(x: Int, y: Int): MapCell?

    abstract suspend fun findPath(
        startPoint: Pair<Int, Int>,
        finishPoint: Pair<Int, Int>,
        algorithm: PathFinderAlgorithm
    ): Path

    abstract suspend fun findPathIncrementally(
        startPoint: Pair<Int, Int>,
        finishPoint: Pair<Int, Int>,
        algorithm: PathFinderAlgorithm,
        callback: ResultsCallback
    )

    interface ResultsCallback {

        suspend fun onPointHandled(point: Pair<Int, Int>)

        suspend fun onPathFound(path: Path)

    }
}