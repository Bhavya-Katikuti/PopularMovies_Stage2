package e.mypc.popularmovies_stage2.adapterclasses;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import e.mypc.popularmovies_stage2.DetailActivity;
import e.mypc.popularmovies_stage2.MainActivity;
import e.mypc.popularmovies_stage2.R;
import e.mypc.popularmovies_stage2.databaseclasses.FavMovies;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder>
{
    Context context;
    List<FavMovies> list;
    public static final String MOVIE_ID="id";
    public static final String MOVIE_TITLE="title";
    public static final String MOVIE_VOTE_AVERAGE="vote_average";
    public static final String MOVIE_POSTER_PATH="poster_path";
    public static final String MOVIE_RELEASE_DATE="release_date";
    public static final String MOVIE_OVER_VIEW="overview";


    public FavAdapter(MainActivity mainActivity, List<FavMovies> favMovieList) {
        this.context = mainActivity;
        this.list = favMovieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.grid, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Picasso.with(context).load(list.get(i).getFposter_path()).into(viewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.grid_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION)
                    {
                        Intent intent=new Intent(context, DetailActivity.class);
                        intent.putExtra(MOVIE_ID,list.get(position).getFid());
                        intent.putExtra(MOVIE_TITLE,list.get(position).getFtitle());
                        intent.putExtra(MOVIE_VOTE_AVERAGE,list.get(position).getFvote_average());
                        intent.putExtra(MOVIE_POSTER_PATH,list.get(position).getFposter_path());
                        intent.putExtra(MOVIE_RELEASE_DATE,list.get(position).getFrelease_date());
                        intent.putExtra(MOVIE_OVER_VIEW,list.get(position).getFoverview());
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
