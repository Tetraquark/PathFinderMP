package ru.tetraquark.pathfinderlib.core.map

import ru.tetraquark.pathfinderlib.core.graph.MutableGraph

interface MapGenerator {

    fun generateMap(width: Int, height: Int, pathGraph: MutableGraph<MapCell, Int>): WorldMap?

}