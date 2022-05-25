package com.donnekt.farmerapp.farmer;

public class Harvest {
    double harvestPrice;
    int harvestId, farmerId, quantity, seasonYear;
    String farmerFName, farmerLName, farmerPhone, itemType, seasonTerm, harvestDate, status;

    public Harvest() {}

    public Harvest(int harvestId, int farmerId, String farmerFName, String farmerLName, String farmerPhone, String itemType, int quantity, double harvestPrice, int seasonYear, String seasonTerm, String harvestDate, String status) {
        this.harvestId = harvestId;
        this.farmerId = farmerId;
        this.farmerFName = farmerFName;
        this.farmerLName = farmerLName;
        this.farmerPhone = farmerPhone;
        this.itemType = itemType;
        this.quantity = quantity;
        this.harvestPrice = harvestPrice;
        this.seasonYear = seasonYear;
        this.seasonTerm = seasonTerm;
        this.harvestDate = harvestDate;
        this.status = status;
    }

    public Harvest(int farmerId, String itemType, int quantity, double harvestPrice, int seasonYear, String seasonTerm) {
        this.farmerId = farmerId;
        this.itemType = itemType;
        this.quantity = quantity;
        this.harvestPrice = harvestPrice;
        this.seasonYear = seasonYear;
        this.seasonTerm = seasonTerm;
    }

    public int getHarvestId() {
        return harvestId;
    }

    public void setHarvestId(int harvestId) {
        this.harvestId = harvestId;
    }

    public int getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(int farmerId) {
        this.farmerId = farmerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getHarvestPrice() {
        return harvestPrice;
    }

    public void setHarvestPrice(double harvestPrice) {
        this.harvestPrice = harvestPrice;
    }

    public int getSeasonYear() {
        return seasonYear;
    }

    public void setSeasonYear(int seasonYear) {
        this.seasonYear = seasonYear;
    }

    public String getFarmerFName() {
        return farmerFName;
    }

    public void setFarmerFName(String farmerFName) {
        this.farmerFName = farmerFName;
    }

    public String getFarmerLName() {
        return farmerLName;
    }

    public void setFarmerLName(String farmerLName) {
        this.farmerLName = farmerLName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getSeasonTerm() {
        return seasonTerm;
    }

    public void setSeasonTerm(String seasonTerm) {
        this.seasonTerm = seasonTerm;
    }

    public String getFarmerPhone() {
        return farmerPhone;
    }

    public void setFarmerPhone(String farmerPhone) {
        this.farmerPhone = farmerPhone;
    }

    public String getHarvestDate() {
        return harvestDate;
    }

    public void setHarvestDate(String harvestDate) {
        this.harvestDate = harvestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
