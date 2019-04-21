package ru.tetraquark.pathfinderlib.core.map

class Path : Iterable<MapCell> {

    private var pathCoordinatesList: MutableList<MapCell> = mutableListOf()

    fun addNextCell(cell: MapCell) {
        pathCoordinatesList.add(cell)
    }

    fun getStartCell(): MapCell? = pathCoordinatesList.firstOrNull()

    fun getFinishCell(): MapCell? = pathCoordinatesList.lastOrNull()

    fun size(): Int = this.count()

    override fun iterator(): Iterator<MapCell> =
        pathCoordinatesList.iterator()

    override fun toString(): String = pathCoordinatesList.toString()

    operator fun contains(cell: MapCell?): Boolean =
        cell in pathCoordinatesList
}