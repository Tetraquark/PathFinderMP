//
//  ViewController.swift
//  PathFinderMPiOS
//
//  Created by Aleksandr Gusev on 17/04/2019.
//  Copyright © 2019 aleksandr.gusev. All rights reserved.
//

import UIKit
import main

class ViewController:   UIViewController, UIPickerViewDelegate,
    UIPickerViewDataSource, UICollectionViewDelegate,
UICollectionViewDataSource, MainContractView  {

    @IBOutlet weak var cellsInWidth: UITextField!
    @IBOutlet weak var cellsInHeight: UITextField!
    @IBOutlet weak var pickerTextField: UITextField!
    @IBOutlet weak var collectionView: UICollectionView!
    @IBOutlet weak var actionButton: UIButton!
    @IBOutlet weak var hintLabel: UILabel!
    
    /* Default cells count on collectionView */
    var cellsCount = 1

    var currentCellsWidth = 0
    var currentMap: WorldMap?
    var currentPath: Path?
    var startCellRowCoord: Int?
    var currentStartCell: Point?
    var currentFinishCell: Point?
    var visitedCells: Dictionary<Int, Point>?

    /* Available algorithms */
    var pickOption = ["Wave", "Dijkstra"]

    var presenter = MainPresenter()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        let pickerView = UIPickerView()
        pickerView.delegate = self as UIPickerViewDelegate
        pickerTextField.inputView = pickerView
        
        /* Show collection view borders */
        collectionView.dataSource = self
        collectionView.delegate = self
        collectionView.register(UICollectionViewCell.self, forCellWithReuseIdentifier: "basicCell")
        collectionView.backgroundColor = UIColor.darkGray
        collectionView.layer.borderColor = UIColor.darkGray.cgColor
        collectionView.layer.borderWidth = 1.0
    }
    
    override func viewDidAppear(_ animated: Bool) {
        cellsCount = (Int(cellsInWidth.text!)! * Int(cellsInHeight.text!)!)
        collectionView.reloadData()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        presenter.attachView(view: self)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        presenter.detachView()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    /* Setup count of elements in picker */
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return pickOption.count
    }
    
    /* setup name of each element */
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return pickOption[row]
    }
    
    /* Update text of textField while row has been choosing  */
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        pickerTextField.text = pickOption[row]
        self.view.endEditing(true)
    }
    
    /* Cells count */
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return cellsCount
    }

    /* Show collectionView borders */
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let selectedCell = collectionView.dequeueReusableCell(withReuseIdentifier: "basicCell", for: indexPath) as UICollectionViewCell

        // check cell in the map
        let cellCoords = fromIndexToCoords(index: indexPath.row, width: (Int(self.collectionView.frame.width) / currentCellsWidth))
        if let mapCell = currentMap?.getCell(x: Int32(cellCoords.x), y: Int32(cellCoords.y)) {
            var color = UIColor.white

            switch(mapCell.cellType) {
            case CellType.open:
                color = UIColor.white
                break
            case CellType.block:
                color = UIColor.darkGray
                break
            default:
                color = UIColor.white
                break
            }
            selectedCell.backgroundColor = color
        }
        if visitedCells?[indexPath.row] != nil {
            selectedCell.backgroundColor = UIColor.yellow
        }
        // check cell in the path
        if let path = currentPath {
            let it = path.iterator()
            var counter = 0

            while it.hasNext() {
                let mapCell: MapCell = it.next() as! MapCell
                if cellCoords.x == mapCell.x && cellCoords.y == mapCell.y {
                    let color = UIColor.cyan
                    selectedCell.backgroundColor = color
                }
                //if counter == path.size() - 1 {
                // then it is the finish cell
                //selectedCell.backgroundColor = UIColor.purple
                //}
                counter += 1
            }
        }

        if let startCell = currentStartCell {
            if cellCoords.x == startCell.x && cellCoords.y == startCell.y {
                selectedCell.backgroundColor = UIColor.green
            }
        }

        if let finishCell = currentFinishCell {
            if cellCoords.x == finishCell.x && cellCoords.y == finishCell.y {
                selectedCell.backgroundColor = UIColor.red
            }
        }

        return selectedCell
    }

    /* Save and setup cell coordinates */
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        var convertedPoint = (x: 0, y: 0)
        convertedPoint = fromIndexToCoords(index: indexPath.row, width: (Int(self.collectionView.frame.width) / currentCellsWidth)  )
        let point: KotlinPair = KotlinPair(first: convertedPoint.x, second: convertedPoint.y)
        presenter.onCellClick(point: point)
        startCellRowCoord = indexPath.row
    }

    func collectionView(_ collectionView: UICollectionView, willDisplay cell: UICollectionViewCell, forItemAt indexPath: IndexPath) {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "basicCell", for: indexPath) as UICollectionViewCell
        cell.isHidden = false
    }

    /* Logic */

    func drawVisitedCell(point: KotlinPair) {
        var convertedIndex = fromCoordsToIndex(x: point.first as! Int,
                                               y: point.second as! Int,
                                               width: (Int(self.collectionView.frame.width) / currentCellsWidth))
        var convertedPoint: Point = Point.init()
        convertedPoint.x = point.first as! Int
        convertedPoint.y = point.second as! Int
        if visitedCells != nil {
            visitedCells![convertedIndex] = convertedPoint
        }
        collectionView.reloadData()
    }

    func drawFinishCell(point: KotlinPair) {
        currentFinishCell = Point.init()
        currentFinishCell?.x = point.first as! Int
        currentFinishCell?.y = point.second as! Int
    }
    
    func getInputMapWidth() -> Int32 {
        return Int32(cellsInWidth.text!) ?? 0
    }
    
    func getInputMapHeight() -> Int32 {
        return Int32(cellsInHeight.text!) ?? 0
    }
    
    func getSelectedAlgorithm() -> RoutingAlgorithm {
        var algorithm = RoutingAlgorithm.wave
        for item in pickOption {
            switch (item) {
            case "Wave":
                algorithm = RoutingAlgorithm.wave
                break
            case "Dijkstra":
                algorithm = RoutingAlgorithm.dijkstra
                break
            default:
                algorithm = RoutingAlgorithm.wave
                break
            }
        }
        return algorithm
    }
    
    func showHintForState(state: MainContractAppState) {
        switch state {
        case MainContractAppState.generateMap:
            hintLabel.text = "Set params, generate map, select start cell"
        case MainContractAppState.selectFinish:
            hintLabel.text = "Select finish cell"
        case MainContractAppState.findRouteProgress:
            hintLabel.text = "The route search in progress"
        case MainContractAppState.showingResults:
            hintLabel.text = "Clear the map and repeat"
        default:
            hintLabel.text = "Set params, generate map, select start cell"
        }
    }
    
    func showAvailableRoutingAlgorithms(routingAlgorithms: [RoutingAlgorithm]) {
        /* TODO: Nothing to implement in iOS project?
         * This data has been stored in pickOption
         */

        // тут надо динамически заполнять pickOption и обновлять пикер
    }
    
    func enableGenerateAction() {
        actionButton.isEnabled = true
        actionButton.setTitle("Generate", for: .normal)
        actionButton.addTarget(self, action: #selector(onGenerateButtonAction), for: .touchUpInside)
    }

    func disableGenerateAction() {
        actionButton.isEnabled = false
        actionButton.removeTarget(nil, action: nil, for: .allEvents)
    }

    @objc func onGenerateButtonAction(sender: UIButton!) {
        cellsCount = (Int(cellsInWidth.text!)! * Int(cellsInHeight.text!)!)
        collectionView.reloadData()
        presenter.onGenerateAction()
    }
    
    func enableClearAction() {
        actionButton.isEnabled = true
        actionButton.setTitle("Clear", for: .normal)
        actionButton.addTarget(self, action: #selector(onClearButtonAction), for: .touchUpInside)
    }
    
    func disableClearAction() {
        actionButton.isEnabled = false
        actionButton.removeTarget(nil, action: nil, for: .allEvents)
    }

    @objc func onClearButtonAction(sender: UIButton!) {
        presenter.onClearAction()
        collectionView.reloadData()
    }
    
    func setStartCell(point: KotlinPair) {
        currentStartCell = Point.init()
        currentStartCell?.x = point.first as! Int
        currentStartCell?.y = point.second as! Int
        self.collectionView.reloadData()
    }
    
    func showProgress() {
        /* TODO: Implement later */
    }
    
    func hideProgress() {
        /* TODO: Implement later */
    }
    
    func showTime(time: Int64) {
        /* TODO: Implement later */
    }
    
    func showIterationsCount(iterations: Int32) {
        /* TODO: Implement later */
    }
    
    func drawMap(map: WorldMap) {
        visitedCells = Dictionary<Int, Point>.init()
        currentMap = map
        self.collectionView.reloadData()
    }
    
    func drawPath(path: Path) {
        currentPath = path
        self.collectionView.reloadData()
    }
    
    func clearMap() {
        currentPath = nil
        currentMap = nil
        currentStartCell = nil
        currentFinishCell = nil
        visitedCells = nil
        self.collectionView.reloadData()
    }
    
    func showError(text: String) {
        print("[App Error] " + text)
    }

    fileprivate func fromIndexToCoords(index: Int, width: Int) -> (x: Int, y: Int) {
        var x: Int = 0
        var y: Int = 0
        x = index % width
        y = index / width
        return (x, y)
    }

    fileprivate func fromCoordsToIndex(x: Int, y: Int, width: Int) -> Int {
        return x + width * y
    }
}

extension ViewController: UICollectionViewDelegateFlowLayout {
    /* Adaptive cell size to screen size */
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let layout = collectionView.collectionViewLayout as? UICollectionViewFlowLayout
        let cellCurrentWidth = (self.collectionView.frame.width / CGFloat(truncating: NumberFormatter().number(from: cellsInWidth.text!)!) - 1)
        let cellCurrentHeight = (self.collectionView.frame.height / CGFloat(truncating: NumberFormatter().number(from: cellsInHeight.text!)!) - layout!.minimumLineSpacing)
        currentCellsWidth = Int(cellCurrentWidth)
        if cellCurrentWidth == 0 || cellCurrentHeight == 0 {
            return CGSize(width: 50.0, height: 50.0)
        }
        /*
         * ширина = число ячеек * (ширина ячейки + ширина пробела)
         * ширина = число ячеек * ширина ячейки +  число ячеек * ширина пробела
         * ширина / число ячеек = ширина ячейки + ширина пробела
         * ширина ячейки = ширина / число ячеек - ширина пробела
         */
        return CGSize(width: cellCurrentWidth, height: cellCurrentWidth)
    }
}

class Point {
    var x = 0
    var y = 0
}
