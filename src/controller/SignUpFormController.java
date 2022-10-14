package controller;

import animatefx.animation.BounceOutRight;
import animatefx.animation.Pulse;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Employee;
import util.GenerateAutoId;

public class SignUpFormController {

    public JFXComboBox<String> cmbRole;
    public AnchorPane DashboardContext;
    public JFXButton btnAddEmployee;
    public JFXTextField txtUserName;
    public JFXTextField txtEmpId;
    public JFXTextField txtAddress;
    public JFXTextField txtTellNo;
    public JFXPasswordField pwdPassword;
    public JFXPasswordField pwdConPassword;
    public JFXTextField txtFullName;
    public JFXTextField txtEmail;

    public void initialize(){
        ObservableList<String> data= FXCollections.observableArrayList("ADMIN");
        cmbRole.setItems(data);

        setAutoId();
        cmbRole.getSelectionModel().select("ADMIN");
        txtEmpId.setEditable(false);
    }

    public void icnArrowOnACtion(MouseEvent mouseEvent) {
        new BounceOutRight(DashboardContext).play();
    }

    public void btnAddMouseMoved(MouseEvent mouseEvent) {
        new Pulse(btnAddEmployee).play();
    }

    public void setAutoId(){
        txtEmpId.setText(GenerateAutoId.autoId("SELECT empId FROM employee ORDER BY empId DESC LIMIT 1", 1, 3,"EM-01"));
    }

    public void btnAddEmployeeOnAction(ActionEvent actionEvent) {
        if(!cmbRole.getSelectionModel().isEmpty()) {
             if (pwdPassword.getText().equals(pwdConPassword.getText())) {
                Employee e = new Employee(txtEmpId.getText(), cmbRole.getValue(), txtFullName.getText(),
                        txtAddress.getText(), txtEmail.getText(), txtTellNo.getText(), txtUserName.getText(),
                        pwdPassword.getText());
                EmployeeCrudController.addEmployee(e);
                setAutoId();
            } else {
                //=====================CONFIRM PASSWORD ERROR==========================
            }
        }
    }
}
