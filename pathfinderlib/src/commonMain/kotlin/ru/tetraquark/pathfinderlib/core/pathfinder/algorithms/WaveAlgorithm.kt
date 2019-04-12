package ru.tetraquark.pathfinderlib.core.pathfinder.algorithms

import ru.tetraquark.pathfinderlib.core.graph.Graph
import ru.tetraquark.pathfinderlib.core.graph.Node
import ru.tetraquark.pathfinderlib.core.map.Map
import ru.tetraquark.pathfinderlib.core.map.MapCell
import ru.tetraquark.pathfinderlib.core.map.Path
import ru.tetraquark.pathfinderlib.core.pathfinder.PathFinderAlgorithm

class WaveAlgorithm<NodeDataT, EdgeWeightT> : PathFinderAlgorithm<NodeDataT, EdgeWeightT> {

    override fun findPath(map: Map<NodeDataT, EdgeWeightT>): Path<NodeDataT> {
        println("findPath started")
        val startCell = map.getStartCell()
        val finishCell = map.getFinishCell()
        println("start $startCell finish $finishCell")
        if (startCell != null && finishCell != null) {
            println("points OK")
            val g = map.getGraph()
            var startId: Int? = null
            var finishId: Int? = null
            for (node in g.getNodes()) {
                if (node.value.data == startCell)
                    startId = node.key
                if (node.value.data == finishCell)
                    finishId = node.key
            }
            println("sid $startId fid $finishId")
            if (startId != null && finishId != null) {
                println("graph ids OK")
                val path = findPath(g, startId, finishId)
                println("path $path")
                val wp = ArrayList<NodeDataT>()
                for (node in path)
                    wp.add(node.data)
                return Path(wp)
            }
        }
        return Path(listOf())
    }

    override fun findPath(graph: Graph<NodeDataT, EdgeWeightT>, startId: Int, finishId: Int): List<Node<NodeDataT>> {
        val path = ArrayList<Node<NodeDataT>>()

        val markT = HashMap<Int, Int>()

        val oldFront = ArrayList<Node<NodeDataT>>()
        val newFront = ArrayList<Node<NodeDataT>>()

        var t = 0
        var curNode = graph.getNode(startId)
        val finishNode = graph.getNode(finishId)

        if (curNode == null || finishNode == null)
            return path

        for (node in graph.getNodes()) {
            markT[node.value.id] = -1
        }

        markT[curNode.id] = 0
        oldFront.add(curNode)

        var found = false
        do {

            for (node in oldFront) {
                for (edge in graph.getEdgesOfNode(node)) {
                    curNode = if (edge.getFrom() == node) edge.getTo() else edge.getFrom()
                    if (markT[curNode.id] == -1) {
                        markT[curNode.id] = t + 1
                        newFront.add(curNode)
                    }
                }
            }

            if (newFront.isEmpty())
                break

            if (finishNode in newFront)
                found = true

            oldFront.clear()
            oldFront.addAll(newFront)
            newFront.clear()

            t++

        } while (!found)

        if (found) {
            curNode = finishNode
            t = markT[curNode.id]!! - 1
            path.add(curNode)
            while (t >= 0) {
                if (curNode == null) return path
                val edges = graph.getEdgesOfNode(curNode)
                for (edge in edges) {
                    val neighbour = if (edge.getFrom() == curNode) edge.getTo() else edge.getFrom()
                    if (markT[neighbour.id] == t) {
                        path.add(neighbour)
                        t--
                        curNode = neighbour
                    }
                }
            }
        }
        return path.reversed()
    }

}