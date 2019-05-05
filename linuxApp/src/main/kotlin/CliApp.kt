package ru.tetraquark.pathfindermp.linuxapp

import kotlinx.cinterop.*
import libncurses.*

import ru.tetraquark.pathfinderlib.core.map.CellType
import ru.tetraquark.pathfinderlib.core.map.Path
import ru.tetraquark.pathfinderlib.core.map.WorldMap
import ru.tetraquark.pathfinderlib.presentation.main.MainContract
import ru.tetraquark.pathfinderlib.presentation.main.MainPresenter
import ru.tetraquark.pathfinderlib.presentation.main.RoutingAlgorithm

internal class CliApp(private val presenter: MainPresenter) : MainContract.View {

    companion object {
        const val MAX_WIDTH = 25
        const val MIN_WIDTH = 5
        const val MAX_HEIGHT = 25
        const val MIN_HEIGHT = 5

        private const val WIDTH_OFFSET = 5
        private const val HEIGHT_OFFSET = 5
    }

    private var inputMapWidth = 0
    private var inputMapHeight = 0
    private var availableAlgs: List<RoutingAlgorithm> = emptyList()
    private var selectedAlgorithm: RoutingAlgorithm = RoutingAlgorithm.WAVE

    private var textColorId = CellType.values().size + 1
    private var bordersColorId = CellType.values().size + 2
    private var startCellColorId = CellType.values().size + 3
    private var finishCellColorId = CellType.values().size + 4
    private var pathCellColorId = CellType.values().size + 5

    fun startApp() {
        enableWin()
        presenter.attachView(this)
    }

    fun stopApp() {
        presenter.detachView()
        disableWin()
    }

    override fun getInputMapWidth(): Int = inputMapWidth

    override fun getInputMapHeight(): Int = inputMapHeight

    override fun getSelectedAlgorithm(): RoutingAlgorithm = selectedAlgorithm

    override fun showHintForState(state: MainContract.AppState) {
    }

    override fun enableGenerateAction() {
        enableInputCursor()
        printStrL("Input width of the map (min $MIN_WIDTH, max $MAX_WIDTH):")
        memScoped {
            val widthVar = alloc<IntVar>()
            scanw("%d", widthVar.ptr)

            inputMapWidth = if (widthVar.value < MIN_WIDTH) {
                MIN_WIDTH
            } else if (widthVar.value > MAX_WIDTH) {
                MAX_WIDTH
            } else {
                widthVar.value
            }
        }

        printStrL("Input height of the map (min $MIN_HEIGHT, max $MAX_HEIGHT):")
        memScoped {
            val heightVar = alloc<IntVar>()
            scanw("%d", heightVar.ptr)
            inputMapHeight = if (heightVar.value < MIN_HEIGHT) {
                MIN_HEIGHT
            } else if (heightVar.value > MAX_HEIGHT) {
                MAX_HEIGHT
            } else {
                heightVar.value
            }
        }
    }

    override fun showAvailableRoutingAlgorithms(routingAlgorithms: List<RoutingAlgorithm>) {
        availableAlgs = routingAlgorithms
        clearWin()
        refreshWin()

        disableInputCursor()
        printEnableAlgorithms()
        enableInputCursor()

        move(availableAlgs.size + 2, 1)

        printStrL("Select algorithm (enter index of the algorithm):")

        var algIndex = 0
        memScoped {
            val algVar = alloc<IntVar>()
            scanw("%d", algVar.ptr)
            algIndex = algVar.value
        }

        selectedAlgorithm = RoutingAlgorithm.values().get(algIndex)

        disableInputCursor()
        start_color()

        presenter.onGenerateAction()
    }

    override fun setStartCell(point: Pair<Int, Int>) {
        withColor(startCellColorId) {
            drawStr(point.first * 2 + WIDTH_OFFSET + 2, point.second + HEIGHT_OFFSET + 2, " ©")
        }

        move(inputMapHeight + HEIGHT_OFFSET + 6, 1)
        printStrL("Enter finish cell (format: \"x,y\"): ")

        refreshWin()

        enableInputCursor()

        var finishCellX = 0
        var finishCellY = 0
        memScoped {
            val finishCellXVar = alloc<IntVar>()
            val finishCellYVar = alloc<IntVar>()
            scanw("%d,%d", finishCellXVar.ptr, finishCellYVar.ptr)
            finishCellX = finishCellXVar.value
            finishCellY = finishCellYVar.value
        }
        disableInputCursor()

        presenter.onCellClick(Pair(finishCellX, finishCellY))
    }

    override fun drawMap(map: WorldMap) {
        clearMap()
        printSelectedAlgrotihm()
        initColors()

        // draws text with coordinate value
        withColor(textColorId) {
            for (i in 0 until inputMapWidth) {
                drawStr(i * 2 + WIDTH_OFFSET + 2, HEIGHT_OFFSET, "$i ")
            }
            for (i in 0 until inputMapHeight) {
                drawStr(WIDTH_OFFSET - 2, i + HEIGHT_OFFSET + 2, "$i")
            }
        }

        // draws borders symbols
        withColor(bordersColorId) {
            for (i in 1 until inputMapWidth + 1) {
                drawStr(i * 2 + WIDTH_OFFSET, HEIGHT_OFFSET + 1, "* ")
            }
            for (i in 1 until inputMapHeight + 1) {
                drawStr(WIDTH_OFFSET + 1, i + HEIGHT_OFFSET + 1, "*")
            }
        }

        // draws map
        map.forEach { cell ->
            withColor(cell.cellType.ordinal) {
                if (cell.cellType == CellType.BLOCK) {
                    drawChar(cell.x * 2 + WIDTH_OFFSET + 2, cell.y + HEIGHT_OFFSET + 2, ACS_CKBOARD)
                    drawChar(cell.x * 2 + WIDTH_OFFSET + 3, cell.y + HEIGHT_OFFSET + 2, ACS_CKBOARD)
                } else if (cell.cellType == CellType.OPEN) {
                    drawStr(cell.x * 2 + WIDTH_OFFSET + 2, cell.y + HEIGHT_OFFSET + 2, "..")
                }
            }
        }

        move(inputMapHeight + HEIGHT_OFFSET + 4, 1)
        printStrL("Enter start cell (format: \"x,y\"): ")

        enableInputCursor()

        var startCellX = 0
        var startCellY = 0
        memScoped {
            val startCellXVar = alloc<IntVar>()
            val startCellYVar = alloc<IntVar>()
            scanw("%d,%d", startCellXVar.ptr, startCellYVar.ptr)
            startCellX = startCellXVar.value
            startCellY = startCellYVar.value
        }
        disableInputCursor()

        presenter.onCellClick(Pair(startCellX, startCellY))
    }

    override fun drawPath(path: Path) {
        val pathLen = path.size()
        path.forEachIndexed { index, cell ->
            if (index == pathLen - 1) {
                withColor(finishCellColorId) {
                    drawStr(cell.x * 2 + WIDTH_OFFSET + 2, cell.y + HEIGHT_OFFSET + 2, " @")
                }
                // finish cell

            } else if (index != 0) {
                // not start cell
                withColor(pathCellColorId) {
                    drawStr(cell.x * 2 + WIDTH_OFFSET + 2, cell.y + HEIGHT_OFFSET + 2, " o")
                }
            }
        }

        move(inputMapHeight + HEIGHT_OFFSET + 8, 1)
        printStrL("Press any key to restart.")

        refreshWin()

        getch()
        presenter.onClearAction()
    }

    override fun clearMap() {
        clearWin()
        refreshWin()
    }

    override fun disableGenerateAction() = Unit
    override fun enableClearAction() = Unit
    override fun disableClearAction() = Unit
    override fun showProgress() = Unit
    override fun hideProgress() = Unit
    override fun showTime(time: Long) = Unit
    override fun showIterationsCount(iterations: Int) = Unit
    override fun showError(text: String) = Unit

    private fun initColors() {
        init_pair(CellType.OPEN.ordinal.toShort(), COLOR_WHITE, COLOR_BLACK)
        init_pair(CellType.BLOCK.ordinal.toShort(), COLOR_MAGENTA, COLOR_BLACK)
        init_pair(textColorId.toShort(), COLOR_WHITE, COLOR_BLACK)
        init_pair(bordersColorId.toShort(), COLOR_YELLOW, COLOR_BLACK)
        init_pair(startCellColorId.toShort(), COLOR_GREEN, COLOR_BLACK)
        init_pair(finishCellColorId.toShort(), COLOR_YELLOW, COLOR_BLACK)
        init_pair(pathCellColorId.toShort(), COLOR_BLUE, COLOR_BLACK)
    }

    private fun printEnableAlgorithms() {
        drawStr(WIDTH_OFFSET + inputMapWidth + 2, 0, "Available algorithms:")
        availableAlgs.forEachIndexed { i, alg ->
            drawStr(WIDTH_OFFSET + inputMapWidth + 2, i + 1, "${alg.ordinal}) ${alg.name}")
        }
    }

    private fun printSelectedAlgrotihm() {
        drawStr(WIDTH_OFFSET + inputMapWidth + 2, 0, "Selected algorithm: ${selectedAlgorithm.name}")
    }

    private fun enableInputCursor() {
        echo()
        curs_set(1)
    }

    private fun disableInputCursor() {
        noecho()
        curs_set(0)
    }
}