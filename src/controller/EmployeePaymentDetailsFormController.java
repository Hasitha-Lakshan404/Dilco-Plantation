package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import model.Customer;
import model.Payment;

import java.sql.SQLException;
import java.util.Optional;

public class EmployeePaymentDetailsFormController {

    public TableView<Payment> tblEmpPayment;
    public AnchorPane apnMain;

    public void initialize() throws SQLException, ClassNotFoundException {
        lordData();

        tblEmpPayment.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("payNo"));
        tblEmpPayment.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("empType"));
        tblEmpPayment.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("status"));
        tblEmpPayment.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("supEmpId"));
        tblEmpPayment.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblEmpPayment.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("payedDate"));
        tblEmpPayment.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("payMethod"));
        tblEmpPayment.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("total"));

    }

    private void lordData() throws SQLException, ClassNotFoundException {
        tblEmpPayment.setItems(EmployeePaymentCrudController.loadAllEmployeePayments());
    }

    public void contextDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Payment selectedItem = tblEmpPayment.getSelectionModel().getSelectedItem();

        BoxBlur blur = new BoxBlur(5, 5, 5);
        apnMain.setEffect(blur);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you Sure you want to Delete?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Payment!!");
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get().equals(ButtonType.YES)) {
            EmployeePaymentCrudController.deletePayment(selectedItem); //for delete
            lordData();
            apnMain.setEffect(null);
        } else {
            apnMain.setEffect(null);
        }

    }
}
