package com.onemorelvl.passwordking;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;

public class AccountViewModel extends AndroidViewModel {
   private AccountRepository repository;
   private LiveData<List<PasswordKingModel>> allAccounts;

   public AccountViewModel(@NonNull Application application) {
      super(application);
      repository = new AccountRepository(application);
      allAccounts = repository.getAllAccounts();
   }

   public void insert(PasswordKingModel account) {repository.insert(account);}

   public Completable update(PasswordKingModel account) {
      return repository.update(account);
   }

   public Completable delete(PasswordKingModel account) {
      return repository.delete(account);
   }

   public LiveData<List<PasswordKingModel>> getAllAccounts() {
      return allAccounts;
   }
}
