package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Customer;
import model.Plant;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class PlantCrudController {


    public static ObservableList<Plant> loadAllItems() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM plant");

        ObservableList<Plant> cObList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            cObList.add(new Plant(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            ));
        }
        return  cObList;

    }


    public static void addPlant(Plant p){

        try {
            if(CrudUtil.execute("INSERT INTO plant VALUES (?,?,?,?,?)",p.getPlantId(),p.getPlantType(),p.getPlantName(),p.getDescription(),p.getUnitPrice())){
//                lordItems();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.CONFIRMATION, e.getMessage()).show();

        }

    }

    public static void  deletePlant(Plant selectedItem) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("DELETE FROM plant WHERE plantId=?",selectedItem.getPlantId());
    }
    public static boolean updatePlant(Plant p) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE plant SET plantType=?,plantName=?,description=?,unitPrice=? WHERE plantId=?",p.getPlantType(),p.getPlantName(),p.getDescription(),p.getUnitPrice(),p.getPlantId());
    }

    public static ObservableList<String> getPlantType() throws SQLException, ClassNotFoundException {

        ResultSet result =CrudUtil.execute("SELECT DISTINCT plantType  FROM plant");
        ObservableList<String> ptObList = FXCollections.observableArrayList();

        while (result.next()) {
            ptObList.add(result.getString(1));
        }
        return ptObList;
    }

    public static ObservableList<String> getPlantIds() throws SQLException, ClassNotFoundException {
        ResultSet result=CrudUtil.execute("SELECT plantId FROM plant");

        ObservableList<String> obList=FXCollections.observableArrayList();

        while(result.next()){
            obList.add(result.getString(1));
        }
        return obList;
    }


    public static ArrayList<Plant> searchReportByPlantId(String s) {
        ArrayList<Plant> arrayList = new ArrayList();

        try {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM plant WHERE plantId LIKE ?",s);
            while(resultSet.next()){
                arrayList.add(new Plant(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDouble(5)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static ArrayList<Plant> searchReportByPlantType(String s) {
        ArrayList<Plant> arrayList = new ArrayList();

        try {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM plant WHERE plantType LIKE ?",s);
            while(resultSet.next()){
                arrayList.add(new Plant(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDouble(5)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }
}
