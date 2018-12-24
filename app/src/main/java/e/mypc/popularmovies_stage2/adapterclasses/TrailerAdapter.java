package e.mypc.popularmovies_stage2.adapterclasses;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import e.mypc.popularmovies_stage2.DetailActivity;
import e.mypc.popularmovies_stage2.R;
import e.mypc.popularmovies_stage2.modelclasses.Trailers;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    ArrayList<Trailers> trailersArrayList;
    Context context;

    public TrailerAdapter(DetailActivity detailActivity, ArrayList<Trailers> trailerslist) {
        this.context=detailActivity;
        this.trailersArrayList=trailerslist;
    }

    @NonNull
    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.trailer,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapter.ViewHolder viewHolder, int i) {
        viewHolder.trailer.setText(trailersArrayList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return trailersArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView trailer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            trailer=itemView.findViewById(R.id.trailer_videotext);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    Trailers item=trailersArrayList.get(position);
                    String vid=trailersArrayList.get(position).getKey();
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + vid));
                    intent.putExtra("ID",vid);
                    context.startActivity(intent);
                }
            });
        }
    }
}
