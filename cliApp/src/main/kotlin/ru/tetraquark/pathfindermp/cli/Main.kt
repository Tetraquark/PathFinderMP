package ru.tetraquark.pathfindermp.cli

import ru.tetraquark.pathfinderlib.core.TestHello
import ru.tetraquark.pathfinderlib.core.graph.UniqueIdFactory
import ru.tetraquark.pathfinderlib.core.graph.impl.SimpleGraph
import ru.tetraquark.pathfinderlib.core.map.CellType
import ru.tetraquark.pathfinderlib.core.map.Map
import ru.tetraquark.pathfinderlib.core.map.MapAdapter
import ru.tetraquark.pathfinderlib.core.map.impl.CellMap

fun main(args: Array<String>) {
    val cliApp = CliApp()

    cliApp.multiplatformTest()
    cliApp.tests_1()
    println(" ")
    cliApp.drawMap(cliApp.testMap)
}

class CliApp {
    val testMap: Map

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

    fun drawMap(map: Map) {
        val it = map.iterator()
        while (it.hasNext()) {
            val cell = it.next()
            var cellSymbol = "o"
            if(cell.cellType == CellType.BLOCK) {
                cellSymbol = "#"
            }
            print(cellSymbol)
            if(cell.x == map.width) {
                print("\n")
            }
        }
    }

    private fun createMap(): Map {
        return CellMap(SimpleGraph(nodesIdFactory, edgesIdFactory)).apply {
            adapter = object : MapAdapter() {

                override fun getCellType(x: Int, y: Int): CellType {
                    if(x == 2 && y == 1)
                        return CellType.BLOCK
                    if(x == 3 && y == 1)
                        return CellType.BLOCK
                    if(x == 2 && y == 2)
                        return CellType.BLOCK
                    if(x == 3 && y == 2)
                        return CellType.BLOCK
                    if(x == 1 && y == 3)
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
        }
    }

}



