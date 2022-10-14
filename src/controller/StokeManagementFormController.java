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
import model.Product;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.GenerateAutoId;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Pattern;

public class StokeManagementFormController {

    public TableView<Product> tblProduct;
    public Pane pneVisible;
    public BorderPane bPane;
    public JFXTextField txtProductId;

    public JFXComboBox<String> cmbProductType;
    public JFXTextField txtProductName;
    public JFXTextField txtNewProductType;
    public JFXTextField txtUnitPrice;
    public JFXTextArea txtDescription;

    public Label lblProductIdError;
    public Label cmbSelectTypeError;
    public Label lblAddProductTypeError;
    public Label lblProductNameError;
    public Label lblDescriptonError;
    public Label lblUnitPriceError;
    public TextField txtSearch;

    Pattern idPattern = Pattern.compile("^(PR-)[0-9]{2,9}$");
    Pattern namePattern = Pattern.compile("^[A-z 0-9-]{3,25}$");
    Pattern doublePattern = Pattern.compile("^[0-9.]{1,}$");


    private double xOffset = 0;
    private double yOffset = 0;

    public void initialize() throws SQLException, ClassNotFoundException {
        setAutoId();
        pneVisible.setVisible(false);
        lordProduct();
        loadCmbData();

        tblProduct.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("productId"));
        tblProduct.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("ProductType"));
        tblProduct.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        tblProduct.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblProduct.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        removeLblVisible();
    }
    private void lordProduct() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM product");

        ObservableList<Product> prObList= FXCollections.observableArrayList();

        while (resultSet.next()) {
            prObList.add(new Product(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            ));
        }
        tblProduct.setItems(prObList);
    }

    public void contextEditOnAction(ActionEvent actionEvent) throws IOException {
        lordWidow("../view/StokeUpdateForm.fxml","ss");
    }

    public void contextDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Product selectedItem = tblProduct.getSelectionModel().getSelectedItem();

        BoxBlur blur=new BoxBlur(5,5,5);
        bPane.setEffect(blur);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you Sure you want to Delete?", ButtonType.YES,ButtonType.NO);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Product!!");
        Optional<ButtonType> buttonType=alert.showAndWait();

        if(buttonType.get().equals(ButtonType.YES)){
            StockCrudController.deleteProduct(selectedItem); //for delete
            lordProduct();
            setAutoId();
            bPane.setEffect(null);
        }else{
            bPane.setEffect(null);
        }
    }

    public void lordWidow(String location,String title) throws IOException {

        Product selectItem=  tblProduct.getSelectionModel().getSelectedItem();

        FXMLLoader loader =new FXMLLoader(getClass().getResource(location));
        Parent root=loader.load();
        Scene scene =new Scene(root);
        StokeUpdateFormController controller =loader.getController();

        controller.getAllData(selectItem,pneVisible);

        Stage stage =new Stage(StageStyle.TRANSPARENT);
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
                stage.setY(event.getScreenY()- yOffset);
            }
        });

    }

    public void pneMouseMoveOnAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        lordProduct();
        pneVisible.setVisible(false);
    }
    private void setAutoId(){
        txtProductId.setText(GenerateAutoId.autoId("SELECT productId FROM product ORDER BY productId DESC LIMIT 1",1,3,"PR-01"));
    }

    public void loadCmbData() throws SQLException, ClassNotFoundException {
        cmbProductType.setItems(StockCrudController.getProductType());
    }

    public void btnAddNewProductType(ActionEvent actionEvent) {
        if(namePattern.matcher(txtNewProductType.getText()).matches()) {
            cmbProductType.getItems().add(txtNewProductType.getText());
            txtNewProductType.setText("");
            lblAddProductTypeError.setVisible(false);
        }else{
            lblAddProductTypeError.setVisible(true);
        }
    }

    public void btnAddProductOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        removeLblVisible();
        if(!cmbProductType.getSelectionModel().isEmpty()) {
            if (!txtDescription.getText().isEmpty()) {

                if (setValidate()) {
                    Product p = new Product(txtProductId.getText(), cmbProductType.getValue(), txtProductName.getText(), txtDescription.getText(),
                            Integer.parseInt(txtUnitPrice.getText()));
                    StockCrudController.addProduct(p);
                    lordProduct();
                    loadCmbData();
                    clearData();
                    setAutoId();


                    Notifications notifications = Notifications.create();
                    notifications.title("Add Product ");
                    notifications.text("New Product Added Successfully...");
                    notifications.hideAfter(Duration.seconds(8));
                    notifications.position(Pos.BOTTOM_RIGHT);
                    notifications.graphic(new ImageView("asserts/images/pro.png"));
                    notifications.darkStyle();
                    notifications.show();

                } else {
                    setErrorLabel();
                }
            } else {
                lblDescriptonError.setVisible(true);
            }
        }else{
            cmbSelectTypeError.setVisible(true);
        }

    }

    public void removeLblVisible() {
        lblProductIdError.setVisible(false);
        lblProductNameError.setVisible(false);
        lblAddProductTypeError.setVisible(false);
        lblDescriptonError.setVisible(false);
        lblUnitPriceError.setVisible(false);
        cmbSelectTypeError.setVisible(false);

    }


    public void clearData() {
        txtNewProductType.setText(null);
        txtDescription.setText(null);
        txtProductName.setText(null);
        txtUnitPrice.setText(null);
        cmbProductType.setValue(null);
    }


    public boolean setValidate() {
        return idPattern.matcher(txtProductId.getText()).matches() && namePattern.matcher(txtProductName.getText()).matches()
                && doublePattern.matcher(txtUnitPrice.getText()).matches();

    }

    public void setErrorLabel() {
        if (!idPattern.matcher(txtProductId.getText()).matches()) {
            lblProductIdError.setVisible(true);
        }
        if (!namePattern.matcher(txtProductName.getText()).matches()) {
            lblProductNameError.setVisible(true);
        }
        if (!doublePattern.matcher(txtUnitPrice.getText()).matches()) {
            lblUnitPriceError.setVisible(true);
        }
    }



    public void searchKeyReleased(KeyEvent keyEvent) {
        if(txtSearch.getText().startsWith("PR")){
            ObservableList<Product> ob = FXCollections.observableArrayList(
                    StockCrudController.searchReportByProductId(
                            "%"+txtSearch.getText()+"%"
                    )
            );
            tblProduct.setItems(ob);
        }else{
            ObservableList<Product> obj = FXCollections.observableArrayList(StockCrudController.searchReportByProductType("%"+txtSearch.getText()+"%"));
            tblProduct.setItems(obj);
        }

    }
}
