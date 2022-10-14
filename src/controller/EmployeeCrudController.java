package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Employee;
import model.Plant;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeCrudController {

    public static ObservableList<Employee> loadAllEmployee() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM employee");

        ObservableList<Employee> eObList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            eObList.add(new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8)
            ));
        }

        return  eObList;

    }

    public static void addEmployee(Employee e){

        try {
            if(CrudUtil.execute("INSERT INTO employee VALUES (?,?,?,?,?,?,?,?)",e.getEmpId(),e.getRole(),
                    e.getFullName(),e.getEmpAddress(),e.getEmail(),e.getTellNo(),e.getUserName(),e.getPassword())){
            }
        } catch (SQLException |ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    public static void deleteEmp(Employee selectedItem) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("DELETE FROM employee WHERE empId=?",selectedItem.getEmpId());

    }

    public static boolean updateEmployee(Employee e) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE employee SET role=?,FullName=?,empAddress=?,empEmail=?,empTellNo=?,UserName=?,Password=? WHERE empId=?",
                e.getRole(),e.getFullName(),e.getEmpAddress(),e.getEmail(),e.getTellNo(),e.getUserName(),e.getPassword(),e.getEmpId());

    }

    public static ObservableList<String> getEmployeeId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT empId FROM employee");

        ObservableList<String> eObList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            eObList.add(resultSet.getString(1));
        }

        return eObList;
    }
}
