package com.themaker.fshmo.klassikaplus.data.persistence.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.themaker.fshmo.klassikaplus.data.persistence.model.DbCategory;
import io.reactivex.Single;

import java.util.List;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM CATEGORIES")
    Single<List<DbCategory>> getAll();

    @Query("SELECT * FROM CATEGORIES WHERE id in (:id)")
    Single<DbCategory> findById(Integer id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DbCategory> category);

}
