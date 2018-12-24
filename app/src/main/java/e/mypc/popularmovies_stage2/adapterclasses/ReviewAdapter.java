package e.mypc.popularmovies_stage2.adapterclasses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import e.mypc.popularmovies_stage2.DetailActivity;
import e.mypc.popularmovies_stage2.R;
import e.mypc.popularmovies_stage2.modelclasses.Reviews;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    Context context;
    List<Reviews> reviewsArrayList;

    public ReviewAdapter(DetailActivity detailActivity, List<Reviews> reviewslist) {
        this.context=detailActivity;
        this.reviewsArrayList=reviewslist;
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.review,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.review.setText(reviewsArrayList.get(i).getAuthor());
        viewHolder.authour.setText(reviewsArrayList.get(i).getContent());

    }

    @Override
    public int getItemCount() {
        return reviewsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView authour,review;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            authour=itemView.findViewById(R.id.reviewAuthor);
            review=itemView.findViewById(R.id.reviewContent);
        }
    }
}
