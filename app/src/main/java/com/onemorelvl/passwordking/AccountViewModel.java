package com.onemorelvl.passwordking;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;

public class AccountViewModel extends AndroidViewModel {
   private static final String TAG = "AccountViewModel";
   private AccountRepository repository;
   private LiveData<List<PasswordKingModel>> allAccounts;
   private CompositeDisposable mDisposable = new CompositeDisposable();


   public AccountViewModel(@NonNull Application application) {
      super(application);
      repository = new AccountRepository(application);
      allAccounts = repository.getAllAccounts();
   }

   public void insert(PasswordKingModel account) {repository.insert(account);}

   public void update(PasswordKingModel account) {
      mDisposable.add(repository.update(account)
              .subscribe());
   }

   public void delete(PasswordKingModel account) {
      mDisposable.add(repository.delete(account)
              .subscribe());
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
