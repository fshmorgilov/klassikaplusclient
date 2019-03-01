package com.themaker.fshmo.klassikaplus.data.persistence.dao;

import androidx.room.*;
import com.themaker.fshmo.klassikaplus.data.persistence.model.DbItem;
import io.reactivex.Flowable;
import io.reactivex.Single;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM ITEMS")
    Single<List<DbItem>> getAll();
    // TODO: 2/8/2019 Refactor to Flawable

    @Insert(onConflict = REPLACE)
    void insertAll(List<DbItem> items);

    @Query("DELETE FROM ITEMS")
    void deleteAll();

    @Query("SELECT * FROM ITEMS WHERE title IN (:name) OR name IN (:name)")
    Single<DbItem> findByName(String name);

    @Query("SELECT * FROM ITEMS WHERE id in (:id)")
    Single<DbItem> findById(String id);

    @Update(onConflict = REPLACE)
    void update(DbItem item);
}
