package ru.tetraquark.pathfinderlib.core.map.impl

import ru.tetraquark.pathfinderlib.core.graph.Graph
import ru.tetraquark.pathfinderlib.core.graph.MutableGraph
import ru.tetraquark.pathfinderlib.core.graph.Node
import ru.tetraquark.pathfinderlib.core.map.CellType
import ru.tetraquark.pathfinderlib.core.map.Map
import ru.tetraquark.pathfinderlib.core.map.MapAdapter
import ru.tetraquark.pathfinderlib.core.map.MapCell

class CellMap(
    graph: MutableGraph<Int, MapCell, Int, Int>
) : Map<Int, MapCell, Int, Int>(graph) {
    companion object {
        private const val DEFAULT_EDGES_WEIGHT = 1
    }

    override fun getGraph(): Graph<Int, MapCell, Int, Int> {
        return graph
    }

    private var startCell: MapCell? = null
    private var finishCell: MapCell? = null

    override fun setStartCell(x: Int, y: Int) {
        println("x $x y $y width $width")
        if (x in 0..width && y in 0..height) {
            for (cell in cellList)
                if (cell.x == x && cell.y == y)
                    startCell = cell
        }
    }

    override fun setFinishCell(x: Int, y: Int) {
        println("x $x y $y width $width")
        if (x in 0..width && y in 0..height) {
            for (cell in cellList)
                if (cell.x == x && cell.y == y)
                    finishCell = cell
        }
    }

    override fun getStartCell(): MapCell? {
        return startCell
    }

    override fun getFinishCell(): MapCell? {
        return finishCell
    }

    private var cellList: MutableList<MapCell> = mutableListOf()

    fun reloadMap(adapter: MapAdapter) {
        graph.clear()

        // TODO: need to add checks width and height (exmp. to a negative value)
        width = adapter.getWidth()
        height = adapter.getHeight()

        cellList = mutableListOf()
        println("edges before ${graph.getEdges()}")
        for(j in (0..height)) {
            for(i in (0..width)) {
                val cell = MapCell(i, j, adapter.getCellType(i, j))

                if(cell.cellType == CellType.OPEN) {
                    // creates nodes and edges with with adjacent nodes
                    val node = graph.addNode(cell)
                    if(node != null) {
                        println("x $i y $j w $width")
                        if(i > 0) {
                            println("to x ${i - 1} y $j")
                            tryToConnectTwoNodes(node, findNode(i - 1, j))
                        }
                        if(i < width - 1) {
                            println("to x ${i + 1} y $j")
                            tryToConnectTwoNodes(node, findNode(i + 1, j))
                        }
                        if(j > 0) {
                            println("to x $i y ${j - 1}")
                            tryToConnectTwoNodes(node, findNode(i, j - 1))
                        }
                        if(j < height - 1) {
                            println("to x $i y ${j + 1}")
                            tryToConnectTwoNodes(node, findNode(i, j + 1))
                        }
                    }
                }

                //cellList[nodeId] = cell
                cellList.add(cell)
            }
        }
        println("edges ${graph.getEdges()}")
    }

    /**
     * List of map cells [cells]
     */
    fun reloadMap(width: Int, height: Int, cells: List<CellType>) {
        reloadMap(object : MapAdapter() {
            override fun getWidth(): Int = width

            override fun getHeight(): Int = height

            override fun getCellType(x: Int, y: Int): CellType =
                cells[fromCoordsToKey(x, y, width)]
        })
    }

    override fun iterator(): Iterator<MapCell> = cellList.iterator()

    private fun fromCoordsToKey(x: Int, y: Int, width: Int): Int = x + width * y

    private fun findNode(x: Int, y: Int): Node<Int, MapCell>? {
        for (cell in cellList)
            if (cell.x == x && cell.y == y)
                return graph.getNodeByData(cell)

        return null
    }

    private fun tryToConnectTwoNodes(from: Node<Int, MapCell>, toNodeId: Int) {
        graph.getNode(toNodeId)?.let {
            println("tryToConnectTwoNodes: connecting (${from.id})${from.data} -> (${it.id})${it.data}")
            if(it.data.cellType != CellType.BLOCK) {
                addNewEdge(from, it, DEFAULT_EDGES_WEIGHT)
            }
        }
    }

    private fun tryToConnectTwoNodes(from: Node<Int, MapCell>, to: Node<Int, MapCell>?) {
        to?.let {
            println("tryToConnectTwoNodes: connecting (${from.id})${from.data} -> (${it.id})${it.data}")
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