package e.mypc.popularmovies_stage2.databaseclasses;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class FavRepository {

    private FavDAO rep_favdao;
    private LiveData<List<FavMovies>> getallrepList;

    public FavRepository(Application application) {

        FavRoomDatabase db=FavRoomDatabase.getDatabase(application);
        rep_favdao=db.favDAO();
        getallrepList=rep_favdao.getAll();
    }

    LiveData<List<FavMovies>> getGetallrepList()
    {
        return getallrepList;
    }

    public void insertrep(FavMovies favMovies)
    {
        new insertAsyntaskrep(rep_favdao).execute(favMovies);
    }

    public void deleteData(FavMovies favMovies)
    {
        new deleteAsyncTask(rep_favdao).execute(favMovies);
    }
    private class insertAsyntaskrep extends AsyncTask<FavMovies,Void,Void> {

        private FavDAO repfavdao;
        public insertAsyntaskrep(FavDAO rep_favdao) {
            repfavdao=rep_favdao;
        }

        @Override
        protected Void doInBackground(FavMovies... favMovies) {
            repfavdao.insertfav(favMovies[0]);
            return null;
        }
    }

    public class deleteAsyncTask extends AsyncTask<FavMovies,Void,Void>
    {
        FavDAO deleteFavDao;

        public deleteAsyncTask(FavDAO rep_favdao) {
            this.deleteFavDao=rep_favdao;
        }

        @Override
        protected Void doInBackground(FavMovies... favMovies)
        {
            deleteFavDao.deletemovieData(favMovies[0]);
            return null;
        }
    }

    public FavMovies searchMovie(int id)
    {
        FavMovies favMovies= rep_favdao.isFavourite(id);
        return favMovies;
    }
}
