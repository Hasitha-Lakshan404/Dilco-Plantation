package controller;

import com.jfoenix.controls.*;
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
import model.Payment;
import model.Supplier;
import model.SupplierOrder;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.GenerateAutoId;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Optional;
import java.util.regex.Pattern;

public class SupplierManagementFormController {

    public Label lblOrderIdError;
    public Label cmbOrSelectSupplierTypeError;
    public Label lblOrDetalError;
    public Label lblOrDateError;
    public Label lblTimeError;
    public Label lblOrQtyError;
    public Label lblOrUnitpError;
    public JFXTextField txtAddSupEmail;
    public JFXTextField txtAddSupName;
    public JFXTextField txtAddSuppId;
    public JFXTextField txtAddSupAddress;
    public JFXTextField txtAddTelNo;
    public JFXComboBox<String> cmbAddSelectType;
    public JFXTextField txtAddProductName;
    public JFXTextField txtOrderId;
    public JFXTextField txtOrName;
    public JFXComboBox<String> cmbOrProductName;
    public JFXTextField txtOrUnitPrice;
    public JFXDatePicker DatePlaceOrderDate;
    public JFXTimePicker TimePLaceOrderTime;
    public JFXTextField txtOrDetails;
    public JFXTextField txtOrTotalCost;
    public JFXTextField txtOrQuantity;
    public JFXComboBox<String> cmbOrSupplierId;
    public JFXTextArea txtOrProductDescription;
    public JFXTextField txtOrProductName;
    public TableView<Supplier> tblSupplier;
    public TableView<SupplierOrder> tblSupplierOrder;
    public Pane pneVisible;
    public JFXComboBox<String> cmbAddProductName;
    public BorderPane bPane;
    public JFXComboBox<String> cmbPaymentMethod;
    public Label lblSupplierIdError;
    public Label cmbSelectTypeError;
    public Label cmbSelectNameTypeError;
    public Label lblSupplierNameError;
    public Label lblEmailError;
    public Label lblAddressError;
    public Label lblTellNoError;
    public Pane pneVisibleSupOrder;
    public TextField txtSearch;
    public Label cmbOrSelectPaymentMethod;

    Pattern idPattern = Pattern.compile("^(S-)[0-9]{2,9}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,25}$");
    Pattern addressPattern = Pattern.compile("^[A-z 0-9,/]{3,25}$");
    Pattern tellNoPattern = Pattern.compile("^(077|076|071|078|075|070)[0-9]{7}$");
    Pattern emailPattern = Pattern.compile("^[a-z0-9]{4,}@(gmail|yahoo|ymail).com$");

    Pattern oRidPattern = Pattern.compile("^(SOR-)[0-9]{2,9}$");
    Pattern datePattern = Pattern.compile("^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$");
    //    Pattern timePattern=Pattern.compile("^(0?[1-9]|1[012])(:[0-5]\\d) [APap][mM]$");
    Pattern timePattern = Pattern.compile("^(0?[1-9]|1[0123456789]|2[01234])(:[0-5]\\d)$");
    Pattern qtyPattern = Pattern.compile("^[0-9]+$");
    Pattern doublePattern = Pattern.compile("^[0-9.]{1,}$");

    private double xOffset = 0;
    private double yOffset = 0;

    public void initialize() throws SQLException, ClassNotFoundException {
        removeSupLblVisible();
        removeOrLblVisible();
        pneVisible.setVisible(false);
        setProductType();
        setSupplierId();
        cmbOrSelectPaymentMethod.setVisible(false);

        txtOrProductName.setEditable(false);
        setAddProductNames();

        ObservableList<String> data = FXCollections.observableArrayList("Cash On Delivery", "Online Pay");
        cmbPaymentMethod.setItems(data);

        DatePlaceOrderDate.setEditable(false);
        TimePLaceOrderTime.setEditable(false);

        setUnitPFromProduct();
        setFromSupId();


        setTotalFromUp();
        setTotal();

        loadAllSuppliers();
        loadAllSupplierOrders();

        tblSupplier.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("supId"));
        tblSupplier.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("name"));
        tblSupplier.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("email"));
        tblSupplier.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("address"));
        tblSupplier.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("telNo"));
        tblSupplier.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("productType"));
        tblSupplier.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("productName"));

        tblSupplierOrder.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("OrderId"));
        tblSupplierOrder.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("productName"));
        tblSupplierOrder.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("supId"));
        tblSupplierOrder.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("description"));
        tblSupplierOrder.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("qty"));
        tblSupplierOrder.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("unitPrice"));
        tblSupplierOrder.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("totalCost"));
        tblSupplierOrder.getColumns().get(7).setCellValueFactory(new PropertyValueFactory("date"));
        tblSupplierOrder.getColumns().get(8).setCellValueFactory(new PropertyValueFactory("time"));

        setAutoId();
        setAutoSupId();

        LocalDate lc = LocalDate.now();
        DatePlaceOrderDate.setValue(lc);

        LocalTime lt = LocalTime.now();
        TimePLaceOrderTime.setValue(lt);
    }

    public void setFromSupId() {
        cmbOrSupplierId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                ResultSet resultSet = CrudUtil.execute("SELECT * FROM supplier WHERE supId=?", newValue);

                if (resultSet.next()) {
//                    txtOrUnitPrice.setText(String.valueOf(resultSet.getDouble(5)));
                    txtOrName.setText(resultSet.getString(2));
//                    cmbOrProductName.setValue(resultSet.getString(7));
                    txtOrProductName.setText(resultSet.getString(7));
                }
                setTotal();


            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    ////////////////////////===============================
    public void setAddProductNames() {
        cmbAddSelectType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!cmbAddSelectType.getSelectionModel().isEmpty()) {
                try {
                    ResultSet resultSet = CrudUtil.execute("SELECT ProductName FROM product  WHERE productType=?", newValue);

                    ObservableList<String> pOb = FXCollections.observableArrayList();

                    while (resultSet.next()) {
                        pOb.add(resultSet.getString(1));
                    }
                    cmbAddProductName.setItems(pOb);


                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }

        });
    }

    public void setUnitPFromProduct() {
        txtOrProductName.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                ResultSet resultSet = CrudUtil.execute("SELECT * FROM product WHERE productName=?", newValue);

                if (resultSet.next()) {
                    txtOrUnitPrice.setText(String.valueOf(resultSet.getDouble(5)));
                    txtOrProductDescription.setText(String.valueOf(resultSet.getString(4)));

                    setTotal();
                }


            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void setTotalFromUp() {
        txtOrUnitPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!txtOrUnitPrice.getText().equals("")) {
                    if (!txtOrUnitPrice.getText().equals("") && !txtOrQuantity.getText().equals("")) {
                        int qty = Integer.parseInt(txtOrQuantity.getText());
                        double uP = Double.parseDouble(txtOrUnitPrice.getText());
                        txtOrTotalCost.setText(String.valueOf(qty * uP));
                    }
                }
            }
        });
    }

    public void setTotal() {
//
        txtOrQuantity.textProperty().addListener((observable, oldValue, newValue) -> {
            if (qtyPattern.matcher(txtOrQuantity.getText()).matches()) {

                if (!txtOrProductName.getText().isEmpty()) {
                    if (!txtOrUnitPrice.getText().isEmpty() && !txtOrQuantity.getText().isEmpty()) {
                        int qty = Integer.parseInt(txtOrQuantity.getText());
                        double uP = Double.parseDouble(txtOrUnitPrice.getText());
                        txtOrTotalCost.setText(String.valueOf(qty * uP));
                    }
                }
                if (txtOrQuantity.getText().equals("")) {
                    txtOrTotalCost.setText("");
                }
            }
        });
//        }


    }

    public void setSupplierId() throws SQLException, ClassNotFoundException {
        cmbOrSupplierId.setItems(SupplierCrudController.getSupplierId());
    }

    private void setProductType() throws SQLException, ClassNotFoundException {
        cmbAddSelectType.setItems(StockCrudController.getProductType());
    }

    public void btnAddNewSupplier(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        removeSupLblVisible();
        if (!cmbAddSelectType.getSelectionModel().isEmpty() && !cmbAddProductName.getSelectionModel().isEmpty()) {

            if (setSupValidate()) {
                Supplier s = new Supplier(txtAddSuppId.getText(), txtAddSupName.getText(), txtAddSupEmail.getText(), txtAddSupAddress.getText(), txtAddTelNo.getText(), cmbAddSelectType.getValue(), cmbAddProductName.getValue());
                SupplierCrudController.addSupplier(s);
                loadAllSuppliers();
                clearData();
                setAutoSupId();

                Notifications notifications = Notifications.create();
                notifications.title("Add Supplier");
                notifications.text("New Supplier Added Successfully...");
                notifications.hideAfter(Duration.seconds(8));
                notifications.position(Pos.BOTTOM_RIGHT);
                notifications.graphic(new ImageView("asserts/images/sup1.png"));
                notifications.darkStyle();
                notifications.show();
            } else {
                setSupErrorLabel();
            }
        } else {
            if (cmbAddSelectType.getSelectionModel().isEmpty()) {
                cmbSelectTypeError.setVisible(true);
            }
            if (cmbAddProductName.getSelectionModel().isEmpty()) {
                cmbSelectNameTypeError.setVisible(true);
            }

        }


    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {

        removeOrLblVisible();
        cmbOrSelectPaymentMethod.setVisible(false);

        if (!cmbOrSupplierId.getSelectionModel().isEmpty()) {
            if (setOrValidate()) {
                if (DatePlaceOrderDate.getValue() != null && TimePLaceOrderTime.getValue() != null && !txtOrDetails.getText().isEmpty()) {

                    //supplier Order
                    java.sql.Date realTimeDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());//getRealDate
                    //real time to sql time
                    java.sql.Time time = new java.sql.Time(Calendar.getInstance().getTime().getTime());

                    if (!cmbPaymentMethod.getSelectionModel().isEmpty()) {

                        //add payment
                        String paymentNo = GenerateAutoId.autoId("SELECT paymentNo FROM payment ORDER BY paymentNo DESC LIMIT 1", 1, 2, "P-01");

                        Payment p = new Payment(paymentNo, "SUPPLIER", "Buy",
                                cmbOrSupplierId.getValue(), txtOrName.getText(), realTimeDate, cmbPaymentMethod.getValue(), Double.parseDouble(txtOrTotalCost.getText()));

                        EmployeePaymentCrudController.addEmployeePayment(p);

                        //Sup
                        SupplierOrder so = new SupplierOrder(txtOrderId.getText(), txtOrProductName.getText(), cmbOrSupplierId.getValue(), txtOrDetails.getText(), Integer.parseInt(txtOrQuantity.getText()),
                                Double.parseDouble(txtOrUnitPrice.getText()), Double.parseDouble(txtOrTotalCost.getText()), realTimeDate, time);

                        tblSupplierOrder.setItems(SupplierOrderCrudController.placeOrder(so));
                        clearOrData();
                        setAutoId();

                        Notifications notifications = Notifications.create();
                        notifications.title("Place Order");
                        notifications.text("Order Placed Successfully...");
                        notifications.hideAfter(Duration.seconds(8));
                        notifications.position(Pos.BOTTOM_RIGHT);
                        notifications.graphic(new ImageView("asserts/images/orderP.png"));
                        notifications.darkStyle();
                        notifications.show();

                    } else {
                        cmbOrSelectPaymentMethod.setVisible(true);
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
                setOrErrorLabel();
            }
        } else {
            cmbOrSelectSupplierTypeError.setVisible(true);
        }

    }

    private void setAutoId() {
        txtOrderId.setText(GenerateAutoId.autoId("SELECT orderId FROM `supplierorder`  ORDER BY orderId DESC LIMIT 1", 1, 4, "SOR-01"));

    }

    private void setAutoSupId() {
        txtAddSuppId.setText(GenerateAutoId.autoId("SELECT supId FROM supplier ORDER BY supId DESC LIMIT 1", 1, 2, "S-01"));

    }

    private void loadAllSuppliers() throws SQLException, ClassNotFoundException {
        tblSupplier.setItems(SupplierCrudController.loadAllSuppliers());
    }

    private void loadAllSupplierOrders() throws SQLException, ClassNotFoundException {
        tblSupplierOrder.setItems(SupplierOrderCrudController.loadAllSupplierOrders());
    }

    public void contextSupplierEditOnAction(ActionEvent actionEvent) throws IOException {
        lordWidow("../view/SupplierUpdateForm.fxml", "ss");
    }

    public void contextSupplierDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Supplier selectedItem = tblSupplier.getSelectionModel().getSelectedItem();

        BoxBlur blur = new BoxBlur(5, 5, 5);
        bPane.setEffect(blur);


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you Sure you want to Delete?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Supplier!!");
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get().equals(ButtonType.YES)) {
            SupplierCrudController.deleteSupplier(selectedItem); //for delete
            loadAllSuppliers();
            setAutoSupId();
            bPane.setEffect(null);
        } else {
            bPane.setEffect(null);
        }
    }


    public void lordWidow(String location, String title) throws IOException {

        Supplier selectItem = tblSupplier.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        SupplierUpdateFormController controller = loader.getController();

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

    public void pneMouseMovedOnAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        pneVisible.setVisible(false);
        loadAllSuppliers();
    }


    //==================================Supplier Order=================

    public void contextSupOrderUpdateOnAction(ActionEvent actionEvent) throws IOException {
        lordWidow("../view/SupplierOrderUpdateForm.fxml");
    }

    public void contextSupOrderDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        SupplierOrder selectedItem = tblSupplierOrder.getSelectionModel().getSelectedItem();

        BoxBlur blur = new BoxBlur(5, 5, 5);
        bPane.setEffect(blur);


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you Sure you want to Delete?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Supplier Order!!");
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get().equals(ButtonType.YES)) {

//            SupplierCrudController.deleteSupplier(selectedItem); //for delete
            SupplierOrderCrudController.deleteSupplierOrder(selectedItem);
            loadAllSupplierOrders();
            setAutoId();
            bPane.setEffect(null);
        } else {
            bPane.setEffect(null);
        }
    }

    public void lordWidow(String location) throws IOException {

        SupplierOrder selectItem = tblSupplierOrder.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        SupplierOrderUpdateFormController controller = loader.getController();

        controller.getAllData(selectItem, pneVisibleSupOrder);

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

    public void removeSupLblVisible() {
        lblSupplierIdError.setVisible(false);
        lblSupplierNameError.setVisible(false);
        lblEmailError.setVisible(false);
        lblAddressError.setVisible(false);
        lblTellNoError.setVisible(false);
        cmbSelectTypeError.setVisible(false);
        cmbSelectNameTypeError.setVisible(false);

    }

    private void clearData() {
        /*txtAddSuppId.setText(null);
        txtAddSupName.setText(null);
        txtAddSupAddress.setText(null);
        txtAddSupEmail.setText(null);
        txtAddTelNo.setText(null);
        if (!cmbAddSelectType.getSelectionModel().isEmpty()) {
            cmbAddSelectType.setValue(null);
        }
        if (!cmbAddProductName.getSelectionModel().isEmpty()) {

            cmbAddProductName.setValue(null);
        }*/
        txtAddSuppId.setText("");
        txtAddSupName.setText("");
        txtAddSupAddress.setText("");
        txtAddSupEmail.setText("");
        txtAddTelNo.setText("");
        if (!cmbAddSelectType.getSelectionModel().isEmpty()) {
            cmbAddSelectType.setValue(null);
        }
        if (!cmbAddProductName.getSelectionModel().isEmpty()) {

            cmbAddProductName.setValue(null);
        }
    }

    public boolean setSupValidate() {
        return idPattern.matcher(txtAddSuppId.getText()).matches() && namePattern.matcher(txtAddSupName.getText()).matches()
                && emailPattern.matcher(txtAddSupEmail.getText()).matches() && tellNoPattern.matcher(txtAddTelNo.getText()).matches()
                && addressPattern.matcher(txtAddSupAddress.getText()).matches();

    }

    public void setSupErrorLabel() {
        if (!idPattern.matcher(txtAddSuppId.getText()).matches()) {
            lblSupplierIdError.setVisible(true);
        }
        if (!namePattern.matcher(txtAddSupName.getText()).matches()) {
            lblSupplierNameError.setVisible(true);
        }
        if (!emailPattern.matcher(txtAddSupEmail.getText()).matches()) {
            lblEmailError.setVisible(true);
        }
        if (!tellNoPattern.matcher(txtAddTelNo.getText()).matches()) {
            lblTellNoError.setVisible(true);
        }
        if (!addressPattern.matcher(txtAddSupAddress.getText()).matches()) {
            lblAddressError.setVisible(true);
        }
    }


    public void removeOrLblVisible() {
        lblOrderIdError.setVisible(false);
        cmbOrSelectSupplierTypeError.setVisible(false);
        lblOrQtyError.setVisible(false);
        lblOrUnitpError.setVisible(false);
        lblOrDetalError.setVisible(false);
        lblOrDateError.setVisible(false);
        lblTimeError.setVisible(false);

    }

    private void clearOrData() {
//        txtOrQuantity.setText(null);
        /*txtOrderId.setText(null);
//        txtOrUnitPrice.setText("0.00");
        txtOrProductName.setText(null);
        txtOrTotalCost.setText("0.00");
        txtOrName.setText(null);
        txtOrProductDescription.setText(null);
        txtOrDetails.setText(null);*/

        txtOrQuantity.setText("");
        txtOrderId.setText("");
//        txtOrUnitPrice.setText("0.00");
        txtOrProductName.setText("");
        txtOrTotalCost.setText("0.00");
        txtOrName.setText("");
        txtOrProductDescription.setText("");
        txtOrDetails.setText("");



    }

    public boolean setOrValidate() {
        return oRidPattern.matcher(txtOrderId.getText()).matches() && qtyPattern.matcher(txtOrQuantity.getText()).matches()
                && doublePattern.matcher(txtOrUnitPrice.getText()).matches() /*&& datePattern.matcher(DatePlaceOrderDate.getValue().toString()).matches()
                && timePattern.matcher(TimePLaceOrderTime.getValue().toString()).matches()*/;
    }

    public void setOrErrorLabel() {
        if (!oRidPattern.matcher(txtOrderId.getText()).matches()) {
            lblOrderIdError.setVisible(true);
        }
        if (!qtyPattern.matcher(txtOrQuantity.getText()).matches()) {
            lblOrQtyError.setVisible(true);
        }
        if (!doublePattern.matcher(txtOrUnitPrice.getText()).matches()) {
            lblOrUnitpError.setVisible(true);

        }
        if (DatePlaceOrderDate.getValue() != null) {
            if (!datePattern.matcher(DatePlaceOrderDate.getValue().toString()).matches()) {
                lblOrDateError.setVisible(true);

            }
        }
        if (TimePLaceOrderTime.getValue() != null) {
            if (!timePattern.matcher(TimePLaceOrderTime.getValue().toString()).matches()) {
                lblTimeError.setVisible(true);
            }
        }

    }

    public void pneMouseMovedOrderOnAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        pneVisibleSupOrder.setVisible(false);
        loadAllSupplierOrders();
    }

    public void searchKeyReleased(KeyEvent keyEvent) {
        if (txtSearch.getText().startsWith("SOR")) {
            ObservableList<SupplierOrder> ob = FXCollections.observableArrayList(
                    SupplierOrderCrudController.searchReportByOrderId(
                            "%" + txtSearch.getText() + "%"
                    )
            );
            tblSupplierOrder.setItems(ob);
        } else if (txtSearch.getText().startsWith("S-")) {
            ObservableList<SupplierOrder> obj = FXCollections.observableArrayList(SupplierOrderCrudController.searchReportBySupId("%" + txtSearch.getText() + "%"));
            tblSupplierOrder.setItems(obj);
        } else {
            ObservableList<SupplierOrder> obj = FXCollections.observableArrayList(SupplierOrderCrudController.searchReportByProductName("%" + txtSearch.getText() + "%"));
            tblSupplierOrder.setItems(obj);
        }
    }
}
