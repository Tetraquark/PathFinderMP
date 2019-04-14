package ru.tetraquark.pathfinderlib.presentation.main

import ru.tetraquark.pathfinderlib.core.map.Path
import ru.tetraquark.pathfinderlib.core.map.WorldMap

interface MainContract {

    interface View {
        fun getInputMapWidth(): Int
        fun getInputMapHeight(): Int
        fun getSelectedAlgorithm(): RoutingAlgorithm

        fun showAvailableRoutingAlgorithms(routingAlgorithms: List<RoutingAlgorithm>)
        fun showTime(time: Long)
        fun showIterationsCount(iterations: Int)
        fun drawMap(map: WorldMap)
        fun drawPath(path: Path)
        fun clearMap()

        fun showError()
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()

        fun onClearButtonClick()
        fun onGenerateButtonClick()
        fun onStartCellSelected(point: Pair<Int, Int>)
        fun onFinishCellSelected(point: Pair<Int, Int>)
    }

}