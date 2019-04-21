//
//  ViewController.swift
//  PathFinderMPiOS
//
//  Created by Aleksandr Gusev on 17/04/2019.
//  Copyright © 2019 aleksandr.gusev. All rights reserved.
//

import UIKit
import main

class ViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource, UICollectionViewDelegate, UICollectionViewDataSource, MainContractView  {

    @IBOutlet weak var cellsInWidth: UITextField!
    @IBOutlet weak var cellsInHeight: UITextField!
    @IBOutlet weak var pickerTextField: UITextField!
    @IBOutlet weak var collectionView: UICollectionView!
    @IBOutlet weak var actionButton: UIButton!
    @IBOutlet weak var hintLabel: UILabel!
    
    /* Default cells count on collectionView */
    var cellsCount = 1
    /* Available algorithms */
    var pickOption = ["Wave"]
    
    /* Presenter */
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
        
        cellsCount = (Int(cellsInWidth.text!)! * Int(cellsInHeight.text!)!)
    }
    
    override func viewDidAppear(_ animated: Bool) {
        cellsCount = (Int(cellsInWidth.text!)! * Int(cellsInHeight.text!)!)
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
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "basicCell", for: indexPath) as UICollectionViewCell
        cell.backgroundColor = UIColor.white
        return cell
    }
    
    /* Change cell color after pressing first time */
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        print("first: \(indexPath.section) second: \(indexPath.row)")
        let point: KotlinPair = KotlinPair(first: indexPath.section, second: indexPath.row)
        presenter.onCellClick(point: point)
        //let cell = collectionView.cellForItem(at: indexPath)
        //cell?.backgroundColor = UIColor.green
    }
    
    /* Change cell color after pressing second time */
    func collectionView(_ collectionView: UICollectionView, didDeselectItemAt indexPath: IndexPath) {
        //let cell = collectionView.cellForItem(at: indexPath)
        //cell?.backgroundColor = UIColor.white
    }
    
    /* Handle button press */
    @IBAction func generateButton(_ sender: UIButton) {
        cellsCount = (Int(cellsInWidth.text!)! * Int(cellsInHeight.text!)!)
        self.collectionView.reloadData()
    }
    
    /* Logic */
    
    func getInputMapWidth() -> Int32 {
        return Int32(cellsInWidth.text!)!
    }
    
    func getInputMapHeight() -> Int32 {
        return Int32(cellsInHeight.text!)!
    }
    
    func getSelectedAlgorithm() -> RoutingAlgorithm {
        var algorithm = RoutingAlgorithm.wave
        for item in pickOption {
            switch (item) {
            case "Wave":
                algorithm = RoutingAlgorithm.wave
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
    }
    
    func enableGenerateAction() {
        actionButton.isEnabled = true
        actionButton.setTitle("Generate", for: .normal)
    }
    
    func disableGenerateAction() {
        actionButton.isEnabled = false
    }
    
    func enableClearAction() {
        actionButton.isEnabled = true
        actionButton.setTitle("Clear", for: .normal)
    }
    
    func disableClearAction() {
        actionButton.isEnabled = false
    }
    
    func setStartCell(point: KotlinPair) {
        
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

    }
    
    func drawPath(path: Path) {
        
    }
    
    func clearMap() {
        self.collectionView.reloadData()
    }
    
    func showError(text: String) {
        /* TODO: Implement later */
    }
}

extension ViewController: UICollectionViewDelegateFlowLayout {
    /* Adaptive cell size to screen size */
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        
        let cellCurrentWidth = (self.collectionView.frame.width / CGFloat(truncating: NumberFormatter().number(from: cellsInWidth.text!)!))
        let cellCurrentHeight = (self.collectionView.frame.height / CGFloat(truncating: NumberFormatter().number(from: cellsInHeight.text!)!))
        if cellCurrentWidth == 0 || cellCurrentHeight == 0 {
            return CGSize(width: 50.0, height: 50.0)
        }
        return CGSize(width: cellCurrentWidth, height: cellCurrentWidth)
    }
}
