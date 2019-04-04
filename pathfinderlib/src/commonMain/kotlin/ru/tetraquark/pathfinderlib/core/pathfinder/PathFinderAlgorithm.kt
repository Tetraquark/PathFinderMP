package ru.tetraquark.pathfinderlib.core

import ru.tetraquark.pathfinderlib.core.map.Map
import ru.tetraquark.pathfinderlib.core.map.Path

interface PathFinderAlgorithm {

    fun findPath(map: Map): Path

}