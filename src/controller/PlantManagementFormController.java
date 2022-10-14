package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Customer;
import model.Plant;
import org.controlsfx.control.Notifications;
import util.GenerateAutoId;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Pattern;

public class PlantManagementFormController {


    public JFXTextField txtPlantId;
    public JFXComboBox<String> cmbPlantType;
    public JFXTextField txtNewPlantType;
    public JFXTextField txtPlantName;
    public JFXTextArea txtDescription;
    public JFXTextField txtUnitPrice;
    public TableView<Plant> tblPlant;
    public Pane pneVisible;
    public BorderPane bPane;
    public Label lblPlantIdError;
    public Label lblPlantNameError;
    public Label lblDescriptonError;
    public Label lblUnitPriceError;
    public Label lblAddPlantTypeError;
    public Label cmbSelectTypeError;
    public TextField txtSearch;
    Pattern plantIdPattern = Pattern.compile("^(Pl-)[0-9]{2,9}$");
    Pattern plantNamePattern = Pattern.compile("^[A-z ]{3,25}$");
    Pattern cusAddressPattern = Pattern.compile("^[A-z 0-9,/]{3,25}$");
    Pattern doublePattern = Pattern.compile("^[0-9.]{1,}$");
    private double xOffset = 0;
    private double yOffset = 0;

    public void initialize() throws SQLException, ClassNotFoundException {
        pneVisible.setVisible(false);
        setPlantId();
        lordItems();
        tblPlant.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("plantId"));
        tblPlant.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("plantName"));
        tblPlant.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("plantType"));
        tblPlant.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblPlant.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        setCmbData();
        removeLblVisible();
    }

    public void lordItems() throws SQLException, ClassNotFoundException {
        tblPlant.setItems(PlantCrudController.loadAllItems());
    }

    public void btnAddNewPlantType(ActionEvent actionEvent) {

        if (plantNamePattern.matcher(txtNewPlantType.getText()).matches()) {
            cmbPlantType.getItems().add(txtNewPlantType.getText());
            txtNewPlantType.setText("");
            lblAddPlantTypeError.setVisible(false);
        } else {
            lblAddPlantTypeError.setVisible(true);

        }


    }

    public void btnAddPlant(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        removeLblVisible();
        if (!cmbPlantType.getSelectionModel().isEmpty()) {
            if (!txtDescription.getText().isEmpty()) {
                if (setValidate()) {

                    Plant p = new Plant(txtPlantId.getText(), cmbPlantType.getValue(), txtPlantName.getText(), txtDescription.getText(), Double.parseDouble(txtUnitPrice.getText()));
                    PlantCrudController.addPlant(p);
                    lordItems();
                    clearText();

                    Notifications notifications = Notifications.create();
                    notifications.title("Add Plant");
                    notifications.text("New Plant added Successfully...");
                    notifications.hideAfter(Duration.seconds(8));
                    notifications.position(Pos.BOTTOM_RIGHT);
                    notifications.graphic(new ImageView("asserts/images/plantMM.png"));
                    notifications.darkStyle();
                    notifications.show();

                    if (txtPlantId.getText().equals("")) {
                        setPlantId();
                    }
                } else {
                    setErrorLabel();
                }
            } else {
                lblDescriptonError.setVisible(true);
            }
        } else {
            cmbSelectTypeError.setVisible(true);
        }

    }

    public  void setCmbData() throws SQLException, ClassNotFoundException {
        cmbPlantType.setItems(PlantCrudController.getPlantType());
    }

    public void clearText() {
        txtPlantId.setText("");
        txtPlantName.setText("");
        txtDescription.setText("");
        txtUnitPrice.setText("");
        cmbPlantType.setValue(null);
    }

    private void setPlantId() {
        txtPlantId.setText(GenerateAutoId.autoId("SELECT plantId FROM plant ORDER BY plantId DESC LIMIT 1", 1, 3, "Pl-01"));
    }

    public void contextEditOnAction(ActionEvent actionEvent) throws IOException {
        lordWidow("../view/PlantUpdateForm.fxml", "ss");
    }

    public void contextDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Plant selectedItem = tblPlant.getSelectionModel().getSelectedItem();

        BoxBlur blur = new BoxBlur(5, 5, 5);
        bPane.setEffect(blur);


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you Sure you want to Delete?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Item!!");
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get().equals(ButtonType.YES)) {
            PlantCrudController.deletePlant(selectedItem); //for delete Plant
            lordItems();
            setPlantId();
            bPane.setEffect(null);
        } else {
            bPane.setEffect(null);
        }
    }

    public void lordWidow(String location, String title) throws IOException {

        Plant selectItem = tblPlant.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        PlantUpdateFormController controller = loader.getController();

        controller.getAllData(selectItem, pneVisible);

        Stage stage = new Stage(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);

        stage.setScene(scene);
        stage.show();

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

    }

    public void paneVisibleMoveOnAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        lordItems();
        pneVisible.setVisible(false);
    }

    public void removeLblVisible() {
        lblPlantIdError.setVisible(false);
        lblPlantNameError.setVisible(false);
        lblAddPlantTypeError.setVisible(false);
        lblDescriptonError.setVisible(false);
        lblUnitPriceError.setVisible(false);
        cmbSelectTypeError.setVisible(false);

    }

    public boolean setValidate() {

        return plantIdPattern.matcher(txtPlantId.getText()).matches() && plantNamePattern.matcher(txtPlantName.getText()).matches()
                && doublePattern.matcher(txtUnitPrice.getText()).matches();

    }

    public void setErrorLabel() {
        if (!plantIdPattern.matcher(txtPlantId.getText()).matches()) {
            lblPlantIdError.setVisible(true);
        }
        if (!plantNamePattern.matcher(txtPlantName.getText()).matches()) {
            lblPlantNameError.setVisible(true);
        }
        if (!doublePattern.matcher(txtUnitPrice.getText()).matches()) {
            lblUnitPriceError.setVisible(true);
        }
    }


   /* public void setSearch(String s){

    }*/

    public void searchKeyReleased(KeyEvent keyEvent) {

        if(txtSearch.getText().startsWith("Pl")){
            ObservableList<Plant> ob = FXCollections.observableArrayList(
                    PlantCrudController.searchReportByPlantId(
                            "%"+txtSearch.getText()+"%"
                    )
            );
            tblPlant.setItems(ob);
        }else{
            ObservableList<Plant> obj = FXCollections.observableArrayList(PlantCrudController.searchReportByPlantType("%"+txtSearch.getText()+"%"));
            tblPlant.setItems(obj);
        }

    }

}

