package src.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;
import src.model.bl.CustomerBl;
import src.model.entity.Admin;
import src.model.entity.Customer;
import src.model.entity.enums.City;
import src.model.entity.enums.Gender;
import src.model.tools.Validator;
import src.view.WindowsManager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Log4j
public class AdminController implements Initializable {
    @FXML
    private TextField idField, fnameField, lnameField, nidField, emailField, phoneField, addressField, usernameField, passwordField;

    @FXML
    private Button exit, adminCreateBtn, adminDeleteBtn, adminEditBtn, adminCustomerBtn, adminAccountBtn, adminTransactionBtn, adminSummeryBtn, adminCardBtn, adminBillBtn, adminLoanBtn;

    @FXML
    private RadioButton maleToggle, femaleToggle;

    @FXML
    private TableView<Customer> adminTable;

    @FXML
    private TableColumn<Admin, Integer> adminTableID;

    @FXML
    private TableColumn<Admin, String> adminTableName, adminTableUsername, adminTablePassword;

    @FXML
    private ToggleGroup genderToggle;

    @FXML
    private ComboBox<City> cityCmb;

    @FXML
    private DatePicker birthDatePicker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        log.info("Entered Admin");

        for (City city : City.values()) {
            cityCmb.getItems().add(city);
        }

        try {
            cityCmb.getSelectionModel().select(0);
            resetForm();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Admin Error\n" + e.getMessage());
            alert.show();
        }

        adminCreateBtn.setOnAction(event -> {
            try {
                RadioButton gender = (RadioButton) genderToggle.getSelectedToggle();
                Customer customer = Customer
                        .builder()
                        .id(Integer.parseInt(idField.getText()))
                        .firstName(Validator.nameValidator(fnameField.getText(), "Invalid First Name!"))
                        .lastName(Validator.nameValidator(lnameField.getText(), "Invalid Last Name!"))
                        .nationalId(Validator.nationalIDValidator(nidField.getText(), "Invalid National ID!"))
                        .gender(Gender.valueOf(gender.getText()))
                        .birthDate(birthDatePicker.getValue())
                        .email(Validator.emailValidator(emailField.getText(), "Invalid Email!"))
                        .phone(Validator.phoneValidator(phoneField.getText(), "Invalid Phone!"))
                        .city(cityCmb.getSelectionModel().getSelectedItem())
                        .address(Validator.addressValidator(addressField.getText(), "Invalid Address!"))
                        .username(usernameField.getText())
                        .password(passwordField.getText())
                        .build();

                CustomerBl.getCustomerBl().save(customer);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Saved!");
                alert.show();
                resetForm();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error!\n" + e.getMessage());
                alert.show();
            }
        });

        adminEditBtn.setOnAction(event -> {
            try {
                RadioButton gender = (RadioButton) genderToggle.getSelectedToggle();
                Customer customer = Customer
                        .builder()
                        .id(Integer.parseInt(idField.getText()))
                        .firstName(Validator.nameValidator(fnameField.getText(), "Invalid First Name!"))
                        .lastName(Validator.nameValidator(lnameField.getText(), "Invalid Last Name!"))
                        .nationalId(Validator.nationalIDValidator(nidField.getText(), "Invalid National ID!"))
                        .gender(Gender.valueOf(gender.getText()))
                        .birthDate(birthDatePicker.getValue())
                        .email(Validator.emailValidator(emailField.getText(), "Invalid Email!"))
                        .phone(Validator.phoneValidator(phoneField.getText(), "Invalid Phone!"))
                        .city(cityCmb.getSelectionModel().getSelectedItem())
                        .address(Validator.addressValidator(addressField.getText(), "Invalid Address!"))
                        .username(usernameField.getText())
                        .password(passwordField.getText())
                        .build();

                CustomerBl.getCustomerBl().edit(customer);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Edited!");
                alert.show();
                resetForm();

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error!\n" + e.getMessage());
                alert.show();
            }
        });

        adminDeleteBtn.setOnAction(event -> {
            try {
                CustomerBl.getCustomerBl().remove(Integer.parseInt(idField.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Deleted!");
                alert.show();
                resetForm();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error!\n" + e.getMessage());
                alert.show();
            }
        });

        adminCustomerBtn.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                Scene scene = new Scene(
                        FXMLLoader.load(WindowsManager.class.getResource("../view/AdminCustomer.fxml"))
                );
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error: \n" + e.getMessage());
                alert.show();
                log.error("AdminCustomer Error : " + e.getMessage());
            }
        });

        adminAccountBtn.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                Scene scene = new Scene(
                        FXMLLoader.load(WindowsManager.class.getResource("../view/AdminAccount.fxml"))
                );
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error: \n" + e.getMessage());
                alert.show();
                log.error("AdminAccount Error : " + e.getMessage());
            }
        });

        adminTransactionBtn.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                Scene scene = new Scene(
                        FXMLLoader.load(WindowsManager.class.getResource("../view/AdminTransaction.fxml"))
                );
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error: \n" + e.getMessage());
                alert.show();
                log.error("AdminTransaction Error : " + e.getMessage());
            }
        });

        adminSummeryBtn.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                Scene scene = new Scene(
                        FXMLLoader.load(WindowsManager.class.getResource("../view/AdminSummery.fxml"))
                );
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error: \n" + e.getMessage());
                alert.show();
                log.error("AdminSummery Error : " + e.getMessage());
            }
        });

        adminCardBtn.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                Scene scene = new Scene(
                        FXMLLoader.load(WindowsManager.class.getResource("../view/CardPanel.fxml"))
                );
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error: \n" + e.getMessage());
                alert.show();
                log.error("CardPanel Error : " + e.getMessage());
            }
        });

        adminBillBtn.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                Scene scene = new Scene(
                        FXMLLoader.load(WindowsManager.class.getResource("../view/BillPanel.fxml"))
                );
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error: \n" + e.getMessage());
                alert.show();
                log.error("BillPanel Error : " + e.getMessage());
            }
        });

        adminLoanBtn.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                Scene scene = new Scene(
                        FXMLLoader.load(WindowsManager.class.getResource("../view/LoanPanel.fxml"))
                );
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error: \n" + e.getMessage());
                alert.show();
                log.error("AdminSummery Error : " + e.getMessage());
            }
        });

        exit.setOnAction((event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Quit?");
            if (alert.showAndWait().get().equals(ButtonType.OK)) {
                Platform.exit();
            }
            log.info("Quited");
        }));

        adminTable.setOnMouseClicked((event) -> {
            Customer customer = adminTable.getSelectionModel().getSelectedItem();
            idField.setText(String.valueOf(customer.getId()));
            fnameField.setText(customer.getFirstName());
            lnameField.setText(customer.getLastName());
            nidField.setText(customer.getNationalId());
            if (customer.getGender().equals(Gender.Male)) {
                maleToggle.setSelected(true);
            } else {
                femaleToggle.setSelected(true);
            }
            birthDatePicker.setValue(customer.getBirthDate());
            emailField.setText(customer.getEmail());
            phoneField.setText(customer.getPhone());
            cityCmb.getSelectionModel().select(customer.getCity().ordinal());
            addressField.setText(customer.getAddress());
            usernameField.setText(customer.getUsername());
            passwordField.setText(customer.getPassword());
        });
    }

    private void showDataOnTable(List<Customer> customerList) throws Exception {
        ObservableList<Customer> observableList = FXCollections.observableList(customerList);
        adminTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        adminTableName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        adminTableUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        adminTablePassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        adminTable.setItems(observableList);
    }

    private void resetForm() throws Exception {
        idField.clear();
        fnameField.clear();
        lnameField.clear();
        nidField.clear();
        maleToggle.setSelected(true);
        emailField.clear();
        phoneField.clear();
        addressField.clear();
        usernameField.clear();
        passwordField.clear();
        birthDatePicker.setValue(null);
        cityCmb.getSelectionModel().select(0);
        usernameField.clear();
        passwordField.clear();
        showDataOnTable(CustomerBl.getCustomerBl().findAll());
    }
}
