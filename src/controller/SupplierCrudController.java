package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Customer;
import model.Supplier;
import model.SupplierOrder;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierCrudController {

    public static ObservableList<String> getSupplierId() throws SQLException, ClassNotFoundException {

        ResultSet result = CrudUtil.execute("SELECT supId FROM supplier");
        ObservableList<String> ptObList = FXCollections.observableArrayList();

        while (result.next()) {
            ptObList.add(result.getString(1));
        }
        return ptObList;
    }

    public static ObservableList<Supplier> loadAllSuppliers() throws SQLException, ClassNotFoundException{

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM supplier");

            ObservableList<Supplier> sObList = FXCollections.observableArrayList();

            while (resultSet.next()) {
                sObList.add(new Supplier(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                ));
            }

            return  sObList;

    }

    public static boolean updateSupplier(Supplier s) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE supplier SET supName=?,supEmail=?,supAddress=?,supTelNo=?,productType=?,productName=? WHERE supId=?",
                s.getName(),s.getEmail(),s.getAddress(),s.getTelNo(),s.getProductType(),s.getProductName(),s.getSupId());
    }

    public static void addSupplier(Supplier s) {
        try {
            if(CrudUtil.execute("INSERT INTO supplier VALUES (?,?,?,?,?,?,?)",s.getSupId(),s.getName(),
                    s.getEmail(),s.getAddress(),s.getTelNo(),s.getProductType(),s.getProductName())){

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.CONFIRMATION, e.getMessage()).show();

        }
    }

    public static void deleteSupplier(Supplier selectedItem) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("DELETE FROM Supplier WHERE supId=?",selectedItem.getSupId());
    }

    
    /*public static void placeSupplierOrder(){
        try{
            if(CrudUtil.execute("INSERT INTO customer VALUES (?,?,?,?,?)",c.getCusId(),c.getCusName(),c.getCusEmail(),c.getCusAddress(),c.getTelNo())){
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
//                lordAllCustomers();
                return loadAllCustomers();
            }

        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.CONFIRMATION, e.getMessage()).show();

        }
        return null;
        
    }*/
}
