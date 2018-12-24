package e.mypc.popularmovies_stage2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

public class TrailerLoader extends AsyncTaskLoader<String> {
    private String trailer;
    private int id;
    public TrailerLoader(@NonNull Context context,String trailer,int id) {

        super(context);
        this.trailer=trailer;
        this.id=id;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return JsonUtils.loadTrailers(trailer,id);
    }
}
