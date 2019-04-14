package ru.tetraquark.pathfinderlib.presentation.main

import ru.tetraquark.pathfinderlib.core.graph.UniqueIdFactory
import ru.tetraquark.pathfinderlib.core.graph.impl.SimpleGraph
import ru.tetraquark.pathfinderlib.core.map.WorldMap
import ru.tetraquark.pathfinderlib.core.map.impl.CellMapGenerator
import ru.tetraquark.pathfinderlib.core.pathfinder.PathFinderAlgorithm
import ru.tetraquark.pathfinderlib.core.pathfinder.algorithms.WaveAlgorithm

class MainPresenter : MainContract.Presenter {
    private var view: MainContract.View? = null

    private var currentMap: WorldMap? = null
    private val mapGenerator = CellMapGenerator()
    private var startCell: Pair<Int, Int>? = null

    override fun attachView(view: MainContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun onGenerateButtonClick() {
        view?.let {
            val mapWidth = it.getInputMapWidth()
            val mapHeight = it.getInputMapHeight()

            val currentMap = mapGenerator.generateMap(
                mapWidth,
                mapHeight,
                SimpleGraph(SimpleGraphIdCounter(), SimpleGraphIdCounter())
            )

            if(currentMap != null) {
                it.drawMap(currentMap)
                this.currentMap = currentMap
            } else {
                // TODO: show error
            }
        }
    }

    override fun onClearButtonClick() {
        view?.clearMap()
        currentMap = null
    }

    override fun onStartCellSelected(point: Pair<Int, Int>) {
        // TODO: coordinates validation
        startCell = point
    }

    override fun onFinishCellSelected(point: Pair<Int, Int>) {
        // TODO: coordinates validation

        val startCell = this.startCell
        val currentMap = this.currentMap
        val algorithm = view?.getSelectedAlgorithm()

        if(startCell != null && currentMap != null && algorithm != null) {
            val path = currentMap.findPath(startCell, point, createAlgorithm(algorithm))
            view?.drawPath(path)
        }
    }

    private fun createAlgorithm(routingAlgorithm: RoutingAlgorithm): PathFinderAlgorithm<Int> = when(routingAlgorithm) {
        RoutingAlgorithm.WAVE -> WaveAlgorithm()
    }

    private class SimpleGraphIdCounter : UniqueIdFactory<Int> {
        private var idCounter = 0

        override fun getUniqueId(): Int = idCounter++
    }

}