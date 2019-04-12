package ru.tetraquark.pathfinderlib.core.map

import ru.tetraquark.pathfinderlib.core.graph.Graph
import ru.tetraquark.pathfinderlib.core.graph.MutableGraph


abstract class Map<NodeDataT, EdgeWeightT>(protected val graph: MutableGraph<NodeDataT, EdgeWeightT>) : Iterable<MapCell> {
    var width = 0
        protected set
    var height = 0
        protected set

    abstract fun setStartCell(x: Int, y: Int)

    abstract fun setFinishCell(x: Int, y: Int)

    abstract fun getStartCell(): NodeDataT?

    abstract fun getFinishCell(): NodeDataT?

    abstract fun getGraph(): Graph<NodeDataT, EdgeWeightT>
}