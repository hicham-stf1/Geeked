package com.aseds.geeked.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.aseds.geeked.Fragments.ArticleDetailFragment;
import com.aseds.geeked.Model.Article;
import com.aseds.geeked.R;


import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private final Context mContext;
    private final List<Article> mArticls;

    public PhotoAdapter(Context mContext, List<Article> mArticls) {
        this.mContext = mContext;
        this.mArticls = mArticls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.photo_item, parent, false);
        return  new PhotoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //get the clicked picture
        final Article post = mArticls.get(position);
        Picasso.get().load(post.getImageurl()).placeholder(R.mipmap.ic_launcher).into(holder.articleImage);

        holder.articleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit().putString("postid", post.getPostid()).apply();

                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ArticleDetailFragment()).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mArticls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView articleImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            articleImage = itemView.findViewById(R.id.post_image);
        }
    }

}
