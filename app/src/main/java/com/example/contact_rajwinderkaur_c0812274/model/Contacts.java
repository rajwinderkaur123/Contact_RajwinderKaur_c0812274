package com.example.contact_rajwinderkaur_c0812274.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact")
public class Contacts {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "first_Name")
    @NonNull
    private String first_Name;

    @ColumnInfo(name = "last_Name")
    private String last_Name;

    @ColumnInfo(name = "Email")
    private String email;

    @ColumnInfo(name = "Phone_number")
    private String phoneNumber;

    @ColumnInfo(name = "Address")
    private String Address;

    @Ignore
    public  Contacts(){}

    public Contacts(@NonNull String first_Name, String last_Name, String email,String phoneNumber, String Address) {
        this.first_Name = first_Name;
        this.last_Name = last_Name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.Address = Address;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getFirst_Name() {
        return first_Name;
    }

    public void setFirst_Name(@NonNull String first_Name) {
        this.first_Name = first_Name;
    }

    public String getLast_Name() {
        return last_Name;
    }

    public void setLast_Name(String last_Name) {
        this.last_Name = last_Name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}