package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
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
import model.Product;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;

public class StokeUpdateFormController extends StokeManagementFormController{

    public ImageView imgClose;
    public JFXTextField txtProductId;
    public JFXTextField txtProductName;
    public JFXTextField txtUnitPrice;
    public JFXTextArea txtDescription;
    public JFXComboBox<String> cmbProductType;

    public Label lblProductIdError;
    public Label cmbSelectTypeError;
    public Label lblProductNameError;
    public Label lblDescriptonError;
    public Label lblUnitPriceError;
    Pane paneVisible;


    public void initialize() throws SQLException, ClassNotFoundException {
//        cmbProductType.setItems(StockCrudController.getProductType());
        removeLblVisible();
        super.loadCmbData();
    }


    public void getAllData(Product selectItem, Pane paneVisible) {
        txtProductId.setText(selectItem.getProductId());
        cmbProductType.getSelectionModel().select(selectItem.getProductType());
        txtProductName.setText(selectItem.getProductName());
        txtDescription.setText(selectItem.getDescription());
        txtUnitPrice.setText(String.valueOf(selectItem.getUnitPrice()));

        this.paneVisible=paneVisible;
        txtProductId.setEditable(false);
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        removeLblVisible();
        if(!cmbProductType.getSelectionModel().isEmpty()) {
            if (!txtDescription.getText().isEmpty()) {

                if (setValidate()) {
                    Product p = new Product(txtProductId.getText(), cmbProductType.getValue(), txtProductName.getText(), txtDescription.getText(), Double.parseDouble(txtUnitPrice.getText()));

                    if (StockCrudController.updateProduct(p)) {
                        paneVisible.setVisible(true);


                        Stage st= (Stage) this.txtUnitPrice.getScene().getWindow();
                        st.close();

                        Notifications notifications = Notifications.create();
                        notifications.title("Stoke Update");
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
                    super.setErrorLabel();
                }
            }else {
                lblDescriptonError.setVisible(true);
            }
        }else{
            cmbSelectTypeError.setVisible(true);
        }
    }

    public void imgCloseOnAction(MouseEvent mouseEvent) {
        Stage stage=(Stage)imgClose.getScene().getWindow();
        stage.close();
    }

    @Override
    public void removeLblVisible() {
        lblProductIdError.setVisible(false);
        lblProductNameError.setVisible(false);
        lblDescriptonError.setVisible(false);
        lblUnitPriceError.setVisible(false);
        cmbSelectTypeError.setVisible(false);

    }
}
