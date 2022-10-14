package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Employee;
import model.Payment;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeePaymentCrudController {
    public static void addEmployeePayment(Payment p){

        try {
            if(CrudUtil.execute("INSERT INTO payment VALUES (?,?,?,?,?,?,?,?)",p.getPayNo(),p.getEmpType(),
                    p.getStatus(),p.getSupEmpId(),p.getName(),p.getPayedDate(),p.getPayMethod(),p.getTotal())){
            }
        } catch (SQLException |ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    public static ObservableList<Payment> loadAllEmployeePayments() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM payment ORDER BY paymentNo  DESC");

        ObservableList<Payment> eObList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            eObList.add(new Payment(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDate(6),
                    resultSet.getString(7),
                    resultSet.getDouble(8)
            ));
        }

        return  eObList;

    }

    public static void deletePayment(Payment selectedItem) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("DELETE FROM payment WHERE paymentNo=?",selectedItem.getPayNo());
    }
}
