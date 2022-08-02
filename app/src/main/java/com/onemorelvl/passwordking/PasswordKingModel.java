package com.onemorelvl.passwordking;

import android.widget.ImageView;
import android.widget.TextView;

class PasswordKingModel {
   private int mImageView;
   private String tvCompanyName, tvUserName, tvPassword;

   public PasswordKingModel(int imageView, String tvCompanyName, String tvUserName, String tvPassword) {
      mImageView = imageView;
      this.tvCompanyName = tvCompanyName;
      this.tvUserName = tvUserName;
      this.tvPassword = tvPassword;
   }

   public int getImageView() {
      return mImageView;
   }

   public String getTvCompanyName() {
      return tvCompanyName;
   }

   public String getTvUserName() {
      return tvUserName;
   }

   public String getTvPassword() {
      return tvPassword;
   }
}
