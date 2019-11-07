package com.example.assignment2;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BloodReading implements Serializable {
    private String bloodReadingID;
    private String date_time;
    private int systolic;
    private int dialostic;
    private String condition;
    private String username;

    // Getter Methods
    public BloodReading() {}

    public BloodReading(String username, String id, int sys, int dia, String time){
        setUsername(username);
        setBloodReadingID(id);
        setSystolic(sys);
        setDialostic(dia);
        setCondition();
        setDate_time(time);

    }
    public void setUsername(String s){this.username = s;};
    public String getUsername(){return username;};
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

    public static ArrayList<BloodReading> getMonthly(ArrayList<BloodReading> list) {
        ArrayList<BloodReading> monthlyAvg = new ArrayList<>();
        String firstDate;
        String secondDate;
        String userName;
        String user;
        int totalDialostic;
        int totalSystolic;
        int count;
        for(int i = 0; i < list.size(); i++) {
            firstDate = list.get(i).getDate_time().substring(0,7);
            userName = list.get(i).getUsername();
            count = 1;
            totalDialostic = list.get(i).getDialostic();
            totalSystolic = list.get(i).getSystolic();
            for (int j = i + 1; j < list.size(); j++) {
                secondDate = list.get(j).getDate_time().substring(0,7);
                user = list.get(j).getUsername();
                if (firstDate.equals(secondDate) && userName.equals(user)) {
                    totalDialostic += list.get(j).getDialostic();
                    totalSystolic += list.get(j).getSystolic();
                    count++;

                }
            }

            totalDialostic = totalDialostic / count;
            totalSystolic = totalSystolic /count;

            BloodReading b = new BloodReading(list.get(i).getUsername(), list.get(i).getBloodReadingID(),totalSystolic,totalDialostic, firstDate);
            monthlyAvg.add(b);
        }
        return monthlyAvg;
    }
}