package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Customer;
import model.Plant;
import model.Product;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StockCrudController {

    public static void addProduct(Product p){

        try {
            if(CrudUtil.execute("INSERT INTO product VALUES (?,?,?,?,?)",p.getProductId(),p.getProductType(),p.getProductName(),p.getDescription(),p.getUnitPrice())){
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.CONFIRMATION, e.getMessage()).show();

        }

    }

    public static ObservableList<String> getProductType() throws SQLException, ClassNotFoundException {

        ResultSet result =CrudUtil.execute("SELECT DISTINCT productType  FROM product");
        ObservableList<String> ptObList = FXCollections.observableArrayList();

        while (result.next()) {
            ptObList.add(result.getString(1));
        }
        return ptObList;
    }


    public static boolean updateProduct(Product p) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE product SET productType=?,productName=?,description=?,unitPrice=? WHERE productId=?",p.getProductType(),p.getProductName(),p.getDescription(),p.getUnitPrice(),p.getProductId());
    }

    public static void  deleteProduct(Product selectedItem) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("DELETE FROM product WHERE productId=?",selectedItem.getProductId());
    }

    public static ObservableList<String> getProductName() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT productName FROM product");
        ObservableList<String> pnOb = FXCollections.observableArrayList();

        while(resultSet.next()) {
            pnOb.add(resultSet.getString(1));
        }

        return pnOb;
    }

    public static ArrayList<Product> searchReportByProductId(String s) {
        ArrayList<Product> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM product WHERE productId LIKE ?",s);
            while(result.next()){
                arrayList.add(new Product(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getDouble(5)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static ArrayList<Product> searchReportByProductType(String s) {
        ArrayList<Product> arrayList = new ArrayList();
        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM product WHERE productType LIKE ?",s);
            while(result.next()){
                arrayList.add(new Product(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getDouble(5)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }
}
