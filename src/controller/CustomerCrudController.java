package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Customer;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerCrudController {

    public static ObservableList<Customer> loadAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM customer ORDER BY cusId DESC");

        ObservableList<Customer> cObList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            cObList.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }

        return cObList;

    }

    public static ObservableList<Customer> addCustomer(Customer c) {
        try {
            if (CrudUtil.execute("INSERT INTO customer VALUES (?,?,?,?,?)", c.getCusId(), c.getCusName(), c.getCusEmail(), c.getCusAddress(), c.getTelNo())) {
//                lordAllCustomers();
                return loadAllCustomers();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.CONFIRMATION, e.getMessage()).show();

        }
        return null;
    }

    public static void deleteCustomer(Customer selectedItem) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("DELETE FROM customer WHERE cusId=?", selectedItem.getCusId());
    }


    public static boolean updateItem(Customer c) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE customer SET cusName=?,cusEmail=?,cusAddress=?,cusTellNo=? WHERE cusId=?", c.getCusName(), c.getCusEmail(), c.getCusAddress(), c.getTelNo(), c.getCusId());
    }


    public static ObservableList<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT cusId FROM customer ORDER BY cusId  DESC");

        ObservableList<String> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(result.getString(1));
        }
        return obList;
    }


    public static ArrayList<Customer> searchReportByCusId(String s) {
        ArrayList<Customer> arrayList = new ArrayList();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM customer WHERE cusId LIKE ?", s);
            while (result.next()) {
                arrayList.add(new Customer(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }


    public static ArrayList<Customer> searchReportByName(String s) {
        ArrayList<Customer> arrayList = new ArrayList();

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM customer WHERE cusName LIKE ?", s);
            while (result.next()) {
                arrayList.add(new Customer(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }
}
