package model;

import java.sql.Date;
import java.sql.Time;

public class SupplierOrder {
    private String OrderId;
    private String productName;
    private String supId;
    private String description;
    private int qty;
    private double unitPrice;
    private double totalCost;
    private Date date;
    private Time time;

    public SupplierOrder() {
    }

    public SupplierOrder(String orderId, String productName, String supId, String description, int qty, double unitPrice, double totalCost, Date date, Time time) {
        OrderId = orderId;
        this.productName = productName;
        this.supId = supId;
        this.description = description;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.totalCost = totalCost;
        this.date = date;
        this.time = time;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "SupplierOrder{" +
                "OrderId='" + OrderId + '\'' +
                ", productName='" + productName + '\'' +
                ", supId='" + supId + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", totalCost=" + totalCost +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
