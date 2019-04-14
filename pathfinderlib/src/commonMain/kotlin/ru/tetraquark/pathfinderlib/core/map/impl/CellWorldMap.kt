package ru.tetraquark.pathfinderlib.core.map.impl

import ru.tetraquark.pathfinderlib.core.graph.MutableGraph
import ru.tetraquark.pathfinderlib.core.graph.Node
import ru.tetraquark.pathfinderlib.core.map.*
import ru.tetraquark.pathfinderlib.core.pathfinder.PathFinderAlgorithm

class CellWorldMap(
    pathGraph: MutableGraph<MapCell, Int>,
    adapter: MapAdapter<Int>
) : WorldMap(pathGraph, adapter) {

    companion object {
        private const val DEFAULT_EDGES_WEIGHT = 1
    }

    private var cellList: MutableList<MapCell>

    init {
        cellList = mutableListOf()
        reloadMap()
    }

    override fun reloadMap() {
        pathGraph.clear()

        width = adapter.getWidth()
        height = adapter.getHeight()

        cellList = mutableListOf()
        for(j in (0 until height)) {
            for(i in (0 until width)) {
                val cell = MapCell(i, j, adapter.getCellType(i, j))
                val nodeId = fromCoordsToKey(i, j, width)
                val node = pathGraph.putNode(nodeId, cell)

                if(cell.cellType == CellType.OPEN) {
                    // creates nodes and edges with with adjacent nodes
                    if(node != null) {
                        if(i > 0) {
                            tryToConnectTwoNodes(node, fromCoordsToKey(i - 1, j, width))
                        }
                        if(i < width - 1) {
                            tryToConnectTwoNodes(node, fromCoordsToKey(i + 1, j, width))
                        }
                        if(j > 0) {
                            tryToConnectTwoNodes(node, fromCoordsToKey(i, j - 1, width))
                        }
                        if(j < height - 1) {
                            tryToConnectTwoNodes(node, fromCoordsToKey(i, j + 1, width))
                        }
                    }
                }

                cellList.add(cell)
            }
        }
    }

    override fun getCell(x: Int, y: Int): MapCell? {
        if(x < 0 || x >= width || y < 0 || y >= height)
            return null
        return cellList[fromCoordsToKey(x, y, width)]
    }

    override fun findPath(
        startPoint: Pair<Int, Int>,
        finishPoint: Pair<Int, Int>,
        algorithm: PathFinderAlgorithm<Int>
    ): Path {
        val startNode = pathGraph.getNode(fromPointsToKey(startPoint, width))
        val finishNode = pathGraph.getNode(fromPointsToKey(finishPoint, width))

        if(startNode != null && finishNode != null) {
            val pathNodes = algorithm.findPath(pathGraph, startNode, finishNode)
            return getPathFromNodesList(pathNodes)
        }

        return Path()
    }

    override fun iterator(): Iterator<MapCell> = cellList.iterator()

    private fun fromCoordsToKey(x: Int, y: Int, width: Int): Int = x + width * y

    private fun fromPointsToKey(point: Pair<Int, Int>, width: Int): Int =
        fromCoordsToKey(point.first, point.second, width)

    private fun findNode(x: Int, y: Int): Node<MapCell>? {
        for (cell in cellList)
            if (cell.x == x && cell.y == y)
                return pathGraph.getNodeByData(cell)

        return null
    }

    private fun tryToConnectTwoNodes(from: Node<MapCell>, toNodeId: Int) {
        pathGraph.getNode(toNodeId)?.let {
            if(it.data.cellType != CellType.BLOCK) {
                addNewEdge(from, it, DEFAULT_EDGES_WEIGHT)
            }
        }
    }

    private fun tryToConnectTwoNodes(from: Node<MapCell>, to: Node<MapCell>?) {
        to?.let {
            if(it.data.cellType != CellType.BLOCK) {
                addNewEdge(from, it, DEFAULT_EDGES_WEIGHT)
            }
        }
    }

    private fun addNewEdge(from: Node<MapCell>, to: Node<MapCell>, weight: Int) {
        // if there is no such edge yet
        if(pathGraph.getEdge(from, to) == null) {
            pathGraph.addEdge(from, to, weight)
        }
    }

    private fun getPathFromNodesList(list: List<Node<*>>): Path {
        return Path().apply {
            list.forEach {
                this.addNextCell(cellList[it.id])
            }
        }
    }
}