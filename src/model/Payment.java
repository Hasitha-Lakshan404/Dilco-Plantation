package model;

import java.sql.Date;

public class Payment {
    private String payNo;
    private String empType;
    private String status;
    private String supEmpId;
    private String name;
    private Date payedDate;
    private String payMethod;
    private double total;

    public Payment() {
    }

    public Payment(String payNo, String empType, String status, String supEmpId, String name, Date payedDate, String payMethod, double total) {
        this.payNo = payNo;
        this.empType = empType;
        this.status = status;
        this.supEmpId = supEmpId;
        this.name = name;
        this.payedDate = payedDate;
        this.payMethod = payMethod;
        this.total = total;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSupEmpId() {
        return supEmpId;
    }

    public void setSupEmpId(String supEmpId) {
        this.supEmpId = supEmpId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPayedDate() {
        return payedDate;
    }

    public void setPayedDate(Date payedDate) {
        this.payedDate = payedDate;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "payNo='" + payNo + '\'' +
                ", empType='" + empType + '\'' +
                ", status='" + status + '\'' +
                ", supEmpId='" + supEmpId + '\'' +
                ", name='" + name + '\'' +
                ", payedDate=" + payedDate +
                ", payMethod='" + payMethod + '\'' +
                ", total=" + total +
                '}';
    }
}
