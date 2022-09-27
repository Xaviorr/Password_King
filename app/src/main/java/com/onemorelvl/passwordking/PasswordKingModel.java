package com.onemorelvl.passwordking;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "account_table")
public class PasswordKingModel {

   @PrimaryKey(autoGenerate = true)
   private int id;

   private int mImageView;
   private String mCompanyName, mUserName, mPassword;

   public PasswordKingModel(int imageView, String companyName, String userName, String password) {
      mImageView = imageView;
      this.mCompanyName = companyName;
      this.mUserName = userName;
      this.mPassword = password;
   }

   public int getId() { return id; }

   public void setId(int id) { this.id = id; }

   public int getImageView() {
      return mImageView;
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
