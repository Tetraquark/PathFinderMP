package ru.tetraquark.pathfinderlib.core.map

interface MapAdapter<PathWeightT> {

    fun getWidth(): Int

    fun getHeight(): Int

    fun getCellType(x: Int, y: Int): CellType

    fun getPathWeight(fromX: Int, fromY: Int, toX: Int, toY: Int): PathWeightT
}