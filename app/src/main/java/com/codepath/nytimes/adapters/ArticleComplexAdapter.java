package com.codepath.nytimes.adapters;

/**
 * Created by Saranu on 3/19/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.nytimes.R;
import com.codepath.nytimes.decorators.ItemClickSupport;
import com.codepath.nytimes.models.NYTArticle;
import com.codepath.nytimes.service.ChromeCustomTabService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.codepath.nytimes.properties.Properties.FIRST_MM_IMAGE;

/**
 * Created by Saranu on 3/16/17.
 */

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class ArticleComplexAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RecyclerView mRecyclerView;
    // Store a member variable for the contacts
    ChromeCustomTabService chromeCustomTabService;
    private List<NYTArticle> mArticles;
    // Store the context for easy access
    private Context mContext;

    private final int IMAGE = 0, NO_IMAGE = 1;

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Pass in the contact array into the constructor
    public ArticleComplexAdapter(Context context, List<NYTArticle> articles) {
        mArticles = articles;
        mContext = context;
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    //Returns the view type of the item at position for the purposes of view recycling.
    @Override
    public int getItemViewType(int position) {
        if (mArticles.get(position).getMultimedia()== null || mArticles.get(position).getNewsDesk() ==null){
            return NO_IMAGE;
        } else if(mArticles.get(position).getMultimedia().isEmpty() || mArticles.get(position).getNewsDesk().isEmpty()){
            return NO_IMAGE;
        } else
            return IMAGE;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
    }


    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View articleView;
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType) {
            case IMAGE:

                 articleView = inflater.inflate(R.layout.item_article, parent, false);
                 viewHolder = new ViewHolder(articleView);
                 break;
            case NO_IMAGE:
                 articleView = inflater.inflate(R.layout.item_article_no_image, parent, false);
                 viewHolder = new ViewHolderNoImage(articleView);
                 break;
            default:
                return null;

        }

        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener((recyclerView, position, v) -> ChromeCustomTabService.getInstance().launchChromeCustomTab(v, mArticles.get(position).getWebUrl(), mArticles.get(position).getArticleColor()));

        return viewHolder;

    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        NYTArticle Nytarticle = mArticles.get(position);

        switch (viewHolder.getItemViewType()) {
            case IMAGE:
                ArticleComplexAdapter.ViewHolder vh = (ArticleComplexAdapter.ViewHolder) viewHolder;
                configureViewHolder(vh,Nytarticle);
                break;
            case NO_IMAGE:
                ArticleComplexAdapter.ViewHolderNoImage vhImage = (ArticleComplexAdapter.ViewHolderNoImage) viewHolder;
                configureViewHolderNoImage(vhImage,Nytarticle);
                break;
            default:
                break;
        }
    }

    private void configureViewHolder(ViewHolder vh, NYTArticle nytarticle) {
        ImageView ivImage = vh.IvArticleImage;
        ivImage.setImageResource(0);
        if (nytarticle.getMultimedia() != null && !(nytarticle.getMultimedia().isEmpty())) {
            String url = nytarticle.getMultimedia().get(FIRST_MM_IMAGE).getUrl();
            Glide.with(mContext).load(url).placeholder(R.drawable.placeholder).
                    error(R.drawable.error).into(ivImage);
        }
        vh.tvArticleHeadline.setText(nytarticle.getHeadline().getMain());
        vh.tvArticleCategory.setText(nytarticle.getNewsDesk());
        vh.tvArticleCategory.setBackgroundColor(nytarticle.getArticleColor());
        vh.tvArticleSynopsis.setText(nytarticle.getSnippet());
    }

    private void configureViewHolderNoImage(ViewHolderNoImage vhImage, NYTArticle nytarticle) {
        vhImage.tvArticleHeadline.setText(nytarticle.getHeadline().getMain());
        vhImage.tvArticleSynopsis.setText(nytarticle.getSnippet());

    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivArticleImage) public ImageView IvArticleImage;
        @BindView(R.id.tvArticleHeadline) public TextView tvArticleHeadline;
        @BindView(R.id.tvArticleCategory) public TextView tvArticleCategory;
        @BindView(R.id.tvArticleSynopsis) public TextView tvArticleSynopsis;


         public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public ImageView getIvArticleImage() {
            return IvArticleImage;
        }

        public void setIvArticleImage(ImageView ivArticleImage) {
            IvArticleImage = ivArticleImage;
        }

        public TextView getTvArticleHeadline() {
            return tvArticleHeadline;
        }

        public void setTvArticleHeadline(TextView tvArticleHeadline) {
            this.tvArticleHeadline = tvArticleHeadline;
        }

        public TextView getTvArticleCategory() {
            return tvArticleCategory;
        }

        public void setTvArticleCategory(TextView tvArticleCategory) {
            this.tvArticleCategory = tvArticleCategory;
        }

        public TextView getTvArticleSynopsis() {
            return tvArticleSynopsis;
        }

        public void setTvArticleSynopsis(TextView tvArticleSynopsis) {
            this.tvArticleSynopsis = tvArticleSynopsis;
        }
    }



    public static class ViewHolderNoImage extends RecyclerView.ViewHolder {

        @BindView(R.id.tvArticleHeadline) public TextView tvArticleHeadline;
        @BindView(R.id.tvArticleSynopsis) public TextView tvArticleSynopsis;
        public ViewHolderNoImage(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public TextView getTvArticleHeadline() {
            return tvArticleHeadline;
        }

        public void setTvArticleHeadline(TextView tvArticleHeadline) {
            this.tvArticleHeadline = tvArticleHeadline;
        }

        public TextView getTvArticleSynopsis() {
            return tvArticleSynopsis;
        }

        public void setTvArticleSynopsis(TextView tvArticleSynopsis) {
            this.tvArticleSynopsis = tvArticleSynopsis;
        }

    }

}
