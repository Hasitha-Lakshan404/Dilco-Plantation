package model;

public class Customer {
    private String cusId;
    private String cusName;
    private String cusEmail;
    private String cusAddress;
    private String telNo;


    public Customer() {
    }

    public Customer(String cusId,String cusName) {
        this.cusId = cusId;
        this.cusName = cusName;
    }

    public Customer(String cusId, String cusName, String cusEmail, String cusAddress, String telNo) {
        this.cusId = cusId;
        this.cusName = cusName;
        this.cusEmail = cusEmail;
        this.cusAddress = cusAddress;
        this.telNo = telNo;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cusId='" + cusId + '\'' +
                ", cusName='" + cusName + '\'' +
                ", cusEmail='" + cusEmail + '\'' +
                ", cusAddress='" + cusAddress + '\'' +
                ", telNo='" + telNo + '\'' +
                '}';
    }
}
