package controller;

import animatefx.animation.BounceIn;
import com.jfoenix.controls.JFXComboBox;
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
import model.Supplier;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;

public class SupplierUpdateFormController extends SupplierManagementFormController {

    public ImageView imgClose;
//    public JFXTextField txtSupplierId;
//    public JFXTextField txtSupplierName;
    public JFXTextField txtAddTelNo;
//    public JFXComboBox<String> cmbProductType;
//    public JFXTextField txtSupplierAddress;
//    public JFXComboBox<String> cmbProductName;
    public Pane paneVisible;
//    public JFXTextField txtEmail;

    public JFXTextField txtAddSuppId;
    public JFXTextField txtAddSupName;
    public JFXComboBox<String> cmbAddSelectType;
    public JFXTextField txtAddSupEmail;
    public JFXComboBox<String> cmbAddProductName;
    public JFXTextField txtAddSupAddress;

    public Label lblSupplierIdError;
    public Label lblSupplierNameError;
    public Label lblAddressError;
    public Label lblEmailError;
    public Label cmbSelectTypeError;
    public Label cmbSelectNameTypeError;
    public Label lblTellNoError;

    public void initialize() throws SQLException, ClassNotFoundException {
        super.removeSupLblVisible();
//        new BounceIn(b)
        setCmbData();
        super.setAddProductNames();

    }

    public void getAllData(Supplier selectItem, Pane paneVisible) {
        txtAddSuppId.setText(selectItem.getSupId());
        txtAddSupName.setText(selectItem.getName());
        txtAddSupEmail.setText(selectItem.getEmail());
        txtAddSupAddress.setText(selectItem.getAddress());
        txtAddTelNo.setText(selectItem.getTelNo());

        cmbAddSelectType.setValue(selectItem.getProductType());
        cmbAddProductName.setValue(selectItem.getProductName());

        this.paneVisible=paneVisible;
        txtAddSuppId.setEditable(false);
    }

    public void imgCloseOnAction(MouseEvent mouseEvent) {
        Stage stage=(Stage)imgClose.getScene().getWindow();
        stage.close();
    }

    private void setCmbData() throws SQLException, ClassNotFoundException {
        cmbAddSelectType.setItems(StockCrudController.getProductType());
        cmbAddProductName.setItems(StockCrudController.getProductName());
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        super.removeSupLblVisible();
        if(super.setSupValidate()) {
            Supplier s = new Supplier(txtAddSuppId.getText(), txtAddSupName.getText(), txtAddSupEmail.getText(),
                    txtAddSupAddress.getText(), txtAddTelNo.getText(), cmbAddSelectType.getValue(), cmbAddProductName.getValue());

            if (SupplierCrudController.updateSupplier(s)) {
                paneVisible.setVisible(true);


                Stage stage = (Stage) this.txtAddSuppId.getScene().getWindow();
                stage.close();


                Notifications notifications = Notifications.create();
                notifications.title("Supplier Update");
                notifications.text("Update Successfully...");
                notifications.hideAfter(Duration.seconds(8));
                notifications.position(Pos.BOTTOM_RIGHT);
                notifications.graphic(new ImageView("asserts/images/Done.png"));
                notifications.darkStyle();
                notifications.show();

            } else {
                new Alert(Alert.AlertType.ERROR, "Un-Successful", ButtonType.CLOSE).show();
            }
        }else{
            super.setSupErrorLabel();
        }


    }
}
