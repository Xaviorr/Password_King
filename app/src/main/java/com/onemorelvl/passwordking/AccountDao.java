package com.onemorelvl.passwordking;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PasswordKingModel account);

    @Update
    Completable update(PasswordKingModel account);

    @Delete
    Completable delete(PasswordKingModel account);

    @Query("SELECT * FROM account_table ORDER BY mCompanyName ASC")
    LiveData<List<PasswordKingModel>> getAllAccounts();

    @Query("SELECT * FROM account_table ORDER BY mCompanyName ASC")
    LiveData<List<PasswordKingModel>> getAllAccountsAsc();

    @Query("SELECT * FROM account_table ORDER BY mCompanyName DESC")
    LiveData<List<PasswordKingModel>> getALlAccountsDesc();

}
