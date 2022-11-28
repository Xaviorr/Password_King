package com.onemorelvl.passwordking;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {PasswordKingModel.class}, version = 2)
public abstract class AccountDatabase extends RoomDatabase {

    private static AccountDatabase instance;

    public abstract AccountDao accountDoa();

    private final PasswordKingModel exampleAccount = new PasswordKingModel(
            'E',
            "Example",
            "Username",
            "Password"
    );

    public static synchronized AccountDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AccountDatabase.class, "account_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static final RoomDatabase.Callback roomCallback = new Callback() {
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
        private final AccountDao accountDao;

        public PopulateDbAsyncTask(AccountDatabase db) {
            accountDao = db.accountDoa();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            accountDao.insert(new PasswordKingModel('D', "Discord", "franzz@charter.net", "Password"));
            accountDao.insert(new PasswordKingModel('P', "Pandora", "FStanley", "Password1"));
            accountDao.insert(new PasswordKingModel('D', "Disney+", "franst2b@gmail.com", "Password2"));
            return null;
        }
    }

}
