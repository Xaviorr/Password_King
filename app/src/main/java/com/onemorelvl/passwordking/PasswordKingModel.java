package com.onemorelvl.passwordking;

class PasswordKingModel {
   private int mImageView;
   private String mCompanyName, mUserName, mPassword;

   public PasswordKingModel(int imageView, String tvCompanyName, String tvUserName, String tvPassword) {
      mImageView = imageView;
      this.mCompanyName = tvCompanyName;
      this.mUserName = tvUserName;
      this.mPassword = tvPassword;
   }

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
