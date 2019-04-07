package ru.tetraquark.pathfinderlib.core.map


abstract class Map : Iterable<MapCell> {

    var width = 0
        protected set
    var height = 0
        protected set

}