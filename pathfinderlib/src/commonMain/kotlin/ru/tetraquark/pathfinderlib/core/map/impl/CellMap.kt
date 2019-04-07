package ru.tetraquark.pathfinderlib.core.map.impl

import ru.tetraquark.pathfinderlib.core.graph.MutableGraph
import ru.tetraquark.pathfinderlib.core.map.CellType
import ru.tetraquark.pathfinderlib.core.map.GraphMap
import ru.tetraquark.pathfinderlib.core.map.MapAdapter

class CellMap(
    graph: MutableGraph<Int, Cell, Int, Int>
) : GraphMap<Int, CellMap.Cell, Int, Int>(graph) {

    var adapter: MapAdapter? = null
        set(value) {
            field = value
            reloadMap()
        }

    private var mapWidth = 0
    private var mapHeight = 0

    private var cellMap: MutableMap<Int, Cell> = mutableMapOf()

    private fun reloadMap() {
        adapter?.let {
            graph.clear()
            cellMap.clear()

            mapWidth = it.getWidth()
            mapHeight = it.getHeight()

            for(i in (0..mapWidth)) {
                for(j in (0..mapHeight)) {
                    val cell = Cell(i, j, it.getCellType(i, j))

                    if(cell.cellType == CellType.OPEN) {
                        // TODO: create nodes and edges with with adjacent nodes
                    }

                    cellMap[fromCoordsToKey(i, j, mapWidth)] = cell
                }
            }
        }
    }

    private fun fromCoordsToKey(x: Int, y: Int, width: Int): Int = x + width * y

    data class Cell(
        val x: Int,
        val y: Int,
        val cellType: CellType
    )
}