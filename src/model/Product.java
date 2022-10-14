package model;

public class Product {
    private String productId;
    private String ProductType;
    private String ProductName;
    private String description;
    private double unitPrice;

    public Product() {
    }

    public Product(String productId, String productType, String productName, String description, double unitPrice) {
        this.productId = productId;
        ProductType = productType;
        ProductName = productName;
        this.description = description;
        this.unitPrice = unitPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", ProductType='" + ProductType + '\'' +
                ", ProductName='" + ProductName + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
