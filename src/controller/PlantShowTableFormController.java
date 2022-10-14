package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Plant;

import java.sql.SQLException;

public class PlantShowTableFormController extends PlantManagementFormController{

    public TableView<Plant> tblPlant;
    public ImageView imgClose;
    public TextField txtSearch;

    public void initialize() throws SQLException, ClassNotFoundException {
        super.lordItems();
        tblPlant.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("plantId"));
        tblPlant.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("plantName"));
        tblPlant.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("plantType"));
        tblPlant.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblPlant.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
    }

    public void imgCloseOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) imgClose.getScene().getWindow();
        stage.close();;
    }

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
