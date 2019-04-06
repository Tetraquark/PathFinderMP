package ru.tetraquark.pathfinderlib.core.map

import ru.tetraquark.pathfinderlib.core.graph.MutableGraph

interface GraphMap<CellDataT, EdgeWeightT> {
    val graph: MutableGraph<CellDataT, EdgeWeightT>
}