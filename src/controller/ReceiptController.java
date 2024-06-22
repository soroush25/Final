package src.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j;
import src.model.bl.ReceiptBl;
import src.model.entity.Receipt;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Log4j
public class ReceiptController implements Initializable {

    @FXML
    private TableView<Receipt> receiptTable;

    @FXML
    private TableColumn<Receipt, Integer> receiptID;

    @FXML
    private TableColumn<Receipt, String> receiptSrc, receiptDst, receiptAmount, receiptDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        log.info("Entered Receipt");
        try {
            resetForm();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Receipt Error\n" + e.getMessage());
            alert.show();
        }
    }
    private void showDataOnTable(List<Receipt> receiptList) throws Exception {
        ObservableList<Receipt> observableList = FXCollections.observableList(receiptList);
        receiptID.setCellValueFactory(new PropertyValueFactory<>("id"));
        receiptSrc.setCellValueFactory(new PropertyValueFactory<>("sourceAccount"));
        receiptDst.setCellValueFactory(new PropertyValueFactory<>("destinationAccount"));
        receiptAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        receiptDate.setCellValueFactory(new PropertyValueFactory<>("transactionDateTime"));
        receiptTable.setItems(observableList);
    }

    private void resetForm() throws Exception {
        showDataOnTable(ReceiptBl.getReceiptBl().findAll());
    }
}
