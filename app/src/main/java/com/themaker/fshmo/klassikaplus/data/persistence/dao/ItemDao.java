package com.themaker.fshmo.klassikaplus.data.persistence.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.themaker.fshmo.klassikaplus.data.persistence.model.DbItem;
import io.reactivex.Flowable;
import io.reactivex.Single;

import java.util.List;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM ITEMS")
    Flowable<List<DbItem>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DbItem> items);

    @Query("DELETE FROM ITEMS")
    void deleteAll();

    @Query("SELECT * FROM ITEMS WHERE title IN (:name) OR name IN (:name)")
    Single<DbItem> findByName(String name);

    @Query("SELECT * FROM ITEMS WHERE id in (:id)")
    Single<DbItem> findById(String id);
}
