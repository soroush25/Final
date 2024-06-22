package src.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j;
import src.model.bl.LoanBl;
import src.model.entity.Loan;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

@Log4j
public class LoanController implements Initializable {
    @FXML
    private TextField idField, amountField, interestField;

    @FXML
    private Button saveBtn, deleteBtn, editBtn;
    
    @FXML
    private TableView<Loan> loanTable;

    @FXML
    private TableColumn<Loan, Integer> loanTableID;

    @FXML
    private TableColumn<Loan, String> loanTableDate, loanTableAmount, loanTableInterest;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        log.info("Entered Loan");

        try {
            resetForm();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Loan Error\n" + e.getMessage());
            alert.show();
        }

        saveBtn.setOnAction(event -> {
            try {
                Loan loan = Loan
                        .builder()
                        .id(Integer.parseInt(idField.getText()))
                        .startDate(Timestamp.valueOf(LocalDateTime.now()))
                        .amount(Double.valueOf(amountField.getText()))
                        .interest(Double.valueOf(interestField.getText()))
                        .build();

                LoanBl.getLoanBl().save(loan);
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
                Loan loan = Loan
                        .builder()
                        .id(Integer.parseInt(idField.getText()))
                        .startDate(Timestamp.valueOf(LocalDateTime.now()))
                        .amount(Double.valueOf(amountField.getText()))
                        .interest(Double.valueOf(interestField.getText()))
                        .build();

                LoanBl.getLoanBl().edit(loan);
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
                LoanBl.getLoanBl().remove(Integer.parseInt(idField.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Deleted!");
                alert.show();
                resetForm();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error!\n" + e.getMessage());
                alert.show();
            }
        });

        loanTable.setOnMouseClicked((event) -> {
            Loan loan = loanTable.getSelectionModel().getSelectedItem();
            idField.setText(String.valueOf(loan.getId()));
            amountField.setText(String.valueOf(loan.getAmount()));
            interestField.setText(String.valueOf(loan.getInterest()));
        });
    }

    private void showDataOnTable(List<Loan> loanList) throws Exception {
        ObservableList<Loan> observableList = FXCollections.observableList(loanList);
        loanTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        loanTableDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        loanTableAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        loanTableInterest.setCellValueFactory(new PropertyValueFactory<>("interest"));
        loanTable.setItems(observableList);
    }

    private void resetForm() throws Exception {
        idField.clear();
        amountField.clear();
        interestField.clear();
        showDataOnTable(LoanBl.getLoanBl().findAll());
    }
}
