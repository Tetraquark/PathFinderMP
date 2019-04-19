package ru.tetraquark.pathfinderlib.core.pathfinder.algorithmresults

import ru.tetraquark.pathfinderlib.core.graph.Node
import ru.tetraquark.pathfinderlib.core.pathfinder.AlgorithmResult

class WaveAlgorithmResult(override val path: List<Node<*>>,val iterationResultsList: List<Map<Int, Int>>) : AlgorithmResult