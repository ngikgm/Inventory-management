package com.example.adprojectteam1;

public class Staff_representative {

    private int staff_representativeId;
    private String start_date;
    private String end_date;
    private String position;
    private Staff representative_staff_obj;

    public Staff_representative() {
    }

    public Staff_representative(String start_date, String end_date, String position, Staff representative_staff_obj) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.position = position;
        this.representative_staff_obj = representative_staff_obj;
    }

    public Staff_representative(int staff_representativeId, String start_date, String end_date, String position, Staff representative_staff_obj) {
        this.staff_representativeId = staff_representativeId;
        this.start_date = start_date;
        this.end_date = end_date;
        this.position = position;
        this.representative_staff_obj = representative_staff_obj;
    }

    public int getStaff_representativeId() {
        return staff_representativeId;
    }

    public void setStaff_representativeId(int staff_representativeId) {
        this.staff_representativeId = staff_representativeId;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Staff getRepresentative_staff_obj() {
        return representative_staff_obj;
    }

    public void setRepresentative_staff_obj(Staff representative_staff_obj) {
        this.representative_staff_obj = representative_staff_obj;
    }
}
