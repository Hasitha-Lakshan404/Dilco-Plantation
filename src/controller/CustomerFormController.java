package controller;

import animatefx.animation.*;
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
import org.controlsfx.control.Notifications;
import util.GenerateAutoId;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class CustomerFormController {

    public TableView<Customer> tblCustomer;

    public JFXTextField txtCustomerId;
    public JFXTextField txtTelNo;
    public JFXTextField txtEmail;
    public JFXTextField txtCustomerName;
    public JFXTextField txtAddress;
    public Pane pneVisible;
    public BorderPane bPaneCus;
    public Label lblTelError;
    public Label lblNameError;
    public Label lblEmailError;
    public Label lblAddressError;
    public Label lblCusIdError;
    public TextField txtSearch;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    Pattern cusIdPattern = Pattern.compile("^(C-)[0-9]{2,9}$");
    Pattern cusNamePattern = Pattern.compile("^[A-z ]{3,25}$");
    Pattern cusAddressPattern = Pattern.compile("^[A-z 0-9,/]{3,25}$");
    Pattern cusTellNoPattern = Pattern.compile("^(077|076|071|078|075|070)[0-9]{7}$");
    Pattern cusEmail = Pattern.compile("^[a-z0-9]{4,}@(gmail|yahoo|ymail).com$");
    private double xOffset = 0;
    private double yOffset = 0;


    public void initialize() throws SQLException, ClassNotFoundException {
        new Pulse(bPaneCus).play();
        pneVisible.setVisible(false); //for refresh Table
        lordAllCustomer();
        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("cusId"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("cusName"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("cusEmail"));
        tblCustomer.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("cusAddress"));
        tblCustomer.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("telNo"));
        setCustomerId();

    }


    private void lordAllCustomer() throws SQLException, ClassNotFoundException {
        tblCustomer.setItems(CustomerCrudController.loadAllCustomers()); //get All Customers from crud
    }


    public void addCustomerOnAction(ActionEvent actionEvent)    {
        removeLblVisible();

        System.out.println(cusIdPattern.matcher(txtCustomerId.getText()).matches());
        System.out.println(cusNamePattern.matcher(txtCustomerName.getText()).matches());
        System.out.println(cusAddressPattern.matcher(txtAddress.getText()).matches());
        System.out.println(cusTellNoPattern.matcher(txtTelNo.getText()).matches());
        System.out.println(cusEmail.matcher(txtEmail.getText()).matches() + "\n");

        if (setValidate()) {


            Customer c = new Customer(txtCustomerId.getText(),txtCustomerName.getText() , txtEmail.getText(), txtAddress.getText(), txtTelNo.getText());

            //save Data and refresh Table
            tblCustomer.setItems(CustomerCrudController.addCustomer(c));


            clearData();
            setCustomerId();

            Notifications notifications = Notifications.create();
            notifications.title("Add Customer");
            notifications.text("New Customer Added Successfully...");
            notifications.hideAfter(Duration.seconds(8));
            notifications.position(Pos.BOTTOM_RIGHT);
            notifications.graphic(new ImageView("asserts/images/receptionC.png"));
            notifications.darkStyle();
            notifications.show();

        } else {
            setErrorLabel();
        }

    }


    public void setErrorLabel() {
        if (!cusIdPattern.matcher(txtCustomerId.getText()).matches()) {
            lblCusIdError.setVisible(true);
        }
        if (!cusNamePattern.matcher(txtCustomerName.getText()).matches()) {
            lblNameError.setVisible(true);
        }
        if (!cusAddressPattern.matcher(txtAddress.getText()).matches()) {
            lblAddressError.setVisible(true);
        }
        if (!cusTellNoPattern.matcher(txtTelNo.getText()).matches()) {
            lblTelError.setVisible(true);
        }
        if (!cusEmail.matcher(txtEmail.getText()).matches()) {
            lblEmailError.setVisible(true);
        }
    }

    public void removeLblVisible() {
        lblCusIdError.setVisible(false);
        lblNameError.setVisible(false);
        lblAddressError.setVisible(false);
        lblTelError.setVisible(false);
        lblEmailError.setVisible(false);

    }

    public void clearData() {
        txtCustomerId.setText(null);
        txtCustomerName.setText(null);
        txtAddress.setText(null);
        txtTelNo.setText(null);
        txtEmail.setText(null);
    }

    public boolean setValidate() {

        return cusIdPattern.matcher(txtCustomerId.getText()).matches() && cusNamePattern.matcher(txtCustomerName.getText()).matches() &&
                cusAddressPattern.matcher(txtAddress.getText()).matches() && cusTellNoPattern.matcher(txtTelNo.getText()).matches() &&
                cusEmail.matcher(txtEmail.getText()).matches();

    }



    public void contextEditOnAction(ActionEvent actionEvent) throws IOException {
        lordWidow("../view/CustomerUpdateForm.fxml", "ss");
    }

    public void contextDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Customer selectedItem = tblCustomer.getSelectionModel().getSelectedItem();

        BoxBlur blur = new BoxBlur(5, 5, 5);
        bPaneCus.setEffect(blur);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you Sure you want to Delete?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Customer!!");
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get().equals(ButtonType.YES)) {
            CustomerCrudController.deleteCustomer(selectedItem); //for delete
            lordAllCustomer();
            setCustomerId();
            bPaneCus.setEffect(null);
        } else {
            bPaneCus.setEffect(null);
        }


    }

    public void lordWidow(String location, String title) throws IOException {


        Customer selectItem = tblCustomer.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        CustomerUpdateFormController controller = loader.getController();

        controller.getAllData(selectItem, pneVisible);
        scene.setFill(Color.TRANSPARENT);

        Stage stage = new Stage(StageStyle.TRANSPARENT);

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

    public void pneVisibleMovedAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        lordAllCustomer();
        pneVisible.setVisible(false);
    }

    private void setCustomerId() {
        txtCustomerId.setText(GenerateAutoId.autoId("SELECT cusId FROM customer ORDER BY cusId DESC LIMIT 1", 1, 2, "C-01"));
    }



    public void searchKeyReleased(KeyEvent keyEvent) {
        if(txtSearch.getText().startsWith("C")){
            ObservableList<Customer> ob = FXCollections.observableArrayList(
                    CustomerCrudController.searchReportByCusId(
                            "%"+txtSearch.getText()+"%"
                    )
            );
            tblCustomer.setItems(ob);
        }else{
            ObservableList<Customer> obj = FXCollections.observableArrayList(CustomerCrudController.searchReportByName("%"+txtSearch.getText()+"%"));
            tblCustomer.setItems(obj);
        }
    }

    public void resetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        initialize();
    }
}
