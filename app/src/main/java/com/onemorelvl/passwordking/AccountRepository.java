package com.onemorelvl.passwordking;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AccountRepository {

    private final AccountDao mAccountDao;
    private final LiveData<List<PasswordKingModel>> allAccounts;

    public AccountRepository(Application application, String password) {
        AccountDatabase database = AccountDatabase.getInstance(application, password);
        mAccountDao = database.accountDoa();
        allAccounts = mAccountDao.getAllAccounts();
    }

    public Completable insert(PasswordKingModel account) {
        return mAccountDao.insert(account)
                .subscribeOn(Schedulers.io());
    }

    public Completable update(PasswordKingModel account) {
        return mAccountDao.update(account)
                .subscribeOn(Schedulers.io());
    }

    public Completable delete(PasswordKingModel account) {
        return mAccountDao.delete(account)
                .subscribeOn(Schedulers.io());
    }

    public LiveData<List<PasswordKingModel>> getAllAccountsASC() {
        return mAccountDao.getAllAccountsAsc();
    }

    public LiveData<List<PasswordKingModel>> getAllAccountsDesc() {
        return mAccountDao.getALlAccountsDesc();
    }

    public LiveData<List<PasswordKingModel>> getAllAccounts() {
        return allAccounts;
    }

}
