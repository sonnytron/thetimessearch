package com.sonnytron.sortatech.nytimessearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sonnytron.sortatech.nytimessearch.models.Article;
import com.squareup.picasso.Picasso;

/**
 * Created by sonnyrodriguez on 7/31/16.
 */
public class ArticleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {



    private Article mArticle;

    private Context mContext;
    private articleCallback mCallback;
    private TextView mTitleTextView;
    private ImageView mImageView;

    public interface articleCallback {
        void onArticleSelected(Article article);
    }

    public void bindArticle(Article article) {
        mArticle = article;
        mTitleTextView.setText(mArticle.getHeadline());
        updateImageView();
    }

    private void updateImageView() {
        if (mArticle.getThumbnailUrl().length() > 0) {
            Picasso.with(mContext).load(mArticle.getThumbnailUrl()).into(mImageView);
        }
    }

    public ArticleHolder(Context context, View itemView) {
        super(itemView);
        mCallback = (articleCallback) context;
        mContext = context;
        itemView.setOnClickListener(this);
        mTitleTextView = (TextView)itemView.findViewById(R.id.tvTitle);
        mImageView = (ImageView) itemView.findViewById(R.id.ivImage);
    }

    @Override
    public void onClick(View v) {
        mCallback.onArticleSelected(mArticle);
    }

}
