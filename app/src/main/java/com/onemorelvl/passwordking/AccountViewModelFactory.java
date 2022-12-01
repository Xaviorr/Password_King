package com.onemorelvl.passwordking;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

class AccountViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private String mPassword;

    public AccountViewModelFactory(Application application, String password) {
        mApplication = application;
        mPassword = password;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AccountViewModel(mApplication, mPassword);
    }
}
