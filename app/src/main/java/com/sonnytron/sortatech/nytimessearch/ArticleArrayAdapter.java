package com.sonnytron.sortatech.nytimessearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sonnytron.sortatech.nytimessearch.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sonnyrodriguez on 7/28/16.
 */
public class ArticleArrayAdapter extends RecyclerView.Adapter<ArticleHolder> {
    private List<Article> mArticles;
    private LayoutInflater mInflater;
    private Context mContext;

    public ArticleArrayAdapter(Context context, List<Article> articles) {
        mContext = context;
        mArticles = articles;
    }

    @Override
    public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.item_article_result, parent, false);
        return new ArticleHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(ArticleHolder holder, int position) {
        Article article = mArticles.get(position);
        holder.bindArticle(article);
    }

    @Override
    public int getItemCount() {
        if (mArticles != null) {
            return mArticles.size();
        } else {
            return 0;
        }
    }

    public void setArticles(List<Article> articles) {
        mArticles = articles;
    }
}


