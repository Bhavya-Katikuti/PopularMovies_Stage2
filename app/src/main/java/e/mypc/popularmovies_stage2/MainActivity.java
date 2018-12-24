package e.mypc.popularmovies_stage2;


import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import e.mypc.popularmovies_stage2.adapterclasses.FavAdapter;
import e.mypc.popularmovies_stage2.adapterclasses.MyMovieAdapter;
import e.mypc.popularmovies_stage2.databaseclasses.FavMovies;
import e.mypc.popularmovies_stage2.databaseclasses.FavViewModel;
import e.mypc.popularmovies_stage2.modelclasses.MoviesItems;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    SaveRecyclerState recyclerView;
    ProgressDialog progressDialog;
    MyMovieAdapter myMovieAdapter;
    MoviesItems moviesItems;
    ArrayList<MoviesItems> moviesItemsArrayList;
    List<FavMovies> favMoviesList;
    FavViewModel favViewModel;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    AlertDialog.Builder builder;


    public static final String MOVIE_ID = "id";
    public static final String MOVIE_TITLE = "title";
    public static final String MOVIE_VOTE_AVERAGE = "vote_average";
    public static final String MOVIE_POSTER_PATH = "poster_path";
    public static final String MOVIE_RELEASE_DATE = "release_date";
    public static final String MOVIE_OVER_VIEW = "overview";
    public static final String POPULAR_MOVIES = "popular";
    public static final String TOP_RATED_MOVIES = "top_rated";
    public static final String FAVOURITE_MOVIES = "favorite";
    public static final String MOVIE_RESULTS = "results";
    public static final String SAVE_STATE="savestate";
    public static final String SHAREDKEY="BHA";
    public static final String PREFKEY="SHREE";
    public static final String TK="top_rated";
    public static final String PK="popular";
    public static final int BPID=1;
    public static final int BTID=2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);

        favViewModel = ViewModelProviders.of(this).get(FavViewModel.class);
        favMoviesList = new ArrayList<>();
        sharedPreferences = getSharedPreferences(PREFKEY,MODE_PRIVATE);
        editor=sharedPreferences.edit();
        builder = new AlertDialog.Builder(this);


        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            String value = sharedPreferences.getString(SHAREDKEY, null);
            if (value != null) {
                if (value.equalsIgnoreCase(POPULAR_MOVIES)) {
                    popularshow();

                } else if (value.equalsIgnoreCase(TOP_RATED_MOVIES)) {
                    topratedshowing();

                } else {
                    favLoad();

                }
            } else {
                popularshow();

            }
        }
        else {
          getSupportActionBar().setTitle(R.string.fav);
            favLoad();

            }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular:
                popularshow();
                editor.putString(SHAREDKEY, POPULAR_MOVIES);
                editor.apply();
                break;

            case R.id.top_rated:
                topratedshowing();
                editor.putString(SHAREDKEY, TOP_RATED_MOVIES);
                editor.apply();
                break;

            case R.id.favorites:
                favLoad();
                editor.putString(SHAREDKEY, FAVOURITE_MOVIES);
                editor.apply();
                break;
        }
        return true;
    }

    private void topratedshowing() {
        setProgressBar();
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Bundle bundle1 = new Bundle();
            bundle1.putString(POPULAR_MOVIES, TK);
            getSupportLoaderManager().initLoader(BTID, bundle1, this);
        } else {
            noInternet();
        }
    }

    private void popularshow() {
        setProgressBar();
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Bundle bundle = new Bundle();
            bundle.putString(POPULAR_MOVIES, PK);
            getSupportLoaderManager().initLoader(BPID, bundle, this);
        } else {
            noInternet();
        }
    }

    public void parseJsonData(String string) {
        try {
            moviesItemsArrayList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(string);
            JSONArray jsonArrayResult = jsonObject.getJSONArray(MOVIE_RESULTS);
            String mov_title, mov_voteaverage, mov_posterpath, mov_overview, mov_releasedate;
            int mov_id;
            for (int i = 0; i < jsonArrayResult.length(); i++) {
                JSONObject mainObject = jsonArrayResult.getJSONObject(i);
                mov_title = mainObject.optString(MOVIE_TITLE);
                mov_voteaverage = mainObject.optString(MOVIE_VOTE_AVERAGE);
                mov_posterpath = mainObject.optString(MOVIE_POSTER_PATH);
                mov_releasedate = mainObject.optString(MOVIE_RELEASE_DATE);
                mov_overview = mainObject.optString(MOVIE_OVER_VIEW);
                mov_id = Integer.parseInt(mainObject.optString(MOVIE_ID));

                moviesItems = new MoviesItems(mov_id, mov_title, mov_voteaverage, mov_posterpath, mov_releasedate, mov_overview);
                moviesItemsArrayList.add(moviesItems);

            }

            if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
            }

            myMovieAdapter = new MyMovieAdapter(this, moviesItemsArrayList);
            recyclerView.setAdapter(myMovieAdapter);
            myMovieAdapter.notifyDataSetChanged();
            progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProgressBar() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.wait));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        if (i == 1) {
            MovieItemLoader movieItemLoader =
                    new MovieItemLoader(this, bundle.getString(POPULAR_MOVIES));
            return movieItemLoader;
        } else {
            MovieItemLoader movieItemLoader =
                    new MovieItemLoader(this, bundle.getString(POPULAR_MOVIES));
            return movieItemLoader;
        }

    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        parseJsonData(s);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    public void favLoad() {

        favViewModel.getGetAlllistViews().observe(this, new Observer<List<FavMovies>>() {
            @Override
            public void onChanged(@Nullable List<FavMovies> favMovies) {
                favMoviesList = favMovies;
                if (favMoviesList.size()==0) {
                   // Toast.makeText(MainActivity.this, R.string.no_display, Toast.LENGTH_SHORT).show();
                   // popularshow();
                    AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle(R.string.no_display);
                    alert.setMessage(R.string.selct_movietype);
                    alert.setCancelable(false);
                    alert.setPositiveButton(R.string.positive_text, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alert.show();
                }
                else {
                    FavAdapter favAdapter = new FavAdapter(MainActivity.this, favMoviesList);
                    if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    } else {
                        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
                    }
                    recyclerView.setAdapter(favAdapter);

                }
            }
        });

    }

    public void noInternet() {
        builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.builder_title);
        builder.setMessage(R.string.builder_message);
        builder.setPositiveButton(R.string.positive_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        sharedPreferences=getSharedPreferences(PREFKEY,MODE_PRIVATE);
        if (sharedPreferences != null)
        {
            if (sharedPreferences.getString(SHAREDKEY,"").equals(POPULAR_MOVIES))
            {
                popularshow();
            }
            else if(sharedPreferences.getString(SHAREDKEY,"").equals(TOP_RATED_MOVIES))
            {
                topratedshowing();
            }
            else {
                favLoad();
            }
        }

    }
}
