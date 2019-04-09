package ru.tetraquark.pathfindermp.cli

import ru.tetraquark.pathfinderlib.core.TestHello
import ru.tetraquark.pathfinderlib.core.graph.UniqueIdFactory
import ru.tetraquark.pathfinderlib.core.graph.impl.SimpleGraph
import ru.tetraquark.pathfinderlib.core.map.*
import ru.tetraquark.pathfinderlib.core.map.Map
import ru.tetraquark.pathfinderlib.core.map.impl.CellMap
import ru.tetraquark.pathfinderlib.core.pathfinder.algorithms.WaveAlgorithm

fun main(args: Array<String>) {
    val cliApp = CliApp()

    cliApp.multiplatformTest()
    //cliApp.tests_1()
    //cliApp.tests_2()
    println(" ")
    cliApp.drawMap(cliApp.testMap)
    println()
    cliApp.tests_3()
}

class CliApp {
    val testMap: Map<Int, MapCell, Int, Int>

    private var nodesCounter = 0
    private var edgesCounter = 0

    private val nodesIdFactory = object : UniqueIdFactory<Int> {
        override fun getUniqueId(): Int = nodesCounter++
    }

    private val edgesIdFactory = object : UniqueIdFactory<Int> {
        override fun getUniqueId(): Int = edgesCounter++
    }

    init {
        testMap = createMap()
    }

    fun multiplatformTest() {
        println(TestHello().multiplatformHello())
    }

    fun tests_1() {
        val g = SimpleGraph<Int, String, Int, Int>(nodesIdFactory, edgesIdFactory)
        println("1: add nodes")
        g.addNode("firstNode")
        g.addNode("secondNode")
        g.addNode("thirdNode")
        g.addNode("forthNode")
        println("graph $g")
        println("nodes ${g.getNodes()}")
        println("edges ${g.getEdges()}")
        println("2: add edges")
        var from = g.getNode(1)
        var to = g.getNode(2)
        if (from != null && to != null)
            g.addEdge(from, to, 1)

        from = g.getNode(2)
        to = g.getNode(3)
        if (from != null && to != null) {
            g.addEdge(from, to, 2)
        }
        println("graph $g")
        println("nodes ${g.getNodes()}")
        println("edges ${g.getEdges()}")

        println("3: remove node 1")
        g.removeNode(1)
        println("graph $g")
        println("nodes ${g.getNodes()}")
        println("edges ${g.getEdges()}")
        println("4: remove node 2")
        g.removeNode(2)
        println("graph $g")
        println("nodes ${g.getNodes()}")
        println("edges ${g.getEdges()}")
    }

    fun tests_2() {
        val g = SimpleGraph<Int, String, Int, Int>(nodesIdFactory, edgesIdFactory)
        println("1: add nodes")
        g.addNode("firstNode")
        g.addNode("secondNode")
        g.addNode("thirdNode")
        g.addNode("forthNode")
        println("graph $g")
        println("nodes ${g.getNodes()}")
        println("edges ${g.getEdges()}")
        println("2: add edges")
        var from = g.getNode(4)
        var to = g.getNode(5)
        if (from != null && to != null)
            g.addEdge(from, to, 1)

        from = g.getNode(5)
        to = g.getNode(6)
        if (from != null && to != null) {
            g.addEdge(from, to, 1)
        }

        from = g.getNode(5)
        to = g.getNode(7)
        if (from != null && to != null) {
            g.addEdge(from, to, 1)
        }

        from = g.getNode(6)
        to = g.getNode(7)
        if (from != null && to != null) {
            g.addEdge(from, to, 1)
        }
        println("graph $g")
        println("nodes ${g.getNodes()}")
        println("edges ${g.getEdges()}")

        val alg = WaveAlgorithm<Int, String, Int, Int>()
        println("3: find path from 4 to 7")
        val path = alg.findPath(g, 4, 7)
        println("path $path")
    }

    fun drawMap(map: Map<Int, MapCell, Int, Int>) {
        val it = map.iterator()
        while (it.hasNext()) {
            val cell = it.next()
            var cellSymbol = "o"
            if(cell.cellType == CellType.BLOCK) {
                cellSymbol = "#"
            }
            print(cellSymbol)
            if(cell.x == map.width - 1) {
                print("\n")
            }
        }
    }

    fun drawMap(map: Map<Int, MapCell, Int, Int>, path: Path<MapCell>) {
        val it = map.iterator()
        while (it.hasNext()) {
            val cell = it.next()
            var cellSymbol = "o"
            when {
                cell.cellType == CellType.BLOCK -> cellSymbol = "#"
                cell == map.getStartCell() -> cellSymbol = "X"
                cell == map.getFinishCell() -> cellSymbol = "+"
                cell in path.waypoints -> cellSymbol = "."
            }
            print(cellSymbol)
            if(cell.x == map.width - 1) {
                print("\n")
            }
        }
    }

    private fun createMap(): Map<Int, MapCell, Int, Int> {
        return CellMap(SimpleGraph(nodesIdFactory, edgesIdFactory)).apply {
            val adapter = object : MapAdapter() {

                override fun getCellType(x: Int, y: Int): CellType {
                    if(x == 2 && y == 1)
                        return CellType.BLOCK
                    if(x == 3 && y == 1)
                        return CellType.BLOCK
                    if(x == 2 && y == 2)
                        return CellType.BLOCK
                    if(x == 3 && y == 2)
                        return CellType.BLOCK
                    if(x == 0 && y == 3)
                        return CellType.BLOCK
                    if(x == 1 && y == 4)
                        return CellType.BLOCK
                    if(x == 4 && y == 4)
                        return CellType.BLOCK
                    if(x == 5 && y == 4)
                        return CellType.BLOCK

                    return CellType.OPEN
                }

                override fun getHeight(): Int = 6
                override fun getWidth(): Int = 7
            }
            reloadMap(adapter)
        }
    }

    fun tests_3() {
        val alg = WaveAlgorithm<Int, MapCell, Int, Int>()
        println("3: find path from [0,0] to [6,5]")
        testMap.setStartCell(0, 0)
        testMap.setFinishCell(6, 5)
        val path = alg.findPath(testMap)
        println("path ${path.waypoints}")
        drawMap(testMap, path)
    }

}



