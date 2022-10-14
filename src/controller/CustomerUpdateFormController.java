package controller;

import com.jfoenix.controls.JFXTextField;
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
import model.Customer;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class CustomerUpdateFormController extends CustomerFormController {

    public ImageView imgClose;

    public Label lblCusIdError;
    public Label lblTelError;
    public Label lblNameError;
    public Label lblEmailError;
    public Label lblAddressError;
    
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtEmail;
    public JFXTextField txtAddress;
    public JFXTextField txtTelNo;


    Pane paneVisible;

    public void initialize() {
        super.removeLblVisible();
    }

    public void getAllData(Customer selectItem, Pane paneVisible) {
        txtCustomerId.setText(selectItem.getCusId());
        txtCustomerName.setText(selectItem.getCusName());
        txtEmail.setText(selectItem.getCusEmail());
        txtAddress.setText(selectItem.getCusAddress());
        txtTelNo.setText(selectItem.getTelNo());
        this.paneVisible = paneVisible;
        txtCustomerId.setEditable(false);
    }

    public void imgCloseOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) imgClose.getScene().getWindow();
        stage.close();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        removeLblVisible();
        if (super.setValidate()) {

            Customer c = new Customer(txtCustomerId.getText(), txtCustomerName.getText(), txtEmail.getText(), txtAddress.getText(), txtTelNo.getText());

            if (CustomerCrudController.updateItem(c)) {
                super.clearData();
                paneVisible.setVisible(true);

                Stage st= (Stage) this.txtCustomerId.getScene().getWindow();
                st.close();

                Notifications notifications = Notifications.create();
                notifications.title("Customer Update");
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
            super.setErrorLabel();
        }
    }

}
