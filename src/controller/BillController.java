package src.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j;
import src.model.bl.BillBl;
import src.model.bl.CustomerBl;
import src.model.entity.Bill;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Log4j
public class BillController implements Initializable {
    @FXML
    private TextField idField, customerField, billNumberField, amountField;

    @FXML
    private Button saveBtn, deleteBtn, editBtn;

    @FXML
    private TableView<Bill> billTable;

    @FXML
    private TableColumn<Bill, Integer> billTableID, billTableAccount, billTableBillNumber, billTableAmount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        log.info("Entered Bill");

        try {
            resetForm();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Bill Error\n" + e.getMessage());
            alert.show();
        }

        saveBtn.setOnAction(event -> {
            try {
                Bill bill = Bill
                        .builder()
                        .id(Integer.parseInt(idField.getText()))
                        .customerId(CustomerBl.getCustomerBl().findById(Integer.parseInt(customerField.getText())))
                        .billNumber(Integer.parseInt(billNumberField.getText()))
                        .amount(Integer.parseInt(amountField.getText()))
                        .build();

                BillBl.getBillBl().save(bill);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Saved!");
                alert.show();
                resetForm();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error!\n" + e.getMessage());
                alert.show();
            }
        });

        editBtn.setOnAction(event -> {
            try {
                Bill bill = Bill
                        .builder()
                        .id(Integer.parseInt(idField.getText()))
                        .customerId(CustomerBl.getCustomerBl().findById(Integer.parseInt(customerField.getText())))
                        .billNumber(Integer.parseInt(billNumberField.getText()))
                        .amount(Integer.parseInt(amountField.getText()))
                        .build();

                BillBl.getBillBl().edit(bill);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Edited!");
                alert.show();
                resetForm();

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error!\n" + e.getMessage());
                alert.show();
            }
        });

        deleteBtn.setOnAction(event -> {
            try {
                BillBl.getBillBl().remove(Integer.parseInt(idField.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Deleted!");
                alert.show();
                resetForm();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error!\n" + e.getMessage());
                alert.show();
            }
        });
        
        billTable.setOnMouseClicked((event) -> {
            Bill bill = billTable.getSelectionModel().getSelectedItem();
            idField.setText(String.valueOf(bill.getId()));
            customerField.setText(String.valueOf(bill.getCustomerId()));
            billNumberField.setText(String.valueOf(bill.getBillNumber()));
            amountField.setText(String.valueOf(bill.getAmount()));
        });
    }

    private void showDataOnTable(List<Bill> billList) throws Exception {
        ObservableList<Bill> observableList = FXCollections.observableList(billList);
        billTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        billTableAccount.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        billTableBillNumber.setCellValueFactory(new PropertyValueFactory<>("billNumber"));
        billTableAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        billTable.setItems(observableList);
    }

    private void resetForm() throws Exception {
        idField.clear();
        customerField.clear();
        billNumberField.clear();
        amountField.clear();
        showDataOnTable(BillBl.getBillBl().findAll());
    }
}
