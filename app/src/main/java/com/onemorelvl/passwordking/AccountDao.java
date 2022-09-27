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
    public void insert(PasswordKingModel account);

    @Update
    public Completable update(PasswordKingModel account);

    @Delete
    public Completable delete(PasswordKingModel account);

    @Query("SELECT * FROM account_table ORDER BY id ASC")
    LiveData<List<PasswordKingModel>> getAllAccounts();


}
