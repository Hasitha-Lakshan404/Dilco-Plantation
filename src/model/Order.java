package model;

import java.sql.Date;
import java.sql.Time;

public class Order {
    private String orderId;
    private String CusId;
    private String productId;
    private String ProductName;
    private Date orderDate;       //SQL DATE
    private Date handoverDate; //SQL DATE
    private Time time;
    private int qty;
    private double advance;
    private double cost;
    private String status;
    private String cusName;


    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Order() {
    }

    /*public Order(String orderId, String cusId, String productId, String productName, Date orderDate, Date handoverDate, Time time, int qty, double advance, double cost) {
        this.orderId = orderId;
        CusId = cusId;
        this.productId = productId;
        ProductName = productName;
        this.orderDate = orderDate;
        this.handoverDate = handoverDate;
        this.time = time;
        this.qty = qty;
        this.advance = advance;
        this.cost = cost;
    }*/

    /*public Order(String orderId, String cusId, String productId, String productName, Date orderDate, Date handoverDate, Time time, int qty, double advance, double cost, String status) {
        this.orderId = orderId;
        CusId = cusId;
        this.productId = productId;
        ProductName = productName;
        this.orderDate = orderDate;
        this.handoverDate = handoverDate;
        this.time = time;
        this.qty = qty;
        this.advance = advance;
        this.cost = cost;
        this.status = status;
    }*/

    public Order(String orderId, String cusId, String productId, String productName, Date orderDate, Date handoverDate, Time time, int qty, double advance, double cost, String status, String cusName) {
        this.orderId = orderId;
        CusId = cusId;
        this.productId = productId;
        ProductName = productName;
        this.orderDate = orderDate;
        this.handoverDate = handoverDate;
        this.time = time;
        this.qty = qty;
        this.advance = advance;
        this.cost = cost;
        this.status = status;
        this.cusName = cusName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCusId() {
        return CusId;
    }

    public void setCusId(String cusId) {
        CusId = cusId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getHandoverDate() {
        return handoverDate;
    }

    public void setHandoverDate(Date handoverDate) {
        this.handoverDate = handoverDate;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getAdvance() {
        return advance;
    }

    public void setAdvance(double advance) {
        this.advance = advance;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", CusId='" + CusId + '\'' +
                ", productId='" + productId + '\'' +
                ", ProductName='" + ProductName + '\'' +
                ", orderDate=" + orderDate +
                ", handoverDate=" + handoverDate +
                ", time=" + time +
                ", qty=" + qty +
                ", advance=" + advance +
                ", cost=" + cost +
                ", status='" + status + '\'' +
                ", cusName='" + cusName + '\'' +
                '}';
    }
}
