package ru.tetraquark.pathfinderlib.core.map.impl

import ru.tetraquark.pathfinderlib.core.map.AlgorithmVisualization
import ru.tetraquark.pathfinderlib.core.map.MapCell

class WaveAlgorithmVisualization(private val iterations: List<Map<MapCell, Int>>) : AlgorithmVisualization {

    private var currentIteration = 0

    fun nextIteration(): Map<MapCell, Int> {
        if (currentIteration == iterations.size)
            currentIteration = 0
        return iterations[currentIteration++]
    }
}