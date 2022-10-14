package controller;

import com.jfoenix.controls.*;
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
import model.Supplier;
import model.SupplierOrder;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SupplierOrderUpdateFormController extends SupplierManagementFormController{

    public ImageView imgClose;
    public JFXTextField txtOrderId;
//    public JFXTextField DatePlaceOrderDate;
    public JFXComboBox<String> cmbOrSupplierId;
    public JFXTextField txtOrProductName;
    public JFXTextField txtOrQuantity;
    public JFXTextField txtOrUnitPrice;
    public JFXTextField txtOrTotalCost;
    public JFXTextField txtOrDetails;
//    public JFXTextField TimePLaceOrderTime;
    public JFXTextField txtOrName;
    public JFXTextArea txtOrProductDescription;
    public JFXDatePicker DatePlaceOrderDate;
    public JFXTimePicker TimePLaceOrderTime;
    public Label lblOrderIdError;
    public Label cmbOrSelectSupplierTypeError;
    public Label lblOrQtyError;
    public Label lblOrDetalError;
    public Label lblOrDateError;
    public Label lblTimeError;
    public Label lblOrUnitpError;
    Pane pneVisibleSupOrder;


    public void initialize() throws SQLException, ClassNotFoundException {
        super.removeOrLblVisible();
        super.setSupplierId();
        super.setFromSupId();
        super.setUnitPFromProduct();
        super.setTotal();
        super.setTotalFromUp();
    }

    public void getAllData(SupplierOrder selectItem, Pane pneVisibleSupOrder) {
        txtOrderId.setText(selectItem.getOrderId());
        txtOrProductName.setText(selectItem.getProductName());
        txtOrDetails.setText(selectItem.getDescription());
        txtOrQuantity.setText(String.valueOf(selectItem.getQty()));
        txtOrUnitPrice.setText(String.valueOf(selectItem.getUnitPrice()));
        txtOrTotalCost.setText(String.valueOf(selectItem.getTotalCost()));


        DatePlaceOrderDate.setValue(selectItem.getDate().toLocalDate());

        TimePLaceOrderTime.setValue(selectItem.getTime().toLocalTime());


        cmbOrSupplierId.setValue(selectItem.getSupId());
        txtOrderId.setEditable(false);

        this.pneVisibleSupOrder=pneVisibleSupOrder;

    }

    public void imgCloseOnAction(MouseEvent mouseEvent) {
        Stage stage =(Stage) imgClose.getScene().getWindow();
        stage.close();
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) throws ParseException, SQLException, ClassNotFoundException {
        super.removeOrLblVisible();

        Date formatter = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(DatePlaceOrderDate.getValue()));
        java.sql.Date Date = new java.sql.Date(formatter.getDate());
//        java.sql.Time timeValue = new java.sql.Time(formatter.parse(time).getTime());



        DateFormat TFormat = new SimpleDateFormat("HH:mm");
        java.sql.Time timeValue = new java.sql.Time(TFormat.parse(TimePLaceOrderTime.getValue().toString()).getTime());

        if (!cmbOrSupplierId.getSelectionModel().isEmpty()) {
            if (super.setOrValidate()) {
                if (DatePlaceOrderDate.getValue() != null && TimePLaceOrderTime.getValue() != null && !txtOrDetails.getText().isEmpty()) {

                    SupplierOrder so = new SupplierOrder(txtOrderId.getText(), txtOrProductName.getText(), cmbOrSupplierId.getValue(),
                            txtOrDetails.getText(), Integer.parseInt(txtOrQuantity.getText()), Double.parseDouble(txtOrUnitPrice.getText()),
                            Double.parseDouble(txtOrTotalCost.getText()),Date,timeValue);


                    if (SupplierOrderCrudController.updateSupplierOrder(so)) {
                        pneVisibleSupOrder.setVisible(true);

                        Stage st= (Stage) this.txtOrTotalCost.getScene().getWindow();
                        st.close();

                        Notifications notifications = Notifications.create();
                        notifications.title("Supplier Order Update");
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
                    if (DatePlaceOrderDate.getValue() == null) {
                        lblOrDateError.setVisible(true);
                    }
                    if (TimePLaceOrderTime.getValue() == null) {
                        lblTimeError.setVisible(true);
                    }
                    if (txtOrDetails.getText().isEmpty()) {
                        lblOrDetalError.setVisible(true);
                    }
                }

            } else {
                super.setOrErrorLabel();
            }
        } else {
            cmbOrSelectSupplierTypeError.setVisible(true);
        }

    }

}
