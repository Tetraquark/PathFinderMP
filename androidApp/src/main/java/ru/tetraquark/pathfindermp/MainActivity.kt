package ru.tetraquark.pathfindermp

import android.graphics.Point
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TableRow
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import ru.tetraquark.pathfinderlib.core.TestHello
import ru.tetraquark.pathfinderlib.core.map.CellType
import ru.tetraquark.pathfinderlib.core.map.Path
import ru.tetraquark.pathfinderlib.core.map.WorldMap
import ru.tetraquark.pathfinderlib.presentation.main.MainContract
import ru.tetraquark.pathfinderlib.presentation.main.MainPresenter
import ru.tetraquark.pathfinderlib.presentation.main.RoutingAlgorithm

class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var presenter: MainContract.Presenter

    private var startCell: Pair<Int, Int>? = null

    private val gridViews = mutableListOf<View>()
    private var gridMapSize: Point = Point(0, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(TestHello().multiplatformHello())

        presenter = MainPresenter()

        map_table.viewTreeObserver.addOnGlobalLayoutListener {
            gridMapSize = Point(map_table.width, map_table.height)
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        presenter.detachView()
        super.onStop()
    }

    override fun showHintForState(state: MainContract.AppState) {
        when (state) {
            MainContract.AppState.GENERATE_MAP -> {
                text_hint.text = getString(R.string.hint_generate_map)
            }
            MainContract.AppState.SELECT_FINISH -> {
                text_hint.text = getString(R.string.hint_select_finish)
            }
            MainContract.AppState.FIND_ROUTE_PROGRESS -> {
                text_hint.text = getString(R.string.hint_in_progress)
            }
            MainContract.AppState.SHOWING_RESULTS -> {
                text_hint.text = getString(R.string.hint_clear)
            }
        }
    }

    override fun enableGenerateAction() {
        button.isEnabled = true
        button.setOnClickListener {
            presenter.onGenerateAction()
        }
        button.text = getString(R.string.generate)
    }

    override fun disableGenerateAction() {
        button.isEnabled = false
    }

    override fun enableClearAction() {
        button.isEnabled = true
        button.setOnClickListener {
            presenter.onClearAction()
        }
        button.text = getString(R.string.clear)
    }

    override fun disableClearAction() {
        button.isEnabled = false
    }

    override fun showProgress() {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setStartCell(point: Pair<Int, Int>) {
        startCell = point
        changeCellColor(
            gridViews[point.first + map_table.columnCount * point.second],
            getColorForCellType(CellType.OPEN, isStart = true, isFinis = false, isPath = false, isVisited = false)
        )
    }

    override fun getInputMapWidth(): Int =
        input_width.text.toString().toInt()

    override fun getInputMapHeight(): Int =
        input_height.text.toString().toInt()

    override fun getSelectedAlgorithm(): RoutingAlgorithm =
        algorithms_spinner.selectedItem as RoutingAlgorithm

    override fun showAvailableRoutingAlgorithms(routingAlgorithms: List<RoutingAlgorithm>) {
        val adapter =
            ArrayAdapter<RoutingAlgorithm>(this, android.R.layout.simple_spinner_item, routingAlgorithms).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        algorithms_spinner.adapter = adapter

        if (routingAlgorithms.isNotEmpty()) {
            algorithms_spinner.setSelection(0)
        }
    }

    override fun showTime(time: Long) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showIterationsCount(iterations: Int) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun drawVisitedCell(point: Pair<Int, Int>) {
        val color = getColorForCellType(CellType.OPEN, false, false, false, true)
        changeCellColor(gridViews[point.first + map_table.columnCount * point.second], color)
    }

    override fun drawFinishCell(point: Pair<Int, Int>) {
        val color = getColorForCellType(CellType.OPEN, false, true, false, false)
        changeCellColor(gridViews[point.first + map_table.columnCount * point.second], color)
    }

    override fun drawMap(map: WorldMap) {
        map_table.columnCount = map.width
        map_table.rowCount = map.height
        val cellLayoutParams =
            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT).apply {
                this.width = gridMapSize.x / map.width
                this.height = gridMapSize.y / map.height
            }

        map.forEach { mapCell ->
            val cell = createCell(mapCell.cellType, isStart = false, isFinis = false, isPath = false, isVisited = false)
            cell.layoutParams = cellLayoutParams
            cell.setOnClickListener {
                presenter.onCellClick(Pair(mapCell.x, mapCell.y))
            }
            gridViews.add(cell)
            map_table.addView(cell)
        }
    }

    override fun drawPath(path: Path) {
        val pathLen = path.count()
        path.forEachIndexed { index, mapCell ->
            val color = if (index == 0) {
                getColorForCellType(CellType.OPEN, isStart = true, isFinis = false, isPath = false, isVisited = false)
            } else if (index == pathLen - 1) {
                getColorForCellType(CellType.OPEN, isStart = false, isFinis = true, isPath = false, isVisited = false)
            } else {
                getColorForCellType(CellType.OPEN, isStart = false, isFinis = false, isPath = true, isVisited = false)
            }
            changeCellColor(gridViews[mapCell.x + map_table.columnCount * mapCell.y], color)
        }
    }

    override fun clearMap() {
        gridViews.clear()
        map_table.removeAllViews()
    }

    override fun showError(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    private fun createCell(cellType: CellType, isStart: Boolean, isFinis: Boolean, isPath: Boolean, isVisited: Boolean): View {
        val color = getColorForCellType(cellType, isStart, isFinis, isPath, isVisited)

        val shape = ContextCompat.getDrawable(this, R.drawable.cell_shape) as GradientDrawable
        shape.setColor(color)

        return View(this).apply {
            background = shape
        }
    }

    private fun getColorForCellType(
        cellType: CellType,
        isStart: Boolean,
        isFinis: Boolean,
        isPath: Boolean,
        isVisited: Boolean
    ): Int =
        when (cellType) {
            CellType.OPEN -> when {
                isStart -> ContextCompat.getColor(this, R.color.cell_start)
                isFinis -> ContextCompat.getColor(this, R.color.cell_finish)
                isPath -> ContextCompat.getColor(this, R.color.cell_path)
                isVisited -> ContextCompat.getColor(this, R.color.cell_visited)
                else -> ContextCompat.getColor(this, R.color.cell_open)
            }
            CellType.BLOCK -> ContextCompat.getColor(this, R.color.cell_block)
        }

    private fun changeCellColor(view: View, color: Int) {
        val shape = ContextCompat.getDrawable(this, R.drawable.cell_shape) as GradientDrawable
        shape.setColor(color)
        view.background = shape
    }
}
