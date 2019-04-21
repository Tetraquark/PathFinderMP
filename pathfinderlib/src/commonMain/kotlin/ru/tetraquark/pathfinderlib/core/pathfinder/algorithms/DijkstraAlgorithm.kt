package ru.tetraquark.pathfinderlib.core.pathfinder.algorithms

import ru.tetraquark.pathfinderlib.core.graph.Graph
import ru.tetraquark.pathfinderlib.core.graph.Node
import ru.tetraquark.pathfinderlib.core.pathfinder.PathFinderAlgorithm

class DijkstraAlgorithm : PathFinderAlgorithm {

    override fun findPath(
        graph: Graph<*>,
        startNode: Node<*>,
        finishNode: Node<*>
    ): List<Node<*>> {
        val notVisitedSet = graph.getNodes().values.toMutableSet()
        val distances = mutableMapOf<Node<*>, DijkstraNode>()

        distances[startNode] = DijkstraNode(null, 0)

        while (true) {
            var node: Node<*>? = null
            var minCost = Int.MAX_VALUE
            notVisitedSet.forEach {
                val nodeCost = distances[it]?.cost
                if (nodeCost != null && nodeCost < minCost) {
                    node = it
                    minCost = nodeCost
                }
            }

            val _node = node ?: return emptyList()
            if (_node == finishNode)
                break

            graph.getEdgesOfNode(_node).forEach { edge ->
                val edgeCost = distances[_node]?.cost!! + edge.weight
                if (!distances.containsKey(edge.to) || distances[edge.to]?.cost!! > edgeCost) {
                    distances[edge.to] = DijkstraNode(_node, edgeCost)
                }
            }
            notVisitedSet.remove(_node)
        }

        val pathList = mutableListOf<Node<*>>()
        var endNode: Node<*>? = finishNode
        while (endNode != null) {
            pathList.add(endNode)
            endNode = distances[endNode]?.prev
        }
        return pathList.reversed()
    }

    private data class DijkstraNode(
        val prev: Node<*>?,
        val cost: Int
    )

}