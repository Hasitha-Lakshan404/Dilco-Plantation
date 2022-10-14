package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Employee;
import model.Payment;
import model.Plant;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.GenerateAutoId;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Optional;
import java.util.regex.Pattern;

public class EmployeeManagementFormController {

    public JFXTextField txtEmpId;
    public JFXComboBox<String> cmbRole;
    public JFXTextField txtName;
    public JFXTextField txtTellNo;
    public JFXTextField txtFullName;
    public JFXPasswordField pwdPassword;
    public JFXPasswordField pwdConPassword;
    public TableView<Employee> tblEmployee;
    public JFXTextField txtEmail;
    public JFXTextField txtAddress;
    public Pane pneVisible;
    public BorderPane bPane;
    public JFXTextField txtPaymentNo;
    public JFXTextField txtPyTotal;
    public JFXComboBox<String> cmbPyPaymentMethod;
    public JFXComboBox<String> cmbPySelectEmployeeId;
    public JFXTextField txtPyEmpName;
    public JFXTextField txtPyEmpType;
    public JFXTextField txtPyStatus;

    public Label lblEmpIdError;
    public Label lblNameError;
    public Label lblEmailError;
    public Label lblAddressError;
    public Label lblTelError;
    public Label lblCmbRoleError;
    public Label lblUserNameError;
    public Label lblPasswordError;
    public Label lblConPasswordError;
    public Label lblCheckPasswordError;

    public Label lblPyPaymentIdError;
    public Label cmbPySelectEmpIdError;
    public Label lblPyTotalError;
    public Label cmbPyPaymentMethodError;


    Pattern idPattern = Pattern.compile("^(EM-)[0-9]{2,9}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,25}$");
    Pattern addressPattern = Pattern.compile("^[A-z 0-9,/]{3,25}$");
    Pattern tellNoPattern = Pattern.compile("^(077|076|071|078|075|070)[0-9]{7}$");
    Pattern emailPattern = Pattern.compile("^[a-z0-9]{4,}@(gmail|yahoo|ymail).com$");

    Pattern userNamePattern = Pattern.compile("^[A-z ]{3,25}$");
    Pattern passwordPattern = Pattern.compile("^[A-z0-9]{3,25}$");


    Pattern pyIdPattern = Pattern.compile("^(P-)[0-9]{2,9}$");
    Pattern doublePattern = Pattern.compile("^[0-9.]{1,}$");



    public void initialize() throws SQLException, ClassNotFoundException {
        removeLabelError();
        pneVisible.setVisible(false);
        ObservableList<String> data= FXCollections.observableArrayList("ADMIN","MANAGER","STAFF");
        cmbRole.setItems(data);

        loadAllEmployees();
        removePyVisible();

        tblEmployee.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("empId"));
        tblEmployee.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("role"));
        tblEmployee.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("FullName"));
        tblEmployee.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("empAddress"));
        tblEmployee.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("email"));
        tblEmployee.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("tellNo"));
        tblEmployee.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("UserName"));
        tblEmployee.getColumns().get(7).setCellValueFactory(new PropertyValueFactory("Password"));

        setDisable();
        setAutoId();

        //======Payment Management=====
        ObservableList<String> data1= FXCollections.observableArrayList("Cash Payment","Online pay");
        cmbPyPaymentMethod.setItems(data1);
        setAutoPaymentId();
        setEmpIds();
        addListenerToCmb();
    }

    private void loadAllEmployees() throws SQLException, ClassNotFoundException {
        tblEmployee.setItems(EmployeeCrudController.loadAllEmployee());

    }

    public void btnAddEmployee(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        removeLabelError();

            if(setValidateToEmp()) {
                if (cmbRole.getValue().equals("STAFF")) {
                    Employee e = new Employee(txtEmpId.getText(), cmbRole.getValue(), txtFullName.getText(),
                            txtAddress.getText(), txtEmail.getText(), txtTellNo.getText(), "No Access",
                            "No Access");
                    EmployeeCrudController.addEmployee(e);
                    loadAllEmployees();
                    clearText();
                    setAutoId();

                    Notifications notifications = Notifications.create();
                    notifications.title("Add STAFF");
                    notifications.text("New Employee Added Successfully...");
                    notifications.hideAfter(Duration.seconds(8));
                    notifications.position(Pos.BOTTOM_RIGHT);
                    notifications.graphic(new ImageView("asserts/images/mem.png"));
                    notifications.darkStyle();
                    notifications.show();


                } else if (pwdPassword.getText().equals(pwdConPassword.getText())) {
                    Employee e = new Employee(txtEmpId.getText(), cmbRole.getValue(), txtFullName.getText(),
                            txtAddress.getText(), txtEmail.getText(), txtTellNo.getText(), txtName.getText(),
                            pwdPassword.getText());
                    EmployeeCrudController.addEmployee(e);
                    loadAllEmployees();
                    clearText();
                    setAutoId();

                    Notifications notifications = Notifications.create();
                    notifications.title("Add Employee");
                    notifications.text("New Employee Added Successfully...");
                    notifications.hideAfter(Duration.seconds(8));
                    notifications.position(Pos.BOTTOM_RIGHT);
                    notifications.graphic(new ImageView("asserts/images/mem.png"));
                    notifications.darkStyle();
                    notifications.show();
                } else {
                    lblCheckPasswordError.setVisible(true);
                }
            }else{
                setErrorLabel();
            }
    }

    private void setDisable( ){
        cmbRole.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(!cmbRole.getValue().equals("")){

                if(cmbRole.getValue().equals("STAFF")){
                    txtName.setText("NoAccess");
                    pwdPassword.setText(null);
                    pwdConPassword.setText(null);

                    txtName.setEditable(false);
                    pwdPassword.setEditable(false);
                    pwdConPassword.setEditable(false);
                }else{
                    txtName.setText("");

                    txtName.setEditable(true);
                    pwdPassword.setEditable(true);
                    pwdConPassword.setEditable(true);
                }

            }


        });
    }

    public void contextUpdateOnAction(ActionEvent actionEvent) throws IOException {
        lordWidow("../view/EmployeeUpdateForm.fxml", "ss");


    }

    public void contextDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Employee selectedItem = tblEmployee.getSelectionModel().getSelectedItem();

        BoxBlur blur = new BoxBlur(5, 5, 5);
        bPane.setEffect(blur);


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you Sure you want to Delete?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Employee!!");
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get().equals(ButtonType.YES)) {
            EmployeeCrudController.deleteEmp(selectedItem); //for delete Plant
            loadAllEmployees();
            setAutoId();
            bPane.setEffect(null);
        } else {
            bPane.setEffect(null);
        }
    }

    private double xOffset = 0;
    private double yOffset = 0;

    public void lordWidow(String location, String title) throws IOException {
        Employee selectItem = tblEmployee.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        EmployeeUpdateFormController controller = loader.getController();

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
        loadAllEmployees();
    }

    private void setAutoId(){
        txtEmpId.setText(GenerateAutoId.autoId("SELECT empId FROM employee ORDER BY empId DESC LIMIT 1", 1, 3,"EM-01"));
    }


    //===========Payment  Details ==========

    private void setAutoPaymentId(){
        txtPaymentNo.setText(GenerateAutoId.autoId("SELECT paymentNo FROM payment ORDER BY paymentNo DESC LIMIT 1", 1, 2,"P-01"));
    }


    private void setEmpIds() throws SQLException, ClassNotFoundException {
        cmbPySelectEmployeeId.setItems(EmployeeCrudController.getEmployeeId());
    }

    private void addListenerToCmb(){
        cmbPySelectEmployeeId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(!cmbPySelectEmployeeId.getSelectionModel().isEmpty()){
                try {
                    ResultSet result = CrudUtil.execute("SELECT role,FullName FROM employee WHERE empid=?",newValue);

                    if(result.next()){
                        txtPyEmpType.setText(result.getString(1));
                        txtPyEmpName.setText(result.getString(2));
                    }

                } catch (SQLException |ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        });
    }


    public void makePaymentOnAction(ActionEvent actionEvent) {
        removePyVisible();
        if(!cmbPySelectEmployeeId.getSelectionModel().isEmpty() && !cmbPyPaymentMethod.getSelectionModel().isEmpty()) {

            if (pyIdPattern.matcher(txtPaymentNo.getText()).matches() && doublePattern.matcher(txtPyTotal.getText()).matches()) {
                java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());  //getRealDate

                Payment p = new Payment(txtPaymentNo.getText(), txtPyEmpType.getText(), txtPyStatus.getText(),
                        cmbPySelectEmployeeId.getValue(), txtPyEmpName.getText(), date, cmbPyPaymentMethod.getValue(), Double.parseDouble(txtPyTotal.getText()));

                EmployeePaymentCrudController.addEmployeePayment(p);
                clearPyText();
                setAutoPaymentId();


                Notifications notifications = Notifications.create();
                notifications.title("Make Payment");
                notifications.text("Payment Successfully...");
                notifications.hideAfter(Duration.seconds(8));
                notifications.position(Pos.BOTTOM_RIGHT);
                notifications.graphic(new ImageView("asserts/images/pay.png"));
                notifications.darkStyle();
                notifications.show();
            } else {
                setErrorLbl();
            }
        }else{
            if(cmbPySelectEmployeeId.getSelectionModel().isEmpty()){
                cmbPySelectEmpIdError.setVisible(true);
            }
            if(cmbPyPaymentMethod.getSelectionModel().isEmpty()){
                cmbPyPaymentMethodError.setVisible(true);
            }
        }


    }

    private  void removePyVisible(){
        lblPyPaymentIdError.setVisible(false);
         cmbPySelectEmpIdError.setVisible(false);
         lblPyTotalError.setVisible(false);
         cmbPyPaymentMethodError.setVisible(false);
        cmbPyPaymentMethodError.setVisible(false);
        cmbPySelectEmpIdError.setVisible(false);
    }
    private void setErrorLbl(){
        if(!pyIdPattern.matcher(txtPaymentNo.getText()).matches()){
            lblPyPaymentIdError.setVisible(true);
        }
        if(!doublePattern.matcher(txtPyTotal.getText()).matches()){
            lblPyTotalError.setVisible(true);
        }
    }

    private void clearPyText(){
        txtPaymentNo.setText(null);
        txtPyEmpName.setText(null);
        txtPyEmpType.setText(null);
        txtPyTotal.setText(null);
        if(!cmbPySelectEmployeeId.getSelectionModel().isEmpty()) {
            cmbPySelectEmployeeId.setValue(null);
        }
        if(!cmbPyPaymentMethod.getSelectionModel().isEmpty()) {
            cmbPyPaymentMethod.setValue(null);
        }
    }

    public void paymentDetailsOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/EmployeePaymentDetailsForm.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Payments");

        stage.setScene(scene);
        stage.show();
    }

    public boolean setValidateToEmp(){
        if(!cmbRole.getSelectionModel().isEmpty()) {
            return idPattern.matcher(txtEmpId.getText()).matches() && namePattern.matcher(txtFullName.getText()).matches() &&
                    addressPattern.matcher(txtAddress.getText()).matches() && emailPattern.matcher(txtEmail.getText()).matches() &&
                    tellNoPattern.matcher(txtTellNo.getText()).matches() && userNamePattern.matcher(txtName.getText()).matches();


        }else{
            lblCmbRoleError.setVisible(true);
            return false;
        }
    }

    public void setErrorLabel(){
        if(!idPattern.matcher(txtEmpId.getText()).matches()){
            lblEmpIdError.setVisible(true);
        }
        if(!namePattern.matcher(txtFullName.getText()).matches()){
            lblNameError.setVisible(true);
        }
        if(!addressPattern.matcher(txtAddress.getText()).matches()){
            lblAddressError.setVisible(true);
        }
        if(!emailPattern.matcher(txtEmail.getText()).matches()){
            lblEmailError.setVisible(true);
        }
        if(!tellNoPattern.matcher(txtTellNo.getText()).matches()){
            lblTelError.setVisible(true);
        }
        if(!userNamePattern.matcher(txtName.getText()).matches()){
            lblUserNameError.setVisible(true);
        }
        if(pwdPassword.getText()!=null && pwdConPassword.getText()!=null) {
            if (!passwordPattern.matcher(pwdPassword.getText()).matches()) {
                lblPasswordError.setVisible(true);
            }
            if (!passwordPattern.matcher(pwdConPassword.getText()).matches()) {
                lblConPasswordError.setVisible(true);
            }
        }
    }

    public void removeLabelError(){
        lblEmpIdError.setVisible(false);
        lblNameError.setVisible(false);
        lblAddressError.setVisible(false);
        lblEmailError.setVisible(false);
        lblTelError.setVisible(false);
        lblUserNameError.setVisible(false);
        lblPasswordError.setVisible(false);
        lblConPasswordError.setVisible(false);
        lblCheckPasswordError.setVisible(false);
        lblCmbRoleError.setVisible(false);
    }

    public void clearText(){
       txtEmpId.setText(null);
       txtFullName.setText(null);
       txtAddress.setText(null);
       txtEmail.setText(null);
       txtTellNo.setText(null);
       txtName.setText(null);
       pwdPassword.setText(null);
       pwdConPassword.setText(null);

//       cmbRole.setValue(null);

    }

}
