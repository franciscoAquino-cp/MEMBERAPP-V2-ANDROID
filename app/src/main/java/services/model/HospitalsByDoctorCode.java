package services.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by casjohnpaul on 5/8/2017.
 */

public class HospitalsByDoctorCode {

    @SerializedName("doctorCode")
    @Expose
    private String doctorCode;
    @SerializedName("docLname")
    @Expose
    private String docLname;
    @SerializedName("docFname")
    @Expose
    private String docFname;
    @SerializedName("docMname")
    @Expose
    private String docMname;
    @SerializedName("hospitalCode")
    @Expose
    private String hospitalCode;
    @SerializedName("hospitalName")
    @Expose
    private String hospitalName;
    @SerializedName("specDesc")
    @Expose
    private String specDesc;
    @SerializedName("specCode")
    @Expose
    private String specCode;
    @SerializedName("schedule")
    @Expose
    private String schedule;
    @SerializedName("room")
    @Expose
    private String room;
    @SerializedName("wtax")
    @Expose
    private String wtax;
    @SerializedName("gracePeriod")
    @Expose
    private Integer gracePeriod;
    @SerializedName("vat")
    @Expose
    private String vat;
    @SerializedName("specialRem")
    @Expose
    private String specialRem;
    @SerializedName("hospRemarks")
    @Expose
    private String hospRemarks;
    @SerializedName("roomBoard")
    @Expose
    private String roomBoard;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("remarks2")
    @Expose
    private String remarks2;
    @SerializedName("streetAddress")
    @Expose
    private String streetAddress;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("province")
    @Expose
    private String province;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("phoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("faxno")
    @Expose
    private String faxno;
    @SerializedName("contactPerson")
    @Expose
    private String contactPerson;

    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }

    public String getDocLname() {
        return docLname;
    }

    public void setDocLname(String docLname) {
        this.docLname = docLname;
    }

    public String getDocFname() {
        return docFname;
    }

    public void setDocFname(String docFname) {
        this.docFname = docFname;
    }

    public String getDocMname() {
        return docMname;
    }

    public void setDocMname(String docMname) {
        this.docMname = docMname;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getSpecDesc() {
        return specDesc;
    }

    public void setSpecDesc(String specDesc) {
        this.specDesc = specDesc;
    }

    public String getSpecCode() {
        return specCode;
    }

    public void setSpecCode(String specCode) {
        this.specCode = specCode;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getWtax() {
        return wtax;
    }

    public void setWtax(String wtax) {
        this.wtax = wtax;
    }

    public Integer getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(Integer gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getSpecialRem() {
        return specialRem;
    }

    public void setSpecialRem(String specialRem) {
        this.specialRem = specialRem;
    }

    public String getHospRemarks() {
        return hospRemarks;
    }

    public void setHospRemarks(String hospRemarks) {
        this.hospRemarks = hospRemarks;
    }

    public String getRoomBoard() {
        return roomBoard;
    }

    public void setRoomBoard(String roomBoard) {
        this.roomBoard = roomBoard;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks2() {
        return remarks2;
    }

    public void setRemarks2(String remarks2) {
        this.remarks2 = remarks2;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getFaxno() {
        return faxno;
    }

    public void setFaxno(String faxno) {
        this.faxno = faxno;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

}
