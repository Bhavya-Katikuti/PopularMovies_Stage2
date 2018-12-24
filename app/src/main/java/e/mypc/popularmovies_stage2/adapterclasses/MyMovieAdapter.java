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

import java.util.ArrayList;

import e.mypc.popularmovies_stage2.DetailActivity;
import e.mypc.popularmovies_stage2.MainActivity;
import e.mypc.popularmovies_stage2.R;
import e.mypc.popularmovies_stage2.modelclasses.MoviesItems;

public class MyMovieAdapter extends RecyclerView.Adapter<MyMovieAdapter.ViewHolder> {
    Context context;
    ArrayList<MoviesItems> itemsArrayList;

    public static final String MOVIE_ID="id";
    public static final String MOVIE_TITLE="title";
    public static final String MOVIE_VOTE_AVERAGE="vote_average";
    public static final String MOVIE_POSTER_PATH="poster_path";
    public static final String MOVIE_RELEASE_DATE="release_date";
    public static final String MOVIE_OVER_VIEW="overview";

    public MyMovieAdapter(MainActivity mainActivity, ArrayList<MoviesItems> moviesItemsArrayList) {
        this.context=mainActivity;
        this.itemsArrayList=moviesItemsArrayList;
    }

    @NonNull
    @Override
    public MyMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.grid,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyMovieAdapter.ViewHolder viewHolder, int i) {
        Picasso.with(context).load(itemsArrayList.get(i).getPoster_path()).placeholder(R.drawable.camera_alert).into(viewHolder.movimg);

    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView movimg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movimg=itemView.findViewById(R.id.grid_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION)
                    {
                        MoviesItems itemclick=itemsArrayList.get(position);
                        Intent intent=new Intent(context, DetailActivity.class);
                        intent.putExtra("id",itemsArrayList.get(position).getId());
                        intent.putExtra(MOVIE_TITLE,itemsArrayList.get(position).getTitle());
                        intent.putExtra(MOVIE_VOTE_AVERAGE,itemsArrayList.get(position).getVote_average());
                        intent.putExtra(MOVIE_POSTER_PATH,itemsArrayList.get(position).getPoster_path());
                        intent.putExtra(MOVIE_RELEASE_DATE,itemsArrayList.get(position).getRelease_date());
                        intent.putExtra(MOVIE_OVER_VIEW,itemsArrayList.get(position).getOverview());
                        context.startActivity(intent);

                    }
                }
            });
        }
    }
}
