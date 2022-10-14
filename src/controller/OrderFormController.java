package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
import javafx.scene.control.cell.TextFieldTableCell;
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
import model.Order;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.GenerateAutoId;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.regex.Pattern;

public class OrderFormController {
    public JFXComboBox<String> cmbCustomerId;
    public JFXComboBox<String> cmbItemId;
    public JFXTextField txtOrderId;
    public JFXTextField txtAdvance;
    public JFXTextField txtItemName;
    public JFXTextField txtCustomerName;
    public JFXTextField txtAddress;
    public JFXTextField txtQty;
    public JFXTextField txtItemType;
    public JFXDatePicker dateHandOverDate;
    public JFXTextField txtUnitPrice;
    public TableView<Order> tblOrders;
    public TableColumn colPending;
    public Pane paneVisible;
    public BorderPane bPane;

    public Label lblTotalShow;
    public Label lblCusIdError;
    public Label lblNameError;
    public Label lblAddressError;

    public Label lblQtyError;
    public Label lblAdvanceError;
    public Label lblOrIdError;
    public Label lblItemIdError;
    public Label lblHandoverDateError;
    public TextField txtSearch;

    Pattern orderIdPattern = Pattern.compile("^(OR-)[0-9]{2,9}$");
    //    Pattern nameTypePattern = Pattern.compile("^[A-z ]{3,25}$");
//    Pattern cusAddressPattern = Pattern.compile("^[A-z 0-9,/]{3,25}$");
    Pattern qtyPattern = Pattern.compile("^[0-9]+$");
    Pattern doublePattern = Pattern.compile("^[0-9.]{1,}$");
    Pattern datePattern = Pattern.compile("^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$");

    private double xOffset = 0;
    private double yOffset = 0;

    public void initialize() throws SQLException, ClassNotFoundException {
        paneVisible.setVisible(false);
        tblOrders.setEditable(true);
        setAutoId();
        lordAllOrders();
        removeLblVisible();


        //setHandOverDate;
        LocalDate date=LocalDate.now().plusDays(30);
        dateHandOverDate.setValue(date);


        tblOrders.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tblOrders.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("cusId"));
        tblOrders.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("productId"));
        tblOrders.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("productName"));
        tblOrders.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        tblOrders.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("handoverDate"));
        tblOrders.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("time"));
        tblOrders.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblOrders.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("advance"));
        tblOrders.getColumns().get(9).setCellValueFactory(new PropertyValueFactory<>("cost"));
        tblOrders.getColumns().get(10).setCellValueFactory(new PropertyValueFactory<>("status"));
        tblOrders.getColumns().get(11).setCellValueFactory(new PropertyValueFactory<>("cusName"));


        cmbCustomerId.setItems(CustomerCrudController.getCustomerIds());
        cmbItemId.setItems(PlantCrudController.getPlantIds());

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                ResultSet resultSet = CrudUtil.execute("SELECT * FROM Customer WHERE cusId=?", newValue);

                while (resultSet.next()) {
                    txtCustomerName.setText(resultSet.getString(2));
                    txtAddress.setText(resultSet.getString(4));
                }


            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

        setThingsFromProduct();
//        cmbCustomerId.getSelectionModel().select(0);
//        dateHandOverDate.getValue();

        colPending.setCellFactory(TextFieldTableCell.forTableColumn());

        setQty();


    }

    public void setThingsFromProduct(){
        cmbItemId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                ResultSet resultSet = CrudUtil.execute("SELECT * FROM plant WHERE plantId=?", newValue);


                while (resultSet.next()) {
                    txtItemName.setText(resultSet.getString(3));
                    txtItemType.setText(resultSet.getString(2));
                    txtUnitPrice.setText(String.valueOf(resultSet.getDouble(5)));

                    if (!txtQty.getText().equals("")) {
                        int qty = Integer.parseInt(txtQty.getText());
                        double uP = 0;
                        uP = resultSet.getInt(5);
                        lblTotalShow.setText(String.valueOf(uP * qty));
                    }

                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void setAutoId() {
        txtOrderId.setText(GenerateAutoId.autoId("SELECT orderId FROM `order`  ORDER BY orderId DESC LIMIT 1", 1, 3, "OR-01"));
    }

    public void setQty() {

        txtQty.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!txtQty.getText().isEmpty()) {
                if(qtyPattern.matcher(txtQty.getText()).matches()) {
                    if (!txtUnitPrice.getText().isEmpty() && !txtUnitPrice.getText().equals("")) {

                        int qty = Integer.parseInt(newValue);
                        double uP = 0;
                        uP = Double.parseDouble(txtUnitPrice.getText());
                        lblTotalShow.setText(String.valueOf(uP * qty));
                    }
                }
            }
        });
    }

    public void btnPlaceOrder(ActionEvent actionEvent) throws ParseException, SQLException, ClassNotFoundException {
        removeLblVisible();
        if (!cmbCustomerId.getSelectionModel().isEmpty() && !cmbItemId.getSelectionModel().isEmpty()) {

            if (setValidate()) {

//        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Date orDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());  //getRealDate

                String orderHDate = String.valueOf(dateHandOverDate.getValue());
                java.util.Date ODate = new SimpleDateFormat("yyyy-MM-dd").parse(orderHDate);
                //Converting util date to the Sql date
                java.sql.Date handoverDate = new java.sql.Date(ODate.getTime());

                //real time to sql time
                java.sql.Time time = new java.sql.Time(Calendar.getInstance().getTime().getTime());

                dateHandOverDate.getValue();

                int qty = Integer.parseInt(txtQty.getText());
                double unPrice = Double.parseDouble(txtUnitPrice.getText());

                Order o = new Order(txtOrderId.getText(), cmbCustomerId.getValue(), cmbItemId.getValue(), txtItemName.getText(), orDate, handoverDate, time,
                        qty, Double.parseDouble(txtAdvance.getText()), (qty * unPrice), "Pending", txtCustomerName.getText()
                );
                tblOrders.setItems(OrderCrudController.placeOrder(o,unPrice));
                clearData();
                txtAddress.setText("");
                setAutoId();


                /*Notifications notifications = Notifications.create();
                notifications.title("Order Update");
                notifications.text("Update Successfully...");
                notifications.hideAfter(Duration.seconds(8));
                notifications.position(Pos.TOP_RIGHT);
                notifications.graphic(new ImageView("asserts/images/Done.png"));
                        notifications.darkStyle();
                notifications.show();*/


            } else {
                setErrorLabel();
            }
        } else {
            if (cmbCustomerId.getSelectionModel().isEmpty()) {
                lblCusIdError.setVisible(true);
            }
            if (cmbItemId.getSelectionModel().isEmpty()) {
                lblItemIdError.setVisible(true);
            }
        }

    }

    private void lordAllOrders() throws SQLException, ClassNotFoundException {
        tblOrders.setItems(OrderCrudController.loadAllOrders());
    }

    public void colPendingEditCommit(TableColumn.CellEditEvent cellEditEvent) {
        Order order = tblOrders.getSelectionModel().getSelectedItem();

        order.setStatus(cellEditEvent.getNewValue().toString());

        //Update Quarry->
        try {
            if (OrderCrudController.updateValues(order)) {
                lordAllOrders();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void contextPendingEditOnAction(ActionEvent actionEvent) throws IOException {
        lordWidow("../view/OrderUpdateForm.fxml", "s");

    }

    public void contextPendingDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Order selectedItem = tblOrders.getSelectionModel().getSelectedItem();

        BoxBlur blur = new BoxBlur(5, 5, 5);
        bPane.setEffect(blur);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you Sure you want to Delete?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Order!!");
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get().equals(ButtonType.YES)) {
            OrderCrudController.deleteOrder(selectedItem);
            lordAllOrders();
            setAutoId();
            bPane.setEffect(null);
        } else {
            bPane.setEffect(null);
        }


    }

    public void lordWidow(String location, String title) throws IOException {
        Order selectItem = tblOrders.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        OrderUpdateFormController controller = loader.getController();

        controller.getAllData(selectItem, paneVisible);

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


    public void pneMouseMoveOnAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        lordAllOrders();
        paneVisible.setVisible(false);
    }

    public void setErrorLabel() {
        if (!orderIdPattern.matcher(txtOrderId.getText()).matches()) {
            lblOrIdError.setVisible(true);
        }
        if (!qtyPattern.matcher(txtQty.getText()).matches()) {
            lblQtyError.setVisible(true);
        }
        if (!doublePattern.matcher(txtAdvance.getText()).matches()) {
            lblAdvanceError.setVisible(true);
        }
        if(dateHandOverDate.getValue()!=null) {
            if (doublePattern.matcher(dateHandOverDate.getValue().toString()).matches()) {
                lblHandoverDateError.setVisible(true);
            }
        }else{
            lblHandoverDateError.setVisible(true);
        }

    }

    public void removeLblVisible() {
        lblCusIdError.setVisible(false);
        lblItemIdError.setVisible(false);
        lblOrIdError.setVisible(false);
        lblAdvanceError.setVisible(false);
        lblQtyError.setVisible(false);
        lblHandoverDateError.setVisible(false);


    }

    public void clearData() {
        cmbCustomerId.setValue(null);
        cmbItemId.setValue(null);
        txtOrderId.setText(null);
        txtCustomerName.setText(null);
        txtItemName.setText("");
        txtItemType.setText("");
        txtAdvance.setText("");
        txtUnitPrice.setText("");
//        txtQty.setText("0");
        lblTotalShow.setText(null);

    }

    public boolean setValidate() {

        if(dateHandOverDate.getValue()!=null){
            return orderIdPattern.matcher(txtOrderId.getText()).matches() &&
                    qtyPattern.matcher(txtQty.getText()).matches() &&
                    doublePattern.matcher(txtAdvance.getText()).matches() && datePattern.matcher(dateHandOverDate.getValue().toString()).matches();
        }else{
            lblHandoverDateError.setVisible(true);
        }
        return false;

    }

    public void calOnAction(ActionEvent actionEvent) {
        try {

            ProcessBuilder p = new ProcessBuilder();

            p.command("calc.exe");
            p.start();

        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public void searchKeyReleased(KeyEvent keyEvent) {
        if(txtSearch.getText().startsWith("OR")){
            ObservableList<Order> ob = FXCollections.observableArrayList(OrderCrudController.searchReportByOrId("%"+txtSearch.getText()+"%"));
            tblOrders.setItems(ob);

        }else if(txtSearch.getText().startsWith("C")){
            ObservableList<Order> obj = FXCollections.observableArrayList(OrderCrudController.searchReportByCusId("%"+txtSearch.getText()+"%"));
            tblOrders.setItems(obj);

        }else if(txtSearch.getText().startsWith("Pl")){
            ObservableList<Order> obj = FXCollections.observableArrayList(OrderCrudController.searchReportByProductId("%"+txtSearch.getText()+"%"));
            tblOrders.setItems(obj);

        }else{
            ObservableList<Order> obj = FXCollections.observableArrayList(OrderCrudController.searchReportByName("%"+txtSearch.getText()+"%"));
            tblOrders.setItems(obj);
        }

    }
}
