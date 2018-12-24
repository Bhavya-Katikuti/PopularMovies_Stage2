package e.mypc.popularmovies_stage2;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import e.mypc.popularmovies_stage2.adapterclasses.ReviewAdapter;
import e.mypc.popularmovies_stage2.adapterclasses.TrailerAdapter;
import e.mypc.popularmovies_stage2.databaseclasses.FavMovies;
import e.mypc.popularmovies_stage2.databaseclasses.FavViewModel;
import e.mypc.popularmovies_stage2.modelclasses.Reviews;
import e.mypc.popularmovies_stage2.modelclasses.Trailers;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    public int MovieID;
    ImageView imageView;
    TextView title, description, release, vote;
    String movtitle, movover, movvote, movrele, movpath;
    String author, content, name;
    TrailerAdapter trailerAdapter;
    ReviewAdapter reviewAdapter;
    RecyclerView recyclerViewreview, recyclerViewtrailer;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    MaterialFavoriteButton favoriteButton;

    public static final String MOVIE_TITLE = "title";
    public static final String MOVIE_VOTE_AVERAGE = "vote_average";
    public static final String MOVIE_POSTER_PATH = "poster_path";
    public static final String MOVIE_RELEASE_DATE = "release_date";
    public static final String MOVIE_OVER_VIEW = "overview";
    public static final String RESULTS = "results";
    public static final String REV_AUTHOR = "author";
    public static final String REV_CONTENT = "content";
    public static final String ID = "id";
    public static final String TRA_VIDEOS = "videos";
    public static final String REVIEWS = "reviews";
    public static final int TRAILER_ID = 1;
    public static final int REVIEW_ID = 2;
    public static final String BTKEY="trailers";
    public static final String BRKEY="reviews";
    public static final String YOUTUBE="youtube";
    public static final String SOURCE="source";
    public static final String NAME="name";

    FavViewModel favViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageView = findViewById(R.id.detail_img);
        title = findViewById(R.id.movie_title);
        description = findViewById(R.id.movie_desc);
        release = findViewById(R.id.movie_release);
        vote = findViewById(R.id.movie_vote_average);
        recyclerViewtrailer = findViewById(R.id.trailer_recycler);
        recyclerViewreview = findViewById(R.id.review_recycler);
        favoriteButton = findViewById(R.id.movie_fav_button);
        favViewModel = ViewModelProviders.of(this).get(FavViewModel.class);


        loadData();


        MovieID = getIntent().getIntExtra(ID, 0);
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Bundle bundle = new Bundle();
            bundle.putString(TRA_VIDEOS, BTKEY);
            getSupportLoaderManager().restartLoader(TRAILER_ID, bundle, this);
            bundle.putString(REVIEWS, BRKEY);
            getSupportLoaderManager().restartLoader(REVIEW_ID, bundle, this);
        }
        makeSelect();
        favoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                if (favorite) {
                    FavMovies favMovies = new FavMovies(MovieID, movtitle, movvote, movpath, movrele, movover);
                    favViewModel.inserviewlist(favMovies);
                    Toast.makeText(DetailActivity.this, R.string.mov_in, Toast.LENGTH_SHORT).show();

                } else {
                    FavMovies favMovies = new FavMovies(MovieID, movtitle, movvote, movpath, movrele, movover);
                    favViewModel.deleteFavMovies(favMovies);
                    Toast.makeText(DetailActivity.this, R.string.mov_del, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadData() {
        Intent intent = getIntent();
        if (intent.hasExtra(MOVIE_TITLE)) {

            movtitle = getIntent().getStringExtra(MOVIE_TITLE);
            movvote = getIntent().getStringExtra(MOVIE_VOTE_AVERAGE);
            movpath = getIntent().getStringExtra(MOVIE_POSTER_PATH);
            movover = getIntent().getStringExtra(MOVIE_OVER_VIEW);
            movrele = getIntent().getStringExtra(MOVIE_RELEASE_DATE);

        } else {
            finish();
        }
        setTitle(movtitle);
        Picasso.with(this).load(movpath).placeholder(R.drawable.camera_alert).into(imageView);
        vote.setText(movvote);
        release.setText(movrele);
        description.setText(movover);
        title.setText(movtitle);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        switch (i) {
            case TRAILER_ID:
                TrailerLoader trailerLoader = new TrailerLoader(this, bundle.getString(TRA_VIDEOS), MovieID);
                return trailerLoader;
            case REVIEW_ID:
                ReviewLoader reviewLoader = new ReviewLoader(this, bundle.getString(REVIEWS), MovieID);
                return reviewLoader;
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        int id = loader.getId();

        switch (id) {
            case TRAILER_ID:
                trailesjson(s);
                break;
            case REVIEW_ID:
                reviewjson(s);
                break;
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    public void trailesjson(String trail) {
        List<Trailers> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(trail);
            JSONArray jsonArray = jsonObject.getJSONArray(YOUTUBE);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject result = jsonArray.getJSONObject(i);
                Trailers trailers = new Trailers();
                trailers.setKey(result.getString(SOURCE));
                trailers.setName(result.getString(NAME));
                list.add(trailers);
                trailerAdapter = new TrailerAdapter(this, (ArrayList<Trailers>) list);
                recyclerViewtrailer.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
                recyclerViewtrailer.setAdapter(trailerAdapter);
                trailerAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    public void reviewjson(String revie) {
        List<Reviews> reviews1 = new ArrayList<>();
        try {
            JSONObject mainObject = new JSONObject(revie);
            JSONArray jsonArray = mainObject.getJSONArray(RESULTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                Reviews reviews = new Reviews();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                author = jsonObject.optString(REV_AUTHOR);
                content = jsonObject.optString(REV_CONTENT);
                reviews.setAuthor(jsonObject.getString(REV_AUTHOR));
                reviews.setContent(jsonObject.getString(REV_CONTENT));
                reviews1.add(reviews);
            }
            reviewAdapter = new ReviewAdapter(DetailActivity.this, reviews1);
            recyclerViewreview.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
            recyclerViewreview.setAdapter(reviewAdapter);
            reviewAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void makeSelect()
    {
        FavMovies movies=favViewModel.findMovie(MovieID);
        if (movies!=null)
        {
            favoriteButton.setFavorite(true);
        }
        else {
            favoriteButton.setFavorite(false);
        }
    }
}
