import kotlinx.cinterop.*
import libncurses.*

import ru.tetraquark.pathfinderlib.core.TestHello
import ru.tetraquark.pathfinderlib.core.map.CellType
import ru.tetraquark.pathfinderlib.core.map.Path
import ru.tetraquark.pathfinderlib.core.map.WorldMap
import ru.tetraquark.pathfinderlib.presentation.main.MainContract
import ru.tetraquark.pathfinderlib.presentation.main.MainPresenter
import ru.tetraquark.pathfinderlib.presentation.main.RoutingAlgorithm

fun main() {
    println(TestHello().multiplatformHello())

    /*
    // Hello world example

    initscr()
    printw("Input width: \n")

    var width = 0
    memScoped {
        val widthVar = alloc<IntVar>()
        scanw("%d", widthVar.ptr)
        width = widthVar.value
        println("1 width = $width")
    }

    println("2 width = $width")
    getch()
    endwin()
    */

    val app = CliApp(MainPresenter())
    app.startApp()
    getch()
    app.stopApp()
}

class CliApp(
    private val presenter: MainPresenter
) : MainContract.View {

    enum class AppState {
        SELECT_SIZE,
        GENERATE_MAP
    }

    private var appState: AppState = AppState.SELECT_SIZE
    private val availableAlgs = mutableListOf<RoutingAlgorithm>()

    private var inputMapWidth = 0
    private var inputMapHeight = 0
    private var selectedAlgorithm: RoutingAlgorithm = RoutingAlgorithm.WAVE

    private var textColorId = CellType.values().size + 1
    private var bordersColorId = CellType.values().size + 2
    private var startCellColorId = CellType.values().size + 3
    private var finishCellColorId = CellType.values().size + 4
    private var pathCellColorId = CellType.values().size + 5

    fun startApp() {
        initscr()

        presenter.attachView(this)
    }

    fun stopApp() {
        presenter.detachView()
        endwin()
    }

    override fun getInputMapWidth(): Int = inputMapWidth

    override fun getInputMapHeight(): Int = inputMapHeight

    override fun getSelectedAlgorithm(): RoutingAlgorithm = selectedAlgorithm

    override fun showHintForState(state: MainContract.AppState) {
    }

    override fun showAvailableRoutingAlgorithms(routingAlgorithms: List<RoutingAlgorithm>) {
        printStrL("Input width of the map (min 5, max 25):")
        memScoped {
            val widthVar = alloc<IntVar>()
            scanw("%d", widthVar.ptr)
            inputMapWidth = widthVar.value
        }

        printStrL("Input height of the map (min 5, max 25):")
        memScoped {
            val heightVar = alloc<IntVar>()
            scanw("%d", heightVar.ptr)
            inputMapHeight = heightVar.value
        }

        printStrL("Select algorithm:")
        routingAlgorithms.iterator().forEach {
            printStrL("${it.ordinal} : ${it.name}")
        }

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

    override fun enableGenerateAction() {}
    override fun disableGenerateAction() {}
    override fun enableClearAction() {}
    override fun disableClearAction() {}
    override fun setStartCell(point: Pair<Int, Int>) {
        attron(COLOR_PAIR(startCellColorId))
        mvprintw(point.second + 2, point.first + 2, "©")
        attron(COLOR_PAIR(startCellColorId))

        move(inputMapHeight + 2 + 4, 1)
        printStrL("Enter finish cell (format: \"x,y\"): ")

        refresh()

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

    override fun showProgress() {}
    override fun hideProgress() {}
    override fun showTime(time: Long) {}
    override fun showIterationsCount(iterations: Int) {}

    override fun drawMap(map: WorldMap) {
        clearMap()
        initColors()

        attron(COLOR_PAIR(textColorId))
        for(i in 0 until inputMapWidth) {
            mvprintw(0, i + 2, "$i")
        }
        for(i in 0 until inputMapHeight) {
            mvprintw(i + 2, 0, "$i")
        }
        attroff(COLOR_PAIR(textColorId))

        attron(COLOR_PAIR(bordersColorId))
        for(i in 1 until inputMapWidth + 2) {
            mvprintw(1, i, "*")
        }
        for(i in 1 until inputMapHeight + 2) {
            mvprintw(i, 1, "*")
        }
        attroff(COLOR_PAIR(bordersColorId))

        map.iterator().forEach { cell ->
            attron(COLOR_PAIR(cell.cellType.ordinal))
            if(cell.cellType == CellType.BLOCK) {
                mvprintw(cell.y + 2, cell.x + 2, "#")
            } else if (cell.cellType == CellType.OPEN) {
                mvprintw(cell.y + 2, cell.x + 2, ".")
            }
            attroff(COLOR_PAIR(cell.cellType.ordinal))
        }

        move(inputMapHeight + 2 + 2, 1)
        printStrL("Enter start cell (format: \"x,y\"): ")

        refresh()

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
            if(index == pathLen - 1) {
                // finish cell
                attron(COLOR_PAIR(finishCellColorId))
                mvprintw(cell.y + 2, cell.x + 2, "@")
                attroff(COLOR_PAIR(finishCellColorId))

            } else if(index != 0) {
                // not start cell
                attron(COLOR_PAIR(pathCellColorId))
                mvprintw(cell.y + 2, cell.x + 2, "o")
                attroff(COLOR_PAIR(pathCellColorId))
            }
        }
        refresh()
    }

    override fun clearMap() {
        clear()
        refresh()
    }

    override fun showError(text: String) {}

    private fun initColors() {
        init_pair(CellType.OPEN.ordinal.toShort(), COLOR_WHITE, COLOR_BLACK)
        init_pair(CellType.BLOCK.ordinal.toShort(), COLOR_MAGENTA, COLOR_BLACK)
        init_pair(textColorId.toShort(), COLOR_WHITE, COLOR_BLACK)
        init_pair(bordersColorId.toShort(), COLOR_YELLOW, COLOR_BLACK)
        init_pair(startCellColorId.toShort(), COLOR_GREEN, COLOR_BLACK)
        init_pair(finishCellColorId.toShort(), COLOR_YELLOW, COLOR_BLACK)
        init_pair(pathCellColorId.toShort(), COLOR_BLUE, COLOR_BLACK)
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