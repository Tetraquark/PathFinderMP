package ru.tetraquark.pathfinderlib.core.map.impl

import ru.tetraquark.pathfinderlib.core.graph.MutableGraph
import ru.tetraquark.pathfinderlib.core.graph.Node
import ru.tetraquark.pathfinderlib.core.map.CellType
import ru.tetraquark.pathfinderlib.core.map.Map
import ru.tetraquark.pathfinderlib.core.map.MapAdapter
import ru.tetraquark.pathfinderlib.core.map.MapCell

class CellMap(
    private val graph: MutableGraph<Int, MapCell, Int, Int>
) : Map() {
    companion object {
        private const val DEFAULT_EDGES_WEIGHT = 1
    }
    var adapter: MapAdapter? = null
        set(value) {
            field = value
            reloadMap()
        }

    private var cellList: MutableList<MapCell> = mutableListOf()

    private fun reloadMap() {
        adapter?.let { _adapter ->
            graph.clear()

            // TODO: need to add checks width and height (exmp. to a negative value)
            width = _adapter.getWidth()
            height = _adapter.getHeight()

            cellList = mutableListOf()

            for(j in (0..height)) {
                for(i in (0..width)) {
                    val cell = MapCell(i, j, _adapter.getCellType(i, j))
                    val nodeId = fromCoordsToKey(i, j, width)

                    if(cell.cellType == CellType.OPEN) {
                        // creates nodes and edges with with adjacent nodes
                        val node = graph.putNode(nodeId, cell)
                        if(node != null) {
                            if(i > 0) {
                                tyrToConnectTwoNodes(node, fromCoordsToKey(i - 1, j, width))
                            }
                            if(i < width - 1) {
                                tyrToConnectTwoNodes(node, fromCoordsToKey(i + 1, j, width))
                            }
                            if(j > 0) {
                                tyrToConnectTwoNodes(node, fromCoordsToKey(i, j - 1, height))
                            }
                            if(j < height - 1) {
                                tyrToConnectTwoNodes(node, fromCoordsToKey(i, j + 1, height))
                            }
                        }
                    }

                    //cellList[nodeId] = cell
                    cellList.add(cell)
                }
            }
        }
    }

    override fun iterator(): Iterator<MapCell> = cellList.iterator()

    private fun fromCoordsToKey(x: Int, y: Int, width: Int): Int = x + width * y

    private fun tyrToConnectTwoNodes(from: Node<Int, MapCell>, toNodeId: Int) {
        graph.getNode(toNodeId)?.let {
            if(it.data.cellType != CellType.BLOCK) {
                addNewEdge(from, it, DEFAULT_EDGES_WEIGHT)
            }
        }
    }

    private fun addNewEdge(from: Node<Int, MapCell>, to: Node<Int, MapCell>, weight: Int) {
        // if there is no such edge yet
        if(graph.getEdge(from, to) == null) {
            graph.addEdge(from, to, weight)
        }
    }
}