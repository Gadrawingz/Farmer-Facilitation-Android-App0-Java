package com.donnekt.farmerapp.farmer;

public class Farmer {
    int farmerId;
    String firstname, lastname, gender, dob, nationalId, telephone, area, password, status, province, district, sector, cell, village;

    public Farmer() { }

    public Farmer(int farmerId, String firstname, String lastname, String gender, String dob, String nationalId, String telephone, String area, String password, String status, String province, String district, String sector, String cell, String village) {
        this.farmerId = farmerId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.dob = dob;
        this.nationalId = nationalId;
        this.telephone = telephone;
        this.area = area;
        this.password = password;
        this.status = status;
        this.province = province;
        this.district = district;
        this.sector = sector;
        this.cell = cell;
        this.village = village;
    }

    public Farmer(int farmerId, String firstname, String lastname, String gender, String nationalId, String telephone, String status, String area, String province, String district, String sector, String cell, String village) {
        this.farmerId = farmerId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.nationalId = nationalId;
        this.telephone = telephone;
        this.status = status;
        this.area = area;
        this.province = province;
        this.district = district;
        this.sector = sector;
        this.cell = cell;
        this.village = village;
    }

    public Farmer(int farmerId, String firstname, String lastname, String gender, String nationalId, String telephone, String status, String area) {
        this.farmerId = farmerId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.nationalId = nationalId;
        this.telephone = telephone;
        this.status = status;
        this.area = area;
    }

    public int getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(int farmerId) {
        this.farmerId = farmerId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

}