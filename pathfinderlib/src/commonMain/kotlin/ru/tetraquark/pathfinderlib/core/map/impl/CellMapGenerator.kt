package ru.tetraquark.pathfinderlib.core.map.impl

import ru.tetraquark.pathfinderlib.core.graph.MutableGraph
import ru.tetraquark.pathfinderlib.core.map.*
import kotlin.random.Random

class CellMapGenerator : MapGenerator {

    override fun generateMap(width: Int, height: Int, pathGraph: MutableGraph<MapCell>): WorldMap? {
        if (width < 2 || height < 2)
            return null

        val cellsNum = width * height
        var blockCellsNum = (cellsNum * 0.2f).toInt()
        val cellList = mutableListOf<CellType>()

        for (i in 0 until cellsNum) {
            var cellType = CellType.OPEN
            if (blockCellsNum > 0) {
                if (Random.nextInt(0, 10) < 2) {
                    cellType = CellType.BLOCK
                    blockCellsNum--
                }
            }
            cellList.add(cellType)
        }

        val adapter = object : MapAdapter<Int> {
            override fun getWidth(): Int = width

            override fun getHeight(): Int = height

            override fun getCellType(x: Int, y: Int): CellType = cellList[x + y * width]

            override fun getPathWeight(fromX: Int, fromY: Int, toX: Int, toY: Int): Int = 1
        }

        return CellWorldMap(pathGraph, adapter)
    }
}