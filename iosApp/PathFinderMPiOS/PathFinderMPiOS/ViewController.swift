//
//  ViewController.swift
//  PathFinderMPiOS
//
//  Created by Aleksandr Gusev on 17/04/2019.
//  Copyright © 2019 aleksandr.gusev. All rights reserved.
//

import UIKit
import main

class ViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource, UICollectionViewDelegate, UICollectionViewDataSource  {

    @IBOutlet weak var cellsInWidth: UITextField!
    @IBOutlet weak var cellsInHeight: UITextField!
    @IBOutlet weak var pickerTextField: UITextField!
    @IBOutlet weak var collectionView: UICollectionView!

    /* Default cells count on collectionView */
    var cellsCount = 1
    /* Available algorithms */
    var pickOption = ["Wave"]

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
    
    /* Handle button press */
    @IBAction func generateButton(_ sender: UIButton) {
        cellsCount = (Int(cellsInWidth.text!)! * Int(cellsInHeight.text!)!)
        self.collectionView.reloadData()
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
