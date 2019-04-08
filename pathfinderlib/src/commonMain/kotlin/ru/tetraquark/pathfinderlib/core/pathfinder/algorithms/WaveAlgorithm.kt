package ru.tetraquark.pathfinderlib.core.pathfinder.algorithms

import ru.tetraquark.pathfinderlib.core.graph.Graph
import ru.tetraquark.pathfinderlib.core.graph.Node
import ru.tetraquark.pathfinderlib.core.map.Map
import ru.tetraquark.pathfinderlib.core.map.Path
import ru.tetraquark.pathfinderlib.core.pathfinder.PathFinderAlgorithm

class WaveAlgorithm<NodeIdT, NodeDataT, EdgeIdT, EdgeWeightT> : PathFinderAlgorithm<NodeIdT, NodeDataT, EdgeIdT, EdgeWeightT> {

    override fun findPath(graph: Map): Path {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findPath(graph: Graph<NodeIdT, NodeDataT, EdgeIdT, EdgeWeightT>, startId: NodeIdT, finishId: NodeIdT): List<Node<NodeIdT, NodeDataT>> {
        val path = ArrayList<Node<NodeIdT, NodeDataT>>()

        val markT = HashMap<NodeIdT, Int>()

        val oldFront = ArrayList<Node<NodeIdT, NodeDataT>>()
        val newFront = ArrayList<Node<NodeIdT, NodeDataT>>()

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