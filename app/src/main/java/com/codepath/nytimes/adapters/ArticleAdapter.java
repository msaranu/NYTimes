package com.codepath.nytimes.adapters;

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
public class ArticleAdapter extends
        RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    RecyclerView mRecyclerView;
    // Store a member variable for the contacts
    ChromeCustomTabService chromeCustomTabService;
    private List<NYTArticle> mArticles;
    // Store the context for easy access
    private Context mContext;
    // Pass in the contact array into the constructor
    public ArticleAdapter(Context context, List<NYTArticle> articles) {
        mArticles = articles;
        mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
    }
    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

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


    }

   // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View articleView = inflater.inflate(R.layout.item_article, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(articleView);

        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener((recyclerView, position, v) -> {
               ChromeCustomTabService.getInstance().launchChromeCustomTab(v,mArticles.get(position).getWebUrl(),mArticles.get(position).getArticleColor());
        });


    return viewHolder;

    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ArticleAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        NYTArticle Nytarticle = mArticles.get(position);

        // Set item views based on your views and data model

        ImageView ivImage = viewHolder.IvArticleImage;
        ivImage.setImageResource(0);
        if(Nytarticle.getMultimedia() !=null && !(Nytarticle.getMultimedia().isEmpty())) {
            String url = Nytarticle.getMultimedia().get(0).getUrl();
            Glide.with(mContext).load(url).into(ivImage);
        }
        viewHolder.tvArticleHeadline.setText(Nytarticle.getHeadline().getMain());

        TextView tvCategory = viewHolder.tvArticleCategory;
        tvCategory.setText(Nytarticle.getNewsDesk());
        tvCategory.setBackgroundColor(Nytarticle.getArticleColor());

        TextView tvSynopsis = viewHolder.tvArticleSynopsis;
        tvSynopsis.setText(Nytarticle.getSnippet());

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mArticles.size();
    }
}
