package e.mypc.popularmovies_stage2.databaseclasses;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class FavViewModel extends AndroidViewModel {

    private FavRepository favRepository_view;
    private LiveData<List<FavMovies>> getAlllistViews;

    public FavViewModel(@NonNull Application application) {
        super(application);

        favRepository_view=new FavRepository(application);
        getAlllistViews=favRepository_view.getGetallrepList();
    }

    public  LiveData<List<FavMovies>> getGetAlllistViews()
    {
        return getAlllistViews;
    }

    public void inserviewlist(FavMovies favMovies)
    {
        favRepository_view.insertrep(favMovies);
    }

    public void deleteFavMovies(FavMovies favMovies)
    {
        favRepository_view.deleteData(favMovies);
    }

    public FavMovies findMovie(int id)
    {
        FavMovies favMovies=favRepository_view.searchMovie(id);
        return favMovies;
    }
}
