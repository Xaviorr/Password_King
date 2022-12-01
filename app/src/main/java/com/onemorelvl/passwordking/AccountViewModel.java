package com.onemorelvl.passwordking;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class AccountViewModel extends AndroidViewModel {
    private static final String TAG = "AccountViewModel";
    private final AccountRepository repository;
    private final LiveData<List<PasswordKingModel>> allAccounts;
    private final CompositeDisposable mDisposable = new CompositeDisposable();


    public AccountViewModel(@NonNull Application application, String password) {
        super(application);
        repository = new AccountRepository(application, password);
        allAccounts = repository.getAllAccounts();
    }

    public void insert(PasswordKingModel account) {
        mDisposable.add(repository.insert(account)
                .subscribe());
    }

    public void update(PasswordKingModel account) {
        mDisposable.add(repository.update(account)
                .subscribe());
    }

    public void delete(PasswordKingModel account) {
        mDisposable.add(repository.delete(account)
                .subscribe());
    }

    public LiveData<List<PasswordKingModel>> getAllAccountsAsc() {
        return repository.getAllAccountsASC();
    }

    public LiveData<List<PasswordKingModel>> getAllAccountsDesc() {
        return repository.getAllAccountsDesc();
    }

    public LiveData<List<PasswordKingModel>> getAllAccounts() {
        return allAccounts;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.clear();
        Log.d(TAG, "onCleared: ");
    }
}
