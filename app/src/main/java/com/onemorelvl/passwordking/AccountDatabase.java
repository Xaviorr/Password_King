package com.onemorelvl.passwordking;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SupportFactory;

import io.reactivex.rxjava3.schedulers.Schedulers;

@Database(entities = {PasswordKingModel.class}, version = 1)
public abstract class AccountDatabase extends RoomDatabase {

    private static AccountDatabase instance;

    public abstract AccountDao accountDoa();


    private final PasswordKingModel exampleAccount = new PasswordKingModel(
            'E',
            "Example",
            "Username",
            "Password"
    );

    public static synchronized AccountDatabase getInstance(Context context, String password) {
        if (instance == null) {
            SupportFactory supportFactory = new SupportFactory(SQLiteDatabase.getBytes(password.toCharArray()));
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AccountDatabase.class, "account_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .openHelperFactory(supportFactory)
                    .build();
        }
        return instance;
    }

    private static final RoomDatabase.Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            AccountDao mAccountDoa = instance.accountDoa();
            mAccountDoa.insert(instance.exampleAccount)
                    .subscribeOn(Schedulers.io())
                    .subscribe();


        }
    };

}
