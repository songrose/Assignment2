package com.example.assignment2;
import java.text.SimpleDateFormat;

public class BloodReading {
    private String bloodReadingID;
    private String date_time;
    private int systolic;
    private int dialostic;
    private String condition;


    // Getter Methods
    public BloodReading() {}

    public BloodReading(String id, int sys, int dia, String time){
        setBloodReadingID(id);
        setSystolic(sys);
        setDialostic(dia);
        setCondition();
        setDate_time(time);

    }

    public String getBloodReadingID() {
        return bloodReadingID;
    }

    public String getDate_time() {
        return date_time;
    }

    public int getSystolic() {
        return systolic;
    }

    public int getDialostic() {
        return dialostic;
    }

    // Setter Methods

    public void setBloodReadingID(String bloodReadingID) {
        this.bloodReadingID = bloodReadingID;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public void setDialostic(int dialostic) {
        this.dialostic = dialostic;
    }

    public void setCondition(){
        condition = "Not Available";

        if (systolic == 0 || dialostic == 0){
            condition = "Not Available";
        } else if(systolic < 120 && dialostic < 180){
            condition = "Normal";
        } else if((120 <= systolic && systolic <= 129 ) && dialostic < 80){
            condition = "Elevated";
        } else if((130 <= systolic && systolic <= 139 ) || (80 <= dialostic  && dialostic <= 89 )){
            condition = "Stage 1";
        } else if((140 <= systolic && systolic <= 179 ) || (90 <= dialostic  && dialostic <= 119 )){
            condition = "Stage 2";
        } else if(140 <= systolic || 128 <= dialostic  ) {
            condition = "Hypertensive";
        }
    }
    public String getCondition(){
        return condition;
    }
}