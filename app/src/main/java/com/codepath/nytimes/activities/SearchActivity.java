package com.codepath.nytimes.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.nytimes.R;
import com.codepath.nytimes.adapters.ArticleAdapter;
import com.codepath.nytimes.decorators.SpacesItemDecoration;
import com.codepath.nytimes.fragments.FilterDialogFragment;
import com.codepath.nytimes.listeners.EndlessRecyclerViewScrollListener;
import com.codepath.nytimes.models.FilterSettings;
import com.codepath.nytimes.models.NYTArticle;
import com.codepath.nytimes.models.NYTArticleResponse;
import com.codepath.nytimes.service.NYTimesClient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements FilterDialogFragment.FilterDialogListener{

    @BindView(R.id.etQuery)EditText etQuery;
    @BindView(R.id.btnSearch)Button btnSearch;
    @BindView(R.id.rvArticles)RecyclerView rvArticles;
    ArrayList<NYTArticle> articles;
    ArticleAdapter adapter;
    int FIRST_PAGE =0;
    FilterSettings fragFilterSettings = new FilterSettings();
    private EndlessRecyclerViewScrollListener scrollListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
         // Create adapter passing in the sample user data
        articles = new ArrayList<>();
        adapter = new ArticleAdapter(this, articles);
        // Attach the adapter to the recyclerview to populate items
        rvArticles.setAdapter(adapter);
        setUpClickListener();
        // Set layout manager to position the items
// First param is number of columns and second param is orientation i.e Vertical or Horizontal
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        // Attach the layout manager to the recycler view
        rvArticles.setLayoutManager(gridLayoutManager);        // That's all!
        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                //pageNumber = page;
                getArticles(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvArticles.addOnScrollListener(scrollListener);
        SpacesItemDecoration spacesitemdecor = new SpacesItemDecoration(10);
        rvArticles.addItemDecoration(spacesitemdecor);


    }

    private void setUpClickListener() {

        btnSearch.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                getArticles(FIRST_PAGE);
            }
        });
    }

    public void getArticles(int page) {
        {
            String query = etQuery.getText().toString();
            String BASE_URL = "https://api.nytimes.com/";
            if(page == FIRST_PAGE){
                articles.clear();
                scrollListener.resetState();
            }

            NYTimesClient NYTClient = new NYTimesClient();
         //  Call<NYTArticleResponse> call = NYTClient.NYTimesClientFactory().
         //           getArticlesFromServer("a5ac3eb802f44561b5fa0f398b07f65f");

         //   Call<NYTArticleResponse> call = NYTClient.NYTimesClientFactory().
                    //         getArticlesFromServer("a5ac3eb802f44561b5fa0f398b07f65f", new Integer(pageNumber),query,fragFilterSettings.beginDate,
                    //               fragFilterSettings.sortOrder, getQueryStringNewsDesk());

            Call<NYTArticleResponse> call = NYTClient.NYTimesClientFactory().
                          getArticlesFromServer("a5ac3eb802f44561b5fa0f398b07f65f", new Integer(page),query,fragFilterSettings.beginDate,
                                   fragFilterSettings.sortOrder, null);

                            call.enqueue(new Callback<NYTArticleResponse>(){
                @Override
                public void onResponse(Call<NYTArticleResponse> call, Response<NYTArticleResponse> response) {
                    Toast.makeText(SearchActivity.this, "This is my Toast SUCCESS!",
                            Toast.LENGTH_LONG).show();
                    NYTArticleResponse NYTAr = response.body();
                    if(NYTAr == null) {

                        Toast.makeText(SearchActivity.this, "No results matching search!",
                                Toast.LENGTH_LONG).show();
                    }else {
                        articles.addAll(NYTAr.getResponse().getArticles());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<NYTArticleResponse> call, Throwable t) {

                    Toast.makeText(SearchActivity.this, "This is my Toast FAILURE!",
                            Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private String getQueryStringNewsDesk() {

        String queryNewsDesk = fragFilterSettings.getNdArtsCheck()+","+fragFilterSettings.getNdFashionCheck()+","+fragFilterSettings.getNdSportsCheck();
        return queryNewsDesk;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onFilterSettings(View v){
        FragmentManager fm = getSupportFragmentManager();
        FilterDialogFragment fdf = new FilterDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("SettingObj", fragFilterSettings);
        fdf.setArguments(bundle);
        fdf.show(fm, "FRAGMENT FILTER");
    }

    @Override
    public void onFinishFilterDialog(FilterSettings fs) {
        fragFilterSettings = fs;
        getArticles(FIRST_PAGE);
    }


}
