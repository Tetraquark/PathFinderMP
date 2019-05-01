package ru.tetraquark.pathfinderlib.presentation.main

import ru.tetraquark.pathfinderlib.core.map.Path
import ru.tetraquark.pathfinderlib.core.map.WorldMap

interface MainContract {

    interface View {
        fun getInputMapWidth(): Int
        fun getInputMapHeight(): Int
        fun getSelectedAlgorithm(): RoutingAlgorithm

        fun showHintForState(state: AppState)
        fun showAvailableRoutingAlgorithms(routingAlgorithms: List<RoutingAlgorithm>)
        fun enableGenerateAction()
        fun disableGenerateAction()
        fun enableClearAction()
        fun disableClearAction()
        fun setStartCell(point: Pair<Int, Int>)
        fun showProgress()
        fun hideProgress()
        fun showTime(time: Long)
        fun showIterationsCount(iterations: Int)
        fun drawMap(map: WorldMap)
        fun drawPath(path: Path)
        fun drawVisitedCell(point: Pair<Int, Int>)
        fun drawFinishCell(point: Pair<Int, Int>)
        fun clearMap()

        fun showError(text: String)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()

        fun onClearAction()
        fun onGenerateAction()
        fun onCellClick(point: Pair<Int, Int>)
    }

    enum class AppState {
        GENERATE_MAP,
        SELECT_FINISH,
        FIND_ROUTE_PROGRESS,
        SHOWING_RESULTS
    }
}