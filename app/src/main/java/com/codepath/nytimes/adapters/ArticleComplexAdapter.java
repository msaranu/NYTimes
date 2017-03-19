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

        // Set item views based on your views and data model


        switch (viewHolder.getItemViewType()) {
            case IMAGE:
                ArticleComplexAdapter.ViewHolder vh = (ArticleComplexAdapter.ViewHolder) viewHolder;
                configureViewHolder(vh,position);
                break;
            case NO_IMAGE:
                ArticleComplexAdapter.ViewHolderNoImage vhImage = (ArticleComplexAdapter.ViewHolderNoImage) viewHolder;
                configureViewHolderNoImage(vhImage,position);
                break;
            default:
                break;
        }


    }

    private void configureViewHolder(ViewHolder vh, int position) {
        NYTArticle Nytarticle = mArticles.get(position);
        ImageView ivImage = vh.IvArticleImage;
        ivImage.setImageResource(0);
        if (Nytarticle.getMultimedia() != null && !(Nytarticle.getMultimedia().isEmpty())) {
            String url = Nytarticle.getMultimedia().get(0).getUrl();
            Glide.with(mContext).load(url).placeholder(R.drawable.placeholder).
                    error(R.drawable.error).into(ivImage);
        }
        vh.tvArticleHeadline.setText(Nytarticle.getHeadline().getMain());

        TextView tvCategory = vh.tvArticleCategory;
        tvCategory.setText(Nytarticle.getNewsDesk());
        tvCategory.setBackgroundColor(Nytarticle.getArticleColor());

        TextView tvSynopsis = vh.tvArticleSynopsis;
        tvSynopsis.setText(Nytarticle.getSnippet());
    }

    private void configureViewHolderNoImage(ViewHolderNoImage vhImage, int position) {
        NYTArticle Nytarticle = mArticles.get(position);

        vhImage.tvArticleHeadline.setText(Nytarticle.getHeadline().getMain());
        TextView tvSynopsisvhImage = vhImage.tvArticleSynopsis;
        tvSynopsisvhImage.setText(Nytarticle.getSnippet());

    }


//**************************VIEW HOLDERS ****************************//






    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        @BindView(R.id.ivArticleImage) public ImageView IvArticleImage;
        @BindView(R.id.tvArticleHeadline) public TextView tvArticleHeadline;
        @BindView(R.id.tvArticleCategory) public TextView tvArticleCategory;
        @BindView(R.id.tvArticleSynopsis) public TextView tvArticleSynopsis;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
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

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        @BindView(R.id.tvArticleHeadline) public TextView tvArticleHeadline;
        @BindView(R.id.tvArticleSynopsis) public TextView tvArticleSynopsis;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolderNoImage(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
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
