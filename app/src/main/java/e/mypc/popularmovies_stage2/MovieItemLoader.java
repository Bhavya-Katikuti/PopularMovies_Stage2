package e.mypc.popularmovies_stage2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

public class MovieItemLoader extends AsyncTaskLoader<String> {
    String movies;
    public MovieItemLoader(@NonNull Context context,String movies) {
        super(context);
        this.movies=movies;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return JsonUtils.loadJsonData(movies);
    }
}
