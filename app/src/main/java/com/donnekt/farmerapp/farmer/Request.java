package com.donnekt.farmerapp.farmer;

public class Request {
    int requestId, farmerId, seedId, seedQuantity, fertilizerId, fertilizerQuantity, pestId, pestQuantity;
    String paymentStatus, seasonYear, seasonTerm, farmerFName, farmerLName, reqStatus, reqDate, sName, sPrice, fName, fPrice, pName, pPrice;
    double finalAmount;

    public Request() {}

    public Request(int farmerId, int seedId, int seedQuantity, int fertilizerId, int fertilizerQuantity, int pestId, int pestQuantity, String paymentStatus, String seasonYear, String seasonTerm) {
        this.farmerId = farmerId;
        this.seedId = seedId;
        this.seedQuantity = seedQuantity;
        this.fertilizerId = fertilizerId;
        this.fertilizerQuantity = fertilizerQuantity;
        this.pestId = pestId;
        this.pestQuantity = pestQuantity;
        this.paymentStatus = paymentStatus;
        this.seasonTerm = seasonTerm;
        this.seasonYear = seasonYear;
    }

    public Request(int requestId, int farmerId, int seedId, int seedQuantity, int fertilizerId, int fertilizerQuantity, int pestId, int pestQuantity, String paymentStatus, String seasonYear, String seasonTerm) {
        this.requestId = requestId;
        this.farmerId = farmerId;
        this.seedId = seedId;
        this.seedQuantity = seedQuantity;
        this.fertilizerId = fertilizerId;
        this.fertilizerQuantity = fertilizerQuantity;
        this.pestId = pestId;
        this.pestQuantity = pestQuantity;
        this.paymentStatus = paymentStatus;
        this.seasonTerm = seasonTerm;
        this.seasonYear = seasonYear;
    }

    public Request(int requestId, int farmerId, String FN, String LN, int seedId, String sName, String sPrice, int seedQuantity, int fertilizerId, String fName, String fPrice, int fertilizerQuantity, int pestId, String pName, String pPrice, int pestQuantity, String paymentStatus, String reqStatus, String reqDate, String seasonYear, String seasonTerm) {
        this.requestId = requestId;
        this.farmerId = farmerId;
        this.farmerFName = FN;
        this.farmerLName = LN;
        this.seedId = seedId;
        this.seedQuantity = seedQuantity;
        this.sName = sName;
        this.sPrice = sPrice;
        this.fertilizerId = fertilizerId;
        this.fName = fName;
        this.fPrice = fPrice;
        this.fertilizerQuantity = fertilizerQuantity;
        this.pestId = pestId;
        this.pName = pName;
        this.pPrice = pPrice;
        this.pestQuantity = pestQuantity;
        this.paymentStatus = paymentStatus;
        this.reqStatus = reqStatus;
        this.reqDate = reqDate;
        this.seasonTerm = seasonTerm;
        this.seasonYear = seasonYear;
    }

    public Request(int requestId, int farmerId, double finalAmount) {
        this.requestId = requestId;
        this.farmerId = farmerId;
        this.finalAmount = finalAmount;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(int farmerId) {
        this.farmerId = farmerId;
    }

    public int getSeedId() {
        return seedId;
    }

    public void setSeedId(int seedId) {
        this.seedId = seedId;
    }

    public int getSeedQuantity() {
        return seedQuantity;
    }

    public void setSeedQuantity(int seedQuantity) {
        this.seedQuantity = seedQuantity;
    }

    public int getFertilizerId() {
        return fertilizerId;
    }

    public void setFertilizerId(int fertilizerId) {
        this.fertilizerId = fertilizerId;
    }

    public int getFertilizerQuantity() {
        return fertilizerQuantity;
    }

    public void setFertilizerQuantity(int fertilizerQuantity) {
        this.fertilizerQuantity = fertilizerQuantity;
    }

    public int getPestId() {
        return pestId;
    }

    public void setPestId(int pestId) {
        this.pestId = pestId;
    }

    public int getPestQuantity() {
        return pestQuantity;
    }

    public void setPestQuantity(int pestQuantity) {
        this.pestQuantity = pestQuantity;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getSeasonTerm() {
        return seasonTerm;
    }

    public void setSeasonTerm(String seasonTerm) {
        this.seasonTerm = seasonTerm;
    }

    public String getSeasonYear() {
        return seasonYear;
    }

    public void setSeasonYear(String seasonYear) {
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

    public String getReqStatus() {
        return reqStatus;
    }

    public void setReqStatus(String reqStatus) {
        this.reqStatus = reqStatus;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public String getfPrice() {
        return fPrice;
    }

    public void setfPrice(String fPrice) {
        this.fPrice = fPrice;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public String getpPrice() {
        return pPrice;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getsPrice() {
        return sPrice;
    }

    public void setsPrice(String sPrice) {
        this.sPrice = sPrice;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    // FINALIZATION

    public double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }
}
