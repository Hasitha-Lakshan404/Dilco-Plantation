package model;

public class Plant {
    private String plantId;
    private String plantType;
    private String plantName;
    private String description;
    private double unitPrice;

    public Plant() {
    }

    public Plant(String plantId, String plantType, String plantName, String description, double unitPrice) {
        this.plantId = plantId;
        this.plantType = plantType;
        this.plantName = plantName;
        this.description = description;
        this.unitPrice = unitPrice;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    public String getPlantType() {
        return plantType;
    }

    public void setPlantType(String plantType) {
        this.plantType = plantType;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
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
        return "Plant{" +
                "plantId='" + plantId + '\'' +
                ", plantType='" + plantType + '\'' +
                ", plantName='" + plantName + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
