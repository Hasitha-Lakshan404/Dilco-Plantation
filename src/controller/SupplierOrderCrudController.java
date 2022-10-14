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
import java.util.ArrayList;

public class SupplierOrderCrudController {

    public static ObservableList<SupplierOrder> loadAllSupplierOrders() throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT * FROM supplierorder ORDER BY orderId DESC");
        ObservableList<SupplierOrder> so= FXCollections.observableArrayList();

        while (resultSet.next()) {
            so.add(new SupplierOrder(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getInt(5),
                    resultSet.getDouble(6),
                    resultSet.getDouble(7),
                    resultSet.getDate(8),
                    resultSet.getTime(9)
            ));
        }
        return so;

    }

    public static ObservableList<SupplierOrder> placeOrder(SupplierOrder so) {

        try{
            if(CrudUtil.execute("INSERT INTO `supplierorder` VALUES (?,?,?,?,?,?,?,?,?)",so.getOrderId(),so.getProductName(),
                    so.getSupId(),so.getDescription(),so.getQty(),so.getUnitPrice(),so.getTotalCost(),so.getDate(),so.getTime())){

                return loadAllSupplierOrders();
            }

        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.CONFIRMATION, e.getMessage()).show();

        }
        return null;
    }

    public static boolean updateSupplierOrder(SupplierOrder  or) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE supplierorder SET productName=?,supId=?,detail=?,qty=?,unitPrice=?,totalCost=?,date=?,time=? WHERE orderId=?",
                or.getProductName(),or.getSupId(),or.getDescription(),or.getQty(),or.getUnitPrice(),or.getTotalCost(),or.getDate(),or.getTime(),or.getOrderId());
    }


    public static void deleteSupplierOrder(SupplierOrder selectedItem) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("DELETE FROM Supplierorder WHERE orderId=?",selectedItem.getOrderId());
    }

    public static ArrayList<SupplierOrder> searchReportByOrderId(String s) {
        ArrayList<SupplierOrder> arrayList = new ArrayList();

        try {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM supplierorder WHERE orderId LIKE ?",s);
            while(resultSet.next()){
                arrayList.add(new SupplierOrder(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getDouble(6),
                        resultSet.getDouble(7),
                        resultSet.getDate(8),
                        resultSet.getTime(9)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static ArrayList<SupplierOrder> searchReportBySupId(String s) {
        ArrayList<SupplierOrder> arrayList = new ArrayList();

        try {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM supplierorder WHERE supId LIKE ?",s);
            while(resultSet.next()){
                arrayList.add(new SupplierOrder(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getDouble(6),
                        resultSet.getDouble(7),
                        resultSet.getDate(8),
                        resultSet.getTime(9)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;


    }

    public static ArrayList<SupplierOrder> searchReportByProductName(String s) {
        ArrayList<SupplierOrder> arrayList = new ArrayList();

        try {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM supplierorder WHERE productName LIKE ?",s);
            while(resultSet.next()){
                arrayList.add(new SupplierOrder(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getDouble(6),
                        resultSet.getDouble(7),
                        resultSet.getDate(8),
                        resultSet.getTime(9)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }
}
