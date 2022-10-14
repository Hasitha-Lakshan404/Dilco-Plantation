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
import model.Plant;
import org.controlsfx.control.Notifications;
import util.CrudUtil;

import java.sql.SQLException;

public class PlantUpdateFormController extends PlantManagementFormController{

    public ImageView imgClose;
    public JFXTextField txtPlantId;
    public JFXTextField txtPlantName;
    public JFXTextField txtUnitPrice;
    public JFXTextArea txtDescription;
    public JFXComboBox<String> cmbPlantType;
    public Label lblPlantIdError;
    public Label cmbSelectTypeError;
    public Label lblPlantNameError;
    public Label lblDescriptonError;
    public Label lblUnitPriceError;
    private Pane paneVisible;

    public void initialize() throws SQLException, ClassNotFoundException {
        super.setCmbData();
        removeLblVisible();

    }


    public void getAllData(Plant selectItem, Pane paneVisible) {
        txtPlantId.setText(selectItem.getPlantId());
//        txtPlantType.setText(selectItem.getPlantType());
        cmbPlantType.setValue(selectItem.getPlantType());

        txtPlantName.setText(selectItem.getPlantName());
        txtDescription.setText(selectItem.getDescription());
        txtUnitPrice.setText(String.valueOf(selectItem.getUnitPrice()));
        this.paneVisible=paneVisible;
        txtPlantId.setEditable(false);
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
      removeLblVisible();
        if (!cmbPlantType.getSelectionModel().isEmpty()) {

            if (!txtDescription.getText().isEmpty()) {
                if (super.setValidate()) {

                    Plant p = new Plant(txtPlantId.getText(), cmbPlantType.getValue(), txtPlantName.getText(), txtDescription.getText(), Double.parseDouble(txtUnitPrice.getText()));

                    if (PlantCrudController.updatePlant(p)) {
                        paneVisible.setVisible(true);




                    } else {
                        new Alert(Alert.AlertType.ERROR, "Un-Successful", ButtonType.CLOSE).show();
                    }

                    Stage st= (Stage) this.txtUnitPrice.getScene().getWindow();
                    st.close();

                    Notifications notifications = Notifications.create();
                    notifications.title("Plant Update");
                    notifications.text("Update Successfully...");
                    notifications.hideAfter(Duration.seconds(8));
                    notifications.position(Pos.BOTTOM_RIGHT);
                    notifications.graphic(new ImageView("asserts/images/Done.png"));
                    notifications.darkStyle();
                    notifications.show();

                }else{
                    super.setErrorLabel();
                }
            } else {
                lblDescriptonError.setVisible(true);
            }
        } else {
            cmbSelectTypeError.setVisible(true);
        }
    }

    public void imgCloseOnAction(MouseEvent mouseEvent) {
        Stage stage=(Stage)imgClose.getScene().getWindow();
        stage.close();
    }

    @Override
    public void removeLblVisible() {
        lblPlantIdError.setVisible(false);
        lblPlantNameError.setVisible(false);
        lblDescriptonError.setVisible(false);
        lblUnitPriceError.setVisible(false);
        cmbSelectTypeError.setVisible(false);

    }
}
