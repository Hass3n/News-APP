package com.seniorsteps.news.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seniorsteps.news.API.Responses.ArticlesResponse.Article;
import com.seniorsteps.news.R;

import java.util.List;

public class ArticlesRecyclerAdapter extends RecyclerView.Adapter<ArticlesRecyclerAdapter.ViewHolder>{


    List<Article> articles;
    Context context;


    public ArticlesRecyclerAdapter(List<Article> articles,Context context) {
        this.articles =articles;
        this.context=context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item_article,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Article article = articles.get(position);

        holder.title.setText(article.getTitle());
        holder.image.setImageBitmap(null);
        Glide.with(context)
                .load(article.getUrlToImage())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView image;
        View cell;
        ViewHolder(View view){
            super(view);
            title= view.findViewById(R.id.title);
            image= view.findViewById(R.id.image);
            cell =view;
        }

    }

    public interface OnItemClickListener{
        void onClick(int position, Article radio);
    }
}
