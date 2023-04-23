package com.onemorelvl.passwordking;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "account_table")
public class PasswordKingModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private final char mIcon;
    private final String mCompanyName;
    private final String mUserName;
    private final String mPassword;

    public PasswordKingModel(char icon, String companyName, String userName, String password) {
        this.mIcon = icon;
        this.mCompanyName = companyName;
        this.mUserName = userName;
        this.mPassword = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getIcon() {
        return mIcon;
    }

    public String getCompanyName() {
        return mCompanyName;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getPassword() {
        return mPassword;
    }
}
