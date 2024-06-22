package src.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j;
import src.model.bl.AccountBl;
import src.model.bl.CardBl;
import src.model.entity.Card;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Log4j
public class CardController implements Initializable {
    @FXML
    private TextField idField, accountField, pinField;

    @FXML
    private Button saveBtn, deleteBtn, editBtn;

    @FXML
    private TableView<Card> cardTable;

    @FXML
    private TableColumn<Card, Integer> cardTableID, cardTableAccount, cardTablePin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        log.info("Entered Card");
        
        try {
            resetForm();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Card Error\n" + e.getMessage());
            alert.show();
        }

        saveBtn.setOnAction(event -> {
            try {
                Card card = Card
                        .builder()
                        .id(Integer.parseInt(idField.getText()))
                        .accountNumber(AccountBl.getAccountBl().findByAccountNumber(Integer.parseInt(accountField.getText())))
                        .pin(Integer.parseInt(pinField.getText()))
                        .build();

                CardBl.getCardBl().save(card);
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
                Card card = Card
                        .builder()
                        .id(Integer.parseInt(idField.getText()))
                        .accountNumber(AccountBl.getAccountBl().findByAccountNumber(Integer.parseInt(accountField.getText())))
                        .pin(Integer.parseInt(pinField.getText()))
                        .build();

                CardBl.getCardBl().edit(card);
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
                CardBl.getCardBl().remove(Integer.parseInt(idField.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Deleted!");
                alert.show();
                resetForm();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error!\n" + e.getMessage());
                alert.show();
            }
        });

        cardTable.setOnMouseClicked((event) -> {
            Card card = cardTable.getSelectionModel().getSelectedItem();
            idField.setText(String.valueOf(card.getId()));
            accountField.setText(String.valueOf(card.getAccountNumber()));
            pinField.setText(String.valueOf(card.getPin()));
        });
    }

    private void showDataOnTable(List<Card> cardList) throws Exception {
        ObservableList<Card> observableList = FXCollections.observableList(cardList);
        cardTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        cardTableAccount.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        cardTablePin.setCellValueFactory(new PropertyValueFactory<>("pin"));
        cardTable.setItems(observableList);
    }

    private void resetForm() throws Exception {
        idField.clear();
        accountField.clear();
        pinField.clear();
        showDataOnTable(CardBl.getCardBl().findAll());
    }
}
