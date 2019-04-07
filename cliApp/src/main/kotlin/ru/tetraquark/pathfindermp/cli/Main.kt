package ru.tetraquark.pathfindermp.cli

import ru.tetraquark.pathfinderlib.core.TestHello
import ru.tetraquark.pathfinderlib.core.graph.impl.SimpleGraph

fun main(args: Array<String>) {
    println(TestHello().multiplatformHello())
    println("1: add nodes")
    val g = SimpleGraph<String, Int>()
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
    if (from != null && to != null)
        g.addEdge(from, to, 2)
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