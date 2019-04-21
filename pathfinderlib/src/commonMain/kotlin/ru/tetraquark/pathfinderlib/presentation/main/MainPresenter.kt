package ru.tetraquark.pathfinderlib.presentation.main

import ru.tetraquark.pathfinderlib.core.graph.UniqueIdFactory
import ru.tetraquark.pathfinderlib.core.graph.impl.SimpleGraph
import ru.tetraquark.pathfinderlib.core.map.CellType
import ru.tetraquark.pathfinderlib.core.map.WorldMap
import ru.tetraquark.pathfinderlib.core.map.impl.CellMapGenerator
import ru.tetraquark.pathfinderlib.core.pathfinder.PathFinderAlgorithm
import ru.tetraquark.pathfinderlib.core.pathfinder.algorithms.DijkstraAlgorithm
import ru.tetraquark.pathfinderlib.core.pathfinder.algorithms.WaveAlgorithm

class MainPresenter : MainContract.Presenter {
    companion object {
        const val MAX_MAP_WIDTH = 100
        const val MAX_MAP_HEIGHT = 100
    }
    private var view: MainContract.View? = null

    private var currentMap: WorldMap? = null
    private val mapGenerator = CellMapGenerator()
    private var startCell: Pair<Int, Int>? = null

    private var appState = MainContract.AppState.GENERATE_MAP

    override fun attachView(view: MainContract.View) {
        this.view = view
        view.enableGenerateAction()
        view.showAvailableRoutingAlgorithms(listOf(RoutingAlgorithm.WAVE, RoutingAlgorithm.DIJKSTRA))
    }

    override fun detachView() {
        this.view = null
    }

    override fun onGenerateAction() {
        view?.let {

            val mapWidth = it.getInputMapWidth()
            val mapHeight = it.getInputMapHeight()

            if(mapWidth > MAX_MAP_WIDTH || mapWidth <= 0 || mapHeight > MAX_MAP_HEIGHT || mapHeight <= 0) {
                it.showError("Wrong input map width or height.")
                return
            }

            it.clearMap()
            val currentMap = mapGenerator.generateMap(
                mapWidth,
                mapHeight,
                SimpleGraph(SimpleGraphIdCounter(), SimpleGraphIdCounter())
            )

            if (currentMap != null) {
                this.currentMap = currentMap
                it.drawMap(currentMap)
            } else {
                it.showError("Can't generate map.")
            }
        }
    }

    override fun onClearAction() {
        currentMap = null
        changeAppState(MainContract.AppState.GENERATE_MAP)
        view?.let {
            it.clearMap()
            it.disableClearAction()
            it.enableGenerateAction()
        }
    }

    override fun onCellClick(point: Pair<Int, Int>) {
        val selectedCell = currentMap?.getCell(point.first, point.second)
        if (selectedCell != null && selectedCell.cellType != CellType.BLOCK) {
            if (appState == MainContract.AppState.GENERATE_MAP) {
                changeAppState(MainContract.AppState.SELECT_FINISH)
                this.startCell = point
                view?.let {
                    it.setStartCell(point)
                    it.disableGenerateAction()
                }
            } else if (appState == MainContract.AppState.SELECT_FINISH) {
                changeAppState(MainContract.AppState.FIND_ROUTE_PROGRESS)
                view?.showProgress()
                finishCellSelected(point)
            }
        }
    }

    private fun finishCellSelected(point: Pair<Int, Int>) {
        val currentMap = this.currentMap

        if (currentMap == null || point.first < 0 || point.first >= currentMap.width
            || point.second < 0 || point.second >= currentMap.height) {
            return
        }

        val startCell = this.startCell
        val algorithm = view?.getSelectedAlgorithm()

        if (startCell != null && algorithm != null) {
            val path = currentMap.findPath(startCell, point, createAlgorithm(algorithm))

            changeAppState(MainContract.AppState.SHOWING_RESULTS)
            view?.let {
                it.hideProgress()
                it.drawPath(path)
                it.enableClearAction()
            }
        }
    }

    private fun changeAppState(appState: MainContract.AppState) {
        this.appState = appState
        view?.showHintForState(appState)
    }

    private fun createAlgorithm(routingAlgorithm: RoutingAlgorithm): PathFinderAlgorithm =
        when (routingAlgorithm) {
            RoutingAlgorithm.WAVE -> WaveAlgorithm()
            RoutingAlgorithm.DIJKSTRA -> DijkstraAlgorithm()
        }

    private class SimpleGraphIdCounter : UniqueIdFactory<Int> {
        private var idCounter = 0

        override fun getUniqueId(): Int = idCounter++
    }
}