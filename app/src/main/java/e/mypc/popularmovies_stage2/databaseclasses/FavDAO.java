package e.mypc.popularmovies_stage2.databaseclasses;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavDAO {

    @Insert
    void insertfav(FavMovies favMovies);

    @Query("SELECT * FROM FAVMOVIES")
    LiveData<List<FavMovies>> getAll();

    @Delete
    void deletemovieData(FavMovies movies);

    @Query("SELECT * FROM favmovies WHERE fid == :id")
    FavMovies isFavourite(int id);


}
