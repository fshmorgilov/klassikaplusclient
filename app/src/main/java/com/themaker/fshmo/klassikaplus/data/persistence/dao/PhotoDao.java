package com.themaker.fshmo.klassikaplus.data.persistence.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.themaker.fshmo.klassikaplus.data.persistence.model.DbPhoto;
import io.reactivex.Single;

import java.util.List;

@Dao
public interface PhotoDao {

    @Query("SELECT * FROM photos")
    Single<List<DbPhoto>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DbPhoto> items);

    @Query("DELETE FROM PHOTOS")
    void deleteAll();

    @Query("SELECT * FROM PHOTOS WHERE id in (:id)")
    Single<DbPhoto> findById(String id);

    @Query("SELECT * FROM PHOTOS WHERE item_id in (:itemId)")
    Single<List<DbPhoto>> findByItemId(String itemId);

}
