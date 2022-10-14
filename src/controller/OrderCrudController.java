package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Customer;
import model.Order;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;
import util.CrudUtil;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class OrderCrudController {

    public static ObservableList<Order> placeOrder(Order o,double unitPrice){


        try{
            if(CrudUtil.execute("INSERT INTO `order` VALUES (?,?,?,?,?,?,?,?,?,?,?,?)",o.getOrderId(),o.getCusId(),o.getProductId(),
            o.getProductName(),o.getOrderDate(),o.getHandoverDate(),o.getTime(),o.getQty(),o.getAdvance(),
                    o.getCost(),o.getStatus(),o.getCusName())){

                //====set Print Button to the Massage Box====

                ButtonType bb=new ButtonType("Print Report");
                Alert alert=new Alert(Alert.AlertType.INFORMATION,"You should print this report",bb);
                alert.setTitle("Receipt");
                alert.setHeaderText("Report is ready !!");
                Optional<ButtonType> buttonType = alert.showAndWait();

                if(buttonType.get().equals(bb)){
//                    System.out.println("Report is Print");

                    /*//methana this eka ain kraaa static nisa meeka
                    JasperDesign load = JRXmlLoader.load(OrderCrudController.class.getResourceAsStream("/view/reports/customerOrderReport.jrxml"));

                    JasperReport companyReport= JasperCompileManager.compileReport(load);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(companyReport,null,new JREmptyDataSource(1));

                    JasperViewer.viewReport(jasperPrint,false);*/
                    double bal=o.getCost()-o.getAdvance();

                    HashMap paramMap=new HashMap();
                    paramMap.put("orId",o.getOrderId());
                    paramMap.put("handOverDate",o.getHandoverDate().toLocalDate().toString());
                    paramMap.put("plantName",o.getProductName());
                    paramMap.put("qty",o.getQty());
                    paramMap.put("unitPrice",unitPrice);
                    paramMap.put("amount",o.getCost());
                    paramMap.put("total",o.getCost());
                    paramMap.put("advance",o.getAdvance());
                    paramMap.put("balance",bal);
                    paramMap.put("cusName",o.getCusName());

                    JasperReport compileReport= (JasperReport) JRLoader.loadObject(OrderCrudController.class.getResourceAsStream("/view/reports/customerOrderReport.jasper"));
                    JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport,paramMap,new JREmptyDataSource(1));
                    JasperViewer.viewReport(jasperPrint,false);

                    Notifications notifications = Notifications.create();
                    notifications.title("Order Add Successfully");
                    notifications.text("new order is Pending..");
                    notifications.hideAfter(Duration.seconds(8));
                    notifications.position(Pos.BOTTOM_RIGHT);
                    notifications.graphic(new ImageView("asserts/images/Bill.png"));
                    notifications.darkStyle();
                    notifications.show();
                }


                return loadAllOrders();
            }

        }catch(ClassNotFoundException | SQLException | JRException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.CONFIRMATION, e.getMessage()).show();

        }
        return null;
    }

    public static ObservableList<Order> loadAllOrders() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM `order` ORDER BY orderId DESC");

        ObservableList<Order> obList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            obList.add(new Order(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDate(5),
                    resultSet.getDate(6),
                    resultSet.getTime(7),
                    resultSet.getInt(8),
                    resultSet.getDouble(9),
                    resultSet.getDouble(10),
                    resultSet.getString(11),
                    resultSet.getString(12)
            ));
        }
        return obList;
    }

    public static  boolean updateValues(Order or) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE `order` SET cusId=?,productId=?,productName=?,orderDate=?,handOverDate=?,time=?,qty=?,advance=?,cost=?,status=?,cusName=? WHERE orderId=?"
                ,or.getCusId(),or.getProductId(),or.getProductName(),or.getOrderDate(),or.getHandoverDate(),or.getTime(),or.getQty(),
                or.getAdvance(),or.getCost(),or.getStatus(),or.getCusName(),or.getOrderId());
    }

    public static void  deleteOrder(Order selectedItem) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("DELETE FROM `order` WHERE orderId=?",selectedItem.getOrderId());
    }


    public static ArrayList<Order> searchReportByOrId(String s) {
        ArrayList<Order> arrayList = new ArrayList();

        try {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM `order` WHERE orderId LIKE ?",s);
            while(resultSet.next()){
                arrayList.add(new Order(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDate(5),
                        resultSet.getDate(6),
                        resultSet.getTime(7),
                        resultSet.getInt(8),
                        resultSet.getDouble(9),
                        resultSet.getDouble(10),
                        resultSet.getString(11),
                        resultSet.getString(12)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static ArrayList<Order> searchReportByCusId(String s) {
        ArrayList<Order> arrayList = new ArrayList();

        try {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM `order` WHERE cusId LIKE ?",s);
            while(resultSet.next()){
                arrayList.add(new Order(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDate(5),
                        resultSet.getDate(6),
                        resultSet.getTime(7),
                        resultSet.getInt(8),
                        resultSet.getDouble(9),
                        resultSet.getDouble(10),
                        resultSet.getString(11),
                        resultSet.getString(12)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static ArrayList<Order> searchReportByName(String s) {
        ArrayList<Order> arrayList = new ArrayList();

        try {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM `order` WHERE cusName LIKE ?",s);
            while(resultSet.next()){
                arrayList.add(new Order(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDate(5),
                        resultSet.getDate(6),
                        resultSet.getTime(7),
                        resultSet.getInt(8),
                        resultSet.getDouble(9),
                        resultSet.getDouble(10),
                        resultSet.getString(11),
                        resultSet.getString(12)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }

    public static ArrayList<Order> searchReportByProductId(String s) {
        ArrayList<Order> arrayList = new ArrayList();

        try {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM `order` WHERE productId LIKE ?",s);
            while(resultSet.next()){
                arrayList.add(new Order(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDate(5),
                        resultSet.getDate(6),
                        resultSet.getTime(7),
                        resultSet.getInt(8),
                        resultSet.getDouble(9),
                        resultSet.getDouble(10),
                        resultSet.getString(11),
                        resultSet.getString(12)
                ));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }
}