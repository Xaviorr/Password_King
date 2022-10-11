package com.onemorelvl.passwordking;

import android.app.Application;
import android.os.AsyncTask;
import android.telephony.VisualVoicemailService;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AccountRepository {

   private AccountDao mAccountDao;
   private LiveData<List<PasswordKingModel>> allAccounts;

   public AccountRepository(Application application) {
      AccountDatabase database = AccountDatabase.getInstance(application);
      mAccountDao = database.accountDoa();
      allAccounts = mAccountDao.getAllAccounts();
   }

   public void insert(PasswordKingModel account) {
      new InsertAccountAsyncTask(mAccountDao).execute(account);
   }

   public Completable update(PasswordKingModel account) {
      return mAccountDao.update(account)
              .subscribeOn(Schedulers.io()); }

   public Completable delete(PasswordKingModel account) {
      return mAccountDao.delete(account)
              .subscribeOn(Schedulers.io());
   }

   public LiveData<List<PasswordKingModel>> getAllAccounts() {
      return allAccounts;
   }

   private static class InsertAccountAsyncTask extends AsyncTask<PasswordKingModel, Void, Void> {
      private AccountDao accountDao;

      public InsertAccountAsyncTask(AccountDao accountDao) {
         this.accountDao = accountDao;
      }

      @Override
      protected Void doInBackground(PasswordKingModel... passwordKingModels) {
         accountDao.insert(passwordKingModels[0]);
         return null;
      }
   }

}
