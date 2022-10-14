package model;

public class Supplier {
    private String supId;
    private String name;
    private String email;
    private String address;
    private String telNo;
    private String productType;
    private String productName;

    public Supplier() {
    }

    public Supplier(String supId, String name, String email, String address, String telNo, String productType, String productName) {
        this.supId = supId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.telNo = telNo;
        this.productType = productType;
        this.productName = productName;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supId='" + supId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", telNo='" + telNo + '\'' +
                ", productType='" + productType + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}
