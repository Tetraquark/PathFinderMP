package ru.tetraquark.pathfinderlib.core.map

abstract class MapAdapter<PathWeightT> {

    abstract fun getWidth(): Int

    abstract fun getHeight(): Int

    abstract fun getCellType(x: Int, y: Int): CellType

    abstract fun getPathWeight(fromX: Int, fromY: Int, toX: Int, toY: Int): PathWeightT
}