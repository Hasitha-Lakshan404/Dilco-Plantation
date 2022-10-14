package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
import model.Order;
import org.controlsfx.control.Notifications;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class OrderUpdateFormController extends OrderFormController {

    public ImageView imgClose;
    public JFXTextField txtOrderId;
    public JFXTextField txtHandoverDate;
    public JFXComboBox<String> cmbCustomerId;
    public JFXComboBox<String> cmbItemId;
    public JFXTextField txtItemName;
    public JFXTextField txtQty;
    public JFXTextField txtAdvance;
    public JFXTextField txtStatus;
    public JFXTextField txtCustomerName;
    public JFXDatePicker dateHandOverDate;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtItemType;
    public Label lblTotalShow;
    public Label lblCusIdError;
    public Label lblItemIdError;
    public Label lblOrIdError;
    public Label lblHandoverDateError;
    public Label lblAdvanceError;
    public Label lblQtyError;


    String orderDate;
    String time;

    Pane paneVisible;

    public void initialize() {
        try {
            cmbCustomerId.setItems(CustomerCrudController.getCustomerIds());
            cmbItemId.setItems(PlantCrudController.getPlantIds());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        txtUnitPrice.setText("1");
        setThingsFromProduct();

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                ResultSet resultSet = CrudUtil.execute("SELECT * FROM customer WHERE cusId=?", newValue);

                while (resultSet.next()) {
                    txtCustomerName.setText(resultSet.getString(2));

                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        super.removeLblVisible();
        super.setQty();
    }

    public void setThingsFromProduct() {
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


    public void getAllData(Order selectItem, Pane paneVisible) {

        txtOrderId.setText(selectItem.getOrderId());
        cmbCustomerId.getSelectionModel().select(selectItem.getCusId());
        cmbItemId.getSelectionModel().select(selectItem.getProductId());
        txtItemName.setText(selectItem.getProductName());

//        txtHandoverDate.setText(String.valueOf(selectItem.getHandoverDate()));

        txtQty.setText(String.valueOf(selectItem.getQty()));
        txtAdvance.setText(String.valueOf(selectItem.getAdvance()));
        lblTotalShow.setText(String.valueOf(selectItem.getCost()));

        txtStatus.setText(selectItem.getStatus());
        txtCustomerName.setText(selectItem.getCusName());

        orderDate = (String.valueOf(selectItem.getOrderDate()));
        time = (String.valueOf(selectItem.getTime()));

        dateHandOverDate.setValue(selectItem.getHandoverDate().toLocalDate());


        this.paneVisible = paneVisible;
        txtOrderId.setEditable(false);
    }

    public void imgCloseOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) imgClose.getScene().getWindow();
        stage.close();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, ParseException {

        //---------------------------Order-Date-------------------------
        //Convert String date to the util date
        Date ODate = new SimpleDateFormat("yyyy-MM-dd").parse(orderDate);

        //Converting util date to the Sql date
        java.sql.Date orderDate = new java.sql.Date(ODate.getTime());
//        java.sql.Date date = new java.sql.Date(pickedDate.getDate().getTime()); //Get Datepicker value and Store in to the database

        //---------------------------Handover-Date-------------------------
        //Convert String date to the util date
//        Date hOverDate=new SimpleDateFormat("yyyy-MM-dd").parse(txtHandoverDate.getText());

        //Converting util date to the Sql date
//        java.sql.Date handOverDate = new java.sql.Date(hOverDate.getTime());


        //---------------------------Parse-String ->local Time--------------------
//        LocalTime times = LocalTime.parse(time); //local Time

        //->String to Sql Time
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        java.sql.Time timeValue = new java.sql.Time(formatter.parse(time).getTime());


        java.sql.Date real = new java.sql.Date(Calendar.getInstance().getTime().getTime());//getRealDate


        //for  update date
        Date hOverDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateHandOverDate.getValue().toString());

        //Converting util date to the Sql date
        java.sql.Date handOverDate = new java.sql.Date(hOverDate.getTime());


        //-----------------------------------------------------------------------------------------------------------


        super.removeLblVisible();

        if (!cmbCustomerId.getSelectionModel().isEmpty() && !cmbItemId.getSelectionModel().isEmpty()) {
            if (super.setValidate()) {

                if (OrderCrudController.updateValues(new Order(
                        txtOrderId.getText(), cmbCustomerId.getValue(), cmbItemId.getValue(),
                        txtItemName.getText(), orderDate, handOverDate,
                        timeValue, Integer.parseInt(txtQty.getText()), Double.parseDouble(txtAdvance.getText()),
                        Double.parseDouble(lblTotalShow.getText()), txtStatus.getText(), txtCustomerName.getText()
                ))) {
//                    new Alert(Alert.AlertType.CONFIRMATION, "Successful", ButtonType.CLOSE).show();
                    paneVisible.setVisible(true);




                } else {
                    new Alert(Alert.AlertType.ERROR, "Un-Successful", ButtonType.CLOSE).show();
                }
                super.clearData();
                txtStatus.setText(null);
                Stage st= (Stage) this.txtUnitPrice.getScene().getWindow();
                st.close();

                Notifications notifications = Notifications.create();
                notifications.title("Order Update");
                notifications.text("Update Successfully...");
                notifications.hideAfter(Duration.seconds(8));
                notifications.position(Pos.BOTTOM_RIGHT);
                notifications.graphic(new ImageView("asserts/images/Done.png"));
                notifications.darkStyle();
                notifications.show();
            } else {
                super.setErrorLabel();
            }

        }else {
            if (cmbCustomerId.getSelectionModel().isEmpty()) {
                lblCusIdError.setVisible(true);
            }
            if (cmbItemId.getSelectionModel().isEmpty()) {
                lblItemIdError.setVisible(true);
            }
        }


    }


}
