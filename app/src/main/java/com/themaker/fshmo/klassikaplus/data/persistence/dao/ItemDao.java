package com.themaker.fshmo.klassikaplus.data.persistence.dao;

import androidx.room.*;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.themaker.fshmo.klassikaplus.data.domain.ItemCategory;
import com.themaker.fshmo.klassikaplus.data.persistence.model.DbItem;
import io.reactivex.Flowable;
import io.reactivex.Single;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM ITEMS")
    Single<List<DbItem>> getAll();

    @Query("SELECT * FROM ITEMS WHERE novelty = :isNovelty")
    Flowable<List<DbItem>> getByNovelty(boolean isNovelty);

    @Query("SELECT * FROM ITEMS WHERE category = :categoryId")
    Flowable<List<DbItem>> getByCategory(Integer categoryId);

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
