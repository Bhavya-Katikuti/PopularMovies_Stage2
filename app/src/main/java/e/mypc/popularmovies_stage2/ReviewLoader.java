package e.mypc.popularmovies_stage2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

public class ReviewLoader extends AsyncTaskLoader<String> {
    private String rev;
    private int id;
    public ReviewLoader(@NonNull Context context,String rev,int id) {
        super(context);
        this.rev=rev;
        this.id=id;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return JsonUtils.loadreviews(rev,id);
    }
}
