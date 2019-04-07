package ru.tetraquark.pathfinderlib.core.map

import ru.tetraquark.pathfinderlib.core.graph.MutableGraph

abstract class GraphMap<NodeIdT, NodeDataT, EdgeIdT, EdgeWeightT>(
    protected val graph: MutableGraph<NodeIdT, NodeDataT, EdgeIdT, EdgeWeightT>
) {

}