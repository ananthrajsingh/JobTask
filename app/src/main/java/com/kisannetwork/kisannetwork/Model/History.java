package com.kisannetwork.kisannetwork.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ananthrajsingh on 17/09/18
 * Model class of history
 *
 * This is the Entity for the database of History.
 */
@Entity
public class History {

    @PrimaryKey(autoGenerate = true)
    private int serialNumber;
    private String name;
    private String otp;
    private long time;

    public History(String name, String otp, long time) {
        this.name = name;
        this.otp = otp;
        this.time = time;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
