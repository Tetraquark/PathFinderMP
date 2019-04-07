package ru.tetraquark.pathfinderlib.core.graph

interface UniqueIdFactory<IdT> {

    fun getUniqueId(): IdT

}