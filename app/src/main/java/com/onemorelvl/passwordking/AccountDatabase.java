package com.onemorelvl.passwordking;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import io.reactivex.rxjava3.schedulers.Schedulers;

@Database(entities = {PasswordKingModel.class}, version = 1)
public abstract class AccountDatabase extends RoomDatabase {

    private static AccountDatabase instance;
    public abstract AccountDao accountDoa();
    private PasswordKingModel exampleAccount = new PasswordKingModel(
            R.drawable.ic_baseline_account_circle_48,
            "Example",
            "Username",
            "Password"
    );

    public static synchronized AccountDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AccountDatabase.class,"account_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
//            AccountDao mAccountDoa = instance.accountDoa();
//            mAccountDoa.insert(instance.exampleAccount)
//                    .subscribeOn(Schedulers.io())
//                    .subscribe();

            new PopulateDbAsyncTask(instance).execute();


        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private AccountDao accountDao;

        public PopulateDbAsyncTask(AccountDatabase db) {
            accountDao = db.accountDoa();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            accountDao.insert(new PasswordKingModel(R.drawable.ic_baseline_account_circle_48, "Discord", "franzz@charter.net", "Password"));
            accountDao.insert(new PasswordKingModel(R.drawable.ic_baseline_account_circle_48, "Pandora", "FStanley", "Password1"));
            accountDao.insert(new PasswordKingModel(R.drawable.ic_baseline_account_circle_48, "Disney+", "franst2b@gmail.com", "Password2"));
            return null;
        }
    }

}
