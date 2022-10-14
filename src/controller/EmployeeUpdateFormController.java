package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Employee;
import model.Plant;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;

public class EmployeeUpdateFormController extends EmployeeManagementFormController {

    public ImageView imgClose;
    public JFXTextField txtEmpId;
    public JFXTextField txtAddress;
    public JFXComboBox<String> cmbRole;
    public JFXTextField txtFullName;
    public JFXTextField txtTellNo;
    public JFXTextField txtName;
    public JFXPasswordField pwdPassword;
    public JFXTextField txtEmail;
    public JFXPasswordField pwdConPassword;

    public Label lblEmpIdError;
    public Label lblCmbRoleError;
    public Label lblTelError;
    public Label lblNameError;
    public Label lblAddressError;
    public Label lblEmailError;
    public Label lblUserNameError;
    public Label lblPasswordError;
    public Label lblConPasswordError;
    public Label lblCheckPasswordError;


    Pane pneVisible;


    public void initialize() {
        setCmb();
        setDisable();
        super.removeLabelError();
    }

    private void setCmb() {
        ObservableList<String> data = FXCollections.observableArrayList("ADMIN", "MANAGER", "STAFF");
        cmbRole.setItems(data);
    }

    public void getAllData(Employee selectItem, Pane pneVisible) {
        txtEmpId.setText(selectItem.getEmpId());
        txtFullName.setText(selectItem.getFullName());
        txtAddress.setText(selectItem.getEmpAddress());
        txtEmail.setText(selectItem.getEmail());
        txtTellNo.setText(selectItem.getTellNo());
        txtName.setText(selectItem.getUserName());
        pwdPassword.setText(selectItem.getPassword());
        pwdConPassword.setText(selectItem.getPassword());

        cmbRole.getSelectionModel().select(selectItem.getRole());
        this.pneVisible = pneVisible;

    }

    private void setDisable() {
        cmbRole.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!cmbRole.getValue().equals("")) {

                if (cmbRole.getValue().equals("STAFF")) {
                    txtName.setText("NoAccess");
                    pwdPassword.setText(null);
                    pwdConPassword.setText(null);

                    txtName.setEditable(false);
                    pwdPassword.setEditable(false);
                    pwdConPassword.setEditable(false);
                } else {

                    txtName.setEditable(true);
                    pwdPassword.setEditable(true);
                    pwdConPassword.setEditable(true);
                }
            }

        });
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        super.removeLabelError();

        if(super.setValidateToEmp()) {

            if (cmbRole.getValue().equals("STAFF")) {
                Employee e = new Employee(txtEmpId.getText(), cmbRole.getValue(), txtFullName.getText(),
                        txtAddress.getText(), txtEmail.getText(), txtTellNo.getText(), "-No Access-",
                        "-No Access-");

                if (EmployeeCrudController.updateEmployee(e)) {
                    pneVisible.setVisible(true);

                    Stage st= (Stage) this.txtEmail.getScene().getWindow();
                    st.close();

                    Notifications notifications = Notifications.create();
                    notifications.title("Employee Update");
                    notifications.text("Update Successfully...");
                    notifications.hideAfter(Duration.seconds(8));
                    notifications.position(Pos.BOTTOM_RIGHT);
                    notifications.graphic(new ImageView("asserts/images/Done.png"));
                    notifications.darkStyle();
                    notifications.show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Un-Successful", ButtonType.CLOSE).show();
                }

            } else if (pwdPassword.getText().equals(pwdConPassword.getText())) {
                Employee e = new Employee(txtEmpId.getText(), cmbRole.getValue(), txtFullName.getText(),
                        txtAddress.getText(), txtEmail.getText(), txtTellNo.getText(), txtName.getText(),
                        pwdPassword.getText());

                if (EmployeeCrudController.updateEmployee(e)) {
                    pneVisible.setVisible(true);

                    Stage st= (Stage) this.txtFullName.getScene().getWindow();
                    st.close();

                    Notifications notifications = Notifications.create();
                    notifications.title("Employee Update");
                    notifications.text("Update Successfully...");
                    notifications.hideAfter(Duration.seconds(8));
                    notifications.position(Pos.BOTTOM_RIGHT);
                    notifications.graphic(new ImageView("asserts/images/Done.png"));
                    notifications.darkStyle();
                    notifications.show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Un-Successful", ButtonType.CLOSE).show();
                }

            } else {
                lblCheckPasswordError.setVisible(true);
            }
        }else{
            super.setErrorLabel();
        }
    }

    public void imgCloseOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) imgClose.getScene().getWindow();
        stage.close();

    }

}
