package ru.tetraquark.pathfinderlib.core.map

abstract class MapAdapter {

    abstract fun getWidth(): Int
    abstract fun getHeight(): Int
    abstract fun getCellType(x: Int, y: Int): CellType

}